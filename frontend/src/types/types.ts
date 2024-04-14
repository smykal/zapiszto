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
}

export type NewWorkbook = {
                            name: string;
}

  