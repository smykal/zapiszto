import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import ClientsService from '../../../../services/clients';
import InvitationsService from '../../../../services/invitations';
import { NewClient, Invitation, Client } from '../../../../types/types';

interface AddClientFormProps {
  onClientAdded: () => void;
  existingClients: Client[];
}

const AddClientForm: React.FC<AddClientFormProps> = ({ onClientAdded, existingClients }) => {
  const [acceptedInvitations, setAcceptedInvitations] = useState<Invitation[]>([]);
  const [selectedInviteeId, setSelectedInviteeId] = useState<number | null>(null);
  const { t } = useTranslation('global');

  useEffect(() => {
    fetchAcceptedInvitations();
  }, []);

  const fetchAcceptedInvitations = async () => {
    try {
      const response = await InvitationsService.getAcceptedInvitations();
      if (Array.isArray(response.data)) {
        setAcceptedInvitations(response.data);
      } else {
        console.error('Unexpected response data format:', response.data);
      }
    } catch (error) {
      console.error('Error fetching accepted invitations:', error);
    }
  };

  const handleInviteeChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedInviteeId(parseInt(e.target.value, 10));
  };

  const handleAddClient = async (values: NewClient, { setSubmitting, resetForm }: any) => {
    try {
      const newClientWithId = { ...values, id: crypto.randomUUID(), userId: selectedInviteeId || 0 };
      await ClientsService.postNewClient(newClientWithId);
      resetForm();
      setSelectedInviteeId(null);
      onClientAdded();
    } catch (error) {
      console.error('Error adding client:', error);
    } finally {
      setSubmitting(false);
    }
  };

  const validationSchema = Yup.object().shape({
    clientName: Yup.string()
      .test('unique-clientName', t('clients.client_name_exists'), value => {
        return !existingClients.some(client => client.clientName === value);
      })
      .required(t('validation.this_field_is_required')),
  });

  return (
    <div>
      <h2>{t('buttons.add_client')}</h2>
      <Formik
        initialValues={{ id: '', clientName: '', userId: 0 }}
        validationSchema={validationSchema}
        onSubmit={handleAddClient}
      >
        {({ isSubmitting }) => (
          <Form>
            <div>
              <label>{t('clients.client_name')}:</label>
              <Field type="text" name="clientName" />
              <ErrorMessage name="clientName" component="div"/>
            </div>
            <div>
              <label>{t('clients.client_assign_user')}:</label>
              <select value={selectedInviteeId || ''} onChange={handleInviteeChange}>
                <option value="">{t('clients.select_user_to_assign')}</option>
                {acceptedInvitations.map(invitation => {
                  if (invitation.status === 'RECIVED') {
                    return (
                      <option key={invitation.inviteeId} value={invitation.inviterId}>
                        {invitation.inviteeEmail}
                      </option>
                    );
                  } else if (invitation.status === 'SENT') {
                    return (
                      <option key={invitation.inviterId} value={invitation.inviterId}>
                        {invitation.inviterEmail}
                      </option>
                    );
                  }
                  return null;
                })}
              </select>
            </div>
            <button type="submit" disabled={isSubmitting}>
              {t('buttons.add')}
            </button>
          </Form>
        )}
      </Formik>
    </div>
  );
};

export default AddClientForm;
