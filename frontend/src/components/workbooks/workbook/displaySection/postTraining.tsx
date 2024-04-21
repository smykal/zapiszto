import React, { Component } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import Service from "../../../../services/workbooks";

type Props = {
    workbook_id: number
};

type State = {
  date: Date | null;
};

export default class PostTraining extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      date: new Date(),
    };
    this.handleDateChange = this.handleDateChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleDateChange(date: Date | null) {
    this.setState({ date: date });
  }

  handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    const { date } = this.state;
    if (date !== null) {
      // Wysłanie nowego treningu do serwisu
      Service.postNewTraining(this.props.workbook_id, date);
    } else {
      console.log("Wybierz datę treningu.");
    }
  }

  render() {
    return (
      <div className="container">
        <form onSubmit={this.handleSubmit}>
        <label> Wybierz datę treningu: </label>
            <DatePicker
              selected={this.state.date}
              onChange={this.handleDateChange}
              dateFormat="yyyy-MM-dd"
            />
            <br/>
            <button type="submit">Dodaj trening</button>
        </form>
      </div>
    );
  }
}
