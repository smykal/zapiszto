import axios from 'axios';
import authHeader from '../auth-header';
import { API_URL } from '../../constants/api';

class SessionService {
  getSessions(microcycleId: string) {
    return axios.get(`${API_URL}/get_sessions/${microcycleId}`, { headers: authHeader() });
  }
}

export default new SessionService();
