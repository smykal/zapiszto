import axios from 'axios';
import authHeader from '../../auth-header';
import { NewDictExercises, NewDictQuantityType, NewExercise, NewDictUnit, NewDictCategory } from '../../../types/types'
import { API_URL } from '../../../constants/api'

class ExercisesSessionService {

  getExercisesBySession(sessionId: string) {
    return axios.get(API_URL + '/get_exercise/session/' + sessionId, { headers: authHeader() });
  }

  updateDictSessionPart(exerciseId: string, newSessionPartId: string) {
    return axios.patch(`${API_URL}/update_exercise_dict_session_part/${exerciseId}`,
      { id: newSessionPartId },
      { headers: authHeader() }
    );
  }

  updateDictQuantityType(exerciseId: string, newQuantityTypeId: string) {
    return axios.patch(`${API_URL}/update_exercise_dict_quantity_type/${exerciseId}`,
      { id: newQuantityTypeId },
      { headers: authHeader() }
    );
  }
}

export default new ExercisesSessionService();