import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { Client, ClientBodyTest } from '../../../../types/types';
import ClientBodyTestsService from '../../../../services/bodyTests/ClientBodyTestsService';

interface ClientDetailsProps {
    client: Client;
}

const GetClientBodyTest: React.FC<ClientDetailsProps> = ({ client }) => {
    const { t } = useTranslation('global');
    const [bodyTests, setBodyTests] = useState<ClientBodyTest[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        ClientBodyTestsService.getClientBodyTest(client.id)
            .then(response => {
                setBodyTests(response.data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Błąd podczas pobierania danych:', error);
                setLoading(false);
            });
    }, [client.id]);

    if (loading) {
        return <div>{t('loading')}</div>;
    }

    return (
        <div>
            <h2>{t('clients.show_done_body_tests')}</h2>
            {bodyTests.length === 0 ? (
                <p>{t('clients.no_body_tests_yet')}</p>
            ) : (
                <table style={{ minWidth: '650px', width: '100%', borderCollapse: 'collapse' }}>
                    <thead>
                        <tr>
                            <th>{t("table.id")}</th>
                            <th>{t("table.name")}</th>
                            <th>{t("table.description")}</th>
                            <th>{t("table.result")}</th>
                            <th>{t("table.purpose")}</th>
                            <th>{t("table.options")}</th>
                        </tr>
                    </thead>
                    <tbody>
                        {bodyTests.map(test => (
                            <tr key={test.id} style={{ borderBottom: '1px solid #ddd' }}>
                                <td>{test.id}</td>
                                <td>{test.name}</td>
                                <td>{test.description}</td>
                                <td>{test.result}</td>
                                <td>{test.purpose}</td>
                                <td>{test.dict}</td>

                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default GetClientBodyTest;
