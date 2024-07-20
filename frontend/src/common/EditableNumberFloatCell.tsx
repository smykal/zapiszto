import React, { useState, useEffect } from 'react';

interface EditableNumberFloatCellProps {
  value: number;
  onSave: (newValue: number) => void;
}

const EditableNumberFloatCell: React.FC<EditableNumberFloatCellProps> = ({ value, onSave }) => {
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
    setCurrentValue(newValue);
  };

  const handleBlur = () => {
    setEditing(false);
    const numericValue = parseFloat(currentValue.toString());
    if (!isNaN(numericValue)) {
      onSave(numericValue);
    }
  };

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter') {
      setEditing(false);
      const numericValue = parseFloat(currentValue.toString());
      if (!isNaN(numericValue)) {
        onSave(numericValue);
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
          step="any"
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

export default EditableNumberFloatCell;
