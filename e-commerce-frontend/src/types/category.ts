export interface Category {
  id: string;
  name: string;
  description: string;
}

export interface CategoryContextType {
  categories: Category[];
  fetchCategories: () => Promise<void>;
  addCategory: (category: Category) => Promise<void>;
  editCategory: (category: Category) => Promise<void>;
  removeCategory: (id: string) => Promise<void>;
}