import React, { useState, useEffect } from "react";
import { DictExercises } from "../../../types/types";
import { useTranslation } from "react-i18next";
import Delete from "./Options";
import AddDictExercisePerUser from "./AddDictExercisePerUser";
import Service from '../../../services/exercises'
import TableContainer from '@mui/material/TableContainer';
import Table from '@mui/material/Table';
import TableHead from '@mui/material/TableHead';
import TableBody from '@mui/material/TableBody';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';

const ShowDictExercises = () => {
    const [dictExercises, setDictExercises] = useState<DictExercises[]>([]);
    const { t } = useTranslation("global");

    useEffect(() => {
        loadDictExercises();
    }, []);

    const loadDictExercises = () => {
        Service.getDictExercises()
            .then(response => {
                setDictExercises(response.data);
            })
            .catch(error => {
                console.error('Error loading dict exercises:', error);
            });
    };

    return (
        <div>
            <AddDictExercisePerUser dictExercises={dictExercises} />
            <TableContainer>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>{t("table.id")}</TableCell>
                            <TableCell>{t("table.name")}</TableCell>
                            <TableCell>{t("table.options")}</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                    {dictExercises.map((row) => (
                        <TableRow
                            key={row.id}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                            <TableCell component='th' scope="row"> {row.id}</TableCell>                                   
                            <TableCell>{row.name}</TableCell>
                            <TableCell>{row.dict === "PER_USER" ? <Delete item={row.dict_id} /> : "menu niedostępne"}</TableCell>                                                          
                        </TableRow>
                    ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
};

export default ShowDictExercises;
