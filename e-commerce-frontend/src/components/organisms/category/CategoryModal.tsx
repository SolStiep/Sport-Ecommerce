import { useEffect, useState } from "react";
import { Modal, Form, Input, Button, Space, Tooltip } from "antd";
import { FiTrash, FiEye, FiEdit3 } from "react-icons/fi";
import { toast } from "react-hot-toast";

import { Category } from "@/types/category";
import { useCategory } from "@/contexts/CategoryContext";
import { ConfirmModal } from "@/components/molecules/ConfirmModal";
import { GenericTable } from "@/components/molecules/GenericTable";

interface CategoryModalProps {
  visible: boolean;
  onClose: () => void;
}

export const CategoryModal = ({ visible, onClose }: CategoryModalProps) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [form] = Form.useForm();
  const {
    categories,
    fetchCategories,
    addCategory,
    editCategory,
    removeCategory,
  } = useCategory();

  const [editing, setEditing] = useState<Category | null>(null);
  const [selectedCategory, setSelectedCategory] = useState<Category | null>(
    null
  );
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (visible) fetchCategories();
  }, [visible]);

  const handleFinish = async (values: {
    name: string;
    description: string;
  }) => {
    try {
      setLoading(true);
      if (editing) {
        await editCategory({ ...editing, ...values });
        toast.success("Category updated");
      } else {
        await addCategory({
          id: "",
          name: values.name,
          description: values.description || "",
        });
        toast.success("Category created");
      }
      form.resetFields();
      setEditing(null);
    } catch {
      toast.error("Error saving category. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = (cat: Category) => {
    setEditing(cat);
    form.setFieldsValue({ name: cat.name, description: cat.description });
  };

  const handleCancelEdit = () => {
    setEditing(null);
    form.resetFields();
  };

  const handleDelete = (category: Category) => {
    setSelectedCategory(category);
    setIsModalOpen(true);
  };

  const handleConfirmDelete = async () => {
    if (!selectedCategory) return;
    await removeCategory(selectedCategory.id);
    toast.success("Category deleted");
    setIsModalOpen(false);
  };

  const handleModalClose = () => {
    form.resetFields();
    setEditing(null);
    onClose();
  };

  const columns = [
    { title: "Name", dataIndex: "name" },
    { title: "Description", dataIndex: "description" },
    {
      title: "Actions",
      render: (_: any, record: Category) => (
        <>
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
                onClick={() => handleDelete(record)}
              />
            </Tooltip>
          </Space>
        </>
      ),
    },
  ];

  return (
    <div>
      <Modal
        width="60vw"
        open={visible}
        onCancel={handleModalClose}
        onOk={form.submit}
        title={editing ? "Edit Category" : "Add Category"}
        okText={editing ? "Update" : "Add"}
        confirmLoading={loading}
      >
        <Form form={form} onFinish={handleFinish} layout="vertical">
          <Form.Item
            name="name"
            label="Category Name"
            rules={[{ required: true, message: "Category name is required" }]}
          >
            <Input placeholder="e.g. Bikes" />
          </Form.Item>

          <Form.Item name="description" label="Description">
            <Input.TextArea
              rows={3}
              placeholder="Brief description of the category"
            />
          </Form.Item>
        </Form>

        {editing && (
          <div className="mt-2 text-right">
            <Button type="default" onClick={handleCancelEdit}>
              Cancel Edit
            </Button>
          </div>
        )}

        <div className="mt-6">
          <h3 className="font-medium mb-2">Existing Categories</h3>
          <GenericTable<Category>
            data={categories}
            columns={columns}
            rowKey="id"
          />
        </div>
      </Modal>

      <ConfirmModal
        isOpen={isModalOpen}
        onConfirm={handleConfirmDelete}
        onCancel={() => setIsModalOpen(false)}
        title="Confirm Deletion"
        message={`Are you sure you want to delete the category "${selectedCategory?.name}"? This action cannot be undone.`}
      />
    </div>
  );
};

export default CategoryModal;
