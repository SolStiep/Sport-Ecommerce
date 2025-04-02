import { Button, Space, Tooltip } from "antd";
import { FiTrash, FiEye, FiEdit3 } from "react-icons/fi";

import { GenericTable } from "@/components/molecules/GenericTable";

export const PresetList = ({ presets, onView, onEdit, onDelete }) => {
  const columns = [
    { title: "Name", dataIndex: "name" },
    {
      title: "Actions",
      render: (_, record) => (
        <Space size="middle">
          <Tooltip title="View Details">
            <Button
              type="link"
              icon={<FiEye />}
              onClick={() => onView(record)}
            />
          </Tooltip>
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
      ),
    },
  ];

  return <GenericTable data={presets} columns={columns} rowKey="id" />;
};
