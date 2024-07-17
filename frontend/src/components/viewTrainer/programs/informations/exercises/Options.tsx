import React, { Component } from "react";
import ExercisesSessionService from '../../../../../services/exercises/session/ExercisesSessionService';
import { withTranslation } from "react-i18next";

type Props = {
  exerciseId: string;
  sessionId: string;
  onExerciseOption: (updatedExercises: any[]) => void; // Function to update exercises list
  t: any;
};
type State = {};

class Options extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
  }

  handleDelete = async () => {
    const { exerciseId, sessionId, onExerciseOption } = this.props;
    try {
      const response = await ExercisesSessionService.deleteExercise(sessionId, exerciseId);
      onExerciseOption(response.data); // Call the function passed as a prop to update exercises list
    } catch (error) {
      console.error('Error deleting exercise:', error);
    }
  };

  handleMoveUp = async () => {
    const { exerciseId, sessionId, onExerciseOption } = this.props;
    try {
      const response = await ExercisesSessionService.updateExerciseOrderNumberUp(sessionId, exerciseId);
      onExerciseOption(response.data);
    } catch (error) {
      console.error('Error moving exercise up:', error);
    }
  };

  handleMoveDown = async () => {
    const { exerciseId, sessionId, onExerciseOption } = this.props;
    try {
      const response = await ExercisesSessionService.updateExerciseOrderNumberDown(sessionId, exerciseId);
      onExerciseOption(response.data);
    } catch (error) {
      console.error('Error moving exercise down:', error);
    }
  };

  render() {
    const { t } = this.props;
    return (
      <div>
        <button onClick={this.handleDelete}>{t('buttons.delete')}</button>
        <button onClick={this.handleMoveUp}>{t('buttons.move_up')}</button>
        <button onClick={this.handleMoveDown}>{t('buttons.move_down')}</button>
      </div>
    );
  }
}

export default withTranslation("global")(Options);
