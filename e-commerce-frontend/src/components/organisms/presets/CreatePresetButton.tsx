import { useState } from "react";
import { toast } from "react-hot-toast";
import { Button } from "antd";

import presetService from "@/services/presets";
import configuratorService from "@/services/configurations";

interface Props {
  product: any;
  selectedOptions: Record<string, string>;
  onReset: () => void;
  name: string;
  price: number;
}

export const CreatePresetButton = ({
  product,
  selectedOptions,
  onReset,
  name,
  price,
}: Props) => {
  const [loading, setLoading] = useState(false);

  const handleCreatePreset = async () => {
    if (!name || price <= 0) {
      toast.error("Name and valid price are required");
      return;
    }

    setLoading(true);
    try {
      const response = await configuratorService.prepare({
        productId: product.id,
        selectedOptions,
        preset: true,
      });

      if (response.status !== 200) {
        toast.error("Configuration is not valid");
        return;
      }

      await presetService.create({
        productId: product.id,
        selectedOptions,
        name,
        price,
      });

      toast.success("Preset created successfully!");
      onReset();
    } catch (err) {
      toast.error("Failed to create preset.");
    } finally {
      setLoading(false);
    }
  };

  const isDisabled =
    Object.keys(selectedOptions).length !== product.parts.length;

  return (
    <div className="text-right mt-6">
      <Button
        onClick={handleCreatePreset}
        disabled={isDisabled || loading}
        className="!bg-stone-500 !py-2 !px-4 !text-white !text-sm !hover:bg-stone-700"
      >
        {loading ? "Validating..." : "Create Preset"}
      </Button>
    </div>
  );
};
