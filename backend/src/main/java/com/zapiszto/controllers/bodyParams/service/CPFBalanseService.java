package com.zapiszto.controllers.bodyParams.service;

import com.zapiszto.controllers.bodyParams.dto.BmrDto;
import com.zapiszto.controllers.bodyParams.dto.CPFDto;
import com.zapiszto.controllers.bodyParams.entity.BodyParamsEntity;
import com.zapiszto.controllers.bodyParams.repository.CPFBalanseRepository;
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
    List<List<CPFDto>> CPFDtos = new ArrayList<>();
    BodyParamsEntity actualWeightForUser = cpfBalanseRepository.getActualWeightForUser(userId);
    double weight = Double.parseDouble(actualWeightForUser.getValue());
    List<BmrDto> bmrByUserId = bmrService.getBmrByUserId(userId);

    List<CPFDto> cpfForReduction = getCPFforReduction(weight, bmrByUserId);
    List<CPFDto> cpfForRegularTime = getCPFforRegularTime(weight, bmrByUserId);
    List<CPFDto> cpfForMass = getCPFforMass(weight, bmrByUserId);

    CPFDtos.add(cpfForReduction);
    CPFDtos.add(cpfForRegularTime);
    CPFDtos.add(cpfForMass);
    log.info("get all body parameters for user: {} ", userId);

    return CPFDtos;
  }

  private List<CPFDto> getCPFforMass(double weight, List<BmrDto> bmrByUserId) {
    List<CPFDto> list = new ArrayList<>();
    String tittle = "mass time";
    for (BmrDto bmr : bmrByUserId) {
      String activity_level = bmr.getCategory();
      double kcalRangeMin = round(bmr.getBmr() * 1.15, 2);
      double kcalRangeMax = round(bmr.getBmr() * 1.2, 2);

      double carbohydrateGramMin = round(weight * 5, 2);
      double carbohydrateGramMax = round(weight * 7, 2);

      double proteinGramMin = round(weight * 1.4, 2);
      double proteinGramMax = round(weight * 1.8, 2);

      double fatGramMin = round((kcalRangeMin * 0.2) / 9, 2);
      double fatGramMax = round((kcalRangeMax * 0.33) / 9, 2);

      list.add(CPFDto.builder()
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
          .build());
    }
    return list;
  }

  private List<CPFDto> getCPFforRegularTime(double weight, List<BmrDto> bmrByUserId) {
    List<CPFDto> list = new ArrayList<>();
    String tittle = "regular time";
    for (BmrDto bmr : bmrByUserId) {
      String activity_level = bmr.getCategory();
      double kcalRangeMin = round(bmr.getBmr() * 0.95, 2);
      double kcalRangeMax = round(bmr.getBmr() * 1.05, 2);

      double carbohydrateGramMin = round((kcalRangeMin * 0.4) / 4, 2);
      double carbohydrateGramMax = round((kcalRangeMax * 0.65) / 4, 2);

      double proteinGramMin = round(weight * 1.2, 2);
      double proteinGramMax = round(weight * 2.0, 2);

      double fatGramMin = round((kcalRangeMin - (carbohydrateGramMin * 4) - (proteinGramMin * 4)) / 9, 2);
      double fatGramMax = round((kcalRangeMax - (carbohydrateGramMax * 4) - (proteinGramMax * 4)) / 9, 2);

      list.add(CPFDto.builder()
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
          .build());
    }
    return list;
  }

  private List<CPFDto> getCPFforReduction(double weight, List<BmrDto> bmrByUserId) {
    List<CPFDto> list = new ArrayList<>();
    String tittle = "reduction time";
    for (BmrDto bmr : bmrByUserId) {
      String activity_level = bmr.getCategory();
      double kcalRangeMin = round(bmr.getBmr() * 0.85, 2);
      double kcalRangeMax = round(bmr.getBmr() * 0.9, 2);

      double carbohydrateGramMin = round(weight * 5, 2);
      double carbohydrateGramMax = round(weight * 7, 2);

      double proteinGramMin = round(weight * 1.6, 2);
      double proteinGramMax = round(weight * 2.0, 2);

      double fatGramMin = round((kcalRangeMin - (carbohydrateGramMin * 4) - (proteinGramMin * 4)) / 9, 2);
      double fatGramMax = round((kcalRangeMax - (carbohydrateGramMax * 4) - (proteinGramMax * 4)) / 9, 2);

      list.add(CPFDto.builder()
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
          .build());
    }
    return list;
  }

  private double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    long factor = (long) Math.pow(10, places);
    value = value * factor;
    long tmp = Math.round(value);
    return (double) tmp / factor;
  }
}
