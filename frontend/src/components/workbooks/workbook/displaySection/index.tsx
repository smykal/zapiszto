import React, { Component } from 'react';
import { Workbook } from '../../../../types/types';
import PostTraining from './postTraining';

type Props = {
  workbook: Workbook
}



class DisplaySection extends Component<Props> {
  constructor(props: Props) {
    super(props);
  }

  render() {
    const { workbook } = this.props;
    return (
      <div>
        <p>.................................................................................................</p>
        <PostTraining workbook_id={workbook.id} />
        <h1>Workbook Details for workbook: {workbook.id}</h1>
        //tutaj dodaj trening 
        <p>tekst</p>
        <p>tekst</p>
        <p>tekst</p>
        <p>tekst</p>
        <p>.................................................................................................</p>
      </div>
    );
  }
}

export default DisplaySection;
