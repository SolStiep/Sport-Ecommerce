import React, { createContext, useState, useContext, ReactNode, useEffect } from "react";
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
    const data = await productService.fetchProducts();
    setProducts(data);
  };

  const addProduct = async (product: Product) => {
    const newProduct = await productService.create(product);
    setProducts((prev) => [...prev, newProduct]);
    return newProduct.id; 
  };

  const editProduct = async (product: Product) => {
    const updatedProduct = await productService.update(product);
    setProducts((prev) =>
      prev.map((p) => (p.id === product.id ? updatedProduct : p))
    );
  };

  const removeProduct = async (id: string) => {
    await productService.deleteProduct(id);
    setProducts((prev) => prev.filter((p) => p.id !== id));
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
