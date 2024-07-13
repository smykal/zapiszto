import React, { Component } from 'react';
import Options from './Options';
import EditableSelectCell from '../../../../../common/EditableSelectCell';
import { ExerciseSession, DictSessionPartDto, DictQuantityType } from '../../../../../types/types';
import { withTranslation } from "react-i18next";
import ExercisesService from '../../../../../services/exercises/session/ExercisesSessionService';
import DictSessionPartService from '../../../../../services/dict/DictSessionPartService';
import DictQuantityTypeService from '../../../../../services/dict/DictQuantityTypeService';

type Props = {
  session_id: string;
  t: any;
}

type State = {
  exercises: ExerciseSession[];
  sessionPartOptions: DictSessionPartDto[];
  quantityTypeOptions: DictQuantityType[];
}

class GetExerciseSession extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      exercises: [],
      sessionPartOptions: [],
      quantityTypeOptions: []
    };
  }

  componentDidMount() {
    this.loadExercises();
    this.loadSessionPartOptions();
    this.loadQuantityTypeOptions();
  }

  loadExercises() {
    const { session_id } = this.props;
    ExercisesService.getExercisesBySession(session_id)
      .then(response => {
        const sortedExercises = response.data.sort((a: ExerciseSession, b: ExerciseSession) => a.orderNumber - b.orderNumber);
        this.setState({ exercises: sortedExercises });
      })
      .catch(error => {
        console.error('Error loading exercises:', error);
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

  handleSaveSessionPart(exerciseId: string, newSessionPartName: string) {
    const newSessionPart = this.state.sessionPartOptions.find(option => option.name === newSessionPartName);

    if (newSessionPart) {
      ExercisesService.updateDictSessionPart(exerciseId, newSessionPart.id)
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
      ExercisesService.updateDictQuantityType(exerciseId, newQuantityType.id)
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

  render() {
    const { exercises, sessionPartOptions, quantityTypeOptions } = this.state;
    const { t } = this.props;

    const sessionPartNames = sessionPartOptions.map(option => option.name);
    const quantityTypeNames = quantityTypeOptions.map(option => option.name);

    return (
      <div>
        <table style={{ minWidth: '600px', width: '100%', borderCollapse: 'collapse' }}>
          <thead>
            <tr>
              <th>{/*t("table.order_number")*/}</th>
              <th>{t("table.session_part")}</th>
              <th>{t("table.exercise")}</th>
              <th>{/*t("table.volume")*/}</th>
              <th>{t("table.unit")}</th>
              <th>{/*t("table.quantity")*/}</th>
              <th>{t("table.type")}</th>
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
                <td>{row.dictExerciseName}</td>
                <td>{row.volume}</td>
                <td>{row.dictUnitName}</td>
                <td>{row.quantity}</td>
                <td>
                  <EditableSelectCell
                    value={row.dictQuantityTypeName}
                    options={quantityTypeNames}
                    onSave={(newValue) => this.handleSaveQuantityType(row.exerciseId, newValue)}
                  />
                </td>
                <td>{row.notes}</td>
                <td>{row.restTime}</td>
                <td>{row.tempo}</td>
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
