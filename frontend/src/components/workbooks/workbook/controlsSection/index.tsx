import React, { Component } from 'react';
import { Workbook } from '../../../../types/types';
import Service from '../../../../services/workbooks'
import workbooks from '../../../../services/workbooks';

type Props = {
  workbook: Workbook;
};



class ControlsSection extends Component<Props> {
  constructor(props: Props) {
    super(props);
  }

  handleDeleteClick = () => {
    const id = this.props.workbook.id.valueOf();
    Service.deleteWorkbook(id).then(() => {
      console.log('Workbook deleted successfully');
      window.location.reload();
    })
      .catch(error => {
        console.error('Error deleting workbook:', error);
      });
  }

  render() {
    const { workbook } = this.props;
    return (
      <div>
        <p>ID: {workbook.id}</p>
        <p>Name: {workbook.name}</p>
        <p>Order Number: {workbook.order_number}</p>
        <p>Insert Date: {workbook.insert_date}</p>
        <button onClick={this.handleDeleteClick}>Delete Workbook</button>
      </div>
    );
  }
}

export default ControlsSection;
