import { Modal, Button, Tooltip, Space } from "antd";
import { FiTrash, FiEdit3 } from "react-icons/fi";

import { PresetBasicForm } from "@/components/organisms/presets/PresetBasicForm";

export const PresetDetailsModal = ({
  visible,
  preset,
  onClose,
  onEdit,
  onDelete,
}) => {
  if (!preset) return null;

  return (
    <Modal
      width="70vw"
      title="Preset Details"
      open={visible}
      onCancel={onClose}
      footer={null}
    >
      <div className="flex justify-end mb-4 gap-2">
        <Space size="middle">
          <Tooltip title="Edit">
            <Button
              type="link"
              icon={<FiEdit3 />}
              onClick={() => onEdit(preset.id)}
            />
          </Tooltip>
          <Tooltip title="Delete">
            <Button
              danger
              type="link"
              icon={<FiTrash />}
              onClick={() => onDelete(preset.id)}
            />
          </Tooltip>
        </Space>
      </div>
      <PresetBasicForm preset={preset} readOnly />
    </Modal>
  );
};
