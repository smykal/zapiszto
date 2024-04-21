import React, { Component } from 'react';
import { Workbook } from '../../../types/types';
import ControlsSection from './controlsSection';
import DisplaySection from './displaySection';
import AnalysisSection from './analysisSection';

type Props = {
  workbook: Workbook;
};

class Wrapper extends Component<Props> {
  constructor(props: Props) {
    super(props);
  }

  render() {
    const { workbook } = this.props;
    return (
      <div>
        <ControlsSection workbook={workbook} />
        {workbook.dict_workbook_schema_name !== 'brak_schematu' && (
          <>
            <DisplaySection workbook={workbook} />
            <AnalysisSection />
          </>
        )}
      </div>
    );
  }
}

export default Wrapper;
