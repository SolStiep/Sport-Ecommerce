import { useState, useEffect } from "react";
import { Form, Input, Button, Select } from "antd";
import { useProduct } from "@/contexts/ProductContext";
import { PartForm } from "@/components/organisms/product/PartForm";
import { useCategory } from "@/contexts/CategoryContext";
import { Product } from "@/types/product";

interface Props {
  onSuccess: (product: Product) => void;
  product?: Product; 
  readOnly?: boolean;
}

export const ProductBasicForm = ({ onSuccess, product, readOnly = false }: Props) => {
  const { addProduct } = useProduct();
  const { categories } = useCategory();
  const [form] = Form.useForm();

  useEffect(() => {
    if (product) {
      form.setFieldsValue(product);
    }
  }, [product, form]);

  const onFinish = async (values: any) => {
    const product = {
      name: values.name,
      description: values.description,
      categoryId: values.categoryId,
      parts: values.parts || [],
    };

    const newProduct = await addProduct(product);
    onSuccess(newProduct);  
  };

  return (
    <Form form={form} onFinish={onFinish} layout="vertical">
      <Form.Item name="name" label="Product Name" rules={[{ required: true }]}>
        <Input readOnly={readOnly}/>
      </Form.Item>
      <Form.Item name="description" label="Description">
        <Input.TextArea readOnly={readOnly}/>
      </Form.Item>
      <Form.Item name="categoryId" label="Category" rules={[{ required: true }]}>
        <Select readOnly={readOnly}>
          {categories.map((cat) => (
            <Select.Option key={cat.id} value={cat.id}>
              {cat.name}
            </Select.Option>
          ))}
        </Select>
      </Form.Item>

      <Form.List name="parts">
        {(fields, { add, remove }) => (
          <>
            {fields.map(({ key, name, ...restField }) => (
              <PartForm key={key} fieldKey={key} fieldName={name} restField={restField} remove={remove} readOnly={readOnly} />
            ))}
            {!readOnly && (
              <Button type="dashed" onClick={() => add()} block>
                Add Part
              </Button>
            )}
          </>
        )}
      </Form.List>

      <Form.Item>
        <Button type="primary" htmlType="submit">
          Create Product
        </Button>
      </Form.Item>
    </Form>
  );
};
