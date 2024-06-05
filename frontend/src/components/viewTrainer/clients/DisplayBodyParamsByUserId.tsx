import React, { useEffect, useState } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { BodyParamsItem } from '../../../types/types';
import ZapiszToService from '../../../services/bodyParams';

interface DisplayBodyParamsProps {
    param_name: string;
    userId: number;
    allBodyParams: BodyParamsItem[]; // Dodaj props allBodyParams
}

const DisplayBodyParamsByUserId: React.FC<DisplayBodyParamsProps> = ({ param_name, userId, allBodyParams }) => {
    const filteredParams = allBodyParams.filter(item => item.dict_body_params_name === param_name);

    const chartData = filteredParams.map(item => ({
        insert_date: item.insert_date,
        value: item.value,
    }));

    return (
        <div style={{ display: 'flex' }}>
            <div style={{ flex: '1' }}>
                <ResponsiveContainer width={300} height={200}>
                    <LineChart data={chartData}>
                        <CartesianGrid strokeDasharray="3 3" fill="#ffffff" />
                        <XAxis dataKey="insert_date" angle={45} tick={{ display: 'none' }} />
                        <YAxis type="number" domain={['chartData.values - 200', 'chartData.values + 200']} />
                        <Tooltip />
                        <Legend />
                        <Line type="monotone" dataKey="value" stroke="#8884d8" />
                    </LineChart>
                </ResponsiveContainer>
            </div>
            <div style={{ flex: '3' }}>
                {filteredParams.map((item, index) => (
                    <p key={index}>
                        <strong>Date: </strong> {item.insert_date}         
                        <strong> Value: </strong> {item.value}
                    </p>
                ))}
            </div>
        </div>
    );
};

export default DisplayBodyParamsByUserId;