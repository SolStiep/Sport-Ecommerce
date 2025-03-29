import { Modal } from "antd";

interface ConfirmModalProps {
  isOpen: boolean;
  onConfirm: () => void;
  onCancel: () => void;
  title: string;
  message: string;
  confirmText?: string;
  cancelText?: string;
}

export const ConfirmModal = ({
  isOpen,
  onConfirm,
  onCancel,
  title,
  message,
  confirmText = "Confirm",
  cancelText = "Cancel",
}: ConfirmModalProps) => {
  return (
    <Modal
      open={isOpen}
      title={title}
      onOk={onConfirm}
      onCancel={onCancel}
      okText={confirmText}
      cancelText={cancelText}
      zIndex={1001} 
    >
      <p>{message}</p>
    </Modal>
  );
};
