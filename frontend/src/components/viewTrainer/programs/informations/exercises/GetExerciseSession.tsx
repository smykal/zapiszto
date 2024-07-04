import React, { Component } from 'react';
import Options from './Options';
import { ExerciseSession } from '../../../../../types/types';
import { withTranslation } from "react-i18next";
import ExercisesService from '../../../../../services/exercises/session/ExercisesSessionService';

type Props = {
  session_id: string;
  t: any;
}

type State = {
  exercises: ExerciseSession[];
}

class GetExerciseSession extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      exercises: [],
    };
  }

  componentDidMount() {
    this.loadExercises();
  }

  loadExercises() {
    const { session_id } = this.props;
    ExercisesService.getExercisesBySession(session_id)
      .then(response => {
        this.setState({ exercises: response.data });
      })
      .catch(error => {
        console.error('Error loading exercises:', error);
      });
  }

  render() {
    const { exercises } = this.state;
    const { t } = this.props;

    return (
      <div>
        <table style={{ minWidth: '600px', width: '100%', borderCollapse: 'collapse' }}>
          <thead>
            <tr>
              <th>{t("table.exercise")}</th>
              <th>{t("table.quantity")}</th>
              <th>{t("table.type")}</th>
              <th>{t("table.volume")}</th>
              <th>{t("table.unit")}</th>
              <th>{t("table.notes")}</th>
              <th>{t("table.order_number")}</th>
              <th>{t("table.rest_time")}</th> {/* Dodanie kolumny rest_time */}
              <th>{t("table.options")}</th>
            </tr>
          </thead>
          <tbody>
            {exercises.map((row) => (
              <tr key={row.exerciseId} style={{ borderBottom: '1px solid #ddd' }}>
                <td>{row.dictExerciseName}</td>
                <td>{row.quantity}</td>
                <td>{row.dictQuantityTypeName}</td>
                <td>{row.volume}</td>
                <td>{row.dictUnitName}</td>
                <td>{row.notes}</td>
                <td>{row.orderNumber}</td>
                <td>{row.restTime}</td> {/* Wyświetlanie restTime */}
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
