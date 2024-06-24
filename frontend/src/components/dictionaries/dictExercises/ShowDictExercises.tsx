import React, { useState, useEffect } from "react";
import { DictCategories, DictExercises } from "../../../types/types";
import { useTranslation } from "react-i18next";
import Options from "./Options";
import AddDictExercisePerUser from "./AddDictExercisePerUser";
import Service from '../../../services/exercises';

const ShowDictExercises = () => {
    const [dictExercises, setDictExercises] = useState<DictExercises[]>([]);
    const [dictCategories, setDictCategories] = useState<DictCategories[]>([]);
    const [showBasic, setShowBasic] = useState(true);
    const [showPerUser, setShowPerUser] = useState(true);
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

    const handleShowBasicChange = () => {
        setShowBasic(!showBasic);
    };

    const handleShowPerUserChange = () => {
        setShowPerUser(!showPerUser);
    };

    const handleAddExercise = (newExercise: DictExercises) => {
        setDictExercises(prevExercises => [...prevExercises, newExercise]);
    };

    const handleDeleteExercise = (id: number) => {
        setDictExercises(prevExercises => prevExercises.filter(exercise => exercise.id !== id));
    };

    const filteredExercises = dictExercises.filter(exercise => {
        if (showBasic && exercise.dict === "BASIC") return true;
        if (showPerUser && exercise.dict === "PER_USER") return true;
        return false;
    });

    return (
        <div>
            <div>
                <label>
                    <input 
                        type="checkbox" 
                        checked={showBasic} 
                        onChange={handleShowBasicChange} 
                    />
                    BASIC
                </label>
                <label>
                    <input 
                        type="checkbox" 
                        checked={showPerUser} 
                        onChange={handleShowPerUserChange} 
                    />
                    PER_USER
                </label>
            </div>
            <AddDictExercisePerUser 
                dictExercises={dictExercises} 
                dictCategories={dictCategories} 
                onAddExercise={handleAddExercise} 
            />
            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                    <tr>
                        <th>{t("table.id")}</th>
                        <th>{t("table.name")}</th>
                        <th>{t("table.category")}</th>
                        <th>{t("table.options")}</th>
                    </tr>
                </thead>
                <tbody>
                    {filteredExercises.map((row) => (
                        <tr key={row.id} style={{ borderBottom: '1px solid #ddd' }}>
                            <td>{row.id}</td>
                            <td>{row.name}</td>
                            <td>{row.category_name}</td>
                            <td>{row.dict === "PER_USER" ? <Options item={row.id} onDeleteExercise={handleDeleteExercise} /> : "menu niedostępne"}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ShowDictExercises;
