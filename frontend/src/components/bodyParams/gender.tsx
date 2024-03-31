import React, { Component } from "react";
import Service from "../../services/bodyParams";

type Props = {};

type State = {
  gender: string;
};

export default class Gender extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      gender: "", // Stan początkowy - brak wybranej płci
    };
    this.handleGenderChange = this.handleGenderChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleGenderChange(event: React.ChangeEvent<HTMLSelectElement>) {
    this.setState({ gender: event.target.value });
  }

  handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    const { gender } = this.state;
    if (gender) {
      Service.postSex(gender); // Wysyłanie wybranej płci do metody Service.putAge()
    } else {
      console.log("Nie wybrano płci.");
    }
  }

  render() {
    return (
      <div className="container">
        <form onSubmit={this.handleSubmit}>
          <label>
            Wybierz płeć:
            <select value={this.state.gender} onChange={this.handleGenderChange}>
              <option value="">Wybierz...</option>
              <option value="male">Mężczyzna</option>
              <option value="female">Kobieta</option>
            </select>
          </label>
          <button type="submit">Wyślij</button>
        </form>
      </div>
    );
  }
}
