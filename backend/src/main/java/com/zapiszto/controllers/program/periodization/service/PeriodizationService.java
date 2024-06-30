package com.zapiszto.controllers.program.periodization.service;


import com.zapiszto.controllers.program.periodization.dto.PeriodizationDto;
import com.zapiszto.controllers.program.periodization.repository.PeriodizationRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PeriodizationService {

  @Autowired
  PeriodizationRepository periodizationRepository;

  public List<PeriodizationDto> getDistinctNameAndDescription() {
    return periodizationRepository.findDistinctNameAndDescription()
        .stream()
        .map(record -> new PeriodizationDto((String) record[0], (String) record[1]))
        .collect(Collectors.toList());
  }
}
