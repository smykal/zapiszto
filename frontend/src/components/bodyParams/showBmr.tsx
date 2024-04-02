import React, { Component } from "react";
import Service from "../../services/bodyParams";
import { BmrType } from "../../types/types"; 

type Props = {};
type State = {
    bmrList: BmrType[];
    date: string | null;
}

export default class GetBmr extends Component<Props, State> {
    constructor(props: Props) {
        super(props);

        this.state = {
            bmrList: [],
            date: null
        };
    }

    componentDidMount() {
        Service.getBmr().then(
            (response) => {
                const bmrList = response.data;
                const date = bmrList.length > 0 ? bmrList[0].insert_date : null;
                this.setState({
                    bmrList,
                    date
                });
            },
            (error) => {
                console.log("Błąd:", error);
            }
        )
    }

    render()  {
        const { bmrList, date } = this.state;
        return (
            <div className="container">
                {date && <p><strong>Wartości BMR przeliczone dla dnia:</strong> {date}</p>}
                {bmrList.map((bmr, index) => (
                    <div key={index}>
                        <p>
                            <strong> BMR:</strong> {bmr.bmr}
                            <strong> Opis:</strong> {bmr.category}
                        </p>
                    </div>
                ))}
            </div>
        )
    }
}
