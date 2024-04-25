package com.zapiszto.controllers.workbooks.entity;

import com.zapiszto.controllers.dictWorkbookSchema.entity.DictWorkbookSchemaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "workbooks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkbooksEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "order_number")
  private int orderNumber;

  @Column(name = "insert_date")
  private ZonedDateTime insert_date;

  @ManyToOne
  @JoinColumn(name = "dict_workbook_schema_id", referencedColumnName = "id")
  private DictWorkbookSchemaEntity dictWorkbookSchemaId;
}
