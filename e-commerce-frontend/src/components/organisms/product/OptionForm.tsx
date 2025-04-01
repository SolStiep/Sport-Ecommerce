import { Form, Input, InputNumber, Switch, Button } from "antd";
import { MinusCircleOutlined } from "@ant-design/icons";

interface OptionFormProps {
  fieldKey: number;
  fieldName: number;
  restField: any;
  remove: (index: number) => void;
  readOnly?: boolean;
}

export const OptionForm = ({
  fieldKey,
  fieldName,
  restField,
  remove,
  readOnly = false,
}: OptionFormProps) => (
  <div
    style={{
      marginBottom: 16,
      padding: 12,
      border: "1px dashed #d9d9d9",
      borderRadius: 6,
    }}
  >
    <div className="flex justify-between items-center mb-2">
      <h4 className="text-md font-medium">Option</h4>
      {!readOnly && (
        <Button
          danger
          type="link"
          icon={<MinusCircleOutlined />}
          onClick={() => remove(fieldName)}
        >
          Remove
        </Button>
      )}
    </div>

    <Form.Item
      {...restField}
      name={[fieldName, "name"]}
      label="Option Name"
      rules={[{ required: true, message: "Missing option name" }]}
    >
      <Input placeholder="e.g. Full-suspension" readOnly={readOnly} />
    </Form.Item>

    <Form.Item
      {...restField}
      name={[fieldName, "price"]}
      label="Price"
      rules={[
        { required: true, type: "number", message: "Enter a valid price" },
      ]}
    >
      <InputNumber
        placeholder="e.g. 130"
        style={{ width: "100%" }}
        readOnly={readOnly}
      />
    </Form.Item>

    <Form.Item
      {...restField}
      name={[fieldName, "inStock"]}
      label="In Stock"
      valuePropName="checked"
    >
      <Switch disabled={readOnly} />
    </Form.Item>
  </div>
);
