import axios from 'axios';
import { MicrocycleDto, MicrocycleStatsDto } from '../../types/types';
import { API_URL } from '../../constants/api';
import authHeader from '../auth-header'; // Assuming you have a method to get auth headers


class MicrocycleService {
  getMicrocycles(mesocycleId: string) {
    return axios.get<MicrocycleDto[]>(`${API_URL}/get_microcycles/${mesocycleId}`, { headers: authHeader() });
  }

  getMicrocycleStats(microcycleId: string) {
    return axios.get<MicrocycleStatsDto[]>(`${API_URL}/get_microcycle_stats/${microcycleId}`, { headers: authHeader() });
  }

  addMicrocycle(mesocycleId: string) {
    return axios.post(`${API_URL}/add_microcycle/${mesocycleId}`, {}, { headers: authHeader() });
  }

  deleteMicrocycle(microcycleId: string) {
    return axios.delete(`${API_URL}/delete_microcycle/${microcycleId}`, { headers: authHeader() });
  }
}

export default new MicrocycleService();
