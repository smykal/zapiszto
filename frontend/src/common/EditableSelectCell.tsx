import React, { useState, useEffect } from 'react';

interface EditableSelectCellProps {
  value: string;
  options: string[];
  onSave: (newValue: string) => void;
}

const EditableSelectCell: React.FC<EditableSelectCellProps> = ({ value, options, onSave }) => {
  const [editing, setEditing] = useState(false);
  const [currentValue, setCurrentValue] = useState(value);

  useEffect(() => {
    setCurrentValue(value);
  }, [value]);

  const handleDoubleClick = () => {
    setEditing(true);
  };

  const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setCurrentValue(e.target.value);
  };

  const handleBlur = () => {
    setEditing(false);
    onSave(currentValue);
  };

  return (
    <span onDoubleClick={handleDoubleClick}>
      {editing ? (
        <select
          value={currentValue}
          onChange={handleChange}
          onBlur={handleBlur}
          autoFocus
        >
          {options.map(option => (
            <option key={option} value={option}>
              {option}
            </option>
          ))}
        </select>
      ) : (
        value
      )}
    </span>
  );
};

export default EditableSelectCell;
