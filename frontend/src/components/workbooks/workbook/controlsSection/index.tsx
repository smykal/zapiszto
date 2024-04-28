import React, { Component } from 'react';
import { Workbook } from '../../../../types/types';
import Service from '../../../../services/workbooks'
import { DictWorkbookSchema } from '../../../../types/types';
import { Formik, Form, Field, ErrorMessage } from 'formik';

type Props = {
  workbook: Workbook;
};

type State = {
  dictWorkbookSchema: DictWorkbookSchema[];
  selectedSchemaName: string; // Zmień na selectedSchemaId
}

class  ControlsSection extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      dictWorkbookSchema: [],
      selectedSchemaName: '' // Zmień na selectedSchemaId
    }
  }

  componentDidMount() {
    Service.getDictWorkbookSchema()
      .then(response => {
        const dictWorkbookSchema: DictWorkbookSchema[] = response.data;
        this.setState({ dictWorkbookSchema });
      })
      .catch(error => {
        console.error('Error fetching dictWorkbookSchema:', error);
      });
    
  }

  handleDeleteClick = () => {
    const id = this.props.workbook.id.valueOf();
    Service.deleteWorkbook(id)
      .then(() => {
        console.log('Workbook deleted successfully');
        window.location.reload();
      })
      .catch(error => {
        console.error('Error deleting workbook:', error);
      });
  }

  handleUpdateDictWorkbookSchema = (values: { selectedSchemaName: string }) => {
    const { selectedSchemaName } = values;
    const { id } = this.props.workbook;
    const selectedSchema = this.state.dictWorkbookSchema.find(schema => schema.name === selectedSchemaName);
    if (selectedSchema) {
      const selectedSchemaId = selectedSchema.id;
      Service.putWorkbookSchemaId(id, selectedSchemaId)
        .then(() => {
          console.log('Workbook schema updated successfully');
          window.location.reload();
        })
        .catch(error => {
          console.error('Error updating workbook schema:', error);
        });
    } else {
      console.error('Selected schema not found');
    }
  }

  render() {
    const { workbook } = this.props;
    const { dictWorkbookSchema, selectedSchemaName } = this.state;

  
    return (
      <div className="controls-section">
        <div className="workbook-info">
          <p>ID: {workbook.id} 
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
          Order Number: {workbook.order_number}
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
          Insert Date: {workbook.insert_date}</p>
          <p>Name: {workbook.name}</p>
          <p>Workbook Schema name: {workbook.dict_workbook_schema_name}</p>
        </div>
        <div className="workbook-actions">
          <div className='input_workbook'>
            <Formik initialValues={{ selectedSchemaName: selectedSchemaName }} onSubmit={this.handleUpdateDictWorkbookSchema}>
              {({ values, handleSubmit }) => (
                <Form onSubmit={handleSubmit}>
                  <label htmlFor='selectedSchemaName'>Select Workbook Schema:</label>
                  <Field as='select' name='selectedSchemaName'>
                    <option value=''>Select...</option>
                    {dictWorkbookSchema.map(schema => (
                      <option key={schema.id} value={schema.name}>
                        {schema.name}
                      </option>
                    ))}
                  </Field>
                  <ErrorMessage name='selectedSchemaName' component='div' className='error' />
                  <button type='submit'>Update Workbook Schema</button>
                </Form>
              )}
            </Formik>
          </div>
          <div className='input_workbook'>
            <button onClick={this.handleDeleteClick}>Delete Workbook</button>
          </div>
        </div>
      </div>
    );
  }
}

export default ControlsSection;
