import { Component } from "react";

import TestForm from '../../components/zapiszto/testForm'
import ShowBodyParams from "./showBodyParams";

type Props = {};
type State = {};

export default class BodyParams extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {};
  }
  render() {
    return (
      <div className="container">
          <div className="sa">
            <ShowBodyParams />
          </div>
          <div className="sa">
            <TestForm />
          </div>
      </div>
    );
  }
}
