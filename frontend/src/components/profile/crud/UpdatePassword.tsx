import React, { useState } from 'react';
import AuthService from '../../../services/auth.service';

const UpdatePassword = () => {
  const [oldPassword, setOldPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [message, setMessage] = useState('');

  const handleUpdatePassword = async (e: any) => {
    e.preventDefault();
    if (newPassword !== confirmPassword) {
      setMessage('New password and confirm password do not match.');
      return;
    }
    try {
      await AuthService.updatePassword(oldPassword, newPassword);
      setMessage('Password updated successfully.');
    } catch (err) {
      setMessage('Error updating password.');
    }
  };

  return (
    <div>
      <form onSubmit={handleUpdatePassword}>
        <div>
          <label>Old Password:</label>
          <input type="password" value={oldPassword} onChange={(e) => setOldPassword(e.target.value)} required />
        </div>
        <div>
          <label>New Password:</label>
          <input type="password" value={newPassword} onChange={(e) => setNewPassword(e.target.value)} required />
        </div>
        <div>
          <label>Confirm Password:</label>
          <input type="password" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} required />
        </div>
        <button type="submit">Update Password</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default UpdatePassword;
