import axiosInstance from "../utils/axiosInstance";

export const getPresets = async () => {
  const { data } = await axiosInstance.get(`/presets/catalog`);
  return data;
};

export const getPresetById = async (presetId: string) => {
  const { data } = await axiosInstance.get(`/presets/${presetId}`);
  return data;
};
