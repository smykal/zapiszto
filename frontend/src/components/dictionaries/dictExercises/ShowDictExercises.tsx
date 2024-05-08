import { Component } from "react";
import { DictExercises } from "../../../types/types";
import Delete from "./Options";
import AddDictExercisePerUser from "./AddDictExercisePerUser";
import Service from '../../../services/exercises'
import TableContainer from '@mui/material/TableContainer';
import Table from '@mui/material/Table';
import TableHead from '@mui/material/TableHead';
import TableBody from '@mui/material/TableBody';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';

type Props = {};
type State = {
    dictExercises: DictExercises[]
};

export default class ShowDictExercises extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            dictExercises: []
        };
    }

    componentDidMount() {
        this.loadDictExercises();
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

    render() {
        const { dictExercises } = this.state
        return (
            <div>
                <AddDictExercisePerUser dictExercises={dictExercises} />
                <TableContainer>
                    <Table sx={{ minWidth: 650 }} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell>Id</TableCell>
                                <TableCell>Name</TableCell>
                                <TableCell>Options</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                        {dictExercises.map((row) => (
                            <TableRow
                            key={row.id}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                                <TableCell component='th' scope="row"> {row.id}</TableCell>                                   
                                <TableCell>{row.name}</TableCell>
                                <TableCell>{row.dict === "PER_USER" ? <Delete itemToDelete={row.dict_id} /> : "menu niedostępne"}</TableCell>                                                          
                            </TableRow>
                        ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        )  
    }
}