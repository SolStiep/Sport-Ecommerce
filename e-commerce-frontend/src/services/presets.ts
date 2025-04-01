import axiosInstance from "../utils/axiosInstance";
import { Preset } from "@types/preset";

class PresetService {
  async getPresets(): Promise<Preset[]> {
    const { data } = await axiosInstance.get(`/presets/catalog`);
    return data;
  }

  async getPresetById(presetId: string): Promise<Preset> {
    const { data } = await axiosInstance.get(`/presets/${presetId}`);
    return data;
  }

  async create(presetData: Preset): Promise<Preset> {
    const { data } = axiosInstance.post("/presets", presetData);
    return data;
  }

  async update(presetId: string, presetData: Preset): Promise<Preset> {
    const { data } = axiosInstance.put(`/presets/${presetId}`, presetData);
    return data;
  }

  async delete(id: string): Promise<void> {
    await axiosInstance.delete(`/presets/${id}`);
  }
}

export default new PresetService();
