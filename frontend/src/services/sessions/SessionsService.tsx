import axios from 'axios';
import authHeader from '../auth-header';
import { API_URL } from '../../constants/api';

class SessionService {
  getSessions(microcycleId: string) {
    return axios.get(`${API_URL}/get_sessions/${microcycleId}`, { headers: authHeader() });
  }

  updateSessionDateTime(sessionId: string, dateTime: Date) {
    return axios.patch(`${API_URL}/update_session_datetime/${sessionId}`, { dateTime }, { headers: authHeader() });
  }

  addSession(microcycleId: string) {
    return axios.post(`${API_URL}/add_session/${microcycleId}`, {}, { headers: authHeader() });
  }

  deleteSession(sessionId: string) {
    return axios.delete(`${API_URL}/delete_session/${sessionId}`, { headers: authHeader() });
  }
}

export default new SessionService();
