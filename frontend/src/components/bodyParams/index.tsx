import { Component } from "react";
import React from 'react';
import PutBodyParam from './putBodyParam'
import ShowBodyParams from "./showBodyParams";
import ShowDiagram from "./showDiagramAll";

export default class BodyParams extends Component {

  render() {
    return (
      <div>
            <ShowBodyParams />
            <PutBodyParam clientId={null}/>
            <ShowDiagram />
      </div>
    );
  }
}
