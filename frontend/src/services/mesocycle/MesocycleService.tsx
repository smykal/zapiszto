import axios from 'axios';
import authHeader from '../auth-header';
import { API_URL } from '../../constants/api';
import { NewMesocycleDto, MesocycleDto } from '../../types/types';

class MesocycleService {
  addMesocycle(newMesocycle: NewMesocycleDto) {
    return axios.post(`${API_URL}/add_mesocycle`, newMesocycle, { headers: authHeader() });
  }

  getMesocycles(macrocycleId: string) {
    return axios.get<MesocycleDto[]>(`${API_URL}/get_mesocycles/${macrocycleId}`, { headers: authHeader() });
  }
}

export default new MesocycleService();
