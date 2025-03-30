import { useCart } from "@/contexts/CartContext";
import { Layout } from "@/components/layout/Layout";
import { CartItemCard } from "@/components/cart/CartItemCard";
import { Link } from "react-router-dom";

export const CartPage = () => {
  const { cartItems, getTotal, clearCart } = useCart();

  const isEmpty = cartItems.length === 0;

  return (
    <Layout>
      <div className="max-w-6xl mx-auto p-6">
        <h1 className="text-3xl font-bold mb-8">Shopping Cart</h1>

        {isEmpty ? (
          <p className="text-gray-600">Your cart is empty.</p>
        ) : (
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
            {/* Left side: Items */}
            <div className="lg:col-span-2 space-y-6">
              <div className="hidden md:grid grid-cols-4 text-sm font-semibold text-gray-500 border-b pb-2">
                <span>Product</span>
                <span className="text-center">Quantity</span>
                <span className="text-right">Subtotal</span>
                <span className="text-right"></span>
              </div>

              {cartItems.map((item) => (
                <CartItemCard key={item.id} item={item} />
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
            <div className="bg-gray-50 border rounded-md p-6 shadow-sm h-fit">
              <h2 className="text-lg font-semibold mb-4">Cart Summary</h2>

              <div className="space-y-3 text-sm text-gray-700">
                <div className="flex justify-between border-b pb-2">
                  <span>Subtotal</span>
                  <span>€{getTotal().toFixed(2)}</span>
                </div>

                <div className="flex justify-between text-sm text-gray-500">
                  <span>Shipping</span>
                  <span>Free</span>
                </div>

                <div className="flex justify-between font-medium border-t pt-2">
                  <span>Total</span>
                  <span>€{getTotal().toFixed(2)}</span>
                </div>
              </div>

              <Link
                to="/checkout"
                className="mt-6 block w-full bg-stone-500 text-white text-center py-2 rounded hover:bg-stone-700 transition"
              >
                Checkout
              </Link>
            </div>
          </div>
        )}
      </div>
    </Layout>
  );
};
