import axios from 'axios';
import authHeader from '../auth-header';
import { API_URL } from '../../constants/api'

class PersonalTrainingsService { 

    getClientProgram(clientId: string) {
        return axios.get(`${API_URL}/get_client_program/${clientId}`, { headers: authHeader() });
      }

}
export default new PersonalTrainingsService();