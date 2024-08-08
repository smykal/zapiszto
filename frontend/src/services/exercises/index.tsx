import axios from 'axios';
import authHeader from '../auth-header';
import { NewDictExercises, NewDictQuantityType, NewExercise, NewDictUnit, NewDictCategory } from '../../types/types'
import { API_URL } from '../../constants/api'

class ExercisesService {
  getDictExercises() {
    return axios.get(API_URL + '/get_exercises_per_user', { headers: authHeader() })
  }

  postDictExercisePerUser(requestBody: NewDictExercises) {
    return axios.post(API_URL + '/add_exercise_per_user', requestBody, { headers: authHeader() })
    .then(response => {
      console.log('Odpowiedź z serwera:', response.data);
    })
    .catch(error => {
      console.error('Błąd podczas wysyłania zapytania:', error);
    });
  }

  deleteDictExercise(itemToDelete: string) {
    return axios.delete(API_URL + '/delete_exercise_per_user/' + itemToDelete, { headers: authHeader() })
  }

  deleteDictQuantityType(itemToDelete: string) {
    return axios.delete(API_URL + '/delete_quantity_type_per_user/' + itemToDelete, { headers: authHeader() })
  }

  postExercise(requestBody: NewExercise) {
    return axios.post(API_URL + '/add_exercise', requestBody, { headers: authHeader() })
      .then(response => {
        console.log('Odpowiedź z serwera:', response.data);
      })
      .catch(error => {
        console.error('Błąd podczas wysyłania zapytania:', error);
      });
  }

  getExercises(trainingId: number) {
    return axios.get(API_URL + '/get_exercise/training/' + trainingId, { headers: authHeader() });
  }

  getExercisesByUserId(trainingId: number, userId: number) {
    return axios.get(`${API_URL}/get_exercise/training/${trainingId}/${userId}`, { headers: authHeader() });
  }

  getWorkbooksByUserId(userId: number) {
    return axios.get(`${API_URL}/get_workbooks/${userId}`, { headers: authHeader() });
  }

  deleteExercise(trainingId: number,
                 exerciseId: number) {
    return axios.delete(API_URL + '/delete_exercise/' + exerciseId + '/' + trainingId, { headers: authHeader() });
  }
}

export default new ExercisesService()