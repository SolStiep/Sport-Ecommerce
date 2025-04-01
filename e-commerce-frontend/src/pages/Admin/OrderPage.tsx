import { useState, useEffect } from "react";
import { Button } from "antd";
import { useNavigate } from "react-router-dom";
import { toast } from "react-hot-toast";

import { Layout } from "@/components/layout/Layout";
import { CartItemCard } from "@/components/molecules/CartItemCard";
import { ConfirmModal } from "@/components/molecules/ConfirmModal";
import orderService from "@/services/orders";
import { useProduct } from "@/contexts/ProductContext";
import { Order } from "@/types/Order";

export const OrderPage = () => {
  const [orders, setOrders] = useState<Order[]>([]);
  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [orderToDelete, setOrderToDelete] = useState<string | null>(null);
  const { products } = useProduct();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const allOrders = await orderService.getAllOrders();
        setOrders(allOrders);
      } catch (error) {
        toast.error("Error loading orders. Please try again later.");
      }
    };

    fetchOrders();
  }, []);

  const handleDelete = async () => {
    if (orderToDelete) {
      try {
        await orderService.delete(orderToDelete);
        setOrders((prevOrders) =>
          prevOrders.filter((order) => order.orderId !== orderToDelete)
        );
        setShowConfirmModal(false);
        toast.success("Order deleted successfully.");
      } catch (error) {
        toast.error("Error deleting order. Please try again later.");
      }
    }
  };

  const handleConfirmDelete = (orderId: string) => {
    setOrderToDelete(orderId);
    setShowConfirmModal(true);
  };

  const mapIdsToNames = (partId: string, optionId: string, product: any) => {
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
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-3xl font-bold">Admin Dashboard - Orders</h1>
        </div>

        {orders.length === 0 ? (
          <p className="text-stone-600">There are no orders yet.</p>
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
                <p className="text-stone-600">User: {order.userName}</p>

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
                          price: item.price,
                          selectedOptions,
                          product,
                          type: item.preset ? "preset" : "custom",
                        }}
                        mapIdsToNames={mapIdsToNames}
                        readOnly={true}
                      />
                    );
                  })}
                </div>

                {/* Botón de eliminar alineado a la derecha */}
                <div className="flex justify-end mt-4">
                  <Button
                    danger
                    onClick={() => handleConfirmDelete(order.orderId)}
                  >
                    Delete Order
                  </Button>
                </div>
              </div>
            ))}
          </div>
        )}

        <ConfirmModal
          isOpen={showConfirmModal}
          onConfirm={handleDelete}
          onCancel={() => setShowConfirmModal(false)}
          title="Delete Order"
          message="Are you sure you want to delete this order?"
          confirmText="Yes, Delete"
          cancelText="Cancel"
        />
      </div>
    </Layout>
  );
};
