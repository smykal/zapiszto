import { Component } from "react";
import { DictQuantityType } from "../../../types/types";
import Service from '../../../services/exercises'
import TableContainer from '@mui/material/TableContainer';
import Table from '@mui/material/Table';
import TableHead from '@mui/material/TableHead';
import TableBody from '@mui/material/TableBody';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';

type Props = {};
type State = {
    dictQuantityTypes: DictQuantityType[]
};

export default class ShowDictQuantityType extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            dictQuantityTypes: []
        };
    }

    componentDidMount() {
        this.loadDictQuantityTypes();
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
        const {dictQuantityTypes} = this.state;
        return (
            <div>
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
                        {dictQuantityTypes.map((row) => (
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
            </div>
        )
    }
}