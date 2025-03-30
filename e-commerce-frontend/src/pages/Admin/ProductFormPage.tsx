import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";

import { Layout } from "@/components/layout/Layout";
import { ProductBasicForm } from "@/components/organisms/product/ProductBasicForm";
import { ConfiguratorForm } from "@/components/organisms/product/ConfiguratorForm";
import { useProduct } from "@/contexts/ProductContext";
import { Product } from "@/types/product";

export const ProductFormPage = () => {
  const { productId } = useParams();
  const { products, fetchProducts } = useProduct();
  const navigate = useNavigate();
  const [product, setProduct] = useState<Product | null>(null);
  const [isEditing, setIsEditing] = useState<boolean>(false);
  const [step, setStep] = useState(1);

  useEffect(() => {
    if (productId && products.length > 0) {
      const existing = products.find((p) => p.id === productId);
      if (existing) {
        setProduct(existing);
        setIsEditing(true);
      }
    } else {
      setProduct(null);
      setIsEditing(false);
    }
  }, [productId, products]);

  const handleBasicFormSuccess = (productId: string) => {
    navigate(`/admin/products/${productId}/configurator`);
  };

  return (
    <Layout>
      <div className="max-w-4xl mx-auto p-6">
        <h1 className="text-2xl font-semibold mb-4">
          {isEditing ? "Edit Product" : "Create Product"}
        </h1>

        <ProductBasicForm onSuccess={handleBasicFormSuccess} />
      </div>
    </Layout>
  );
};
