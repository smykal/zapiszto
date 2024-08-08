import axios from 'axios';
import authHeader from '../auth-header';
import { NewDictCategory } from '../../types/types'
import { API_URL } from '../../constants/api'

class DictCategoryService {
    getDictCategory() {
        return axios.get(API_URL + '/get_category_per_user', { headers: authHeader() });
    }

    postDictCategoryPerUser(requestBody: NewDictCategory) {
        return axios.post(API_URL + '/add_category_per_user', requestBody, { headers: authHeader() })
        .then(response => {
            console.log('Odpowiedź z serwera:', response.data);
        })
        .catch(error => {
            console.error('Błąd podczas wysyłania zapytania:', error);
        });
    }

    deleteDictCategory(itemToDelete: number) {
        return axios.delete(API_URL + '/delete_category_per_user/' + itemToDelete, { headers: authHeader() });
    }
}

export default new DictCategoryService();
