import axios from 'axios';
import authHeader from '../auth-header';
import {API_URL} from '../../constants/api'
import { BodyParamDto } from '../../types/types';

class BodyParamsService {

  getDictBodyParams() {
    return axios.get(API_URL + '/dictBodyParams', { headers: authHeader() });
  }

  getActualBodyParams() {
    return axios.get(API_URL + '/actual_body_params', { headers: authHeader() });
  }
  getActualBodyParamsByUserId(userId: number) {
    return axios.get(API_URL + '/actual_body_params/' + userId, { headers: authHeader() });
  }

  getAllBodyParams() {
    return axios.get(API_URL + '/all_body_params', { headers: authHeader() });
  }

  getAllBodyParamsByUserId(userId: number) {
    return axios.get(API_URL + '/all_body_params/' + userId, { headers: authHeader() });
  }

  getBmiParams() {
    return axios.get(API_URL + '/bmi', { headers: authHeader() });
  }

  getGender() {
    return axios.get(API_URL + '/get_sex', { headers: authHeader() });
  }

  getGenderByUserId(userId: number) {
    return axios.get(`${API_URL}/get_sex/${userId}`, { headers: authHeader() });
  }

  getAge() {
    return axios.get(API_URL + '/get_age', { headers: authHeader() });
  }

  getAgeByUserId(userId: number) {
    return axios.get(`${API_URL}/get_age/${userId}`, { headers: authHeader() });
  }

  getBmr() {
    return axios.get(API_URL + '/get_bmr', { headers: authHeader() });
  }

  getCpf() {
    return axios.get(API_URL + '/cpf', {headers: authHeader() });
  }

  postBodyParam(bodyParamDto: BodyParamDto) {
    return axios.post(API_URL + '/add_body_param', bodyParamDto, { headers: authHeader() })
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
    return axios.post(API_URL + '/post_sex', gender, { headers: authHeader() })
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
    return axios.post(API_URL + '/post_birthdate', birthdate, { headers: authHeader() })
      .then(response => {
        console.log('Odpowiedź z serwera:', response.data);
      })
      .catch(error => {
        console.error('Błąd podczas wysyłania zapytania:', error);
      });
  }
}

export default new BodyParamsService();