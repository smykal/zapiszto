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

  updateExercise(exerciseId: string, updateExercise: { id: string }) {
    return axios.patch(`${API_URL}/update_exercise_exercise/${exerciseId}`, updateExercise, { headers: authHeader() });
  }

  updateDictUnit(exerciseId: string, updateDictUnit: { dictUnitId: string }) {
    return axios.patch(`${API_URL}/update_exercise_dict_unit/${exerciseId}`, updateDictUnit, { headers: authHeader() });
  }

  updateSets(exerciseId: string, updateSetsDto: { sets: number }) {
    return axios.patch(`${API_URL}/update_exercise_sets/${exerciseId}`,
      updateSetsDto,
      { headers: authHeader() }
    );
  }

  updateEquipment(exerciseId: string, updateEquipmentDto: { dictEquipmentId: string }) {
    return axios.patch(`${API_URL}/update_exercise_equipment/${exerciseId}`, updateEquipmentDto, { headers: authHeader() });
  }

  updateEquipmentAttribute(exerciseId: string, updateEquipmentAttributeDto: { equipmentAttribute: string }) {
    return axios.patch(`${API_URL}/update_exercise_equipment_attribute/${exerciseId}`, updateEquipmentAttributeDto, { headers: authHeader() });
  }

  updateExerciseOrderNumberUp(sessionId: string, exerciseId: string) {
    return axios.patch(`${API_URL}/update_exercise_order_number_up/${sessionId}/${exerciseId}`,{}, { headers: authHeader() });
  }

  updateExerciseOrderNumberDown(sessionId: string, exerciseId: string) {
    return axios.patch(`${API_URL}/update_exercise_order_number_down/${sessionId}/${exerciseId}`,{}, { headers: authHeader() });
  }
  
  deleteExercise(sessionId: string, exerciseId: string) {
    return axios.delete(`${API_URL}/delete_exercise_session/${sessionId}/${exerciseId}`, { headers: authHeader() });
  }
  addExercise(sessionId: string) {
    return axios.get(`${API_URL}/add_exercise/session/${sessionId}`, { headers: authHeader() });
  }
}

export default new ExercisesSessionService();