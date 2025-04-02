import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { Layout } from "@/components/layout/Layout";
import { ProductSelector } from "@/components/organisms/customProduct/ProductSelector";
import { PartConfigurator } from "@/components/organisms/customProduct/PartConfigurator";
import { Summary } from "@/components/organisms/customProduct/Summary";
import { CreatePresetButton } from "@/components/organisms/presets/CreatePresetButton";
import { usePreset } from "@/contexts/PresetContext";
import { useProduct } from "@/contexts/ProductContext";
import { Product } from "@/types/product";

export const PresetCreatorPage = () => {
  const { presetId } = useParams();
  const { presets } = usePreset();
  const { products } = useProduct();
  const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
  const [selectedOptions, setSelectedOptions] = useState<Record<string, string>>({});
  const [availableOptions, setAvailableOptions] = useState<Record<string, string[]>>({});
  const [presetName, setPresetName] = useState("");
  const [presetPrice, setPresetPrice] = useState<number>(0);

  useEffect(() => {
    if (presetId) {
      const presetToEdit = presets.find((p) => p.id === presetId);
      if (presetToEdit) {
        setPresetName(presetToEdit.name);
        setPresetPrice(presetToEdit.price);

        const product = products.find((p) => p.id === presetToEdit.product.id);
        
        if (product) {
          setSelectedProduct(product);
          const mappedOptions = Object.keys(presetToEdit.selectedOptions).reduce(
            (acc, partName) => {
              const part = product.parts.find((p) => p.name === partName);
              const option = part?.options.find(
                (opt) => opt.name === presetToEdit.selectedOptions[partName]
              );
              if (part && option) {
                acc[part.id] = option.id;
              }
              return acc;
            },
            {} as Record<string, string>
          );

          setSelectedOptions(mappedOptions);

          const avail = product.parts.reduce((acc, part) => {
            acc[part.id] = part.options.map((opt) => opt.id);
            return acc;
          }, {} as Record<string, string[]>);

          setAvailableOptions(avail);
        }
      }
    } else {
      setSelectedProduct(null);
      setSelectedOptions({});
      setPresetName("");
      setPresetPrice(0);
      setAvailableOptions({});
    }
  }, [presetId, presets, products]);

  const handleProductSelect = (productId: string) => {
    const product = products.find((p) => p.id === productId);
    setSelectedProduct(product);
    setSelectedOptions({});
    setPresetName("");
    setPresetPrice(0);
    if (product) {
      const avail = product.parts.reduce((acc, part) => {
        acc[part.id] = part.options.map((opt) => opt.id);
        return acc;
      }, {} as Record<string, string[]>);
      setAvailableOptions(avail);
    }
  };

  const handleOptionChange = (partId: string, optionId: string) => {
    setSelectedOptions((prev) => ({ ...prev, [partId]: optionId }));
  };

  const resetForm = () => {
    setSelectedProduct(null);
    setSelectedOptions({});
    setPresetName("");
    setPresetPrice(0);
  };

  return (
    <Layout>
      <div className="max-w-4xl mx-auto p-6 bg-white shadow-md rounded-lg">
        <h1 className="text-3xl font-bold mb-6 text-center">
          {presetId ? "Edit Preset Product" : "Create Preset Product"}
        </h1>
        {!presetId && <ProductSelector products={products} onSelect={handleProductSelect} />}

        {selectedProduct && (
          <>
            <PartConfigurator
              product={selectedProduct}
              selectedOptions={selectedOptions}
              availableOptions={availableOptions}
              onOptionChange={handleOptionChange}
            />

            <div className="mb-4 grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-gray-700">Preset Name</label>
                <input
                  type="text"
                  value={presetName}
                  onChange={(e) => setPresetName(e.target.value)}
                  className="mt-1 block w-full p-2 border rounded"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700">Preset Price</label>
                <input
                  type="number"
                  value={presetPrice}
                  onChange={(e) => setPresetPrice(Number(e.target.value))}
                  className="mt-1 block w-full p-2 border rounded"
                />
              </div>
            </div>

            <Summary
              product={selectedProduct}
              selectedOptions={selectedOptions}
              totalPrice={presetPrice}
            />

            <CreatePresetButton
              product={selectedProduct}
              selectedOptions={selectedOptions}
              name={presetName}
              price={presetPrice}
              onReset={resetForm}
              presetId={presetId}
              isEditing={!!presetId}
            />
          </>
        )}
      </div>
    </Layout>
  );
};
