import axios from 'axios';
import authHeader from '../auth-header';
import { NewDictBodyTest, NewClientBodyTest } from '../../types/types'
import { API_URL } from '../../constants/api'

class ClientBodyTestsService {
  postClientBodyTestPerUser(requestBody: NewClientBodyTest) {
    return axios.post(API_URL + '/add_client_body_test', requestBody, { headers: authHeader() })
      .then(response => {
        console.log('Odpowiedź z serwera:', response.data);
      })
      .catch(error => {
        console.error('Błąd podczas wysyłania zapytania:', error);
      });
  }
}

export default new ClientBodyTestsService();
