import { Component } from "react";

import PutBodyParam from './putBodyParam'
import ShowBodyParams from "./showBodyParams";
import ShowDiagram from "./showDiagramAll";

export default class BodyParams extends Component {

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
