import React, { Component } from 'react';
import AddExercise from './addExercise';
import GetExercises from '../exercises/';
import { Exercise } from '../../../../../types/types'; 
import ExercisesService from '../../../../../services/exercises'

type Props = {
    training_id: number
}

type State = {
    exercises: Exercise[]; // Zmieniamy typ listy exercises na GetExercise
}

class GetExercise extends Component<Props, State> {
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
        const { training_id } = this.props;
        ExercisesService.getExercises(training_id)
            .then(response => {
                this.setState({ exercises: response.data });
            })
            .catch(error => {
                console.error('Error loading exercises:', error);
            });
    }

    render() {
        const { exercises } = this.state;

        return (
            <div>
                <ul>
                    {exercises.map((exercise, index) => (
                        <li key={index}>
                            <div>Exercise ID: {exercise.exerciseId}</div>
                            <div>Training ID: {exercise.trainingId}</div>
                            <div>Exercise Name: {exercise.dictExerciseName}</div>
                            <div>Quantity: {exercise.quantity}</div>
                            <div>Quantity Type Name: {exercise.dictQuantityTypeName}</div>
                            <div>Volume: {exercise.volume}</div>
                            <div>Unit Name: {exercise.dictUnitName}</div>
                            <div>Notes: {exercise.notes}</div>
                        </li>
                    ))}
                </ul>
            </div>
        );
    }
}

export default GetExercise;
