import React, {
  createContext,
  useState,
  useContext,
  ReactNode,
  useEffect,
} from "react";
import { toast } from "react-hot-toast";

import { Product } from "@/types/product";
import productService from "@/services/products";
import { ProductContextType } from "@/types/product";

const ProductContext = createContext<ProductContextType | undefined>(undefined);

export const ProductProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      const data = await productService.fetchProducts();
      setProducts(data);
    } catch (err) {
      toast.error("Error fetching products. Please try again.");
    }
  };

  const addProduct = async (product: Product) => {
    try {
      const newProduct = await productService.create(product);
      setProducts((prev) => [...prev, newProduct]);
      return newProduct.id;
    } catch (err) {
      toast.error("Error adding product. Please try again.");
    }
  };

  const editProduct = async (product: Product) => {
    try {
      const updatedProduct = await productService.update(product);
      setProducts((prev) =>
        prev.map((p) => (p.id === product.id ? updatedProduct : p))
      );
    } catch (err) {
      toast.error("Error editing product. Please try again.");
    }
  };

  const removeProduct = async (id: string) => {
    try {
      await productService.deleteProduct(id);
      setProducts((prev) => prev.filter((p) => p.id !== id));
    } catch (err) {
      toast.error("Error deleting product. Please try again.");
    }
  };

  return (
    <ProductContext.Provider
      value={{
        products,
        fetchProducts,
        addProduct,
        editProduct,
        removeProduct,
      }}
    >
      {children}
    </ProductContext.Provider>
  );
};

export const useProduct = () => {
  const context = useContext(ProductContext);
  if (!context) {
    throw new Error("useProduct must be used within a ProductProvider");
  }
  return context;
};
