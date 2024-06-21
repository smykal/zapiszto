import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import GoalsService from '../../../../services/goals/GoalsService'; 
import { Goal } from "../../../../types/types"; 

const GetGoals = ({ clientId }: { clientId: string }) => {
    const [goals, setGoals] = useState<Goal[]>([]);
    const { t } = useTranslation("global");

    useEffect(() => {
        loadGoals();
    }, [clientId]);

    const loadGoals = () => {
        GoalsService.getGoals(clientId)
            .then(response => {
                setGoals(response.data);
            })
            .catch(error => {
                console.error('Error loading goals:', error);
            });
    };

    return (
        <div>
            <h2>{t('goals.title')}</h2>
            <table style={{ minWidth: '650px', width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                    <tr>
                        <th>{t("table.goalType")}</th>
                        <th>{t("table.dictBodyParam")}</th>
                        <th>{t("table.dictBodyTest")}</th>
                        <th>{t("table.dictUnit")}</th>
                        <th>{t("table.action")}</th>
                        <th>{t("table.value")}</th>
                        <th>{t("table.goalDate")}</th>
                        <th>{t("table.insertDate")}</th>
                    </tr>
                </thead>
                <tbody>
                    {goals.map((goal) => (
                        <tr key={goal.id} style={{ borderBottom: '1px solid #ddd' }}>
                            <td>{goal.goalType}</td>
                            <td>{goal.dictBodyParam}</td>
                            <td>{goal.dictBodyTest}</td>
                            <td>{goal.dictUnit}</td>
                            <td>{goal.action}</td>
                            <td>{goal.value}</td>
                            <td>{new Date(goal.goalDate).toLocaleDateString()}</td>
                            <td>{new Date(goal.insertDate).toLocaleDateString()}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default GetGoals;
