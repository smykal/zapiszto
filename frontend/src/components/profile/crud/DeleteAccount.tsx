import React from 'react';
import AuthService from '../../../services/auth.service';
import { useTranslation } from 'react-i18next';

const DeleteAccount = () => {
  const { t } = useTranslation('global');

  const handleDelete = async () => {
    if (window.confirm(t('profile.confirm_delete_account'))) {
      try {
        await AuthService.deleteAccount();
        AuthService.logout();
        window.location.href = '/home';
      } catch (error) {
        console.error('Error deleting account:', error);
      }
    }
  };

  return (
    <button onClick={handleDelete} className="myButton">
      {t('profile.delete_account')}
    </button>
  );
};

export default DeleteAccount;
