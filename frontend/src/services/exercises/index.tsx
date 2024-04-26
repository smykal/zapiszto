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

}

export default new ExercisesService()