import axios from 'axios';
import authHeader from '../auth-header';
import { Client, NewClient, NewDictBodyTest } from '../../types/types'
import { API_URL } from '../../constants/api'

class ClientsService {
    getClients() {
        return axios.get(API_URL + '/get_clients', { headers: authHeader() })
    }

    postNewClient(requestBody: NewClient) {
        return axios.post(API_URL + '/add_client', requestBody, { headers: authHeader() })
            .then(response => {
                console.log('Odpowiedź z serwera:', response.data);
            })
            .catch(error => {
                console.error('Błąd podczas wysyłania zapytania:', error);
            });
    }

    putClient(requestBody: Client) {
        return axios.patch(API_URL + '/update_client', requestBody, { headers: authHeader() })
            .then(response => {
                console.log('Client updated successfully');
                return response.data;
            })
            .catch(error => {
                console.error('Error updating client:', error);
                throw error;
            });
    }

    updateClientUser(clientId: string, userId: number) {
        return axios.patch(API_URL + `/update_client_user/${clientId}`, { userId }, { headers: authHeader() })
            .then(response => {
                console.log('Odpowiedź z serwera:', response.data);
            })
            .catch(error => {
                console.error('Błąd podczas aktualizacji użytkownika:', error);
            });
    }

    postNewDictBodyTest(requestBody: NewDictBodyTest) {
        return axios.post(API_URL + '/add_body_test_per_user', requestBody, { headers: authHeader() })
            .then(response => {
                console.log('Odpowiedź z serwera:', response.data);
            })
            .catch(error => {
                console.error('Błąd podczas wysyłania zapytania:', error);
            });
    }
}

export default new ClientsService();
