import axios from 'axios';
import authHeader from '../auth-header';
import { API_URL } from '../../constants/api';

class DictQuantityTypeService {
  getDictQuantityType() {
    return axios.get(API_URL + '/get_quantity_type_per_user', { headers: authHeader() });
  }
}

export default new DictQuantityTypeService();
