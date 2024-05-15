import { Component } from "react";
import { DictCategories } from "../../../types/types";
import Service from '../../../services/exercises'
import AddDictCategoryPerUser from "./AddDictCategoryPerUser";
import TableContainer from '@mui/material/TableContainer';
import Table from '@mui/material/Table';
import TableHead from '@mui/material/TableHead';
import TableBody from '@mui/material/TableBody';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';
import Options from './Options'
import { withTranslation } from "react-i18next";


type Props = {
    t: any
};
type State = {
    dictCategories: DictCategories[]
};
class ShowDictCategory extends Component<Props, State>  {
    constructor(props: Props) {
        super(props);
        this.state = {
            dictCategories: []
        };
    }
    componentDidMount() {
        this.loadDictCategories()
    }
    loadDictCategories() {
        Service.getDictCategory()
            .then(response => {
                this.setState({ dictCategories: response.data });
            })
            .catch(error => {
                console.error('Error loading dict quantity types:', error);
            });
    }



    render() {
        const { dictCategories } = this.state;
        const { t } = this.props;

        return (
            <div>
                <AddDictCategoryPerUser dictCategory={dictCategories} />
                <TableContainer>
                    <Table sx={{ minWidth: 650 }} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell>{t("table.id")}</TableCell>
                                <TableCell>{t("table.name")}</TableCell>
                                <TableCell>{t("table.description")}</TableCell>
                                <TableCell>{t("table.options")}</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                        {dictCategories.map((row) => (
                            <TableRow
                            key={row.id}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                                <TableCell component='th' scope="row"> {row.id}</TableCell>                                   
                                <TableCell>{row.name}</TableCell>
                                <TableCell>{row.description}</TableCell>
                                <TableCell>{row.dict === "PER_USER" ? <Options item={row.dict_id} /> : "menu niedostępne"}</TableCell>                                                          
                            </TableRow>
                        ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        )
    }
}

export default withTranslation("global")(ShowDictCategory)