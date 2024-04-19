import axios from 'axios';
import authHeader from '../auth-header';
import { DictWorkbookSchema, WorkbookSchemaIdPut,  NewWorkbook } from '../../types/types';


const API_URL = 'http://localhost:8080/v1';

class WorkbooksService {
  getWorkbooks() {
    return axios.get(API_URL + '/get_workbooks', { headers: authHeader() });
  }

  getDictWorkbookSchema() {
    return axios.get(API_URL + '/get_dict_workbook_schema', { headers: authHeader() });
  }

  postNewWorkbook(name: string) {
    const newWorkbook: NewWorkbook = {
      name: name
    }
    return axios.post(API_URL + '/add_workbook', newWorkbook, { headers: authHeader() })
      .then(response => {
        console.log('Odpowiedź z serwera:', response.data);
      })
      .catch(error => {
        console.error('Błąd podczas wysyłania zapytania:', error);
      });
  }

  deleteWorkbook(id: number) {
    return axios.delete(API_URL + '/delete_workbook/' + id, { headers: authHeader() })
      .then(response => {
        console.log('Workbook deleted successfully');
        return response.data;
      })
      .catch(error => {
        console.error('Error deleting workbook:', error);
        throw error;
      });
  }

  putWorkbookSchemaId(workbookId: number, dictWorkbookId: number) {
    const workbookSchemaPut: WorkbookSchemaIdPut = {
      id: workbookId,
      dict_workbook_schema_id: dictWorkbookId
    };

    return axios.patch(API_URL + '/put_workbook_schema_id', workbookSchemaPut, { headers: authHeader() })
    .then(response => {
      console.log('Workbook updated successfully');
      return response.data;
    })
    .catch(error => {
      console.error('Error updating workbook:', error);
      throw error;
    });
  }


}

export default new WorkbooksService()