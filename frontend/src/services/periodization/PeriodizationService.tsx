import axios from 'axios';
import authHeader from '../auth-header';
import { API_URL } from '../../constants/api';
import { PeriodizationDto } from '../../types/types'; 

class PeriodizationService {
  getPeriodizations() {
    return axios.get(`${API_URL}/periodization/distinct`, { headers: authHeader() });
  }

  getDistinctPeriodizations() {
    return axios.get(`${API_URL}/periodization_distinct`, { headers: authHeader() });
  }

  addPeriodization(newPeriodization: PeriodizationDto) {
    return axios.post(`${API_URL}/periodization`, newPeriodization, { headers: authHeader() });
  }

  deletePeriodization(id: number) {
    return axios.delete(`${API_URL}/periodization/${id}`, { headers: authHeader() });
  }

  updatePeriodization(id: number, updatedPeriodization: PeriodizationDto) {
    return axios.patch(`${API_URL}/periodization/${id}`, updatedPeriodization, { headers: authHeader() });
  }
}

export default new PeriodizationService();
