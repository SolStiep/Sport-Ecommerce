import { createContext, useContext, useState, useEffect, ReactNode } from "react";

import { loginApi, registerApi, logoutApi } from "@/services/auth";
import { AuthContextType, User } from "@/types/auth";
import { useCart } from "@/contexts/CartContext";

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);
  const { clearCart } = useCart();

  useEffect(() => {
    const stored = localStorage.getItem("authUser");
    if (stored) {
      setUser(JSON.parse(stored));
    }
    setLoading(false);
  }, []);

  const login = async (email: string, password: string) => {
    const res = await loginApi(email, password);
    const user = { name: res.name, email, role: res.role, token: res.token || "" };
    setUser(user);
    localStorage.setItem("authUser", JSON.stringify(user));
  };
  
  const register = async (name: string, email: string, password: string) => {
    await registerApi(name, email, password);
    await login(email, password);
  };
  
  const logout = () => {
    setUser(null);
    localStorage.removeItem("authUser");
    logoutApi();
    clearCart();
  };


  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <AuthContext.Provider value={{ user, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) throw new Error("useAuth must be used within AuthProvider");
  return context;
};
