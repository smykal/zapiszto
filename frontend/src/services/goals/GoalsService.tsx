import axios from 'axios';
import authHeader from '../auth-header';
import { NewGoal } from '../../types/types'
import { API_URL } from '../../constants/api'

class GoalsService {
    getGoals(clientId: string) {
        return axios.get(`${API_URL}/get_goals/${clientId}`, { headers: authHeader() })
    }

    postNewGoal(requestBody: NewGoal) {
        return axios.post(API_URL + '/add_goal', requestBody, { headers: authHeader() })
            .then(response => {
                console.log('Odpowiedź z serwera:', response.data);
            })
            .catch(error => {
                console.error('Błąd podczas wysyłania zapytania:', error);
            });
    }

    putGoal(requestBody: NewGoal) {
        return axios.patch(API_URL + '/update_goal', requestBody, { headers: authHeader() })
            .then(response => {
                console.log('Goal updated successfully');
                return response.data;
            })
            .catch(error => {
                console.error('Error updating goal:', error);
                throw error;
            });
    }
}

export default new GoalsService();
