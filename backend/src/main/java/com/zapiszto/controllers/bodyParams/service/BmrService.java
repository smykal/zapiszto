package com.zapiszto.controllers.bodyParams.service;

import com.zapiszto.controllers.bodyParams.dto.BmrCategory;
import com.zapiszto.controllers.bodyParams.dto.BmrDto;
import com.zapiszto.controllers.bodyParams.repository.BmrRepository;
import com.zapiszto.controllers.userSex.repository.UserSexRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class BmrService {

  @Autowired
  BmrRepository bmrRepository;

  @Autowired
  UserSexRepository userSexRepository;

  public List<BmrDto> getBmrByUserId(Long userId) {
    String bmrData = bmrRepository.getBmrData(userId);
    String[] bmrDataTable = bmrData.split(",");
    List<BmrDto> bmrDtos = new ArrayList<>();

    double CONST = getConstForUser(userId);

    for (BmrCategory category : BmrCategory.values()) {
      String[] insert_date = bmrDataTable[1].split(" ");
      double weight = Double.parseDouble(bmrDataTable[3]);
      double height = Double.parseDouble(bmrDataTable[4]);
      double age = Double.parseDouble(bmrDataTable[2]);
      double bmr = countBMR(
          CONST,
          weight,
          height,
          age
      ) * category.getActivityCoefficient();
      BmrDto bmrDto = BmrDto.builder()
          .userId(userId)
          .insert_date(insert_date[0])
          .bmr((int) Math.round(bmr))
          .category(category.getDescriptionEN())
          .build();

      bmrDtos.add(bmrDto);
    }
    return bmrDtos;
  }

  private double countBMR(
      double CONST,
      double weight,
      double height,
      double age
  ) {
    return 10 * weight + 6.25 * height - 5 * age + CONST;
  }

  private double getConstForUser(Long userId) {
    String genderByUserId = userSexRepository.findGenderByUserId(userId);
    return genderByUserId.equalsIgnoreCase("Female") ? -161 : 5;
  }
}
