import { useState, useMemo, useEffect } from "react";
import { useQuery } from "@tanstack/react-query";

import categoryService from "@/services/categories"; 
import { getPresets } from "@/services/presets";
import { Layout } from "@/components/layout/Layout";
import { CategoryFilter } from "@/components/home/CategoryFilter";
import { PresetCard } from "@/components/home/PresetCard";
import { Preset } from "@/types/preset";

export const HomePage = () => {
  const [selectedCategories, setSelectedCategories] = useState<string[]>([]);
  const [expandedPresetId, setExpandedPresetId] = useState<string | null>(null);

  useEffect(() => {
    setExpandedPresetId(null);
  }, [selectedCategories]);

  const { data: categories = [], isLoading: loadingCategories } = useQuery({
    queryKey: ["categories"],
    queryFn: () => categoryService.getCategories(),
  });

  const { data: presets = [], isLoading: loadingPresets } = useQuery<Preset[]>({
    queryKey: ["presets"],
    queryFn: getPresets,
  });

  const handleToggleDetails = (id: string) => {
    setExpandedPresetId((prev) => (prev === id ? null : id));
  };

  const handleCategoryChange = (id: string) => {
    setSelectedCategories((prev) =>
      prev.includes(id) ? prev.filter((c) => c !== id) : [...prev, id]
    );
  };

  const filteredPresets = useMemo(() => {
    if (selectedCategories.length === 0) return presets;
    return presets.filter((preset: Preset) =>
      selectedCategories.includes(preset.product.category.id)
    );
  }, [presets, selectedCategories]);

  return (
    <Layout>
      <div className="max-w-5xl mx-auto px-4">
        <div className="text-center my-10">
          <h1 className="text-4xl font-bold mb-3">Find Your Perfect Bike</h1>
          <p className="text-gray-600">
            Explore our range of high-quality bicycles for every type of rider.
          </p>
        </div>

        {loadingCategories ? (
          <p>Loading categories...</p>
        ) : (
          <CategoryFilter
            categories={categories}
            selected={selectedCategories}
            onChange={handleCategoryChange}
            onClear={() => setSelectedCategories([])}
          />
        )}

        {loadingPresets ? (
          <p>Loading presets...</p>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
            {filteredPresets.map((preset: Preset) => (
              <PresetCard
                key={preset.id}
                preset={preset}
                isExpanded={expandedPresetId === preset.id}
                onToggle={() => handleToggleDetails(preset.id)}
              />
            ))}

          </div>
        )}
      </div>
    </Layout>
  );
};
