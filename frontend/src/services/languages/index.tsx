import axios from 'axios';
import authHeader from '../auth-header';
import { UserDetailsLanguage, UserDetails } from '../../types/types';
import { API_URL } from '../../constants/api'

class LanguageService {
  getLanguage() {
    return axios.get(API_URL + '/get_language', { headers: authHeader() });
  }

  postLanguage(requestBody: UserDetailsLanguage) {
    return axios.post(API_URL + '/post_language', requestBody, { headers: authHeader() })
      .then(response => {
        console.log('Odpowiedź z serwera:', response.data);
      })
      .catch(error => {
        console.error('Błąd podczas wysyłania zapytania:', error);
      });
  }
}

export default new LanguageService()