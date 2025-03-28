import { CartItem } from "@/types/cart";
import { useCart } from "@/contexts/CartContext";
import { FiTrash2 } from "react-icons/fi";

interface Props {
  item: CartItem;
}

export const CartItemCard = ({ item }: Props) => {
  const { removeItem } = useCart();

  return (
    <div className="grid grid-cols-1 md:grid-cols-4 items-start border p-4 rounded shadow-sm bg-white gap-4">
      <div>
        <h3 className="text-lg font-medium">{item.name}</h3>
        <ul className="text-sm text-gray-700 mt-2">
        {Object.entries(item.selectedOptions as Record<string, string>).map(([key, value]) => (
            <li key={key}>
              <span className="font-medium">{key}:</span> {value}
            </li>
          ))}
        </ul>
      </div>

      <div className="flex items-center justify-center text-gray-600 text-sm font-medium">
        {item.quantity}
      </div>

      <div className="flex items-center justify-end text-sm text-gray-700">
        €{(item.price * item.quantity).toFixed(2)}
      </div>

      <div className="flex items-center justify-end">
        <button
          onClick={() => removeItem(item.id)}
          className="text-red-500 hover:text-red-700"
          title="Remove item"
        >
          <FiTrash2 size={18} />
        </button>
      </div>
    </div>
  );
};
