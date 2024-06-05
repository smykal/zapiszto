import React, { Component } from "react";

import UserService from "../../services/user.service";

type Props = {};

type State = {
  content: string;
}

export default class Home extends Component<Props, State> {
  constructor(props: Props) {
    super(props);

    this.state = {
      content: ""
    };
  }

  componentDidMount() {
    UserService.getPublicContent().then(
      response => {
        this.setState({
          content: response.data
        });
      },
      error => {
        this.setState({
          content:
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        });
      }
    );
  }

  render() {
    return (
      <div style={{ width: "400px", height: "500px" }} className="alinka">
        <header>
          <h3>{this.state.content}</h3>
        </header>
      </div>
    );
  }
}
