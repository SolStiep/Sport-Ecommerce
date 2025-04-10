import axiosInstance from "../utils/axiosInstance";

import { Order } from "@/types/order";

class OrderService {
  async create(order: Omit<Order, "id">): Promise<Order> {
    const response = await axiosInstance.post<Order>("/orders", order);
    return response;
  }

  async getOrders(): Promise<Order[]> {
    const { data } = await axiosInstance.get<Order[]>(`/orders`);
    return data;
  }

  async getAllOrders(): Promise<Order[]> {
    const { data } = await axiosInstance.get<Order[]>(`/orders/all`);
    return data;
  }

  async delete(id: string): Promise<void> {
    await axiosInstance.delete(`/orders/${id}`);
  }
}

export default new OrderService();
