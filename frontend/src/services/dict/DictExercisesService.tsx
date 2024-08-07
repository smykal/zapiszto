import axios from 'axios';
import authHeader from '../auth-header';

import { API_URL } from '../../constants/api'
import { NewDictExercises } from '../../types/types';

class DictExercisesService {
    getDictExercises() {
        return axios.get(API_URL + '/get_exercises_per_user', { headers: authHeader() });
    }

    postDictExercisePerUser(newDictExercise: NewDictExercises) {
        return axios.post(API_URL + '/add_exercise_per_user', newDictExercise, { headers: authHeader() });
    }

    deleteDictExercise(itemId: string) {
        return axios.delete(API_URL + `/delete_exercise_per_user/${itemId}`, { headers: authHeader() });
    }

}
export default new DictExercisesService();
