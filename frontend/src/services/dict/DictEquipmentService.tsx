import axios from 'axios';
import authHeader from '../auth-header';
import { NewDictEquipment } from '../../types/types';

import { API_URL } from '../../constants/api'

class DictEquipmentService {
    getDictEquipment() {
        return axios.get(`${API_URL}/get_equipment_per_user`, { headers: authHeader() });
    }

    postDictEquipmentPerUser(newEquipment: NewDictEquipment) {
        return axios.post(`${API_URL}/add_equipment_per_user`, newEquipment, { headers: authHeader() });
    }

    deleteDictEquipment(equipmentId: string) {
        return axios.delete(`${API_URL}/delete_equipment_per_user/${equipmentId}`, { headers: authHeader() });
    }

}
export default new DictEquipmentService();
