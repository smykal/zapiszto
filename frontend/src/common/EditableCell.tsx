import React, { useState, useEffect } from 'react';

interface EditableCellProps {
  value: string;
  onSave: (newValue: string) => void;
}

const EditableCell: React.FC<EditableCellProps> = ({ value, onSave }) => {
  const [editing, setEditing] = useState(false);
  const [currentValue, setCurrentValue] = useState(value);

  useEffect(() => {
    setCurrentValue(value);
  }, [value]);

  const handleDoubleClick = () => {
    setEditing(true);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCurrentValue(e.target.value);
  };

  const handleBlur = () => {
    setEditing(false);
    onSave(currentValue);
  };

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter') {
      setEditing(false);
      onSave(currentValue);
    } else if (e.key === 'Escape') {
      setCurrentValue(value);
      setEditing(false);
    }
  };

  return (
    <span onDoubleClick={handleDoubleClick}>
      {editing ? (
        <input
          type="text"
          value={currentValue}
          onChange={handleChange}
          onBlur={handleBlur}
          onKeyDown={handleKeyDown}
          autoFocus
        />
      ) : (
        value
      )}
    </span>
  );
};

export default EditableCell;
