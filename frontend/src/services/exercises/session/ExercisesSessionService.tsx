import axios from 'axios';
import authHeader from '../../auth-header';
import { NewDictExercises, NewDictQuantityType, NewExercise, NewDictUnit, NewDictCategory } from '../../../types/types'
import { API_URL } from '../../../constants/api'

class ExercisesSessionService {

getExercisesBySession(sessionId: string) {
    return axios.get(API_URL + '/get_exercise/session/' + sessionId, { headers: authHeader() });
  }
}

export default new ExercisesSessionService();