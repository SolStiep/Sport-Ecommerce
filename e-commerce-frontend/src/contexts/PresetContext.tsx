import { createContext, useContext, useEffect, useState, ReactNode } from "react";
import { toast } from "react-hot-toast";

import { Preset } from "@/types/preset";
import presetService from "@/services/presets";

interface PresetContextType {
  presets: Preset[];
  fetchPresets: () => void;
  removePreset: (id: string) => Promise<void>;
}

const PresetContext = createContext<PresetContextType | undefined>(undefined);

export const PresetProvider = ({ children }: { children: ReactNode }) => {
  const [presets, setPresets] = useState<Preset[]>([]);

  const fetchPresets = async () => {
    try {
      const data = await presetService.getPresets();
      setPresets(data);
    } catch (err) {
      toast.error("Error fetching presets. Please try again.");
    }
  };

  const removePreset = async (id: string) => {
    try {
      await presetService.delete(id);
      setPresets((prev) => prev.filter((p) => p.id !== id));
    } catch (err) {
      toast.error("Error deleting preset. Please try again.");
    }
  };

  useEffect(() => {
    fetchPresets();
  }, []);

  return (
    <PresetContext.Provider value={{ presets, fetchPresets, removePreset }}>
      {children}
    </PresetContext.Provider>
  );
};

export const usePreset = () => {
  const context = useContext(PresetContext);
  if (!context) {
    throw new Error("usePreset must be used within a PresetProvider");
  }
  return context;
};
