import axios from 'axios';
import { MicrocycleDto } from '../../types/types';
import { API_URL } from '../../constants/api';
import authHeader from '../auth-header'; // Assuming you have a method to get auth headers


class MicrocycleService {
  getMicrocycles(mesocycleId: string) {
    return axios.get<MicrocycleDto[]>(`${API_URL}/get_microcycles/${mesocycleId}`, { headers: authHeader() });
  }
}

export default new MicrocycleService();
