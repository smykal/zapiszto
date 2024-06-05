import axios from 'axios';
import authHeader from '../auth-header';
import { NewDictBodyTest } from '../../types/types'
import { API_URL } from '../../constants/api'

class DictBodyTestService {
  getDictBodyTest() {
    return axios.get(API_URL + '/get_body_test_per_user', { headers: authHeader() })
  }

  postDictBodyTestPerUser(requestBody: NewDictBodyTest) {
    return axios.post(API_URL + '/add_body_test_per_user', requestBody, { headers: authHeader() })
      .then(response => {
        console.log('Odpowiedź z serwera:', response.data);
      })
      .catch(error => {
        console.error('Błąd podczas wysyłania zapytania:', error);
      });
  }

  deleteDictBodyTest(itemToDelete: number) {
    return axios.delete(API_URL + '/delete_body_test_per_user/' + itemToDelete, { headers: authHeader() })
  }
}

export default new DictBodyTestService();
