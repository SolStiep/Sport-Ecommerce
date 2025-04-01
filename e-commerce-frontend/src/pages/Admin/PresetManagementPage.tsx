import { useState, useEffect } from "react";
import { Button } from "antd";
import { useNavigate } from "react-router-dom";

import { Layout } from "@/components/layout/Layout";
import { PresetList } from "@/components/organisms/presets/PresetList";
import { PresetDetailsModal } from "@/components/organisms/presets/PresetDetailsModal";
import { UnderConstructionModal } from "@/components/molecules/UnderConstructionModal";
import { ConfirmModal } from "@/components/molecules/ConfirmModal"; 
import { usePreset } from "@/contexts/PresetContext";

export const PresetManagementPage = () => {
  const { presets, removePreset, fetchPresets } = usePreset();
  const [selectedPreset, setSelectedPreset] = useState(null);
  const [detailsModalVisible, setDetailsModalVisible] = useState(false);
  const [showConstructionModal, setShowConstructionModal] = useState(false);
  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [presetToDelete, setPresetToDelete] = useState<string | null>(null); 
  const navigate = useNavigate();

  useEffect(() => {
    fetchPresets();
  }, []);

  const handleDelete = async () => {
    if (presetToDelete) {
      await removePreset(presetToDelete);
      setShowConfirmModal(false); 
    }
  };

  const handleConfirmDelete = (id: string) => {
    setPresetToDelete(id);
    setShowConfirmModal(true);
  };

  const handleEdit = (presetId: string) => {
    navigate(`/admin/presets/${presetId}`);
  };

  const handleView = (preset) => {
    setSelectedPreset(preset);
    setDetailsModalVisible(true);
  };

  return (
    <Layout>
      <div className="max-w-6xl mx-auto p-6">
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-3xl font-bold">Admin Dashboard - Presets</h1>
          <Button
            type="primary"
            className="!bg-stone-500 !hover:bg-stone-700 !text-white"
            onClick={() => navigate("/admin/presets/new")}
          >
            Add Preset
          </Button>
        </div>

        <PresetList
          presets={presets}
          onView={handleView}
          onEdit={(p) => handleEdit(p.id)}
          onDelete={handleConfirmDelete} 
        />

        <PresetDetailsModal
          visible={detailsModalVisible}
          preset={selectedPreset}
          onClose={() => setDetailsModalVisible(false)}
          onEdit={handleEdit}
          onDelete={handleConfirmDelete}
        />

        <ConfirmModal
          isOpen={showConfirmModal}
          onConfirm={handleDelete}
          onCancel={() => setShowConfirmModal(false)}
          title="Delete Preset"
          message="Are you sure you want to delete this preset?"
          confirmText="Yes, Delete"
          cancelText="Cancel"
        />
      </div>
      <UnderConstructionModal
        visible={showConstructionModal}
        onClose={() => setShowConstructionModal(false)}
      />
    </Layout>
  );
};
