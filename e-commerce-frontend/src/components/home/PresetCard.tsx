import { FiArrowDown, FiArrowUp, FiShoppingBag } from "react-icons/fi";

import { Preset } from "@/types/preset";

interface PresetCardProps {
  preset: Preset;
  isExpanded: boolean;
  onToggle: () => void;
}

export const PresetCard = ({ preset, isExpanded, onToggle }: PresetCardProps) => {
  const { id, name, price, selectedOptions } = preset;

  console.log("Rendering card:", name, "| ID:", id, "| isExpanded:", isExpanded);

  const handleAddToCart = () => {
    // TODO: Integrar con Context o carrito global
    alert(`Added "${name}" to cart`);
  };

  return (
    <div className="border rounded-lg shadow-sm hover:shadow-md transition-all bg-white p-5 flex flex-col justify-between">
      <div>
        <h3 className="text-lg font-semibold text-gray-800 mb-1">{name}</h3>
        <p className="text-sm text-gray-500 mb-2">€{price.toFixed(2)}</p>

        <button
          className="flex items-center text-sm text-blue-600 hover:underline mb-2"
          onClick={onToggle}
        >
          {isExpanded ? (
            <>
              Hide Details <FiArrowUp className="ml-1" />
            </>
          ) : (
            <>
              View Details <FiArrowDown className="ml-1" />
            </>
          )}
        </button>

        {isExpanded && (
          <ul className="bg-gray-50 p-3 rounded text-sm text-gray-700 space-y-1 border border-gray-200 animate-fade-in">
            {Object.entries(selectedOptions as Record<string, string>).map(([key, value]) => (
              <li key={key}>
                <span className="font-medium">{key}:</span> {value}
              </li>
            ))}
          </ul>
        )}
      </div>

      <button
        className="mt-4 w-full bg-blue-600 text-white py-2 rounded-md text-sm hover:bg-blue-700 transition flex items-center justify-center gap-2"
        onClick={handleAddToCart}
      >
        <FiShoppingBag size={14} />
        Add to Cart
      </button>
    </div>
  );
};
