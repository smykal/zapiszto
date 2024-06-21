import React, { useEffect, useState } from "react";
import { Training } from "../../../../../types/types";
import Service from "../../../../../services/workbooks";
import Collapsible from "react-collapsible";
import GetExercise from './GetExercise';


type Props = {
  workbook_id: number;
  userId: number;
};

const ShowTrainings: React.FC<Props> = ({ workbook_id, userId }) => {
  const [trainings, setTrainings] = useState<Training[]>([]);
  const [expandedItemIndex, setExpandedItemIndex] = useState<number | null>(null);

  useEffect(() => {
    Service.getTrainingsByUserId(workbook_id, userId).then((response) => {
      if (response.data.length > 0) {
        const parsedTrainings = response.data.map((training: Training) => {
          const parsedDate = new Date(training.date).toISOString().split('T')[0];
          return {
            ...training,
            date: parsedDate,
          };
        });
        setTrainings(parsedTrainings);
      }
    });

    const expandedItemIndex = localStorage.getItem('expandedItemIndex');
    if (expandedItemIndex !== null) {
      setExpandedItemIndex(parseInt(expandedItemIndex));
    }
  }, [workbook_id]);

  const handleItemClick = (index: number) => {
    setExpandedItemIndex((prevState) => (prevState === index ? null : index));
    localStorage.setItem('expandedItemIndex', index.toString());
  };

  const isItemExpanded = (index: number): boolean => expandedItemIndex === index;

  return (
    <div>
      <ul>
        {trainings.map((item, index) => (
          <li key={index}>
            <Collapsible
              trigger={
                <div
                  style={{
                    cursor: 'pointer',
                    display: 'flex',
                    flexDirection: 'column',
                    marginBottom: '10px',
                  }}
                  onClick={() => handleItemClick(index)}
                >
                  <span style={{ marginRight: '10px', width: '350px', textAlign: 'right' }}>
                    {item.date}: {item.notes}, workbook_id: {item.workbooks_id}, training_id: {item.id}
                  </span>
                </div>
              }
              open={isItemExpanded(index)}
            >
              <GetExercise training_id={item.id} userId={userId}></GetExercise>
            </Collapsible>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ShowTrainings;
