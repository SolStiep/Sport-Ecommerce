import { Form, Input, Button, Select, InputNumber } from "antd";
import { useState, useEffect } from "react";
import { useProduct } from "@/contexts/ProductContext";
import { PartForm } from "@/components/organisms/product/PartForm";
import { RestrictionRule } from "@/types/RestrictionRule";
import configuratorService from "@/services/configurators";

interface Props {
  productId: string;
  onSuccess: () => void;
  readOnly?: boolean;
}

export const ConfiguratorForm = ({
  productId,
  onSuccess,
  readOnly = false,
}: Props) => {
  const { products, editProduct } = useProduct();
  const [product, setProduct] = useState<any>(null);
  const [form] = Form.useForm();
  const operators = ["REQUIRES", "EXCLUDES"];

  useEffect(() => {
    const fetchProduct = () => {
      const fetchedProduct = products.find((p) => p.id === productId);
      if (fetchedProduct) {
        setProduct(fetchedProduct);
        form.setFieldsValue(fetchedProduct);
      }
    };

    fetchProduct();
  }, [productId, products]);

  const onFinish = async (values: any) => {
    const configurator = {
      priceStrategyType: "SIMPLE",
      rules: values.configurator.rules,
      productId: product.id,
    };
    await configuratorService.create(configurator);
    onSuccess();
  };

  const getPartOptions = (parts: any[]) => {
    return parts.flatMap((part) =>
      part.options.map((opt: any) => ({
        label: `${part.name} - ${opt.name}`,
        value: opt.id,
      }))
    );
  };

  return (
    <Form form={form} onFinish={onFinish} layout="vertical">
      {/* Restriction Rules */}
      <Form.List name={["configurator", "rules", "restrictionRules"]}>
        {(fields, { add, remove }) => (
          <>
            {fields.map(({ key, name, ...restField }) => (
              <div key={key} className="mb-4">
                <Form.Item
                  {...restField}
                  name={[name, "ifOption"]}
                  label="If Option"
                  rules={[{ required: true }]}
                >
                  <Select disabled={readOnly}>
                    {/* Populate options dynamically from product parts */}
                    {product?.parts?.map((part: any) =>
                      part.options.map((opt: any) => (
                        <Select.Option key={opt.id} value={opt.id}>
                          {`${part.name} - ${opt.name}`}
                        </Select.Option>
                      ))
                    )}
                  </Select>
                </Form.Item>

                <Form.Item
                  {...restField}
                  name={[name, "operator"]}
                  label="Operator"
                  rules={[{ required: true }]}
                >
                  <Select disabled={readOnly}>
                    {operators.map((op) => (
                      <Select.Option key={op} value={op}>
                        {op}
                      </Select.Option>
                    ))}
                  </Select>
                </Form.Item>

                <Form.Item
                  {...restField}
                  name={[name, "targetOptions"]}
                  label="Target Options"
                  rules={[{ required: true }]}
                >
                  <Select mode="multiple" disabled={readOnly}>
                    {/* Populate target options dynamically from product parts */}
                    {product?.parts?.map((part: any) =>
                      part.options.map((opt: any) => (
                        <Select.Option key={opt.id} value={opt.id}>
                          {`${part.name} - ${opt.name}`}
                        </Select.Option>
                      ))
                    )}
                  </Select>
                </Form.Item>
                {!readOnly && (
                  <Button danger type="link" onClick={() => remove(name)}>
                    Remove Rule
                  </Button>
                )}
              </div>
            ))}
            {!readOnly && (
              <Button type="dashed" onClick={() => add()} block>
                Add Restriction Rule
              </Button>
            )}
          </>
        )}
      </Form.List>

      {/* Price Rules */}
      <Form.List name={["configurator", "rules", "priceConditionRules"]}>
        {(fields, { add, remove }) => (
          <>
            {fields.map(({ key, name, ...restField }) => (
              <div key={key} className="mb-4 mt-6">
                <Form.Item
                  {...restField}
                  name={[name, "ifOption"]}
                  label="If Option"
                  rules={[{ required: true }]}
                >
                  <Select disabled={readOnly}>
                    {product?.parts?.map((part: any) =>
                      part.options.map((opt: any) => (
                        <Select.Option key={opt.id} value={opt.id}>
                          {`${part.name} - ${opt.name}`}
                        </Select.Option>
                      ))
                    )}
                  </Select>
                </Form.Item>

                <Form.Item
                  {...restField}
                  name={[name, "requiredOptions"]}
                  label="Required Options"
                  rules={[{ required: true }]}
                >
                  <Select mode="multiple" disabled={readOnly}>
                    {product?.parts?.map((part: any) =>
                      part.options.map((opt: any) => (
                        <Select.Option key={opt.id} value={opt.id}>
                          {`${part.name} - ${opt.name}`}
                        </Select.Option>
                      ))
                    )}
                  </Select>
                </Form.Item>

                <Form.Item
                  {...restField}
                  name={[name, "price"]}
                  label="Additional Price"
                  rules={[
                    {
                      required: true,
                      type: "number",
                      message: "Enter a valid price",
                    },
                  ]}
                >
                  <InputNumber readOnly={readOnly} />
                </Form.Item>

                {!readOnly && (
                  <Button danger type="link" onClick={() => remove(name)}>
                    Remove Price Rule
                  </Button>
                )}
              </div>
            ))}
            {!readOnly && (
              <Button type="dashed" className="!mt-4" onClick={() => add()} block>
                Add Price Rule
              </Button>
            )}
          </>
        )}
      </Form.List>
      {!readOnly && (
        <div className="flex justify-end mb-4 mt-4">
          <Form.Item>
            <Button
              type="primary"
              htmlType="submit"
              className="!bg-stone-500 !hover:bg-stone-700 !text-white"
            >
              Save Configurator
            </Button>
          </Form.Item>
        </div>
      )}
    </Form>
  );
};
