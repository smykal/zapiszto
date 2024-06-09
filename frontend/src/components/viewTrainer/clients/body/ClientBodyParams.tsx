import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Client, BodyParamsItem } from '../../../../types/types';
import Service from '../../../../services/bodyParams';
import DisplayBodyParamsByUserId from './DisplayBodyParamsByUserId';
import Collapsible from 'react-collapsible'; // Importuj Collapsible


interface ClientDetailsProps {
    client: Client
}

const ClientBodyParams: React.FC<ClientDetailsProps> =  ({ client }) => {
    const { t } = useTranslation('global');
    const [bodyParams, setBodyParams] = useState<BodyParamsItem[]>([]);
    const [allBodyParams, setAllBodyParams] = useState<BodyParamsItem[]>([]);


    useEffect(() => {
        Service.getActualBodyParamsByUserId(client.userId).then((response) => {
            setBodyParams(response.data);
        }).catch((error) => {
            console.log("Błąd podczas pobierania danych:", error);
        });
    }, [client.userId]);

    useEffect(() => {
        Service.getAllBodyParamsByUserId(client.userId).then((response) => {
            setAllBodyParams(response.data);
        }).catch((error) => {
            console.log("Błąd podczas pobierania danych:", error);
        });
    }, [client.userId]);

    return (
        <div>
            <ul>
                {bodyParams.map((item: BodyParamsItem, index: number) => (
                    <li key={index} id="bodyParams">
                        <Collapsible 
                            trigger={`Actual ${item.dict_body_params_name}: ${item.value} >>`}
                            openedClassName="Collapsible__content--open" >
                            <DisplayBodyParamsByUserId 
                                param_name={item.dict_body_params_name} 
                                userId={client.userId} 
                                allBodyParams={allBodyParams} />
                        </Collapsible>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ClientBodyParams;
