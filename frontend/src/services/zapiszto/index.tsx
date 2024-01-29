import axios from 'axios';
import authHeader from '../auth-header';
import TestForm from '../../components/zapiszto/testForm';

const API_URL = 'http://localhost:8080/v1/';



class ZapiszToService {

  getDictBodyParams() {
    return axios.get(API_URL + 'dictBodyParams', { headers: authHeader() });
  }

  getActualBodyParams() {
    return axios.get(API_URL + 'actual_body_params', { headers: authHeader() });
  }

  getTestData() {
    return axios.get(API_URL + 'test_get', { headers: authHeader() });
  }

  

  postTestData(field_1 : string, field_2: string, userId: number) {
      const test_data = {
        kolumna_1: field_1,
        kolumna_2: field_2,
        userId: userId
      };
    return axios.post(API_URL + 'test_post', test_data, { headers: authHeader()  })
      .then(response => {
        console.log('Odpowiedź z serwera:', response.data);
      })
      .catch(error => {
        console.error('Błąd podczas wysyłania zapytania:', error);
      });
  }

}

export default new ZapiszToService();
