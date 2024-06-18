import axios from 'axios';
import authHeader from '../auth-header';
import { API_URL } from '../../constants/api'

class ProgramsService {
  getPrograms() {
    return axios.get(API_URL + '/get_programs', { headers: authHeader() });
  }

  postNewProgram(newProgram: any) {
    return axios.post(API_URL + '/add_program', newProgram, { headers: authHeader() });
  }

  deleteProgram(id: string) {
    return axios.delete(API_URL + `/delete_program/${id}`, { headers: authHeader() });
  }

  updateProgram(updatedProgram: any) {
    return axios.patch(API_URL + '/update_program', updatedProgram, { headers: authHeader() });
  }
}

export default new ProgramsService();
