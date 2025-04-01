import { Form, Input, Button } from "antd";
import { MinusCircleOutlined } from "@ant-design/icons";

import { OptionForm } from "@/components/organisms/product/OptionForm";

interface PartFormProps {
  fieldKey: number;
  fieldName: number;
  restField: any;
  remove: (index: number) => void;
  readOnly?: boolean;
}

export const PartForm = ({
  fieldKey,
  fieldName,
  restField,
  remove,
  readOnly = false,
}: PartFormProps) => (
  <div style={{ marginBottom: 16, padding: 16, border: "1px solid #d9d9d9" }}>
    <div className="flex justify-between items-center mb-2">
      <h4 className="text-lg font-medium">Part</h4>
      {!readOnly && (
        <Button
          danger
          type="link"
          icon={<MinusCircleOutlined />}
          onClick={() => remove(fieldName)}
        >
          Remove Part
        </Button>
      )}
    </div>

    <Form.Item
      {...restField}
      name={[fieldName, "name"]}
      label="Part Name"
      rules={[{ required: true, message: "Missing part name" }]}
    >
      <Input placeholder="Part Name" readOnly={readOnly} />
    </Form.Item>

    <Form.List name={[fieldName, "options"]}>
      {(fields, { add, remove }) => (
        <>
          {fields.map(({ key, name, ...restField }) => (
            <OptionForm
              key={key}
              fieldKey={key}
              fieldName={name}
              restField={restField}
              remove={remove}
              readOnly={readOnly}
            />
          ))}
          {!readOnly && (
            <Button type="dashed" onClick={() => add()} block>
              Add Option
            </Button>
          )}
        </>
      )}
    </Form.List>
  </div>
);
