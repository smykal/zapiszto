import axios from 'axios';
import authHeader from '../auth-header';
import { NewInvitation, Invitation } from '../../types/types';
import { API_URL } from '../../constants/api'

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

  getInvitations() {
    return axios.get(API_URL + '/get_invitations', { headers: authHeader() });
  }

  getAcceptedInvitations() {
    return axios.get(API_URL + '/get_accepted_invitations', { headers: authHeader() });
  }

  postApproveInvitation(requestBody: Invitation) {
    return axios.post(API_URL + '/approve_invitation', requestBody, { headers: authHeader() })
      .then(response => {
        console.log('Odpowiedź z serwera:', response.data);
      })
      .catch(error => {
        console.error('Błąd podczas wysyłania zapytania:', error);
      });
  }

  postRejectInvitation(requestBody: Invitation) {
    return axios.post(API_URL + '/reject_invitation', requestBody, { headers: authHeader() })
      .then(response => {
        console.log('Odpowiedź z serwera:', response.data);
      })
      .catch(error => {
        console.error('Błąd podczas wysyłania zapytania:', error);
      });
  }
}

export default new InvitationsService()