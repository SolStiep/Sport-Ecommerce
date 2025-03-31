import { Modal } from "antd";

interface UnderConstructionModalProps {
  visible: boolean;
  onClose: () => void;
}

export const UnderConstructionModal = ({
  visible,
  onClose,
}: UnderConstructionModalProps) => {
  return (
    <Modal
      open={visible}
      onCancel={onClose}
      onOk={onClose}
      title="🚧 Under Construction"
      okText="Got it"
      cancelButtonProps={{ style: { display: "none" } }}
    >
      <p>This page or feature is currently under development.</p>
      <p>Please check back later!</p>
    </Modal>
  );
};
