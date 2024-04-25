package com.bezkoder.spring.security.postgresql.controllers.bodyParams.service;

import com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto.BmrDto;
import com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto.CPFDto;
import com.bezkoder.spring.security.postgresql.controllers.bodyParams.entity.BodyParamsEntity;
import com.bezkoder.spring.security.postgresql.controllers.bodyParams.repository.CPFBalanseRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CPFBalanseService {

  @Autowired
  private CPFBalanseRepository cpfBalanseRepository;

  @Autowired
  BmrService bmrService;

  public List<List<CPFDto>> getCPF(long userId) {
    List<List<CPFDto>> CPFDtos = new ArrayList();
    BodyParamsEntity actualWeightForUser = cpfBalanseRepository.getActualWeightForUser(userId);
    double weight = Double.parseDouble(actualWeightForUser.getValue());
    List<BmrDto> bmrByUserId = bmrService.getBmrByUserId(userId);

    List<CPFDto> cpfForReduction = getCPFforReduction(
        weight,
        bmrByUserId
    );
    List<CPFDto> cpfForRegularTime = getCPFforRegularTime(
        weight,
        bmrByUserId
    );
    List<CPFDto> cpfForMass = getCPFforMass(
        weight,
        bmrByUserId
    );

    CPFDtos.add(cpfForReduction);
    CPFDtos.add(cpfForRegularTime);
    CPFDtos.add(cpfForMass);
    log.info("get all body parameters for user: {} ", userId);

    return CPFDtos;
  }

  private List<CPFDto> getCPFforMass(
      double weight,
      List<BmrDto> bmrByUserId
  ) {
    List<CPFDto> list = new ArrayList();
    String tittle = "mass time";
    for (BmrDto bmr :
        bmrByUserId) {
      String activity_level = bmr.getCategory();
      double kcalRangeMin = bmr.getBmr() * 1.15;
      double kcalRangeMax = bmr.getBmr() * 1.2;

      double carbohydrateGramMin = weight * 5;
      double carbohydrateGramMax = weight * 7;

      double proteinGramMin = weight * 1.4;
      double proteinGramMax = weight * 1.8;

      double fatGramMin = (kcalRangeMin * 0.2) / 9;
      double fatGramMax = (kcalRangeMax * 0.33) / 9;

      list.add(
          CPFDto.builder()
              .activity_level(activity_level)
              .tittle(tittle)
              .kcalMin(kcalRangeMin)
              .kcalMax(kcalRangeMax)
              .carbohydrateMin(carbohydrateGramMin)
              .carbohydrateMax(carbohydrateGramMax)
              .proteinMin(proteinGramMin)
              .proteinMax(proteinGramMax)
              .fatMin(fatGramMin)
              .fatMax(fatGramMax)
              .build()
      );
    }
    return list;
  }

  private List<CPFDto> getCPFforRegularTime(
      double weight,
      List<BmrDto> bmrByUserId
  ) {
    List<CPFDto> list = new ArrayList();
    String tittle = "regular time";
    for (BmrDto bmr :
        bmrByUserId) {
      String activity_level = bmr.getCategory();
      double kcalRangeMin = bmr.getBmr() * 0.95;
      double kcalRangeMax = bmr.getBmr() * 1.05;

      double carbohydrateGramMin = (kcalRangeMin * 0.4) / 4;
      double carbohydrateGramMax = (kcalRangeMax * 0.65) / 4;

      double proteinGramMin = weight * 1.2;
      double proteinGramMax = weight * 2.0;

      double fatGramMin = (kcalRangeMin - (carbohydrateGramMin * 4) - (proteinGramMin * 4)) / 9;
      double fatGramMax = (kcalRangeMax - (carbohydrateGramMax * 4) - (proteinGramMax * 4)) / 9;

      list.add(
          CPFDto.builder()
              .activity_level(activity_level)
              .tittle(tittle)
              .kcalMin(kcalRangeMin)
              .kcalMax(kcalRangeMax)
              .carbohydrateMin(carbohydrateGramMin)
              .carbohydrateMax(carbohydrateGramMax)
              .proteinMin(proteinGramMin)
              .proteinMax(proteinGramMax)
              .fatMin(fatGramMin)
              .fatMax(fatGramMax)
              .build()
      );
    }
    return list;
  }

  private List<CPFDto> getCPFforReduction(
      double weight,
      List<BmrDto> bmrByUserId
  ) {
    List<CPFDto> list = new ArrayList();
    String tittle = "reduction time";
    for (BmrDto bmr :
        bmrByUserId) {
      String activity_level = bmr.getCategory();
      double kcalRangeMin = bmr.getBmr() * 0.85;
      double kcalRangeMax = bmr.getBmr() * 0.9;

      double carbohydrateGramMin = weight * 5;
      double carbohydrateGramMax = weight * 7;

      double proteinGramMin = weight * 1.6;
      double proteinGramMax = weight * 2.0;

      double fatGramMin = (kcalRangeMin - (carbohydrateGramMin * 4) - (proteinGramMin * 4)) / 9;
      double fatGramMax = (kcalRangeMax - (carbohydrateGramMax * 4) - (proteinGramMax * 4)) / 9;

      list.add(
          CPFDto.builder()
              .activity_level(activity_level)
              .tittle(tittle)
              .kcalMin(kcalRangeMin)
              .kcalMax(kcalRangeMax)
              .carbohydrateMin(carbohydrateGramMin)
              .carbohydrateMax(carbohydrateGramMax)
              .proteinMin(proteinGramMin)
              .proteinMax(proteinGramMax)
              .fatMin(fatGramMin)
              .fatMax(fatGramMax)
              .build()
      );
    }


    return list;
  }
}
