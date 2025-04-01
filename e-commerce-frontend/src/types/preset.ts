export interface Preset {
  id: string;
  name: string;
  price: number;
  selectedOptions: Record<string, string>;
  product: {
    id: string;
    categoryId: string;
    name: string;
  };
}
