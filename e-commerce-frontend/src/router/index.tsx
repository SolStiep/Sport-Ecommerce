import { createBrowserRouter } from "react-router-dom";
import { HomePage } from "@/pages/Home/HomePage";
import { AboutPage } from "@/pages/About/AboutPage";
import { ContactPage } from "@/pages/Contact/ContactPage";
import { LoginPage } from "@/pages/Auth/LoginPage";
import { RegisterPage } from "@/pages/Auth/RegisterPage";
import { CartPage } from "@/pages/Cart/CartPage";
import { AdminDashboard } from "@/pages/Admin/AdminDashboard";
import { ProductFormPage } from "@/pages/Admin/ProductFormPage";
import { ConfiguratorFormPage } from "@/pages/Admin/ConfiguratorFormPage";
import { PresetCreatorPage } from "@/pages/Admin/PresetCreatorPage";
import { PresetManagementPage } from "@/pages/Admin/PresetManagementPage";
import { OrderPage } from "@/pages/Admin/OrderPage";
import { OrderHistoryPage } from "@/pages/Order/OrderHistoryPage";
import { ProductCustomizationPage } from "@/pages/ProductCustomization/ProductCustomizationPage";
import { UnauthorizedPage } from "@/pages/UnauthorizedPage";
import AdminRoute from "@/router/AdminRoute";

export const router = createBrowserRouter([
  { path: "/", element: <HomePage /> },
  { path: "/customize", element: <ProductCustomizationPage /> },
  { path: "/orders", element: <OrderHistoryPage /> },
  { path: "/about", element: <AboutPage /> },
  { path: "/contact", element: <ContactPage /> },
  { path: "/login", element: <LoginPage /> },
  { path: "/register", element: <RegisterPage /> },
  { path: "/unauthorized", element: <UnauthorizedPage /> },
  {
    path: '/admin',
    element: (
      <AdminRoute>
        <AdminDashboard />
      </AdminRoute>
    ),
  },
  {
    path: "/admin/products/new",
    element: (
      <AdminRoute>
        <ProductFormPage />
      </AdminRoute>
    ),
  },
  {
    path: "/admin/products/:productId",
    element: (
      <AdminRoute>
        <ProductFormPage />
      </AdminRoute>
    ),
  },
  {
    path: "/admin/products/:productId/configurator",
    element: (
      <AdminRoute>
        <ConfiguratorFormPage />
      </AdminRoute>
    ),
  },
  {
    path: "/admin/presets/new",
    element: (
      <AdminRoute>
        <PresetCreatorPage />
      </AdminRoute>
    ),
  },
  {
    path: "/admin/presets",
    element: (
      <AdminRoute>
        <PresetManagementPage />
      </AdminRoute>
    ),
  },
  {
    path: "/admin/presets/:presetId",
    element: (
      <AdminRoute>
        <PresetCreatorPage />
      </AdminRoute>
    ),
  },
  {
    path: "/admin/orders",
    element: (
      <AdminRoute>
        <OrderPage />
      </AdminRoute>
    ),
  },
    { path: "/cart", element: <CartPage /> },
]);
