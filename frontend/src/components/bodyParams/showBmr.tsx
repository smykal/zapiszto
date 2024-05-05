import React, { Component } from "react";
import Service from "../../services/bodyParams";
import { BmrType } from "../../types/types";

type Props = {};
type State = {
    bmrList: BmrType[] | null;
    date: string | null;
    loading: boolean;
}

export default class GetBmr extends Component<Props, State> {
    constructor(props: Props) {
        super(props);

        this.state = {
            bmrList: [],
            date: null,
            loading: true
        };
    }

    componentDidMount() {
        Service.getBmr().then(
            (response) => {
                if (response.status === 200) {
                    const bmrList = response.data;
                    const date = bmrList.length > 0 ? bmrList[0].insert_date : null;
                    this.setState({ bmrList, date, loading: false });
                } else if (response.status === 204) {
                    this.setState({ bmrList: null, date: null, loading: false });
                } else {
                    console.log("Nieoczekiwany kod stanu:", response.status);
                    this.setState({ loading: false }); // Ustawienie stanu ładowania na false w przypadku błędu
                }
            },
            (error) => {
                console.log("Błąd pobierania BMR:", error);
            }
        )
    }

    render() {
        const { bmrList, date, loading } = this.state;

        if (loading) {
            return <p>Loading...</p>;
        }

        if (bmrList == null) {
            return <p>Brak danych</p>
        } else {
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
}
