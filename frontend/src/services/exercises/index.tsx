import axios from 'axios';
import authHeader from '../auth-header';
import { DictExercises, DictQuantityType, DictUnits, NewDictExercises, NewDictQuantityType, NewExercise, NewDictUnit } from '../../types/types'
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

  deleteDictExercise(itemToDelete: number) {
    return axios.delete(API_URL + '/delete_exercise_per_user/' + itemToDelete, { headers: authHeader() })
  }

  getDictUnits() {
    return axios.get(API_URL + '/get_units_per_user', { headers: authHeader() })
  }

  postDictUnitPerUser(requestBody: NewDictUnit) {
    return axios.post(API_URL + '/add_units_per_user', requestBody, { headers: authHeader() })
    .then(response => {
      console.log('Odpowiedź z serwera:', response.data);
    })
    .catch(error => {
      console.error('Błąd podczas wysyłania zapytania:', error);
    });
  }

  deleteDictUnits(itemToDelete: number) {
    return axios.delete(API_URL + '/delete_units_per_user/' + itemToDelete, { headers: authHeader() })
  }

  getDictQuantityType() {
    return axios.get(API_URL + '/get_quantity_type_per_user', { headers: authHeader() })
  }

  postDictQuantityTypePerUser(requestBody: NewDictQuantityType) {
    return axios.post(API_URL + '/add_quantity_type_per_user', requestBody, { headers: authHeader() })
    .then(response => {
      console.log('Odpowiedź z serwera:', response.data);
    })
    .catch(error => {
      console.error('Błąd podczas wysyłania zapytania:', error);
    });
  }

  deleteDictQuantityType(itemToDelete: number) {
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
}

export default new ExercisesService()