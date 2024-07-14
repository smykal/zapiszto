import React, { useState, useEffect } from 'react';

interface EditableNumberCellProps {
  value: number;
  onSave: (newValue: number) => void;
}

const EditableNumberCell: React.FC<EditableNumberCellProps> = ({ value, onSave }) => {
  const [editing, setEditing] = useState(false);
  const [currentValue, setCurrentValue] = useState<number | string>(value);

  useEffect(() => {
    setCurrentValue(value);
  }, [value]);

  const handleDoubleClick = () => {
    setEditing(true);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newValue = e.target.value;
    setCurrentValue(newValue === '' ? '' : parseInt(newValue, 10));
  };

  const handleBlur = () => {
    setEditing(false);
    if (currentValue !== '' && !isNaN(Number(currentValue))) {
      onSave(Number(currentValue));
    }
  };

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter') {
      setEditing(false);
      if (currentValue !== '' && !isNaN(Number(currentValue))) {
        onSave(Number(currentValue));
      }
    } else if (e.key === 'Escape') {
      setCurrentValue(value);
      setEditing(false);
    }
  };

  return (
    <span onDoubleClick={handleDoubleClick}>
      {editing ? (
        <input
          type="number"
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

export default EditableNumberCell;
