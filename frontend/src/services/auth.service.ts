import axios from "axios";
import authHeader from './auth-header';
import i18n from '../translations/i18n';
import LanguageService from '../services/languages/';
import { AUTH_URL } from '../constants/api'

class AuthService {
  async login(username: string, password: string) {
    try {
      const response = await axios.post(AUTH_URL + 'signin', {
        username,
        password,
      });

      if (response.data.accessToken) {
        localStorage.setItem('user', JSON.stringify(response.data));

        // Fetch and set user language preference after login
        try {
          const res = await LanguageService.getLanguage();
          const langCode = res.data.languageCode;

          console.log('Fetched language code:', langCode); // Log fetched language code
          
          await i18n.changeLanguage(langCode); // Change the language
          console.log('Language changed to:', langCode); // Log after changing language
        } catch (err) {
          console.error('Error fetching user language:', err);
        }
      }

      return response.data;
    } catch (error) {
      console.error('Login error:', error);
      throw error;
    }
  }

  logout() {
    localStorage.removeItem("user");
  }

  register(username: string, email: string, password: string, role: string) {
    return axios.post(AUTH_URL + "signup", {
      username,
      email,
      password,
      role
    });
  }

  getCurrentUser() {
    const userStr = localStorage.getItem("user");
    if (userStr) return JSON.parse(userStr);

    return null;
  }

  deleteAccount() {
    return axios.delete(AUTH_URL + `deleteAccount`, { headers: authHeader() });
    this.logout();
  }

  updatePassword(oldPassword: string, newPassword: string) {
    return axios.post(AUTH_URL + 'updatePassword', {
      oldPassword,
      newPassword
    }, { headers: authHeader() });
  }

  forgotPassword(email: string) {
    return axios.post(AUTH_URL + `forgot_password`, { email });
  }

  resetPassword(token: string, newPassword: string) {
    return axios.post(`${AUTH_URL}reset_password`, { token, newPassword });
  }
  
}

export default new AuthService();
