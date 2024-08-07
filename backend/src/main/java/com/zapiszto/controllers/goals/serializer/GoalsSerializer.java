package com.zapiszto.controllers.goals.serializer;

import com.zapiszto.controllers.common.SerializerCommon;
import com.zapiszto.controllers.goals.dto.GoalDto;
import com.zapiszto.controllers.goals.entity.GoalEntity;

public class GoalsSerializer implements SerializerCommon {

  public static GoalDto convert(GoalEntity goalEntity) {
    return new GoalDto(
        goalEntity.getId(),
        goalEntity.getClientId(),
        getGoalType(goalEntity),
        getDictBodyParam(goalEntity),
        getBodyTestType(goalEntity),
        getDictBodyTest(goalEntity),
        getUnitType(goalEntity),
        getDictUnit(goalEntity),
        goalEntity.getAction(),
        goalEntity.getValue(),
        goalEntity.getGoalDate(),
        goalEntity.getInsertDate()
    );
  }


  private static String getDictUnit(GoalEntity goalEntity) {
    if (goalEntity.getDictUnitsEntity()
        .getDictUnitsBasicEntity() != null) {
      return goalEntity.getDictUnitsEntity()
          .getDictUnitsBasicEntity()
          .getName()
          .get("en");
    } else {
      return goalEntity.getDictUnitsEntity()
          .getDictUnitsPerUserEntity()
          .getName()
          .get("en");
    }
  }

  private static String getDictBodyTest(GoalEntity goalEntity) {
    if (goalEntity.getDictBodyTestEntity() != null) {
      if (goalEntity.getDictBodyTestEntity()
          .getDictBodyTestBasicEntity() != null) {
        return goalEntity.getDictBodyTestEntity()
            .getDictBodyTestBasicEntity()
            .getName().get("en");
      } else {
        return goalEntity.getDictBodyTestEntity()
            .getDictBodyTestPerUserEntity()
            .getName().get("en");
      }
    } else {return "";}

  }

  private static String getDictBodyParam(GoalEntity goalEntity) {
    if (getGoalType(goalEntity).equals(BODY_PARAM)) {
      if (goalEntity.getDictBodyParamsEntity() != null) {
        return goalEntity.getDictBodyParamsEntity()
            .getName();
      } else {return "";}
    } else {return "";}

  }

  private static String getGoalType(GoalEntity goalEntity) {
    if (goalEntity.getDictBodyParamsEntity() != null) {
      return BODY_PARAM;
    } else {
      return BODY_TEST;
    }
  }

  private static String getBodyTestType(GoalEntity goalEntity) {
    if (getGoalType(goalEntity).equals(BODY_TEST)) {
      if (goalEntity.getDictBodyTestEntity()
          .getDictBodyTestBasicEntity() != null) {
        return BASIC;
      } else {
        return PER_USER;
      }
    } else {
      return "";
    }
  }

  private static String getUnitType(GoalEntity goalEntity) {
    if (goalEntity.getDictUnitsEntity()
        .getDictUnitsBasicEntity() != null) {
      return BASIC;
    } else {
      return PER_USER;
    }
  }
}
