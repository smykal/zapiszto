import { Component } from "react";
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import Service from "../../services/zapiszto"

type BodyParamsItem = {
  dict_body_params_name: string;
  value: number;
  insert_date: Date;
};

type Props = {};
type State = {
    bodyParams: BodyParamsItem[];
};

export default class ShowDiagram extends Component<Props, State> {
  constructor(props: Props) {
    super(props);

    this.state = {
        bodyParams: []
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
    // Grupowanie danych według dict_body_params_name
    const groupedData: { [key: string]: BodyParamsItem[] } = {};
    this.state.bodyParams.forEach((item) => {
      if (!groupedData[item.dict_body_params_name]) {
        groupedData[item.dict_body_params_name] = [];
      }
      groupedData[item.dict_body_params_name].push(item);
    });
    console.log("Grupowane dane:", groupedData);

    // Przygotowanie serii danych dla wykresu
    const chartLines = Object.keys(groupedData).map((paramName) => (
      <Line
        key={paramName}
        type="monotone"
        dataKey="value"
        name={paramName}
        data={groupedData[paramName]}
        stroke={`#${Math.floor(Math.random()*16777215).toString(16)}`} // losowy kolor dla każdej linii
      />
    ));

    return (
      <ResponsiveContainer width="100%" height="100%">
        <LineChart
          width={500}
          height={300}
          data={this.state.bodyParams}
          margin={{
            top: 5,
            right: 30,
            left: 20,
            bottom: 5,
          }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis  
                    dataKey="insert_date" 
                   angle={-90} 
                   textAnchor="end" 
                   height={100} />
          <YAxis />
          <Tooltip />
          <Legend />
          {chartLines}
        </LineChart>
      </ResponsiveContainer>
    );
  }
}
