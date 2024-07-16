import React, { Component } from 'react';
import Options from './Options';
import EditableSelectCell from '../../../../../common/EditableSelectCell';
import EditableCell from '../../../../../common/EditableCell';
import EditableNumberCell from '../../../../../common/EditableNumberCell';
import { ExerciseSession, DictSessionPartDto, DictQuantityType, DictExercises, DictUnits } from '../../../../../types/types';
import { withTranslation } from "react-i18next";
import ExercisesSessionService from '../../../../../services/exercises/session/ExercisesSessionService';
import DictSessionPartService from '../../../../../services/dict/DictSessionPartService';
import DictQuantityTypeService from '../../../../../services/dict/DictQuantityTypeService';
import DictExercisesService from '../../../../../services/dict/DictExercisesService';
import DictUnitsService from '../../../../../services/dict/DictUnitsService';

type Props = {
  session_id: string;
  t: any;
}

type State = {
  exercises: ExerciseSession[];
  sessionPartOptions: DictSessionPartDto[];
  quantityTypeOptions: DictQuantityType[];
  exercisesOptions: DictExercises[];
  unitsOptions: DictUnits[]; // Add units options state
}

class GetExerciseSession extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      exercises: [],
      sessionPartOptions: [],
      quantityTypeOptions: [],
      exercisesOptions: [],
      unitsOptions: [] // Initialize units options
    };
  }

  componentDidMount() {
    this.loadExercises();
    this.loadSessionPartOptions();
    this.loadQuantityTypeOptions();
    this.loadExercisesOptions();
    this.loadUnitsOptions(); // Load units options
  }

  loadExercises() {
    const { session_id } = this.props;
    ExercisesSessionService.getExercisesBySession(session_id)
      .then(response => {
        const sortedExercises = response.data.sort((a: ExerciseSession, b: ExerciseSession) => a.orderNumber - b.orderNumber);
        this.setState({ exercises: sortedExercises });
      })
      .catch(error => {
        console.error('Error loading exercises:', error);
      });
  }

  loadExercisesOptions() {
    DictExercisesService.getDictExercises()
    .then(response => {
      this.setState({exercisesOptions: response.data});
    })
    .catch(error => {
      console.error('Error loading exercises options:', error);
    });
  }

  loadSessionPartOptions() {
    DictSessionPartService.getSessionPartOptions()
      .then(response => {
        this.setState({ sessionPartOptions: response.data });
      })
      .catch(error => {
        console.error('Error loading session part options:', error);
      });
  }

  loadQuantityTypeOptions() {
    DictQuantityTypeService.getDictQuantityType()
      .then(response => {
        this.setState({ quantityTypeOptions: response.data });
      })
      .catch(error => {
        console.error('Error loading quantity type options:', error);
      });
  }

  loadUnitsOptions() {
    DictUnitsService.getDictUnits()
      .then(response => {
        this.setState({ unitsOptions: response.data });
      })
      .catch(error => {
        console.error('Error loading units options:', error);
      });
  }

  handleSaveExercise(exerciseId: string, newExerciseName: string) {
    const newExercise = this.state.exercisesOptions.find(option => option.name === newExerciseName);

    if (newExercise) {
      ExercisesSessionService.updateExercise(exerciseId, { id: newExercise.id })
        .then(() => {
          this.setState(prevState => ({
            exercises: prevState.exercises.map(ex => 
              ex.exerciseId === exerciseId ? { ...ex, dictExerciseName: newExerciseName, dictCategoryName: newExercise.category_name } : ex
            )
          }));
        })
        .catch(error => {
          console.error('Error updating exercise:', error);
        });
    }
  }

  handleSaveSessionPart(exerciseId: string, newSessionPartName: string) {
    const newSessionPart = this.state.sessionPartOptions.find(option => option.name === newSessionPartName);

    if (newSessionPart) {
      ExercisesSessionService.updateDictSessionPart(exerciseId, newSessionPart.id)
        .then(() => {
          this.setState(prevState => ({
            exercises: prevState.exercises.map(ex => 
              ex.exerciseId === exerciseId ? { ...ex, dictSessionPartName: newSessionPartName } : ex
            )
          }));
        })
        .catch(error => {
          console.error('Error updating session part:', error);
        });
    }
  }

  handleSaveQuantityType(exerciseId: string, newQuantityTypeName: string) {
    const newQuantityType = this.state.quantityTypeOptions.find(option => option.name === newQuantityTypeName);

    if (newQuantityType) {
      ExercisesSessionService.updateDictQuantityType(exerciseId, newQuantityType.id)
        .then(() => {
          this.setState(prevState => ({
            exercises: prevState.exercises.map(ex => 
              ex.exerciseId === exerciseId ? { ...ex, dictQuantityTypeName: newQuantityTypeName } : ex
            )
          }));
        })
        .catch(error => {
          console.error('Error updating quantity type:', error);
        });
    }
  }

  handleSaveNotes(exerciseId: string, newNotes: string) {
    ExercisesSessionService.updateNotes(exerciseId, { notes: newNotes })
      .then(() => {
        this.setState(prevState => ({
          exercises: prevState.exercises.map(ex => 
            ex.exerciseId === exerciseId ? { ...ex, notes: newNotes } : ex
          )
        }));
      })
      .catch(error => {
        console.error('Error updating notes:', error);
      });
  }

  handleSaveTempo(exerciseId: string, newTempo: string) {
    ExercisesSessionService.updateTempo(exerciseId, { tempo: newTempo })
      .then(() => {
        this.setState(prevState => ({
          exercises: prevState.exercises.map(ex => 
            ex.exerciseId === exerciseId ? { ...ex, tempo: newTempo } : ex
          )
        }));
      })
      .catch(error => {
        console.error('Error updating tempo:', error);
      });
  }

  handleSaveQuantity(exerciseId: string, newQuantity: number) {
    ExercisesSessionService.updateQuantity(exerciseId, { quantity: newQuantity })
      .then(() => {
        this.setState(prevState => ({
          exercises: prevState.exercises.map(ex => 
            ex.exerciseId === exerciseId ? { ...ex, quantity: newQuantity } : ex
          )
        }));
      })
      .catch(error => {
        console.error('Error updating quantity:', error);
      });
  }

  handleSaveVolume(exerciseId: string, newVolume: number) {
    ExercisesSessionService.updateVolume(exerciseId, { volume: newVolume })
      .then(() => {
        this.setState(prevState => ({
          exercises: prevState.exercises.map(ex => 
            ex.exerciseId === exerciseId ? { ...ex, volume: newVolume } : ex
          )
        }));
      })
      .catch(error => {
        console.error('Error updating volume:', error);
      });
  }

  handleSaveRestTime(exerciseId: string, newRestTime: number) {
    ExercisesSessionService.updateRestTime(exerciseId, { restTime: newRestTime })
      .then(() => {
        this.setState(prevState => ({
          exercises: prevState.exercises.map(ex => 
            ex.exerciseId === exerciseId ? { ...ex, restTime: newRestTime } : ex
          )
        }));
      })
      .catch(error => {
        console.error('Error updating rest time:', error);
      });
  }

  handleSaveUnit(exerciseId: string, newUnitName: string) { 
    const newUnit = this.state.unitsOptions.find(option => option.name === newUnitName);

    if (newUnit) {
      ExercisesSessionService.updateDictUnit(exerciseId, {dictUnitId: newUnit.id})
        .then(() => {
          this.setState(prevState => ({
            exercises: prevState.exercises.map(ex => 
              ex.exerciseId === exerciseId ? { ...ex, dictUnitName: newUnitName } : ex
            )
          }));
        })
        .catch(error => {
          console.error('Error updating unit:', error);
        });
    }
  }

  handleSaveSets(exerciseId: string, newSets: number) { 
    ExercisesSessionService.updateSets(exerciseId, { sets: newSets })
      .then(() => {
        this.setState(prevState => ({
          exercises: prevState.exercises.map(ex => 
            ex.exerciseId === exerciseId ? { ...ex, sets: newSets } : ex
          )
        }));
      })
      .catch(error => {
        console.error('Error updating sets:', error);
      });
  }

  render() {
    const { exercises, sessionPartOptions, quantityTypeOptions, exercisesOptions, unitsOptions } = this.state;
    const { t } = this.props;

    const sessionPartNames = sessionPartOptions.map(option => option.name);
    const quantityTypeNames = quantityTypeOptions.map(option => option.name);
    const exercisesNames = exercisesOptions.map(option => option.name);
    const unitsNames = unitsOptions.map(option => option.name); // Unit names for the select options

    return (
      <div>
        <table style={{ minWidth: '600px', width: '100%', borderCollapse: 'collapse' }}>
          <thead>
            <tr>
              <th>{/*t("table.order_number")*/}</th>
              <th>{t("table.session_part")}</th>
              <th>{t("table.category")}</th>
              <th>{t("table.exercise")}</th>
              <th>{/*t("table.volume")*/}</th>
              <th>{t("table.unit")}</th>
              <th>{/*t("table.quantity")*/}</th>
              <th>{/*t("table.type")*/}</th>
              <th>{t("table.sets")}</th>
              <th>{t("table.notes")}</th>
              <th>{t("table.rest_time")}</th>
              <th>{t("table.tempo")}</th>
              <th>{t("table.options")}</th>
            </tr>
          </thead>
          <tbody>
            {exercises.map((row) => (
              <tr key={row.exerciseId} style={{ borderBottom: '1px solid #ddd' }}>
                <td>{row.orderNumber}</td>
                <td>
                  <EditableSelectCell
                    value={row.dictSessionPartName}
                    options={sessionPartNames}
                    onSave={(newValue) => this.handleSaveSessionPart(row.exerciseId, newValue)}
                  />
                </td>
                <td>{row.dictCategoryName}</td>
                <td>
                  <EditableSelectCell
                    value={row.dictExerciseName}
                    options={exercisesNames}
                    onSave={(newValue) => this.handleSaveExercise(row.exerciseId, newValue)}
                  />
                </td>
                <td>
                  <EditableNumberCell
                    value={row.volume ?? 0}
                    onSave={(newValue) => this.handleSaveVolume(row.exerciseId, newValue)}
                  />
                </td>
                <td>
                  <EditableSelectCell
                    value={row.dictUnitName}
                    options={unitsNames}
                    onSave={(newValue) => this.handleSaveUnit(row.exerciseId, newValue)}
                  />
                </td>
                <td>
                  <EditableNumberCell
                    value={row.quantity ?? 0}
                    onSave={(newValue) => this.handleSaveQuantity(row.exerciseId, newValue)}
                  />
                </td>
                <td>
                  <EditableSelectCell
                    value={row.dictQuantityTypeName}
                    options={quantityTypeNames}
                    onSave={(newValue) => this.handleSaveQuantityType(row.exerciseId, newValue)}
                  />
                </td>
                <td>
                  <EditableNumberCell
                    value={row.sets ?? 0}
                    onSave={(newValue) => this.handleSaveSets(row.exerciseId, newValue)}
                  />
                </td>
                <td>
                  <EditableCell
                    value={row.notes ?? ''}
                    onSave={(newValue) => this.handleSaveNotes(row.exerciseId, newValue)}
                  />
                </td>
                <td>
                  <EditableNumberCell
                    value={row.restTime ?? 0}
                    onSave={(newValue) => this.handleSaveRestTime(row.exerciseId, newValue)}
                  />
                </td>
                <td>
                  <EditableCell
                    value={row.tempo ?? ''}
                    onSave={(newValue) => this.handleSaveTempo(row.exerciseId, newValue)}
                  />
                </td>
                <td><Options exerciseId={row.exerciseId} sessionId={row.sessionId} /></td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default withTranslation("global")(GetExerciseSession);
