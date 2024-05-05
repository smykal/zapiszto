import React, { Component } from "react";
import Service from "../../services/bodyParams";
import PostGender from "./postGender";

type Props = {};

type State = {
  gender: string | null;
  loading: boolean;
};

export default class GetGender extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      gender: null,
      loading: true
    };
  }

  componentDidMount() {
    this.getGender();
  }

  getGender() {
    Service.getGender()
      .then(response => {
        if (response.status === 200) {
          const gender = response.data.gender;
        this.setState({ gender, loading: false });
        } else if (response.status === 204) {
          this.setState( {gender: null, loading: false });
        } else {
          console.log("Nieoczekiwany kod stanu:", response.status);
          this.setState({ loading: false }); // Ustawienie stanu ładowania na false w przypadku błędu
        }
      })
      .catch(error => {
        console.log("Błąd pobierania płci")
        this.setState({ loading: false });
      });
  }

  render() {
    const { gender, loading } = this.state;

    // Jeśli dane są nadal ładowane, wyświetl komunikat "Loading..."
    if (loading) {
      return <p>Loading...</p>;
    }

    // Jeśli gender nie jest null, wyświetl go
    if (gender !== null) {
      return (
        <div className="container">
          <p>Gender: {gender}</p>
        </div>
      );
    } else {
      // Jeśli gender jest null, wyświetl komponent PostGender
      return <PostGender />;
    }

    
  }
}
