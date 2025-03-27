
import axiosInstance from "../utils/axiosInstance";

export const getCategories = async () => {
    const { data } = await axiosInstance.get(`/categories`);
    return data;
};
