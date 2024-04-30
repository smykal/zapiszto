import axios from 'axios';
import authHeader from '../auth-header';
import {DictExercises, DictQuantityType, DictUnits} from '../../types/types'

const API_URL = 'http://localhost:8080/v1';

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

    postExercise(training_id: number,
                dict_exercise_id:number,
                quantity: number,
                dict_quantity_type_id: number,
                volume: number,
                dict_unit_id: number
              ){
      const exercise = {
        trainingId: training_id,
        dictExerciseId: dict_exercise_id,
        quantity: quantity,
        dictQuantityTypeId: dict_quantity_type_id,
        volume: volume,
        dictUnitId: dict_unit_id
      }
      return axios.post(API_URL + '/add_exercise', exercise, { headers: authHeader() })
      .then(response => {
        console.log('Odpowiedź z serwera:', response.data);
      })
      .catch(error => {
        console.error('Błąd podczas wysyłania zapytania:', error);
      });

    }
}

export default new ExercisesService()