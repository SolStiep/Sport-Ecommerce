import { useState } from "react";
import { Button } from "antd";
import { toast } from "react-hot-toast";

import { useCart } from "@/contexts/CartContext";
import configuratorService from "@/services/configurations";

interface AddToCartButtonProps {
  product: {
    id: string;
    name: string;
    parts: {
      id: string;
      name: string;
      options: { id: string; name: string; price: number }[];
    }[];
  };
  selectedOptions: Record<string, string>;
  setIsValidConfiguration: React.Dispatch<React.SetStateAction<boolean>>;
  setError: React.Dispatch<React.SetStateAction<string | null>>;
  setTotalPrice: React.Dispatch<React.SetStateAction<number>>;
  isValidConfiguration: boolean;
  error: string | null;
  onReset: () => void;
}

export const AddToCartButton = ({
  product,
  selectedOptions,
  setIsValidConfiguration,
  setError,
  error,
  setTotalPrice,
  isValidConfiguration,
  onReset,
}: AddToCartButtonProps) => {
  const [loading, setLoading] = useState(false);
  const [priceViolations, setPriceViolations] = useState<any[]>([]);
  const [configurationData, setConfigurationData] = useState<any | null>(null);
  const { addItem } = useCart();

  const handleAddToCart = async () => {
    setLoading(true);
    setError(null);
    setPriceViolations([]);

    try {
      if (isValidConfiguration && configurationData) {
        const cartItem = {
          id: product.id,
          name: product.name,
          price: configurationData.totalPrice,
          quantity: 1,
          selectedOptions,
          type: "custom",
          product,
        };
        addItem(cartItem);
        toast.success("Product added to cart!");
        onReset();
        return;
      }

      const response = await configuratorService.prepare({
        productId: product.id,
        selectedOptions,
        quantity: 1,
        preset: false,
      });

      if (response.status === 200) {
        setIsValidConfiguration(true);
        setTotalPrice(response.data.totalPrice);
        setConfigurationData(response.data);
        setPriceViolations(response.data.priceViolations || []);
      } else {
        setIsValidConfiguration(false);
        setError("The selected configuration is invalid.");
      }
    } catch (err: any) {
      setIsValidConfiguration(false);
      toast.error("Error validating configuration.");
    } finally {
      setLoading(false);
    }
  };

  const isDisabled =
    Object.keys(selectedOptions).length !== product.parts.length;

  const mapOptionIdToName = (optionId: string) => {
    for (const part of product.parts) {
      const option = part.options.find((opt) => opt.id === optionId);
      if (option) return option.name;
    }
    return optionId;
  };

  const renderPriceViolations = () => {
    if (priceViolations.length === 0) return null;

    return (
      <div className="text-yellow-500 mt-2">
        <h3 className="font-semibold">Price Violations:</h3>
        <ul>
          {priceViolations.map((violation, index) => (
            <li key={index}>
              <span className="font-bold">
                {mapOptionIdToName(violation.ifOption)}
              </span>{" "}
              requires{" "}
              <span className="font-bold">
                {violation.requiredOptions.map(mapOptionIdToName).join(", ")}
              </span>
              . Additional price: +${violation.price}
            </li>
          ))}
        </ul>
      </div>
    );
  };

  return (
    <div className="text-right mt-6">
      <button
        onClick={handleAddToCart}
        loading={loading}
        disabled={isDisabled}
        className="bg-stone-500 py-2 px-4 text-white rounded-md text-sm hover:bg-stone-700"
      >
        {isValidConfiguration ? "Add to Cart" : "Validate"}
      </button>

      {error && <p className="text-red-500 mt-2">{error}</p>}

      {renderPriceViolations()}
    </div>
  );
};
