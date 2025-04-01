import { useState, useEffect } from "react";

export const PartConfigurator = ({
  product,
  selectedOptions,
  availableOptions,
  onOptionChange,
}) => {
  const [availableParts, setAvailableParts] = useState([]);

  useEffect(() => {
    setAvailableParts(product.parts || []);
  }, [product]);

  const handleOptionChange = (partId, optionId) => {
    onOptionChange(partId, optionId);
  };

  return (
    <div className="mb-6">
      <h2 className="text-xl font-semibold mb-4">
        Configure the parts of your product
      </h2>
      {availableParts.map((part) => (
        <div key={part.id} className="mb-4">
          <label className="block text-lg font-medium text-stone-700 mb-2">
            {part.name}
          </label>
          <select
            value={selectedOptions[part.id] || ""}
            onChange={(e) => handleOptionChange(part.id, e.target.value)}
            className="w-full p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            <option value="">--Selet--</option>
            {part.options
              .filter(
                (option) =>
                  availableOptions[part.id]?.includes(option.id) &&
                  option.inStock
              )
              .map((option) => (
                <option key={option.id} value={option.id}>
                  {option.name} (+${option.price})
                </option>
              ))}
          </select>
        </div>
      ))}
    </div>
  );
};
