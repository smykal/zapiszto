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
}

export default new WorkbooksService()