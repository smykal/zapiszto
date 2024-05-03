import { Component } from 'react';
import TableContainer from '@mui/material/TableContainer';
import Table from '@mui/material/Table';
import TableHead from '@mui/material/TableHead';
import TableBody from '@mui/material/TableBody';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';

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
                <TableContainer>
                    <Table sx={{ minWidth: 650 }} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell>Exercise</TableCell>
                                <TableCell>Quantity</TableCell>
                                <TableCell>Type</TableCell>
                                <TableCell>Volume</TableCell>
                                <TableCell>Unit</TableCell>
                                <TableCell>Notes</TableCell>
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
                                </TableRow>
                            ))}

                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        );
    }
}

export default GetExercise;
