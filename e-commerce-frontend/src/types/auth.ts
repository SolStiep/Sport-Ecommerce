export interface AuthResponse {
  name: string;
  email: string;
  token?: string;
}

export interface User {
  name: string;
  email: string;
  token?: string;
}

export interface AuthContextType {
  user: User | null;
  login: (email: string, password: string) => Promise<void>;
  register: (name: string, email: string, password: string) => Promise<void>;
  logout: () => void;
}

export interface TokenPayload {
  sub: string;
  exp: number;
  [key: string]: any;
}
