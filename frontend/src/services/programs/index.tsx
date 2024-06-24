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

  updateProgramName(id: string, newName: string) {
    return axios.patch(`${API_URL}/update_program_name/${id}`, { programName: newName }, { headers: authHeader() });
  }
  
  getProgramDetails(programId: string) {
    return axios.get(`${API_URL}/get_program_details/${programId}`, { headers: authHeader() });
  }

  updateProgramAssignedClient(programId: string, assignedClient: string) {
    return axios.patch(`${API_URL}/update_program`, { programId, assignedClient }, { headers: authHeader() });
  }
  
}

export default new ProgramsService();
