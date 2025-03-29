export interface AuthResponse {
  name: string;
  email: string;
  token?: string;
  role: 'USER' | 'ADMIN';
}

export interface User {
  name: string;
  email: string;
  token?: string;
  role: 'USER' | 'ADMIN';
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
