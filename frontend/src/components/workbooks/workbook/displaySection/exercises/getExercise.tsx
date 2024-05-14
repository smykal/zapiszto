import { Component } from 'react';
import Options from './Options';
import TableContainer from '@mui/material/TableContainer';
import Table from '@mui/material/Table';
import TableHead from '@mui/material/TableHead';
import TableBody from '@mui/material/TableBody';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';

import { Exercise } from '../../../../../types/types';
import { withTranslation } from "react-i18next";

import ExercisesService from '../../../../../services/exercises'

type Props = {
    training_id: number
    t: any
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
        const { t } = this.props;

        return (
            <div>
                <TableContainer>
                    <Table sx={{ minWidth: 600 }} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell>{t("table.exercise")}</TableCell>
                                <TableCell>{t("table.quantity")}</TableCell>
                                <TableCell>{t("table.type")}</TableCell>
                                <TableCell>{t("table.volume")}</TableCell>
                                <TableCell>{t("table.unit")}</TableCell>
                                <TableCell>{t("table.notes")}</TableCell>
                                <TableCell>{t("table.order_number")}</TableCell>
                                <TableCell>{t("table.options")}</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {exercises.map((row) => (
                                <TableRow
                                    key={row.dictExerciseName}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                                    <TableCell component='th' scope="row"> {row.dictExerciseName}</TableCell>
                                    <TableCell>{row.quantity}</TableCell>
                                    <TableCell>{row.dictQuantityTypeName}</TableCell>
                                    <TableCell>{row.volume}</TableCell>
                                    <TableCell>{row.dictUnitName}</TableCell>
                                    <TableCell>{row.notes}</TableCell>
                                    <TableCell>{row.orderNumber}</TableCell>
                                    <TableCell><Options exerciseId={row.exerciseId} trainingId={row.trainingId} /></TableCell>
                                </TableRow>
                            ))}

                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        );
    }
}

export default withTranslation("global")(GetExercise)
