import React, { Component } from "react";

import PutBodyParam from './putBodyParam'
import ShowBodyParams from "./showBodyParams";
import ShowDiagram from "./showDiagramAll";

// type Props = {};
// type State = {};
// <Props, State>
export default class BodyParams extends Component {
  // constructor(props: Props) {
  //   super(props);
  //   this.state = {};
  // }
  render() {
    return (
      <div className="container">
          <div className="sa">
            <ShowBodyParams />
          </div>
          <div className="sa1">
            <PutBodyParam />
          </div>
          <div className="sa2">
            <ShowDiagram />
          </div>
      </div>
    );
  }
}
