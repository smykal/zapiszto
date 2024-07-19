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
}

export default new MicrocycleService();
