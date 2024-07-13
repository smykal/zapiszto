import { NumberSchema } from "yup";

export type BodyParamsItem = {
                              dict_body_params_name: string;
                              value: number;
                              insert_date: string;
  };

export type DictBodyParam = {
                              id: number;
                              name: string;
                              description: string;
}

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
                                id: string;
                                dict: string;
                                dict_id: string;
                                name: string;
                                shortcut: string
}

export type NewDictQuantityType = {
                                    id: string
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
                              id: string;
                              dict: string;
                              dict_id: string;
                              name: string
                              category_type: string
                              category_id: number
                              category_name: string
}

export type NewDictExercises = {
                                id: string
                                name: string
                                categoryId: number
}

export type NewExercise = {
                        id: string;
                        trainingId: number,
                        dictExerciseId: number,
                        quantity: number,
                        dictQuantityTypeId: number,
                        volume: number,
                        dictUnitId: number,
                        notes: string
}

export type Exercise = {
                        exerciseId: string,
                        trainingId: number,
                        dictExerciseName: string,
                        quantity: number,
                        dictQuantityTypeName: string,
                        volume: number,
                        dictUnitName: string,
                        notes: number,
                        orderNumber: number
}

export type ExerciseSession = {
                                exerciseId: string;
                                sessionId: string;
                                dictExerciseName: string;
                                quantity: number;
                                dictQuantityTypeName: string;
                                volume: number;
                                dictUnitName: string;
                                notes: string;
                                orderNumber: number;
                                restTime: number | null; 
                                tempo: string | null;
                                dictSessionPartName: string;
};

export type NewInvitation = {
                              email: string;
}

export type Invitation = {
                          id: string,
                          inviterId: number,
                          inviterName: string,
                          inviterEmail: string,
                          inviteeId: number,
                          inviteeName: string,
                          inviteeEmail: string,
                          status: string
}

export type UserDetails = {
                            languageCode: string,
                            languageLabel: string
}

export type UserDetailsLanguage = {
                                    languageCode: string
}

export type Client = {
                      id: string,
                      clientName: string,
                      userId: number
}

export type NewClient = {
                          id: string,
                          clientName: string,
                          userId: number
}

export type NewDictBodyTest = {
                                name: string,
                                description: string,
                                purpose: string
}

export type DictBodyTest = {
                            id: number,
                            dict: string,
                            dict_id: number,
                            name: string,
                            description: string,
                            purpose: string
}

export type NewClientBodyTest = {
                            id: string,
                            clientId: string,
                            dictBodyTestId: number,
                            result: string
}

export type ClientBodyTest = {
                              id: number
                              dict_body_test_id: number
                              dict: string
                              dict_id: number
                              name: string
                              result: string
                              description: string
                              purpose: string
}

export type NewGoal = {
                        id: string,
                        clientId: string,
                        dictBodyParamId: number | null,
                        dictBodyTestId: number | null,
                        dictUnitId: number,
                        action: string,
                        value: string,
                        goalDate: string
}

export type Goal = {
                      id: string;
                      clientId: string;
                      goalType: string;
                      dictBodyParam: string;
                      dictBodyTestType: string;
                      dictBodyTest: string;
                      dictUnitType: string;
                      dictUnit: string;
                      action: string;
                      value: string;
                      goalDate: Date;
                      insertDate: Date;
}

export interface Program {
                          id: string;
                          programName: string;
                          createdBy: number;
                          createDate: string;
}

export interface NewProgram {
                            id: string;
                            programName: string;
                            createdBy: number;
}

export interface ProgramDetails {
                                  programId: string;
                                  assignedClient: string;
}

export interface ProgramAssignedClient {
                          id: string;
                          assignedClient: string;
}

export type GoalDetailsDto = {
                              clientId: string;
                              bodyParamName: string;
                              bodyTestName: string;
                              unitName: string;
                              action: string;
                              value: string;
}

export type NewMacrocycleDto = {
                                id: string;
                                programId: string;
                                duration: number;
                                mesocycleDuration: number;
                                periodization: string; 
                                durationSession: number;
                                sessionsForMicrocycle: number;
};

export type MacrocycleDto = {
                              id: string;
                              programId: string;
                              duration: number;
                              durationLeft: number;
}

export type MesocycleDto = {
                              id: string;
                              macrocycleId: string;
                              duration: number;
                              orderId: number;
                              comments: string;
                              dictType: string;
                              dictId: number;
                              dictName: string;
                              label: string;
}

export type NewMesocycleDto = {
                                id: string;
                                macrocycleId: string;
                                duration: number;
                                comments: string;
}

export type MicrocycleDto = {
                              id: string;
                              mesocycleId: string;
                              orderId: number;
                              dictType: string;
                              dictId: number;
                              dictName: string;
}

export type PeriodizationDto = {
                              name: string;
                              description: string;
}

export type SessionDto = {
                            id: string;
                            microcycleId: string;
                            orderId: number;
                            label: string;
                            dateTime: string;
}

export type DictSessionPartDto = {
                                  id: string;
                                  dict: string;
                                  dict_id: string;
                                  name: string
}