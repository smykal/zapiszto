import { NumberSchema } from "yup";

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

export type Training = {
                        id: number;
                        workbooks_id: number;
                        date: string;
                        notes: string
}

export type DictQuantityType = {
                                id: number;
                                dict: string;
                                dict_id: number;
                                name: string;
                                shortcut: string
}

export type NewDictQuantityType = {
                                    name: string,
                                    shortcut: string

}

export type DictUnits = {
                          id: number;
                          dict: string;
                          dict_id: number;
                          name: string;
                          shortcut: string
}

export type NewDictUnit = {
                            name: string,
                            shortcut: string
}

export type NewDictCategory = {
                                name: string,
                                description: string
}

export type DictCategories = {
                          id: number;
                          dict: string;
                          dict_id: number;
                          name: string;
                          description: string
}

export type DictExercises = {
                              id: number;
                              dict: string;
                              dict_id: number;
                              name: string
}

export type NewDictExercises = {
                                name: string
}

export type NewExercise = {
                        trainingId: number,
                        dictExerciseId: number,
                        quantity: number,
                        dictQuantityTypeId: number,
                        volume: number,
                        dictUnitId: number,
                        notes: string
}

export type Exercise = {
                        exerciseId: number,
                        trainingId: number,
                        dictExerciseName: string,
                        quantity: number,
                        dictQuantityTypeName: string,
                        volume: number,
                        dictUnitName: string,
                        notes: number,
                        orderNumber: number
}
