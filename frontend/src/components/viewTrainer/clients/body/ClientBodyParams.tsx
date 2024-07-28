import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Client, BodyParamsItem } from '../../../../types/types';
import Service from '../../../../services/bodyParams';
import DisplayBodyParamsByUserId from './DisplayBodyParamsByUserId';
import Collapsible from 'react-collapsible';
import PutBodyParam from '../../../bodyParams/putBodyParam';

interface ClientDetailsProps {
    client?: Client; // Użyj opcjonalnego typu
}

const ClientBodyParams: React.FC<ClientDetailsProps> = ({ client }) => {
    const { t } = useTranslation('global');
    const [bodyParams, setBodyParams] = useState<BodyParamsItem[]>([]);
    const [allBodyParams, setAllBodyParams] = useState<BodyParamsItem[]>([]);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        if (client) {
            Service.getActualBodyParamsByUserId(client.userId).then((response) => {
                setBodyParams(response.data || []); // Upewnij się, że dane są tablicą
                setIsLoading(false);
            }).catch((error) => {
                console.log("Błąd podczas pobierania danych:", error);
                setIsLoading(false);
            });
        }
    }, [client]);

    useEffect(() => {
        if (client) {
            Service.getAllBodyParamsByUserId(client.userId).then((response) => {
                setAllBodyParams(response.data || []); // Upewnij się, że dane są tablicą
            }).catch((error) => {
                console.log("Błąd podczas pobierania danych:", error);
            });
        }
    }, [client]);

    if (!client) {
        return <div>{t('client_not_set')}</div>; // Możesz wyświetlić komunikat, że klient nie jest ustawiony
    }

    if (isLoading) {
        return <div>Loading...</div>; // Możesz wyświetlić komunikat ładowania
    }

    return (
        <div>
            <ul>
                {Array.isArray(bodyParams) && bodyParams.map((item: BodyParamsItem, index: number) => (
                    <li key={index} id="bodyParams">
                        <Collapsible
                            trigger={`Actual ${item.dict_body_params_name}: ${item.value} >>`}
                            openedClassName="Collapsible__content--open">
                            <DisplayBodyParamsByUserId
                                param_name={item.dict_body_params_name}
                                userId={client.userId}
                                allBodyParams={allBodyParams} />
                        </Collapsible>
                    </li>
                ))}
                <PutBodyParam clientId={client.id}/>
            </ul>
        </div>
    );
};

export default ClientBodyParams;
