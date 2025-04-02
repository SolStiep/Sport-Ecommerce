import { useState } from "react";
import { toast } from "react-hot-toast";
import { useNavigate } from "react-router-dom";
import { Button } from "antd";

import { usePreset } from "@/contexts/PresetContext";
import presetService from "@/services/presets";
import configuratorService from "@/services/configurations";

interface Props {
  product: any;
  selectedOptions: Record<string, string>;
  onReset: () => void;
  name: string;
  price: number;
  isEditing?: boolean;
  presetId?: string;
}

export const CreatePresetButton = ({
  product,
  selectedOptions,
  onReset,
  name,
  price,
  isEditing = false,
  presetId,
}: Props) => {
  const [loading, setLoading] = useState(false);
  const { updatePreset, createPreset } = usePreset();
  const navigate = useNavigate();

  const handleSubmit = async () => {
    if (!name || price <= 0) {
      toast.error("Name and valid price are required");
      return;
    }

    setLoading(true);
    try {
      const response = await configuratorService.prepare({
        productId: product.id,
        selectedOptions,
        preset: false,
        quantity: 1,
      });

      if (response.status !== 200) {
        toast.error("Configuration is not valid");
        return;
      }

      if (isEditing && presetId) {
        await updatePreset(presetId, {
          productId: product.id,
          selectedOptions,
          name,
          price,
        });
        toast.success("Preset updated successfully!");
      } else {
        await createPreset({
          productId: product.id,
          selectedOptions,
          name,
          price,
        });
        toast.success("Preset created successfully!");
      }

      onReset();
      navigate("/admin/presets");
    } catch (err) {
      toast.error("Failed to submit preset.");
    } finally {
      setLoading(false);
    }
  };

  const isDisabled =
    Object.keys(selectedOptions).length !== product.parts.length;

  return (
    <div className="text-right mt-6">
      <Button
        onClick={handleSubmit}
        disabled={isDisabled || loading}
        className="!bg-stone-500 !py-2 !px-4 !text-white !text-sm !hover:bg-stone-700"
      >
        {loading
          ? isEditing
            ? "Updating..."
            : "Validating..."
          : isEditing
          ? "Update Preset"
          : "Create Preset"}
      </Button>
    </div>
  );
};
