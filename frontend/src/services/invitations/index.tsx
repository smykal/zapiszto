import axios from 'axios';
import authHeader from '../auth-header';
import { NewInvitation } from '../../types/types';
import {API_URL} from '../../constants/api'

class InvitationsService {

    postNewInvitation(requestBody: NewInvitation) {
        return axios.post(API_URL + '/add_invitation', requestBody, { headers: authHeader() })
          .then(response => {
            console.log('Odpowiedź z serwera:', response.data);
          })
          .catch(error => {
            console.error('Błąd podczas wysyłania zapytania:', error);
          });
      }
}

export default new InvitationsService()