import axios from 'axios';
import authHeader from '../auth-header';

import { API_URL } from '../../constants/api'

class DictExercisesService {
    getDictExercises() {
        return axios.get(API_URL + '/get_exercises_per_user', { headers: authHeader() })
    }

}
export default new DictExercisesService();
