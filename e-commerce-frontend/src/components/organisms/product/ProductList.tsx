import { Product } from "@/types/product";
import { Button, Space, Tooltip } from "antd";
import { FiTrash, FiEye, FiEdit3 } from "react-icons/fi";
import { GenericTable } from "@/components/molecules/GenericTable";

interface Props {
  products: Product[];
  onView: (product: Product) => void;
  onEdit: (product: Product) => void;
  onDelete: (id: string) => void;
}

export const ProductList = ({ products, onView, onEdit, onDelete }: Props) => {
  const columns = [
    { title: "Name", dataIndex: "name" },
    {
      title: "Category",
      render: (_: any, record: Product) =>
        record.category?.name || record.categoryName || "N/A",
    },
    {
      title: "Actions",
      render: (_: any, record: Product) => (
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

  return <GenericTable<Product> data={products} columns={columns} rowKey="id" />;
};
