import axios from 'axios';
import authHeader from '../auth-header';

// const API_URL = 'http://localhost:8080/v1/';

const API_URL = 'https://zapiszto-service.onrender.com/v1/';

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

  getGender() {
    return axios.get(API_URL + 'get_sex', { headers: authHeader() });
  }

  getAge() {
    return axios.get(API_URL + 'get_age', { headers: authHeader() });
  }

  getBmr() {
    return axios.get(API_URL + 'get_bmr', { headers: authHeader() });
  }

  getCpf() {
    return axios.get(API_URL + 'cpf', {headers: authHeader() })
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

  postSex(sex: string) {
    const gender = {
      gender: sex
    }
    return axios.post(API_URL + 'post_sex', gender, { headers: authHeader() })
      .then(response => {
        console.log('Odpowiedź z serwera:', response.data);
      })
      .catch(error => {
        console.error('Błąd podczas wysyłania zapytania:', error);
      });
  }

  postBirthDate(date: Date) {
    const birthdate = {
      birthDate: date
    }
    return axios.post(API_URL + 'post_birthdate', birthdate, { headers: authHeader() })
      .then(response => {
        console.log('Odpowiedź z serwera:', response.data);
      })
      .catch(error => {
        console.error('Błąd podczas wysyłania zapytania:', error);
      });
  }
}

export default new ZapiszToService();