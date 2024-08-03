import axios from 'axios';
import authHeader from '../auth-header';
import { API_URL } from '../../constants/api';
import { NewDictQuantityType } from '../../types/types';

class DictQuantityTypeService {
  getDictQuantityType() {
    return axios.get(API_URL + '/get_quantity_type_per_user', { headers: authHeader() });
  }

  postDictQuantityTypePerUser(requestBody: NewDictQuantityType) {
    return axios.post(API_URL + '/add_quantity_type_per_user', requestBody, { headers: authHeader() })
    .then(response => {
      console.log('Odpowiedź z serwera:', response.data);
    })
    .catch(error => {
      console.error('Błąd podczas wysyłania zapytania:', error);
    });
  }
}

export default new DictQuantityTypeService();
