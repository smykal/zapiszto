import axios from 'axios';
import authHeader from '../auth-header';
import { NewDictUnit } from '../../types/types'
import { API_URL } from '../../constants/api'

class DictUnitsService {
    getDictUnits() {
        return axios.get(API_URL + '/get_units_per_user', { headers: authHeader() });
    }

    postDictUnitPerUser(requestBody: NewDictUnit) {
        return axios.post(API_URL + '/add_units_per_user', requestBody, { headers: authHeader() })
        .then(response => {
            console.log('Odpowiedź z serwera:', response.data);
        })
        .catch(error => {
            console.error('Błąd podczas wysyłania zapytania:', error);
        });
    }

    deleteDictUnits(itemToDelete: string) {
        return axios.delete(API_URL + '/delete_unit_per_user/' + itemToDelete, { headers: authHeader() })
    }


}
export default new DictUnitsService();
