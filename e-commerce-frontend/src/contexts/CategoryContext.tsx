import React, {
  createContext,
  useState,
  useContext,
  ReactNode,
  useEffect,
} from "react";
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
    const data = await categoryService.getCategories();
    setCategories(data);
  };

  const addCategory = async (category: Category) => {
    const newCategory = await categoryService.create(category);
    setCategories((prev) => [...prev, newCategory]);
  };

  const editCategory = async (category: Category) => {
    const updatedCategory = await categoryService.update(category);
    setCategories((prev) =>
      prev.map((c) => (c.id === category.id ? updatedCategory : c))
    );
  };

  const removeCategory = async (id: string) => {
    await categoryService.delete(id);
    setCategories((prev) => prev.filter((c) => c.id !== id));
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
