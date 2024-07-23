import axios from 'axios';
import authHeader from '../auth-header';
import { API_URL } from '../../constants/api';

class DiagramService {
  getExerciseStats(mesocycleId: string) {
    return axios.get(`${API_URL}/get_exercise_stats/${mesocycleId}`, { headers: authHeader() });
  }
}

export default new DiagramService();
