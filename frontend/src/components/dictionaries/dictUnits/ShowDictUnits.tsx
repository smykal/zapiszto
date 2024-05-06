import { Component } from "react";
import { DictUnits } from "../../../types/types";
import Service from '../../../services/exercises'
import TableContainer from '@mui/material/TableContainer';
import Table from '@mui/material/Table';
import TableHead from '@mui/material/TableHead';
import TableBody from '@mui/material/TableBody';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';

type Props = {};
type State = {
    dictUnits: DictUnits[]
};

export default class ShowDictUnits extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            dictUnits: []
        };
    }

    componentDidMount() {
        this.loadDictUnits();
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

    render() {
        const {dictUnits} = this.state;
        return (
            <TableContainer>
                    <Table sx={{ minWidth: 650 }} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell>Id</TableCell>
                                <TableCell>Name</TableCell>
                                <TableCell>Shortcut</TableCell>
                                <TableCell>Options</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                        {dictUnits.map((row) => (
                            <TableRow
                            key={row.id}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                                <TableCell component='th' scope="row"> {row.id}</TableCell>                                   
                                <TableCell>{row.name}</TableCell>
                                <TableCell>{row.shortcut}</TableCell>
                                <TableCell>{row.dict === "PER_USER" ? "menu" : "menu niedostępne"}</TableCell>                                                          
                            </TableRow>
                        ))}
                        </TableBody>
                    </Table>
                </TableContainer>
        )
    }
}