import React from 'react';

interface CopyToNextSessionProps {
  onClose: () => void;
}

const CopyToNextSession: React.FC<CopyToNextSessionProps> = ({ onClose }) => {
  const handleCopy = () => {
    // Implement the copy logic here
    console.log('Copying to next session...');
    onClose(); // Close the modal after copying
  };

  return (
    <div>
      <p>Here you can add options for copying to the next session.</p>
      <button onClick={handleCopy}>Copy</button>
      <button onClick={onClose}>Cancel</button>
    </div>
  );
};

export default CopyToNextSession;
