import { Modal, Button, Tooltip, Space } from "antd";
import { FiTrash, FiEye, FiEdit3 } from "react-icons/fi";

import { Product } from "@/types/product";
import { ProductBasicForm } from "@/components/organisms/product/ProductBasicForm";

interface ProductDetailsModalProps {
  visible: boolean;
  product: Product | null;
  onClose: () => void;
  onEdit: (productId: string) => void;
  onDelete: (productId: string) => void;
}

export const ProductDetailsModal = ({
  visible,
  product,
  onClose,
  onEdit,
  onDelete,
}: ProductDetailsModalProps) => {
  if (!product) return null;

  return (
    <Modal
      width="70vw"
      title="Product Details"
      open={visible}
      onCancel={onClose}
      footer={null}
      title="Product Details"
    >
      <div className="flex justify-end mb-4 gap-2">
        <Space size="middle">
          <Tooltip title="Edit">
            <Button
              type="link"
              icon={<FiEdit3 />}
              onClick={() => onEdit(record)}
            />
          </Tooltip>
          <Tooltip title="Delete">
            <Button
              danger
              type="link"
              icon={<FiTrash />}
              onClick={() => onDelete(record.id)}
            />
          </Tooltip>
        </Space>
      </div>
      <ProductBasicForm product={product} readOnly />
    </Modal>
  );
};
