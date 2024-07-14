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

  updateNotes(exerciseId: string, updateNotesDto: { notes: string }) {
    return axios.patch(`${API_URL}/update_exercise_notes/${exerciseId}`,
      updateNotesDto,
      { headers: authHeader() }
    );
  }

  updateTempo(exerciseId: string, updateTempoDto: { tempo: string }) {
    return axios.patch(`${API_URL}/update_exercise_tempo/${exerciseId}`,
      updateTempoDto,
      { headers: authHeader() }
    );
  }

  updateQuantity(exerciseId: string, updateQuantityDto: { quantity: number }) {
    return axios.patch(`${API_URL}/update_exercise_quantity/${exerciseId}`,
      updateQuantityDto,
      { headers: authHeader() }
    );
  }

  updateVolume(exerciseId: string, updateVolumeDto: { volume: number }) {
    return axios.patch(`${API_URL}/update_exercise_volume/${exerciseId}`,
      updateVolumeDto,
      { headers: authHeader() }
    );
  }

  updateRestTime(exerciseId: string, updateRestTimeDto: { restTime: number }) {
    return axios.patch(`${API_URL}/update_exercise_rest_time/${exerciseId}`,
      updateRestTimeDto,
      { headers: authHeader() }
    );
  }
}

export default new ExercisesSessionService();