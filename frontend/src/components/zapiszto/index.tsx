import { Component } from "react";

import UserService from "../../services/user.service";
import Service from "../../services/zapiszto"
import EventBus from "../../common/EventBus";
import TestForm from '../../components/zapiszto/testForm'

type Props = {};

type State = {
  content: string;
  responseData: any[];
}

export default class BoardUser extends Component<Props, State> {
  constructor(props: Props) {
    super(props);

    this.state = {
      content: "",
      responseData: []
    };
  }

  componentDidMount() {
    UserService.getUserBoard().then(
      response => {
        this.setState({
          content: response.data
        });
      },
      error => {
        this.setState({
          content:
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString()
        });

        if (error.response && error.response.status === 401) {
          EventBus.dispatch("logout");
        }
      }
    );
Service.getActualBodyParams()
      //Service.getTestData()
      .then(
        (response) => {
            this.setState({
                responseData: response.data,
            });
        },
        (error) => {
            console.log("jakis błąd", error)
        }
      )
  }
  

  render() {
    return (
      <div className="container">
        <header className="jumbotron">
          <h3>{this.state.content}</h3>
          <ul>
            {this.state.responseData.map((item, index) => (
              <li key={index}>
                <strong>Kolumna 1:</strong> {item.kolumna_1},{" "}
                <strong>Kolumna 2:</strong> {item.kolumna_2},{" "}
                <strong>userId:</strong> {item.userId}
              </li>
            ))}
          </ul>
          <div className="sa">
          <TestForm />
      </div>
        </header>
      </div>
      
    );
  }
}
