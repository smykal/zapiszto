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
      <div style={{ width: "400px", height: "500px" }}>
        <header>
          <h3>{this.state.content}</h3>
          <p>test_user_account: Alinka, pw: Alinka</p>
          <p>test_trener_account: trener, pw: trener</p>
          <p>backend stand up after 180 seconds becouse right now it is being hosted at free version of render</p>
        </header>
      </div>
    );
  }
}
