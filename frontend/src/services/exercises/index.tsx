import axios from 'axios';
import authHeader from '../auth-header';
import {DictExercises, DictQuantityType, DictUnits, NewExercise} from '../../types/types'
import {API_URL} from '../../constants/api'

class ExercisesService {
    getDictExercises() {
        return axios.get(API_URL + '/get_exercises_per_user', { headers: authHeader() })
    }
    getDictUnits() {
        return axios.get(API_URL + '/get_units_per_user', { headers: authHeader() })
    }
    getDictQuantityType() {
        return axios.get(API_URL + '/get_quantity_type_per_user', { headers: authHeader() })
    }

    postExercise(requestBody: NewExercise){
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