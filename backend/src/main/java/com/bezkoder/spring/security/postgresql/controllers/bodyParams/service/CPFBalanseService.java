package com.bezkoder.spring.security.postgresql.controllers.bodyParams.service;

import com.bezkoder.spring.security.postgresql.controllers.bodyParams.repository.CPFBalanseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CPFBalanseService {

  private final int carbohydrateMin = 5;
  private final int carbohydrateMax = 5;

  private final double proteinMin = 1.6;
  private final double proteinMax = 2.2;

  private final double fatMin = 0.5;
  private final double fatMax = 1.5;

  @Autowired
  private CPFBalanseRepository cpfBalanseRepository;
}
