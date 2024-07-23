import React, { useEffect, useState } from "react";
import { MesocycleDto } from "../../../../../types/types";
import MesocycleService from "../../../../../services/mesocycle/MesocycleService";
import { useTranslation } from "react-i18next";
import { Tabs, Tab, TabList, TabPanel } from 'react-tabs';
import Microcycle from "../microcycle/Microcycle";
import EditableCell from '../../../../../common/EditableCell';
import Diagram from "./diagrams/Diagram";
import DeleteMesocycle from './DeleteMesocycle'; // Import DeleteMesocycle component
import AddSingleMesocycle from './AddSingleMesocycle'; // Import AddMesocycle component
import Modal from '../../../../../constants/Modal'; // Import Modal component
import 'react-tabs/style/react-tabs.css';

interface MesocycleProps {
  macrocycleId: string;
  initialDurationLeft: number;
}

const Mesocycle: React.FC<MesocycleProps> = ({ macrocycleId, initialDurationLeft }) => {
  const [mesocycles, setMesocycles] = useState<MesocycleDto[]>([]);
  const [message, setMessage] = useState<string>('');
  const [showModal, setShowModal] = useState<boolean>(false); // State for modal visibility
  const { t } = useTranslation('global');

  useEffect(() => {
    loadMesocycles();
  }, [macrocycleId]);

  const loadMesocycles = (newMesocycleId?: string) => {
    MesocycleService.getMesocycles(macrocycleId)
      .then(response => {
        const sortedMesocycles = response.data.sort((a: MesocycleDto, b: MesocycleDto) => a.orderId - b.orderId);
        setMesocycles(sortedMesocycles);
      })
      .catch(error => {
        console.error('Error loading mesocycles:', error);
        setMessage(t('mesocycle.loading_error'));
      });
  };

  const handleSaveLabel = (id: string, newLabel: string) => {
    MesocycleService.updateMesocycleLabel(id, newLabel)
      .then(() => {
        setMesocycles((prevMesocycles) =>
          prevMesocycles.map((mesocycle) =>
            mesocycle.id === id ? { ...mesocycle, label: newLabel } : mesocycle
          )
        );
      })
      .catch(error => {
        console.error('Error updating label:', error);
        setMessage(t('mesocycle.update_error'));
      });
  };

  const handleSaveComment = (id: string, newComment: string) => {
    MesocycleService.updateMesocycleComment(id, newComment)
      .then(() => {
        setMesocycles((prevMesocycles) =>
          prevMesocycles.map((mesocycle) =>
            mesocycle.id === id ? { ...mesocycle, comments: newComment } : mesocycle
          )
        );
      })
      .catch(error => {
        console.error('Error updating comment:', error);
        setMessage(t('mesocycle.update_error'));
      });
  };

  const handleMesocycleDeleted = () => {
    loadMesocycles();  // Reload mesocycles after one is deleted
  };

  const handleAddMesocycle = () => {
    setShowModal(true);
  };

  const handleModalClose = () => {
    setShowModal(false);
  };

  const handleMesocycleAdded = () => {
    setShowModal(false);
    loadMesocycles();  // Reload mesocycles after adding a new one
  };

  return (
    <div>
      {message && <p>{message}</p>}
      <Tabs>
        <TabList>
          {mesocycles.map((mesocycle) => (
            <Tab key={mesocycle.id}>{mesocycle.label || mesocycle.orderId}</Tab>
          ))}
          <Tab onClick={handleAddMesocycle}>+</Tab> {/* Tab with plus sign */}
        </TabList>
        {mesocycles.map((mesocycle) => (
          <TabPanel key={mesocycle.id}>
            <DeleteMesocycle mesocycle={mesocycle} onMesocycleDeleted={handleMesocycleDeleted} />
            <p>
              {t('table.label')}:{' '}
              <EditableCell value={mesocycle.label || ''} onSave={(newLabel) => handleSaveLabel(mesocycle.id, newLabel)} />
            </p>
            <p>{t('table.duration')}: {mesocycle.duration} tygodnie</p>
            <p>
              {t('table.comments')}:{' '}
              <EditableCell value={mesocycle.comments || ''} onSave={(newComment) => handleSaveComment(mesocycle.id, newComment)} />
            </p>
            <p>{t('table.dict_mesocycle_phase')}: {mesocycle.dictName}</p>
            <Diagram mesocycleId={mesocycle.id} />
            <Microcycle mesocycleId={mesocycle.id} />
          </TabPanel>
        ))}
      </Tabs>
      <Modal show={showModal} onClose={handleModalClose} title={t('mesocycle.add_title')}>
        <AddSingleMesocycle macrocycleId={macrocycleId} onMesocycleAdded={handleMesocycleAdded} />
      </Modal>
    </div>
  );
};

export default Mesocycle;
