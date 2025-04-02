import { useState, useEffect } from "react";
import productService from "@/services/products";
import { Summary } from "@/components/organisms/customProduct/Summary";
import { PartConfigurator } from "@/components/organisms/customProduct/PartConfigurator";
import { ProductSelector } from "@/components/organisms/customProduct/ProductSelector";
import { AddToCartButton } from "@/components/organisms/customProduct/AddToCartButton";
import { Layout } from "@/components/layout/Layout";
import { Product } from "@/types/product";

export const ProductCustomizationPage = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
  const [selectedOptions, setSelectedOptions] = useState<
    Record<string, string>
  >({});
  const [availableOptions, setAvailableOptions] = useState<
    Record<string, string[]>
  >({});
  const [error, setError] = useState<string | null>(null);
  const [isValidConfiguration, setIsValidConfiguration] =
    useState<boolean>(false);
  const [totalPrice, setTotalPrice] = useState<number>(0); 

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const data = await productService.fetchProducts();
        setProducts(data);
      } catch (err) {
        console.error("Error fetching products:", err);
      }
    };
    fetchProducts();
  }, []);

  const getAvailableOptions = (product: Product) => {
    const availableOptions: Record<string, string[]> = {};
    product.parts.forEach((part) => {
      availableOptions[part.id] = part.options.map((option) => option.id);
    });
    return availableOptions;
  };

  const handleProductSelect = (productId: string) => {
    const product = products.find((p) => p.id === productId);
    setSelectedProduct(product);
    setSelectedOptions({});
    setError(null);
    setIsValidConfiguration(false);
    setTotalPrice(0);
    if (product) {
      setAvailableOptions(getAvailableOptions(product));
    }
  };

  const handleOptionChange = (partId: string, optionId: string) => {
    const newSelectedOptions = { ...selectedOptions, [partId]: optionId };
    setIsValidConfiguration(false);
    setSelectedOptions(newSelectedOptions);
    setTotalPrice(0);
    setError(null);
  };

  const resetForm = () => {
    setSelectedProduct(null);
    setSelectedOptions({});
    setIsValidConfiguration(false);
    setTotalPrice(0);
    setError(null);
  };  

  return (
    <Layout>
      <div className="max-w-4xl mx-auto p-6 bg-white shadow-md rounded-lg">
        <h1 className="text-3xl font-bold mb-6 text-center">
          Customize your Product
        </h1>
        <ProductSelector products={products} onSelect={handleProductSelect} />

        {selectedProduct && (
          <>
            <PartConfigurator
              product={selectedProduct}
              selectedOptions={selectedOptions}
              availableOptions={availableOptions}
              onOptionChange={handleOptionChange}
            />
            <Summary
              product={selectedProduct}
              selectedOptions={selectedOptions}
              totalPrice={totalPrice} 
            />
            {error && <div className="text-red-500">{error}</div>}
            <AddToCartButton
              product={selectedProduct}
              selectedOptions={selectedOptions}
              setIsValidConfiguration={setIsValidConfiguration}
              setError={setError}
              error={error}
              setTotalPrice={setTotalPrice} 
              isValidConfiguration={isValidConfiguration} 
              onReset={resetForm}
            />
          </>
        )}
      </div>
    </Layout>
  );
};
