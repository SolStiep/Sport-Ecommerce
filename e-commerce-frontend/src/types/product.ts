export interface Product {
  id: string;
  name: string;
  description: string;
  categoryId: string;
  parts: Part[];
  configurator: Configurator;
}

export interface ProductContextType {
  products: Product[];
  fetchProducts: () => Promise<void>;
  addProduct: (product: Product) => Promise<void>;
  editProduct: (product: Product) => Promise<void>;
  removeProduct: (id: string) => Promise<void>;
}
export interface ConditionalPrice {
  rule: PriceConditionRule;
  price: number;
}
export interface Option {
  id: string;
  name: string;
  price: number;
  inStock: boolean;
}

export interface Part {
  id: string;
  name: string;
  options: Option[];
}

export interface RestrictionRule {
  ifOption: string;
  operator: 'REQUIRES' | 'EXCLUDES';
  targetOptions: string[];
}

export interface PriceConditionRule {
  ifOption: string;
  requiredOptions: string[];
  price: number;
}

export interface Configurator {
  productId: string;
  rules: {
    restrictionRules: RestrictionRule[];
    priceConditionRules: PriceConditionRule[];
  };
  priceStrategyType: 'SIMPLE';
}


