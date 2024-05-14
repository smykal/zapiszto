import { Component } from 'react';
import { Workbook } from '../../../../types/types';
import PostTraining from './postTraining';
import ShowTrainings from './showTrainings';

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
        <PostTraining workbook_id={workbook.id} />
        <ShowTrainings workbook_id={workbook.id} />
      </div>
    );
  }
}

export default DisplaySection;
