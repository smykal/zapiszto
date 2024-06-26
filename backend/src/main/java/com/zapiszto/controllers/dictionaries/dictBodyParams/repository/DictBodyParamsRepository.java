package com.zapiszto.controllers.dictionaries.dictBodyParams.repository;

import com.zapiszto.controllers.dictionaries.dictBodyParams.entity.DictBodyParamsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DictBodyParamsRepository extends JpaRepository<DictBodyParamsEntity, Integer> {

  @Query(nativeQuery = true, value = "SELECT * FROM dict_body_params where id = :id")
  DictBodyParamsEntity getDictBodyParamById(
      @Param("id") int id
  );
}