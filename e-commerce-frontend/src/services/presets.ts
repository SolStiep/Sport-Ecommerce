import axiosInstance from "../utils/axiosInstance";

export const getPresets = async () => {
  const { data } = await axiosInstance.get(`/presets/catalog`);
  return data;
};
