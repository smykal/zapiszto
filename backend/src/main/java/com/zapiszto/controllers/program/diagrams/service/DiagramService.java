package com.zapiszto.controllers.program.diagrams.service;

import com.zapiszto.controllers.program.diagrams.dto.ExerciseStatsDto;
import com.zapiszto.controllers.program.diagrams.repository.DiagramRepository;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class DiagramService {
  @Autowired
  DiagramRepository diagramRepository;

  public List<ExerciseStatsDto> getExerciseStats(UUID mesocycleId) {
    List<Object[]> diagram = diagramRepository.getExerciseStatsByMesocycleId(mesocycleId);
    if (diagram.isEmpty()) {
      return Collections.emptyList();  // Zwracaj pustą listę zamiast singletonList
    }

    return diagram.stream()
        .map(result ->
            new ExerciseStatsDto(
                ((Number) result[0]).intValue(), // order id
                (String) result[1],              // category
                ((Number) result[2]).intValue(), // repetitions
                ((Number) result[3]).intValue()  // sets
            )
        )
        .collect(Collectors.toList());
  }
}
