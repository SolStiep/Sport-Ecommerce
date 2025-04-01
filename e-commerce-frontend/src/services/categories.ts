
import axiosInstance from "../utils/axiosInstance";

import { Category } from '@/types/category';

class CategoryService {
  async getCategories(): Promise<Category[]> {
    const { data } = await axiosInstance.get<Category[]>('/categories');
    return data;
  }

  async getById(id: string): Promise<Category> {
    const { data } = await axiosInstance.get<Category>(`/categories/${id}`);
    return data;
  }

  async create(category: Omit<Category, 'id'>): Promise<Category> {
    const { data } = await axiosInstance.post<Category>('/categories', category);
    return data;
  }

  async update(category: Partial<Category>): Promise<Category> {
    const { data } = await axiosInstance.put<Category>(`/categories/${category.id}`, category);
    return data;
  }

  async delete(id: string): Promise<void> {
    await axiosInstance.delete(`/categories/${id}`);
  }
}

export default new CategoryService();
