import axios from 'axios';
import authHeader from '../auth-header';
import { API_URL } from '../../constants/api';
import { NewMacrocycleDto, MacrocycleDto } from '../../types/types';

class MacrocycleService {
  addMacrocycle(newMacrocycle: NewMacrocycleDto) {
    return axios.post(`${API_URL}/add_macrocycle`, newMacrocycle, { headers: authHeader() });
  }

  getMacrocycle(programId: string) {
    return axios.get<MacrocycleDto>(`${API_URL}/get_macrocycle/${programId}`, { headers: authHeader() });
  }
}

export default new MacrocycleService();
