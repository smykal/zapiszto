import React, { Component } from "react";
import ExercisesSessionService from '../../../../../services/exercises/session/ExercisesSessionService';
import { withTranslation } from "react-i18next";

type Props = {
  exerciseId: string;
  sessionId: string;
  onExerciseDeleted: (updatedExercises: any[]) => void; // Function to update exercises list
  t: any;
};
type State = {};

class Options extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
  }

  handleDelete = async () => {
    const { exerciseId, sessionId, onExerciseDeleted } = this.props;
    try {
      const response = await ExercisesSessionService.deleteExercise(sessionId, exerciseId);
      onExerciseDeleted(response.data); // Call the function passed as a prop to update exercises list
    } catch (error) {
      console.error('Error deleting exercise:', error);
    }
  };

  render() {
    const { t } = this.props;
    return (
      <div>
        <button onClick={this.handleDelete}>{t('buttons.delete')}</button>
        <button>/</button>
        <button>\</button>
      </div>
    );
  }
}

export default withTranslation("global")(Options);
