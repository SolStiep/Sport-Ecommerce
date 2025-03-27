import {
    createContext,
    useContext,
    useState,
    ReactNode,
    useEffect,
  } from "react";
  import { CartItem } from "@/types/cart";
  
  interface CartContextType {
    cartItems: CartItem[];
    addItem: (item: CartItem) => void;
    removeItem: (id: string) => void;
    clearCart: () => void;
    getTotal: () => number;
  }
  
  const CartContext = createContext<CartContextType | undefined>(undefined);
  
  export const CartProvider = ({ children }: { children: ReactNode }) => {
    const [cartItems, setCartItems] = useState<CartItem[]>([]);
  
    const addItem = (item: CartItem) => {
      setCartItems((prev) => {
        const existing = prev.find((i) => i.id === item.id && i.type === item.type);
        if (existing) {
          return prev.map((i) =>
            i.id === item.id && i.type === item.type
              ? { ...i, quantity: i.quantity + item.quantity }
              : i
          );
        }
        return [...prev, item];
      });
    };
  
    const removeItem = (id: string) => {
      setCartItems((prev) => prev.filter((item) => item.id !== id));
    };
  
    const clearCart = () => {
      setCartItems([]);
    };
  
    const getTotal = () => {
      return cartItems.reduce((acc, item) => acc + item.price * item.quantity, 0);
    };
  
    return (
      <CartContext.Provider
        value={{ cartItems, addItem, removeItem, clearCart, getTotal }}
      >
        {children}
      </CartContext.Provider>
    );
  };
  
  export const useCart = () => {
    const context = useContext(CartContext);
    if (!context) throw new Error("useCart must be used within CartProvider");
    return context;
  };
  