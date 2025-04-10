import { useState } from "react";
import { Button } from "antd";
import { useNavigate } from "react-router-dom";
import { Layout } from "@/components/layout/Layout";
import { ProductList } from "@/components/organisms/product/ProductList";
import { ProductDetailsModal } from "@/components/organisms/product/ProductDetailsModal";
import { CategoryModal } from "@/components/organisms/category/CategoryModal";
import { UnderConstructionModal } from "@/components/molecules/UnderConstructionModal";
import { useProduct } from "@/contexts/ProductContext";
import { ConfirmModal } from "@/components/molecules/ConfirmModal";

export const AdminDashboard = () => {
  const { products, removeProduct } = useProduct();
  const [categoryModalOpen, setCategoryModalOpen] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
  const [detailsModalVisible, setDetailsModalVisible] = useState(false);
  const [showConstructionModal, setShowConstructionModal] = useState(false);
  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [productToDelete, setProductToDelete] = useState<string | null>(null);
  const navigate = useNavigate();

  const handleDelete = async () => {
    if (productToDelete) {
      await removeProduct(productToDelete);
      setShowConfirmModal(false);
    }
  };

  const handleConfirmDelete = (id: string) => {
    setProductToDelete(id);
    setShowConfirmModal(true);
  };

  const handleEdit = (productId: string) => {
    setShowConstructionModal(true);
    // navigate(`/admin/products/${productId}/edit`);
  };

  const handleView = (product: Product) => {
    setSelectedProduct(product);
    setDetailsModalVisible(true);
  };

  return (
    <Layout>
      <div className="max-w-6xl mx-auto p-6">
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-3xl font-bold">Admin Dashboard - Products</h1>
          <div className="flex gap-3">
            <Button
              type="default"
              className="!text-stone-600"
              onClick={() => setCategoryModalOpen(true)}
            >
              Manage Categories
            </Button>
            <Button
              type="primary"
              className="!bg-stone-500 !hover:bg-stone-700 !text-white"
              onClick={() => navigate("/admin/products/new")}
            >
              Add Product
            </Button>
          </div>
        </div>

        <ProductList
          products={products}
          onView={handleView}
          onEdit={(p) => handleEdit(p.id)}
          onDelete={handleConfirmDelete}
        />

        <CategoryModal
          visible={categoryModalOpen}
          onClose={() => setCategoryModalOpen(false)}
        />

        <ProductDetailsModal
          visible={detailsModalVisible}
          product={selectedProduct}
          onClose={() => setDetailsModalVisible(false)}
          onEdit={handleEdit}
          onDelete={handleConfirmDelete}
        />
      </div>
      <UnderConstructionModal
        visible={showConstructionModal}
        onClose={() => setShowConstructionModal(false)}
      />

      <ConfirmModal
        isOpen={showConfirmModal}
        onConfirm={handleDelete}
        onCancel={() => setShowConfirmModal(false)}
        title="Delete Product"
        message="Are you sure you want to delete this product?"
        confirmText="Yes, Delete"
        cancelText="Cancel"
      />
    </Layout>
  );
};
