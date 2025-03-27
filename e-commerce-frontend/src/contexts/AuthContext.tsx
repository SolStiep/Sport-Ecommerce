import { createContext, useContext, useState, useEffect, ReactNode } from "react";
import { jwtDecode } from "jwt-decode";

import { loginApi, registerApi, logoutApi } from "@/services/auth";
import { AuthContextType, User, TokenPayload } from "@/types/auth";

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);

  useEffect(() => {
    const stored = localStorage.getItem("authUser");
    if (stored) {
      setUser(JSON.parse(stored));
    }
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
  };

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
