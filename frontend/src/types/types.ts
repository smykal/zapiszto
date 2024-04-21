export type BodyParamsItem = {
                              dict_body_params_name: string;
                              value: number;
                              insert_date: string;
  };

export type BmiItem = {
                        value: number;
                        date: string;
                        description: string;
}

export type BmrType = {
                        userId: number;
                        insert_date: string;
                        bmr: number;
                        category: string;
}

export type CpfType = {
                      activity_level: string;
                      tittle: string;
                      kcalMin: number;
                      kcalMax: number;
                      carbohydrateMin: number;
                      carbohydrateMax: number;
                      proteinMin: number;
                      proteinMax: number;
                      fatMin: number;
                      fatMax: number;
}

export type Workbook = {
                    id: number;
                    name: string;
                    order_number: number
                    insert_date: string
                    dict_workbook_schema_name: string
}

export type WorkbookSchemaIdPut = {
                                  id: number;
                                  dict_workbook_schema_id: number;
}

export type NewWorkbook = {
                            name: string;
}

export type DictWorkbookSchema = {
                                  id: number;
                                  name: string;
                                  description: string;
}

export type NewTraining = {
                            workbook_id: number;
                            date: Date;
}

export type TrainingNotes = {
                             id: number;
                             notes: string 
}