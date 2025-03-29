import { Product } from "@/types/product";
import { Button } from "antd";
import { GenericTable } from "@/components/molecules/GenericTable";

interface Props {
  products: Product[];
  onEdit: (product: Product) => void;
  onDelete: (id: string) => void;
}

export const ProductList = ({ products, onEdit, onDelete }: Props) => {
  const columns = [
    { title: "Nombre", dataIndex: "name" },
    {
      title: "Categoría",
      render: (_: any, record: Product) =>
        record.category?.name || record.categoryName || "N/A",
    },
    {
      title: "Acciones",
      render: (_: any, record: Product) => (
        <>
          <Button type="link" onClick={() => onEdit(record)}>
            Editar
          </Button>
          <Button danger type="link" onClick={() => onDelete(record.id)}>
            Eliminar
          </Button>
        </>
      ),
    },
  ];

  return <GenericTable<Product> data={products} columns={columns} rowKey="id" />;
};
