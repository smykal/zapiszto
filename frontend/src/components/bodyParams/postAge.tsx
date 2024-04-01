import React, { Component } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import Service from "../../services/bodyParams";

type Props = {};

type State = {
  birthdate: Date | null;
};

export default class PostBirthDate extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      birthdate: null,
    };
    this.handleDateChange = this.handleDateChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleDateChange(date: Date | null) {
    this.setState({ birthdate: date });
  }

  handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    const { birthdate } = this.state;
    if (birthdate !== null) {
      // Wysłanie daty urodzenia do serwisu
      Service.postBirthDate(birthdate);
    } else {
      console.log("Wybierz datę urodzenia.");
    }
  }

  render() {
    return (
      <div className="container">
        <form onSubmit={this.handleSubmit}>
          <label>
            Wybierz datę urodzenia:
            <DatePicker
              selected={this.state.birthdate}
              onChange={this.handleDateChange}
              dateFormat="yyyy-MM-dd"
            />
          </label>
          <br />
          <button type="submit">Wyślij</button>
        </form>
      </div>
    );
  }
}
