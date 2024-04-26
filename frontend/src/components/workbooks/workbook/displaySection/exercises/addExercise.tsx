import React, { Component } from 'react';
import { DictExercises, DictQuantityType, DictUnits } from '../../../../../types/types';
import Service from '../../../../../services/exercises'


type Props = {
    workbook_id: number,
    training_id: number
}

type State = {
    dictExercises: DictExercises[],
    dictUnits: DictUnits[],
    dictQuantityTypes: DictQuantityType[]
}

class AddExercise extends Component<Props, State> {
    constructor(props: Props) {
        super(props)
        this.state = {
            dictExercises: [],
            dictUnits: [],
            dictQuantityTypes: []
        }
    }
    componentDidMount() {
        this.loadDictExercises();
        this.loadDictUnits();
        this.loadDictQuantityTypes();
    }

    loadDictExercises() {
        Service.getDictExercises()
            .then(response => {
                this.setState({ dictExercises: response.data });
            })
            .catch(error => {
                console.error('Error loading dict exercises:', error);
            });
    }

    loadDictUnits() {
        Service.getDictUnits()
            .then(response => {
                this.setState({ dictUnits: response.data });
            })
            .catch(error => {
                console.error('Error loading dict units:', error);
            });
    }

    loadDictQuantityTypes() {
        Service.getDictQuantityType()
            .then(response => {
                this.setState({ dictQuantityTypes: response.data });
            })
            .catch(error => {
                console.error('Error loading dict quantity types:', error);
            });
    }

    render() {
        const {workbook_id, training_id} = this.props;
        const { dictExercises, dictUnits, dictQuantityTypes } = this.state;
        return (
            <div>
                <p>workbook id: {workbook_id}   training id: {training_id}</p>
                <h2>Dict Exercises</h2>
                <ul>
                    {dictExercises.map(exercise => (
                        <li key={exercise.id}>{exercise.name}</li>
                    ))}
                </ul>
                <h2>Dict Units</h2>
                <ul>
                    {dictUnits.map(unit => (
                        <li key={unit.id}>{unit.name}</li>
                    ))}
                </ul>
                <h2>Dict Quantity Types</h2>
                <ul>
                    {dictQuantityTypes.map(type => (
                        <li key={type.id}>{type.name}</li>
                    ))}
                </ul>
            </div>
        );
        
    }
}

export default AddExercise;