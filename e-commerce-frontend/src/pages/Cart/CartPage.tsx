import { useState } from "react";
import { Link } from "react-router-dom";
import { toast } from "react-hot-toast";

import { Layout } from "@/components/layout/Layout";
import { CartItemCard } from "@/components/molecules/CartItemCard";
import orderService from "@/services/orders";
import { useCart } from "@/contexts/CartContext";
import { useAuth } from "@/contexts/AuthContext";
import { useProduct } from "@/contexts/ProductContext";
import { Product } from "@/types/Product";

export const CartPage = () => {
  const { cartItems, getTotal, clearCart } = useCart();
  const { products } = useProduct();
  const [loading, setLoading] = useState(false);
  const { user } = useAuth();

  const isEmpty = cartItems.length === 0;

  const mapIdsToNames = (partId, optionId, product, type) => {
    if (type === "preset") {
      return {
        partName: partId,
        optionName: optionId,
      };
    }
    const part = product?.parts.find((p) => p.id === partId);
    const option = part?.options.find((opt) => opt.id === optionId);
    return {
      partName: part?.name || partId,
      optionName: option?.name || optionId,
    };
  };

  const mapNamesToIds = (item) => {
    const product = products.find((p) => p.id === item.product.id);
    if (!product) return {};

    const selectedOptions = {};

    Object.entries(item.selectedOptions).forEach(([partName, optionName]) => {
      const part = product.parts.find((p) => p.name === partName);
      const option = part?.options.find((opt) => opt.name === optionName);
      if (part && option) {
        selectedOptions[part.id] = option.id;
      }
    });

    return selectedOptions;
  };

  const handleCreateOrder = async () => {
    setLoading(true);

    const items = cartItems.map((item) => {
      const selectedOptions =
        item.type === "preset" ? mapNamesToIds(item) : item.selectedOptions;
      return {
        productId: item.product.id,
        selectedOptions,
        quantity: item.quantity,
        preset: item.type === "preset",
        presetPrice: item.type === "preset" ? item.price : undefined,
        presetName: item.type === "preset" ? item.name : undefined,
      };
    });

    try {
      const email = user?.email || "";
      const orderData = {
        userEmail: email,
        items,
      };
      await orderService.create(orderData);
      toast.success("Order created successfully!");
      clearCart();
    } catch (error) {
      toast.error("Error creating order. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Layout>
      <div className="max-w-6xl mx-auto p-6">
        <h1 className="text-3xl font-bold mb-8">Shopping Cart</h1>

        {isEmpty ? (
          <p className="text-stone-600">Your cart is empty.</p>
        ) : (
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
            {/* Left side: Items */}
            <div className="lg:col-span-2 space-y-6">
              <div className="hidden md:grid grid-cols-4 text-sm font-semibold text-stone-500 border-b pb-2">
                <span>Product</span>
                <span className="text-center">Quantity</span>
                <span className="text-right">Subtotal</span>
                <span className="text-right"></span>
              </div>

              {cartItems.map((item) => (
                <CartItemCard
                  key={item.id}
                  item={item}
                  mapIdsToNames={mapIdsToNames}
                />
              ))}

              <div className="mt-6 flex flex-col sm:flex-row justify-between gap-4">
                <button
                  onClick={clearCart}
                  className="text-sm text-red-500 hover:underline mt-2 sm:mt-0"
                >
                  Clear Cart
                </button>
              </div>
            </div>

            {/* Right side: Summary */}
            <div className="bg-stone-50 border rounded-md p-6 shadow-sm h-fit">
              <h2 className="text-lg font-semibold mb-4">Cart Summary</h2>

              <div className="space-y-3 text-sm text-stone-700">
                <div className="flex justify-between border-b pb-2">
                  <span>Subtotal</span>
                  <span>€{getTotal().toFixed(2)}</span>
                </div>

                <div className="flex justify-between text-sm text-stone-500">
                  <span>Shipping</span>
                  <span>Free</span>
                </div>

                <div className="flex justify-between font-medium border-t pt-2">
                  <span>Total</span>
                  <span>€{getTotal().toFixed(2)}</span>
                </div>
              </div>

              <button
                onClick={handleCreateOrder}
                disabled={loading}
                className="mt-4 block w-full bg-stone-500 text-white text-center py-2 rounded hover:bg-stone-700 transition"
              >
                {loading ? "Process..." : "Create Order"}
              </button>
            </div>
          </div>
        )}
      </div>
    </Layout>
  );
};
