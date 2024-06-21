import axios from 'axios';
import authHeader from '../auth-header';
import { DictWorkbookSchema, WorkbookSchemaIdPut,  NewWorkbook, NewTraining, TrainingNotes } from '../../types/types';
import {API_URL} from '../../constants/api'

class WorkbooksService {
  getWorkbooks() {
    return axios.get(API_URL + '/get_workbooks', { headers: authHeader() });
  }

  getDictWorkbookSchema() {
    return axios.get(API_URL + '/get_dict_workbook_schema', { headers: authHeader() });
  }

  postNewWorkbook(name: string) {
    const newWorkbook: NewWorkbook = {
      name: name
    }
    return axios.post(API_URL + '/add_workbook', newWorkbook, { headers: authHeader() })
      .then(response => {
        console.log('Odpowiedź z serwera:', response.data);
      })
      .catch(error => {
        console.error('Błąd podczas wysyłania zapytania:', error);
      });
  }

  deleteWorkbook(id: number) {
    return axios.delete(API_URL + '/delete_workbook/' + id, { headers: authHeader() })
      .then(response => {
        console.log('Workbook deleted successfully');
        return response.data;
      })
      .catch(error => {
        console.error('Error deleting workbook:', error);
        throw error;
      });
  }

  putWorkbookSchemaId(workbookId: number, dictWorkbookId: number) {
    const requestBody: WorkbookSchemaIdPut = {
      id: workbookId,
      dict_workbook_schema_id: dictWorkbookId
    };

    return axios.patch(API_URL + '/put_workbook_schema_id', requestBody, { headers: authHeader() })
    .then(response => {
      console.log('Workbook updated successfully');
      return response.data;
    })
    .catch(error => {
      console.error('Error updating workbook:', error);
      throw error;
    });
  }

  postNewTraining(workbookId: number, date: Date){
    const requestBody: NewTraining = {
      workbook_id: workbookId,
      date: date
    }
    return axios.post(API_URL + '/add_training', requestBody, { headers: authHeader() })
      .then(response => {
        console.log('Odpowiedź z serwera:', response.data);
      })
      .catch(error => {
        console.error('Błąd podczas wysyłania zapytania:', error);
      });
  }

  getTrainings(workbookId: number) {
    return axios.get(API_URL + '/get_trainings/workbook/' + workbookId, { headers: authHeader() });
  }

  getTrainingsByUserId(workbookId: number, userId: number) {
    return axios.get(`${API_URL}/get_trainings/workbook/${workbookId}/${userId}`, { headers: authHeader() });
  }

  patchTrainingNotes(id: number, notes: string){
    const requestBody: TrainingNotes = {
      id: id,
      notes: notes
    }
    return axios.patch(API_URL + '/patch_trening_notes', requestBody, { headers: authHeader() })
    .then(response => {
      console.log('Odpowiedź z serwera:', response.data);
    })
    .catch(error => {
      console.error('Błąd podczas wysyłania zapytania:', error);
    });
  }
}

export default new WorkbooksService()