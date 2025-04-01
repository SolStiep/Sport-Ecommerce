import axiosInstance from "../utils/axiosInstance";
import { Product } from '@/types/product';

class ProductService {
  async getProducts(): Promise<Product[]> {
    const { data } = await axiosInstance.get<Product[]>('/products');
    return data;
  }

  async fetchProducts(): Promise<Product[]> {
    const { data } = await axiosInstance.get<Product[]>('/products/full');
    return data;
  }

  async getById(id: string): Promise<Product> {
    const { data }  = await axiosInstance.get<Product>(`/products/${id}`);
    return data;
  }

  async create(product: Omit<Product, 'id'>): Promise<Product> {
    const { data }  = await axiosInstance.post<Product>('/products', product);
    return data;
  }

  async update(id: string, product: Partial<Product>): Promise<Product> {
    const { data }  = await axiosInstance.put<Product>(`/products/${id}`, product);
    return data;
  }

  async deleteProduct(id: string): Promise<void> {
    await axiosInstance.delete(`/products/${id}`);
  }
}

export default new ProductService();
