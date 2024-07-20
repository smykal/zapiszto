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

  updateMesocycleLabel(mesocycleId: string, newLabel: string) {
    return axios.patch(`${API_URL}/update_mesocycle_label/${mesocycleId}`, { label: newLabel }, { headers: authHeader() });
  }

  updateMesocycleComment(mesocycleId: string, newComment: string) {
    return axios.patch(`${API_URL}/update_mesocycle_comment/${mesocycleId}`, { comment: newComment }, { headers: authHeader() });
  }
}

export default new MesocycleService();
