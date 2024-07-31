package com.zapiszto.controllers.program.programs.service;

import com.zapiszto.controllers.exercises.entity.ExerciseEntity;
import com.zapiszto.controllers.exercises.repository.ExerciseSessionRepository;
import com.zapiszto.controllers.program.macrocycle.entity.MacrocycleEntity;
import com.zapiszto.controllers.program.macrocycle.repository.MacrocycleRepository;
import com.zapiszto.controllers.program.mesocycle.entity.MesocycleEntity;
import com.zapiszto.controllers.program.mesocycle.repository.MesocycleRepository;
import com.zapiszto.controllers.program.microcycle.entity.MicrocycleEntity;
import com.zapiszto.controllers.program.microcycle.repository.MicrocycleRepository;
import com.zapiszto.controllers.program.programs.serializer.ProgramsSerializer;
import com.zapiszto.controllers.program.programs.dto.NewProgramDto;
import com.zapiszto.controllers.program.programs.dto.ProgramDto;
import com.zapiszto.controllers.program.programs.dto.ProgramNameDto;
import com.zapiszto.controllers.program.programs.entity.ProgramEntity;
import com.zapiszto.controllers.program.programs.repository.ProgramsRepository;
import com.zapiszto.controllers.program.programsDetails.entity.ProgramDetailsEntity;
import com.zapiszto.controllers.program.programsDetails.repository.ProgramDetailsRepository;
import com.zapiszto.controllers.program.sessions.entity.SessionEntity;
import com.zapiszto.controllers.program.sessions.repository.SessionRepository;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class ProgramsService {

  @Autowired
  ProgramsRepository programsRepository;

  @Autowired
  ProgramDetailsRepository programDetailsRepository;

  @Autowired
  ProgramsSerializer programsSerializer;

  @Autowired
  MacrocycleRepository macrocycleRepository;

  @Autowired
  MesocycleRepository mesocycleRepository;

  @Autowired
  MicrocycleRepository microcycleRepository;

  @Autowired
  SessionRepository sessionRepository;

  @Autowired
  ExerciseSessionRepository exerciseSessionRepository;

  public List<ProgramDto> getPrograms(long trainerId) {
    var allByTrainerId = programsRepository.getAllByTrainerId(trainerId);
    return allByTrainerId.stream().map(programsSerializer::convert)
        .collect(Collectors.toList());
  }

  @Transactional
  public void addProgram(NewProgramDto newProgramDto, long trainerId) {
    UUID uuid = UUID.fromString(newProgramDto.id());
    ProgramEntity programEntity = ProgramEntity.builder()
        .id(uuid)
        .name(newProgramDto.programName())
        .trainerId(trainerId)
        .insert_date(ZonedDateTime.now())
        .build();

    programsRepository.save(programEntity);

    ProgramDetailsEntity programDetailsEntity = ProgramDetailsEntity.builder()
        .id(uuid)
        .build();
    programDetailsRepository.save(programDetailsEntity);
  }

  @Transactional
  public void deleteProgram(UUID programId) {
    programsRepository.deleteById(programId);
    log.info("Program with ID {} and its related entities have been deleted", programId);
  }

  @Modifying
  @Transactional
  public void updateProgramName(String id, ProgramNameDto programNameDto) {
    ProgramEntity programEntity = programsRepository.findByUuid(UUID.fromString(id))
        .orElseThrow(() -> new RuntimeException("Program not found"));
    programEntity.setName(programNameDto.programName());
    programsRepository.save(programEntity);
  }

  @Transactional
  public void duplicateProgram(UUID id, String programName) {
    // Znajdź oryginalny program
    ProgramEntity originalProgram = programsRepository.findByUuid(id)
        .orElseThrow(() -> new RuntimeException("Program not found"));

    // Utwórz nowy program
    var programId = UUID.randomUUID();
    ProgramEntity newProgram = ProgramEntity.builder()
        .id(programId)
        .name(programName)
        .trainerId(originalProgram.getTrainerId())
        .insert_date(ZonedDateTime.now())
        .build();

    // Zapisz nowy program, żeby uzyskać jego ID
    programsRepository.saveAndFlush(newProgram);

    ProgramDetailsEntity programDetailsEntity = ProgramDetailsEntity.builder()
        .id(programId)
        .build();
    programDetailsRepository.save(programDetailsEntity);

    // Zduplikuj wszystkie makrocykle
    List<MacrocycleEntity> originalMacrocycles = originalProgram.getMacrocycles();
    List<MacrocycleEntity> newMacrocycles = new ArrayList<>();
    for (MacrocycleEntity originalMacrocycle : originalMacrocycles) {
      MacrocycleEntity newMacrocycle = MacrocycleEntity.builder()
          .id(UUID.randomUUID())
          .programId(newProgram.getId()) // Powiąż z nowym programem
          .duration(originalMacrocycle.getDuration())
          .durationLeft(originalMacrocycle.getDurationLeft())
          .build();

      // Zapisz nowy makrocykl
      newMacrocycles.add(newMacrocycle);
      // Zapisz makrocykl, żeby uzyskać jego ID
      macrocycleRepository.saveAndFlush(newMacrocycle);

      // Zduplikuj wszystkie mezocykl w makrocyklu
      List<MesocycleEntity> originalMesocycles = originalMacrocycle.getMesocycles();
      List<MesocycleEntity> newMesocycles = new ArrayList<>();
      for (MesocycleEntity originalMesocycle : originalMesocycles) {
        MesocycleEntity newMesocycle = MesocycleEntity.builder()
            .id(UUID.randomUUID())
            .macrocycleId(newMacrocycle.getId()) // Powiąż z nowym makrocyklem
            .duration(originalMesocycle.getDuration())
            .orderId(originalMesocycle.getOrderId())
            .comments(originalMesocycle.getComments())
            .dictMesocyclePhaseId(originalMesocycle.getDictMesocyclePhaseId())
            .label(originalMesocycle.getLabel())
            .build();
        // Zapisz nowy mezocykl
        newMesocycles.add(newMesocycle);
        // Zapisz mezocykl, żeby uzyskać jego ID
        MesocycleEntity mesocycleEntity = mesocycleRepository.saveAndFlush(newMesocycle);

        // Zduplikuj wszystkie mikrocykle w mezocyklu
        List<MicrocycleEntity> originalMicrocycles = originalMesocycle.getMicrocycles();
        List<MicrocycleEntity> newMicrocycles = new ArrayList<>();
        for (MicrocycleEntity originalMicrocycle : originalMicrocycles) {
          MicrocycleEntity newMicrocycle = MicrocycleEntity.builder()
              .id(UUID.randomUUID())
              .mesocycleId(mesocycleEntity.getId()) // Powiąż z nowym mezocykl
              .orderId(originalMicrocycle.getOrderId())
              .share(false)
              .build();

          // Zapisz nowy mikrocykl
          newMicrocycles.add(newMicrocycle);
          // Zapisz mikrocykl, żeby uzyskać jego ID
          MicrocycleEntity microcycleEntity = microcycleRepository.saveAndFlush(newMicrocycle);

          // Zduplikuj wszystkie sesje w mikrocyklu
          List<SessionEntity> originalSessions = originalMicrocycle.getSessions();
          List<SessionEntity> newSessions = new ArrayList<>();
          for (SessionEntity originalSession : originalSessions) {
            SessionEntity newSession = SessionEntity.builder()
                .id(UUID.randomUUID())
                .microcycleId(microcycleEntity.getId()) // Powiąż z nowym mikrocyklem
                .orderId(originalSession.getOrderId())
                .label(originalSession.getLabel())
                .build();

            // Zapisz nową sesję
            newSessions.add(newSession);
            // Zapisz sesję, żeby uzyskać jej ID
            SessionEntity sessionEntity = sessionRepository.saveAndFlush(newSession);

            // Zduplikuj wszystkie ćwiczenia w sesji
            List<ExerciseEntity> originalExercises = originalSession.getExercises();
            List<ExerciseEntity> newExercises = new ArrayList<>();
            for (ExerciseEntity originalExercise : originalExercises) {
              ExerciseEntity newExercise = ExerciseEntity.builder()
                  .id(UUID.randomUUID())
                  .sessionId(sessionEntity.getId()) // Powiąż z nową sesją
                  .quantity(originalExercise.getQuantity())
                  .volume(originalExercise.getVolume())
                  .notes(originalExercise.getNotes())
                  .orderNumber(originalExercise.getOrderNumber())
                  .restTime(originalExercise.getRestTime())
                  .tempo(originalExercise.getTempo())
                  .dictSessionPartId(originalExercise.getDictSessionPartId())
                  .dictExerciseId(originalExercise.getDictExerciseId())
                  .dictQuantityTypeId(originalExercise.getDictQuantityTypeId())
                  .dictUnitId(originalExercise.getDictUnitId())
                  .sets(originalExercise.getSets())
                  .dictEquipmentId(originalExercise.getDictEquipmentId())
                  .equipmentAttribute(originalExercise.getEquipmentAttribute())
                  .weightPerSide(originalExercise.getWeightPerSide())
                  .duration(originalExercise.getDuration())
                  .build();

              newExercises.add(newExercise);
              // Zapisz nowe ćwiczenie
              exerciseSessionRepository.saveAndFlush(newExercise);
            }
            newSession.setExercises(newExercises);
          }
          newMicrocycle.setSessions(newSessions);
        }
        newMesocycle.setMicrocycles(newMicrocycles);
      }
      newMacrocycle.setMesocycles(newMesocycles);
    }
    newProgram.setMacrocycles(newMacrocycles);

    // Zapisz nowy program i powiązane jednostki w bazie danych
    programsRepository.saveAndFlush(newProgram);
  }

}
