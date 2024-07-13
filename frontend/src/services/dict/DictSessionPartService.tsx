import axios from 'axios';
import authHeader from '../auth-header';
import { API_URL } from '../../constants/api';

class DictSessionPartService {
  getSessionPartOptions() {
    return axios.get(`${API_URL}/get_session_part_per_user`, { headers: authHeader() });
  }

}

export default new DictSessionPartService();