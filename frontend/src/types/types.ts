import { NumberSchema } from "yup";

export type BodyParamsItem = {
                              dict_body_params_name: string;
                              value: number;
                              insert_date: string;
}

export interface BodyParamDto  {
                                  dict_body_params_id: number
                                  value: string
                                  clientId: string
} 

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

export interface NewDictQuantityType {
                                      id: string;
                                      name: { [key: string]: string };
                                      shortcut: { [key: string]: string };
                                    }

export type DictUnits = {
                          id: string
                          dict: string
                          dict_id: string
                          name: string
                          shortcut: string
}

export type NewDictUnit = {
                            id: string
                            name: { [key: string]: string };
                            shortcut: { [key: string]: string };
}

export type NewDictCategory = {
                                name: { [key: string]: string },
                                description: { [key: string]: string }
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
                                name: { [key: string]: string };
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
                                dictCategoryName: string;
                                quantity: number;
                                dictQuantityTypeName: string;
                                volume: number;
                                dictUnitName: string;
                                notes: string;
                                orderNumber: number;
                                restTime: number | null; 
                                tempo: string | null;
                                dictSessionPartName: string;
                                sets: number;
                                equipmentName: string;
                                equipmentAttribute: string;
                                weightPerSide: number;
                                duration: number;
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

export interface ClientProgramDto {
                                      name: string;
                                      macrocycles: ClientProgramMacrocycleDto[];
}

export interface ClientProgramMacrocycleDto {
                                      id: string;
                                      mesocycles: ClientProgramMesocycleDto[];
}

export interface ClientProgramMesocycleDto {
                                            orderId: number;
                                            microcycles: ClientProgramMicrocycleDto[];
}

export interface ClientProgramMicrocycleDto {
                                            orderId: number;
                                            sessions: ClientProgramSessionDto[];
}

export interface ClientProgramSessionDto {
                                          orderId: number;
                                          exercises: ClientProgramExerciseDto[];
}

export interface ClientProgramExerciseDto {
                                          orderId: number;
                                          purpose: string;
                                          category: string;
                                          exercise: string;
                                          weight: number;
                                          weightUnit: string;
                                          repetitions: number;
                                          repetitionsUnit: string;
}

export type NewDictBodyTest = {
                                name: { [key: string]: string };
                                description: { [key: string]: string };
                                purpose: { [key: string]: string };
}

export type DictBodyTest = {
                            id: string,
                            dict: string,
                            dict_id: string,
                            name: string,
                            description: string,
                            purpose: string
}

export type NewClientBodyTest = {
                            id: string,
                            clientId: string,
                            dictBodyTestId: string,
                            result: string
}

export type ClientBodyTest = {
                              id: string
                              dict_body_test_id: number
                              dict: string
                              dict_id: string
                              name: string
                              result: string
                              description: string
                              purpose: string
}

export type NewGoal = {
                        id: string,
                        clientId: string,
                        dictBodyParamId: number | null,
                        dictBodyTestId: string | null,
                        dictUnitId: string,
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
                          clientName: string;
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
                              share: boolean;
}

export type MicrocycleStatsDto = {
                                    category: string;
                                    repetitions: number;
                                    sets: number;
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

export interface DictEquipment {
                                  id: string;
                                  name: string;
                                  dict: string;
                                  dict_id: string;
}

export interface NewDictEquipment {
                                    id: string;
                                    name: { [key: string]: string };
                                  }

export interface ExerciseStats {
                                orderId: number;
                                category: string;
                                repetitions: number;
                                sets: number;
}

export interface CopyParametersDto {
                                  weightIncrease: number;
                                  weightIncreaseUnit: string;
                                  repetitions: number;
}