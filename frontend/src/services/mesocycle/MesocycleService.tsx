import axios from 'axios';
import authHeader from '../auth-header';
import { API_URL } from '../../constants/api';
import { NewMesocycleDto, MesocycleDto } from '../../types/types';

class MesocycleService {
  addMesocycle(newMesocycle: NewMesocycleDto) {
    return axios.post(`${API_URL}/add_mesocycle`, newMesocycle, { headers: authHeader() });
  }

  addSingleMesocycle(newMesocycle: NewMesocycleDto, macrocycleId: string) {
    return axios.post(`${API_URL}/add_mesocycle/${macrocycleId}`, newMesocycle, { headers: authHeader() });
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

  deleteMesocycle(mesocycleId: string) {
    return axios.delete(`${API_URL}/delete_mesocycle/${mesocycleId}`, { headers: authHeader() });
  }
}

export default new MesocycleService();
