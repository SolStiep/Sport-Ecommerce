import { FiArrowDown, FiArrowUp, FiShoppingBag } from "react-icons/fi";
import { Button } from "antd";

import { Preset } from "@/types/preset";
import { useCart } from "@/contexts/CartContext";

interface PresetCardProps {
  preset: Preset;
  isExpanded: boolean;
  onToggle: () => void;
}

export const PresetCard = ({ preset, isExpanded, onToggle }: PresetCardProps) => {
  const { id, name, price, selectedOptions, product } = preset;
  const { addItem } = useCart();

  const handleAddToCart = () => {
    addItem({
      id,
      name,
      price,
      type: "preset",
      selectedOptions,
      quantity: 1,
      product
    });
  };

  return (
    <div className="border rounded-lg shadow-sm hover:shadow-md transition-all bg-white p-5 flex flex-col justify-between">
      <div>
        <h3 className="text-lg font-semibold text-stone-700 mb-1">{name}</h3>
        <p className="text-sm text-stone-500 mb-2">€{price.toFixed(2)}</p>

        <button
          className="flex items-center text-sm text-stone-670 hover:underline mb-2"
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
          <ul className="bg-gray-50 p-3 mb-2 rounded text-sm text-stone-700 space-y-1 border border-gray-200 animate-fade-in">
            {Object.entries(selectedOptions as Record<string, string>).map(([key, value]) => (
              <li key={key}>
                <span className="font-medium">{key}:</span> {value}
              </li>
            ))}
          </ul>
        )}
      </div>

      <button
        className="bg-stone-500 py-2 px-4 text-white rounded-full text-sm hover:bg-stone-700 transition flex items-center justify-center gap-2"
        onClick={handleAddToCart}
      >
        <FiShoppingBag size={14} />
        Add to Cart
      </button>
    </div>
  );
};
