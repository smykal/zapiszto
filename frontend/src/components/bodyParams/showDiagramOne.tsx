import { Component } from 'react';
import Service from "../../services/bodyParams";
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { BodyParamsItem } from '../../types/types';

type Props = {
    param_name: string;
};

type State = {
    bodyParams: BodyParamsItem[];
};

class DisplayBodyParams extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            bodyParams: [],
        };
    }

    componentDidMount() {
        Service.getAllBodyParams().then(
            (response) => {
                // Przetwarzanie danych przed zapisaniem do stanu
                const formattedData = response.data.map((item: BodyParamsItem) => ({
                    ...item,
                    insert_date: new Date(item.insert_date).toISOString().split('T')[0],
                }));

                formattedData.sort((a: any, b: any) => new Date(a.insert_date).getTime() - new Date(b.insert_date).getTime());

                this.setState({
                    bodyParams: formattedData,
                });
            },
            (error) => {
                console.log("Jakiś błąd", error);
            }
        );
    }

    render() {
        const { param_name } = this.props;
        const { bodyParams } = this.state;

        // Filtruj dane przed wyświetleniem
        const filteredParams = bodyParams.filter(item => item.dict_body_params_name === param_name);

        // Przygotuj dane dla wykresu
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
                <div style={{ flex: '1' }}>
                    {filteredParams.map((item, index) => (
                        <p key={index}>
                            <strong>date: </strong> {item.insert_date}         
                            <strong> value : </strong> {item.value}
                        </p>
                    ))}
                </div>
            </div>
        );
    }
}

export default DisplayBodyParams;
