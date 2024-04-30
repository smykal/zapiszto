import React, { Component } from 'react';
import { DictExercises, DictQuantityType, DictUnits, NewExercise } from '../../../../../types/types';
import Service from '../../../../../services/exercises'
import {Formik, Form, Field} from 'formik'


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
    postExercise = (exercise: NewExercise) => {
        Service.postExercise(exercise)
    }

    render() {
        const {workbook_id, training_id} = this.props;
        const { dictExercises, dictUnits, dictQuantityTypes} = this.state;
        const initialExercise: NewExercise = {
            trainingId: this.props.training_id,
            dictExerciseId: 1,
            quantity:  0,
            dictQuantityTypeId: 1,
            volume: 0,
            dictUnitId: 1,
            notes: 'notes'
        };

        return (
            <div>
                <Formik
                initialValues={initialExercise}
                onSubmit={this.postExercise}
                >
                    <Form>
                        <Field as="select" name="dictExerciseId">
                            <option value="" disabled>
                                Select exercise
                            </option>
                            {this.state.dictExercises.map((exercise) => (
                                <option key={exercise.id} value={exercise.id}>
                                    {exercise.name}
                                </option>
                            ))}
                        </Field>
                        <Field name="quantity" type="number" />
                        <Field as="select" name="dictQuantityTypeId">
                            <option value="" disabled>
                                Select quantity
                            </option>
                            {this.state.dictQuantityTypes.map((quantity) => (
                                <option key={quantity.id} value={quantity.id}>
                                    {quantity.name}
                                </option>
                            ))}
                        </Field>
                        <Field name="volume" type="number" />
                        <Field as="select" name="dictUnitId">
                            <option value="" disabled>
                                Select unit
                            </option>
                            {this.state.dictUnits.map((unit) => (
                                <option key={unit.id} value={unit.id}>
                                    {unit.name}
                                </option>
                            ))}
                        </Field>
                        <Field name="notes" type="text" />
                        <button type="submit" className="btn btn-primary btn-block">
                  <span>Add</span>
                </button>
                    </Form>
                </Formik>
            </div>
        );
    }
}

export default AddExercise;