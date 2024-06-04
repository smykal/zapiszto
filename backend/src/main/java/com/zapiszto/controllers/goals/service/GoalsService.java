package com.zapiszto.controllers.goals.service;

import com.zapiszto.controllers.goals.dto.NewGoalDto;
import com.zapiszto.controllers.goals.entity.GoalEntity;
import com.zapiszto.controllers.goals.repository.GoalsRepository;
import java.time.ZonedDateTime;
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
        .dictBodyParamsId(newGoalDto.getDictBodyParamId())
        .dictBodyTestId(newGoalDto.getDictBodyTestId())
        .dictUnitsId(newGoalDto.getDictUnitId())
        .action(newGoalDto.getAction())
        .value(newGoalDto.getValue())
        .goalDate(newGoalDto.getGoalDate())
        .insertDate(ZonedDateTime.now())
        .build();
    goalsRepository.save(goalEntity);
  }
}
