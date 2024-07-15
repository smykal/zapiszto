package com.zapiszto.controllers.goals.service;

import com.zapiszto.controllers.goals.dto.GoalDetailsDto;
import com.zapiszto.controllers.goals.dto.GoalDto;
import com.zapiszto.controllers.goals.dto.NewGoalDto;
import com.zapiszto.controllers.goals.entity.GoalEntity;
import com.zapiszto.controllers.goals.repository.GoalsRepository;
import com.zapiszto.controllers.goals.serializer.GoalsSerializer;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GoalsService {

  @Autowired
  GoalsRepository goalsRepository;

  public void addGoal(NewGoalDto newGoalDto) {
    GoalEntity goalEntity = GoalEntity.builder()
        .id(newGoalDto.getId())
        .clientId(newGoalDto.getClientId())
        .dictBodyParamsId(newGoalDto.getDictBodyParamId() > 0 ? newGoalDto.getDictBodyParamId() : null)
        .dictBodyTestId(Long.valueOf(newGoalDto.getDictBodyTestId() > 0 ? newGoalDto.getDictBodyTestId() : null))
        .dictUnitsId(newGoalDto.getDictUnitId() > 0 ? newGoalDto.getDictUnitId() : null)
        .action(newGoalDto.getAction())
        .value(newGoalDto.getValue())
        .goalDate(newGoalDto.getGoalDate())
        .insertDate(ZonedDateTime.now())
        .build();
    goalsRepository.save(goalEntity);
  }

  public List<GoalDto> getGoals(UUID clientId) {
    List<GoalEntity> goalsByClientId = goalsRepository.getGoalsByClientId(clientId);

    return goalsByClientId.stream()
        .map(GoalsSerializer::convert)
        .collect(Collectors.toList());
  }

  public List<GoalDetailsDto> getGoalDetails(UUID clientId) {
    List<Object[]> goalDetailsByClientId = goalsRepository.findGoalDetailsByClientId(clientId);

    if (goalDetailsByClientId.isEmpty()) {
      return Collections.singletonList(new GoalDetailsDto(
          clientId,  // Możesz zostawić clientId jako wartość
          "",  // bodyParamName
          "",  // bodyTestName
          "",  // unitName
          "",  // action
          ""   // value
      ));
    }

    return goalDetailsByClientId.stream()
        .map(result ->
            new GoalDetailsDto(
                (UUID) result[0], // clientId
                (String) result[1], // bodyParamName
                (String) result[2], // bodyTestName
                (String) result[3], // unitName
                (String) result[4], // action
                (String) result[5]  // value
            )
        )
        .collect(Collectors.toList());
  }
}
