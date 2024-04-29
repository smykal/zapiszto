import React, { Component } from 'react';
import { DictExercises, DictQuantityType, DictUnits, Exercise } from '../../../../../types/types';
import Service from '../../../../../services/exercises'
import {Formik, Form, Field} from 'formik'



type Props = {
    workbook_id: number,
    training_id: number
}

type State = {
    dictExercises: DictExercises[],
    dictUnits: DictUnits[],
    dictQuantityTypes: DictQuantityType[],
    dict_exercise_id: number | null,
    dict_unit_id: number | null,
    dict_quantity_type_id: number | null,
}

class AddExercise extends Component<Props, State> {
    constructor(props: Props) {
        super(props)
        this.state = {
            dictExercises: [],
            dictUnits: [],
            dictQuantityTypes: [],
            dict_exercise_id: null,
            dict_unit_id: null,
            dict_quantity_type_id: null,
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
    postExercise(exercise: Exercise) {
        alert(JSON.stringify(exercise))
    }

    render() {
        const {workbook_id, training_id} = this.props;
        const { dictExercises, dictUnits, dictQuantityTypes, dict_exercise_id, dict_unit_id, dict_quantity_type_id } = this.state;
        const initialExercise: Exercise = {
            training_id: this.props.training_id,
            dict_exercise_id: 1,
            quantity: null,
            dict_quantity_type_id: 1,
            volume: null,
            dict_unit_id: 1,
        };

        return (
            <div>
                <Formik
                initialValues={initialExercise}
                onSubmit={this.postExercise}
                >
                    <Form>
                        <Field as="select" name="dict_exercise_id">
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
                        <Field as="select" name="dict_quantity_type_id">
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
                        <Field as="select" name="dict_unit_id">
                            <option value="" disabled>
                                Select unit
                            </option>
                            {this.state.dictUnits.map((unit) => (
                                <option key={unit.id} value={unit.id}>
                                    {unit.name}
                                </option>
                            ))}
                        </Field>
                        <button type="submit" className="btn btn-primary btn-block">
                  <span>Add</span>
                </button>
                    </Form>
                </Formik>
                <p>workbook id: {workbook_id}   training id: {training_id}</p>
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