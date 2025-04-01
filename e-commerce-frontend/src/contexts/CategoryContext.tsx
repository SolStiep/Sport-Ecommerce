import React, {
  createContext,
  useState,
  useContext,
  ReactNode,
  useEffect,
} from "react";
import { toast } from "react-hot-toast";

import { Category, CategoryContextType } from "@/types/category";
import categoryService from "@/services/categories";

const CategoryContext = createContext<CategoryContextType | undefined>(
  undefined
);

export const CategoryProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    try {
      const data = await categoryService.getCategories();
      setCategories(data);
    } catch (err) {
      toast.error("Error fetching categories. Please try again.");
    }
  };

  const addCategory = async (category: Category) => {
    try {
      const newCategory = await categoryService.create(category);
      setCategories((prev) => [...prev, newCategory]);
    } catch (err) {
      toast.error("Error adding category. Please try again.");
    }
  };

  const editCategory = async (category: Category) => {
    try {
      const updatedCategory = await categoryService.update(category);
      setCategories((prev) =>
        prev.map((c) => (c.id === category.id ? updatedCategory : c))
      );
    } catch (err) {
      toast.error("Error editing categories. Please try again.");
    }
  };

  const removeCategory = async (id: string) => {
    try {
      await categoryService.delete(id);
      setCategories((prev) => prev.filter((c) => c.id !== id));
    } catch (err) {
      toast.error("Error deleting category. Please try again.");
    }
  };

  return (
    <CategoryContext.Provider
      value={{
        categories,
        fetchCategories,
        addCategory,
        editCategory,
        removeCategory,
      }}
    >
      {children}
    </CategoryContext.Provider>
  );
};

export const useCategory = () => {
  const context = useContext(CategoryContext);
  if (!context) {
    throw new Error("useCategory must be used within a CategoryProvider");
  }
  return context;
};
