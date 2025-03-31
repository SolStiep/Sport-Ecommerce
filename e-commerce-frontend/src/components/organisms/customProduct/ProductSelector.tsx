import { useState } from "react";

export const ProductSelector = ({ products, onSelect }) => {
  const [selectedProductId, setSelectedProductId] = useState("");

  const handleChange = (event) => {
    const productId = event.target.value;
    setSelectedProductId(productId);
    onSelect(productId);
  };

  return (
    <div className="mb-6">
      <label
        htmlFor="product"
        className="block text-lg font-medium text-stone-700 mb-2"
      >
        Select a base product:
      </label>
      <select
        id="product"
        value={selectedProductId}
        onChange={handleChange}
        className="w-full p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
      >
        <option value="">--Select--</option>
        {products.map((product) => (
          <option key={product.id} value={product.id}>
            {product.name}
          </option>
        ))}
      </select>
    </div>
  );
};
