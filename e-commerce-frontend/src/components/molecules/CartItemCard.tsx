import { FiTrash2 } from "react-icons/fi";

import { CartItem } from "@/types/cart";
import { useCart } from "@/contexts/CartContext";
import { Product } from "@/types/Product";

interface Props {
  item: CartItem;
  mapIdsToNames: (partId: string, optionId: string, product: Product) => string;
  readOnly?: boolean;
}

export const CartItemCard = ({
  item,
  mapIdsToNames,
  readOnly = false,
}: Props) => {
  const { removeItem } = useCart();

  return (
    <div className="grid grid-cols-1 md:grid-cols-4 items-start border p-4 rounded shadow-sm bg-white gap-4">
      <div>
        <h3 className="text-lg font-medium">{item.name}</h3>
        <ul className="text-sm text-stone-700 mt-2">
          {Object.entries(item.selectedOptions as Record<string, string>).map(
            ([partId, optionId]) => {
              const { partName, optionName } = mapIdsToNames(
                partId,
                optionId,
                item.product,
                item.type
              );
              return (
                <li key={partId}>
                  <span className="font-medium">{partName}:</span> {optionName}
                </li>
              );
            }
          )}
        </ul>
      </div>

      <div className="flex items-center justify-center text-stone-600 text-sm font-medium">
        {item.quantity}
      </div>

      <div className="flex items-center justify-end text-sm text-stone-700">
        €{(item.price * item.quantity).toFixed(2)}
      </div>

      <div className="flex items-center justify-end">
        {!readOnly && (
          <button
            onClick={() => removeItem(item.id)}
            className="text-red-500 hover:text-red-700"
            title="Remove item"
          >
            <FiTrash2 size={18} />
          </button>
        )}
      </div>
    </div>
  );
};
