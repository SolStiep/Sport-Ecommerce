import { useEffect, useState } from "react";
import { Modal, Form, Input, Button, List, Space } from "antd";
import { toast } from 'react-hot-toast';
import { Category } from "@/types/category";
import { useCategory } from "@/contexts/CategoryContext";
import { ConfirmModal } from "../molecules/ConfirmModal";

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
  const [selectedCategory, setSelectedCategory] = useState<Category | null>(null);
  const [loading, setLoading] = useState(false);

  const handleFinish = async (values: { name: string; description: string }) => {
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
      toast.error("Error saving category");
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

  const handleDelete = (category) => {
    setSelectedCategory(category);
    setIsModalOpen(true);
  };

  const handleConfirmDelete = () => {
    removeCategory(selectedCategory.id);
    setIsModalOpen(false);
  };

  const handleModalClose = () => {
    form.resetFields();
    setEditing(null);
    onClose();
  };

  return (
    <div>
    <Modal
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

        <Form.Item
          name="description"
          label="Description"
        >
          <Input.TextArea rows={3} placeholder="Brief description of the category" />
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
        <List
          bordered
          dataSource={categories}
          renderItem={(item) => (
            <List.Item
              actions={[
                <Button type="link" onClick={() => handleEdit(item)}>Edit</Button>,
                <Button type="link" danger   onClick={() => handleDelete(item)}>Delete</Button>,
              ]}
            >
              <Space direction="vertical">
                <span className="font-medium">{item.name}</span>
                <span className="text-sm text-gray-500">{item.description}</span>
              </Space>
            </List.Item>
          )}
        />
      </div>
    </Modal>

<ConfirmModal
isOpen={isModalOpen}
onConfirm={handleConfirmDelete}
onCancel={() => setIsModalOpen(false)}
title="Confirm Deletion"
message={`Are you sure you want to delete the category  "${selectedCategory?.name}"? This action cannot be undone.`}
/>
</div>
  );
};

export default CategoryModal;
