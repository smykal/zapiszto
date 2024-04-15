import axios from 'axios';
import authHeader from '../auth-header';
import { NewWorkbook } from '../../types/types';


const API_URL = 'http://localhost:8080/v1/';

class WorkbooksService {
  getWorkbooks() {
    return axios.get(API_URL + 'get_workbooks', { headers: authHeader() });
  }

  postNewWorkbook(name: string) {
    const newWorkbook: NewWorkbook = {
      name: name
    }
    return axios.post(API_URL + 'add_workbook', newWorkbook, { headers: authHeader() })
      .then(response => {
        console.log('Odpowiedź z serwera:', response.data);
      })
      .catch(error => {
        console.error('Błąd podczas wysyłania zapytania:', error);
      });
  }

  deleteWorkbook(id: number) {
    return axios.delete(API_URL + 'delete_workbook/' + id, { headers: authHeader() })
      .then(response => {
        console.log('Workbook deleted successfully');
        return response.data;
      })
      .catch(error => {
        console.error('Error deleting workbook:', error);
        throw error;
      });
  }

}

export default new WorkbooksService()