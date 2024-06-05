import React, { useState, useEffect } from "react";
import { DictCategories, DictExercises } from "../../../types/types";
import { useTranslation } from "react-i18next";
import Options from "./Options";
import AddDictExercisePerUser from "./AddDictExercisePerUser";
import Service from '../../../services/exercises';

const ShowDictExercises = () => {
    const [dictExercises, setDictExercises] = useState<DictExercises[]>([]);
    const [dictCategories, setDictCategories] = useState<DictCategories[]>([]);
    const { t } = useTranslation("global");

    useEffect(() => {
        loadDictExercises();
        loadDictCategories();
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

    const loadDictCategories = () => {
        Service.getDictCategory()
            .then(response => {
                setDictCategories(response.data);
            })
            .catch(error => {
                console.error('Error loading dict categories:', error);
            });
    };

    return (
        <div>
            <AddDictExercisePerUser dictExercises={dictExercises} dictCategories={dictCategories} />
            <table style={{ minWidth: '650px', width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                    <tr>
                        <th>{t("table.id")}</th>
                        <th>{t("table.name")}</th>
                        <th>{t("table.category")}</th>
                        <th>{t("table.options")}</th>
                    </tr>
                </thead>
                <tbody>
                    {dictExercises.map((row) => (
                        <tr key={row.id} style={{ borderBottom: '1px solid #ddd' }}>
                            <td>{row.id}</td>
                            <td>{row.name}</td>
                            <td>{row.category_name}</td>
                            <td>{row.dict === "PER_USER" ? <Options item={row.dict_id} /> : "menu niedostępne"}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ShowDictExercises;
