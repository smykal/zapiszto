import React, { Component } from 'react';
import { Exercise } from '../../../../../types/types';
import { withTranslation } from "react-i18next";
import ExercisesService from '../../../../../services/exercises';

type Props = {
    training_id: number;
    userId: number;
    t: any;
}

type State = {
    exercises: Exercise[];
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
        const { training_id, userId } = this.props;
        ExercisesService.getExercisesByUserId(training_id, userId)
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
                        </tr>
                    </thead>
                    <tbody>
                        {exercises.map((row) => (
                            <tr key={row.dictExerciseName} style={{ borderBottom: '1px solid #ddd' }}>
                                <td>{row.dictExerciseName}</td>
                                <td>{row.quantity}</td>
                                <td>{row.dictQuantityTypeName}</td>
                                <td>{row.volume}</td>
                                <td>{row.dictUnitName}</td>
                                <td>{row.notes}</td>
                                <td>{row.orderNumber}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default withTranslation("global")(GetExercise);
