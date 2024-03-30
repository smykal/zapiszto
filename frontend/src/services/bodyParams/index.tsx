import axios from 'axios';
import authHeader from '../auth-header';

const API_URL = 'http://localhost:8080/v1/';

class ZapiszToService {

  getDictBodyParams() {
    return axios.get(API_URL + 'dictBodyParams', { headers: authHeader() });
  }

  getActualBodyParams() {
    return axios.get(API_URL + 'actual_body_params', { headers: authHeader() });
  }

  getAllBodyParams() {
    return axios.get(API_URL + 'all_body_params', { headers: authHeader() });
  }

  getBmiParams() {
    return axios.get(API_URL + 'bmi', { headers: authHeader() });

  }

  postBodyParam(field_1: string, field_2: string, userId: number) {
    const test_data = {
      dict_body_params_id: field_1,
      value: field_2,
      userId: userId
    };
    return axios.post(API_URL + 'add_body_param', test_data, { headers: authHeader() })
      .then(response => {
        console.log('Odpowiedź z serwera:', response.data);
      })
      .catch(error => {
        console.error('Błąd podczas wysyłania zapytania:', error);
      });
  }
}

export default new ZapiszToService();