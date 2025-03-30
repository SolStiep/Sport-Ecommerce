import { useNavigate, useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import { Layout } from "@/components/layout/Layout";
import { ConfiguratorForm } from "@/components/organisms/product/ConfiguratorForm";
import { useProduct } from "@/contexts/ProductContext";
import { Product } from "@/types/product";

export const ConfiguratorFormPage = () => {
  const { productId } = useParams();
  const { products, fetchProducts } = useProduct();
  const navigate = useNavigate();
  const [product, setProduct] = useState<Product | null>(null);

  useEffect(() => {
    const fetchedProduct = products.find((p) => p.id === productId);
    setProduct(fetchedProduct || null);
  }, [productId, products]);

  const handleConfiguratorSuccess = () => {
    fetchProducts();
    navigate("/admin");
  };

  return (
    <Layout>
      <div className="max-w-4xl mx-auto p-6">
        <h1 className="text-2xl font-semibold mb-4">Product Configurator</h1>
        {product && (
          <ConfiguratorForm
            productId={product.id}
            onSuccess={handleConfiguratorSuccess}
          />
        )}
      </div>
    </Layout>
  );
};
