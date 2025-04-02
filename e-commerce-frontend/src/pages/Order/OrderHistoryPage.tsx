import { useState, useEffect } from "react";
import { toast } from "react-hot-toast";

import { Layout } from "@/components/layout/Layout";
import { CartItemCard } from "@/components/molecules/CartItemCard";
import orderService from "@/services/orders";
import { useAuth } from "@/contexts/AuthContext";
import { useProduct } from "@/contexts/ProductContext";
import { Order } from "@/types/Order";

export const OrderHistoryPage = () => {
  const [orders, setOrders] = useState<Order[]>([]);
  const { products } = useProduct();

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const userOrders = await orderService.getOrders();
        setOrders(userOrders);
      } catch (error) {
        toast.error("Error loading orders. Please try again later.");
      }
    };

    fetchOrders();
  }, []);

  const mapIdsToNames = (
    partId: string,
    optionId: string,
    product: Product
  ) => {
    const part = product?.parts.find((p) => p.id === partId);
    const option = part?.options.find((opt) => opt.id === optionId);
    return {
      partName: part?.name || partId,
      optionName: option?.name || optionId,
    };
  };

  return (
    <Layout>
      <div className="max-w-6xl mx-auto p-6">
        <h1 className="text-3xl font-bold mb-8">Order History</h1>

        {orders.length === 0 ? (
          <p className="text-stone-600">You have no previous orders.</p>
        ) : (
          <div className="space-y-6">
            {orders.map((order) => (
              <div
                key={order.orderId}
                className="border p-4 rounded shadow-sm bg-white"
              >
                <h2 className="text-md font-semibold mb-2">
                  Order ID: {order.orderId}
                </h2>
                <p className="text-stone-600">
                  Total: €{order.totalPrice.toFixed(2)}
                </p>

                <div className="mt-4 space-y-4">
                  {order.items.map((item, index) => {
                    const product = products.find(
                      (p) => p.id === item.productId
                    );
                    if (!product) return null;

                    const selectedOptions = Object.fromEntries(
                      item.selectedOptions.map((opt) => [
                        opt.partId,
                        opt.optionId,
                      ])
                    );

                    return (
                      <CartItemCard
                        key={index}
                        item={{
                          id: item.productId,
                          name: item.productName,
                          quantity: item.quantity,
                          price: item.price/item.quantity,
                          selectedOptions,
                          product,
                          type: "custom",
                        }}
                        mapIdsToNames={mapIdsToNames}
                        readOnly={true}
                      />
                    );
                  })}
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </Layout>
  );
};
