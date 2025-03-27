
import axiosInstance from "../utils/axiosInstance";

import { AuthResponse } from "@/types/auth";

export const loginApi = async (email: string, password: string): Promise<AuthResponse> => {
    const { data } = await axiosInstance.post("/auth/login", { email, password });
    return data;
};

export const registerApi = async (name: string, email: string, password: string): Promise<void> => {
    await axiosInstance.post("/auth/register", { name, email, password });
};

export const logoutApi = async (): Promise<void> => {
    await axiosInstance.post("/auth/logout");
};
