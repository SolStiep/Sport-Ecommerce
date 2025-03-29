import { createBrowserRouter } from "react-router-dom";
import { HomePage } from "@/pages/Home/HomePage";
import { AboutPage } from "@/pages/About/AboutPage";
import { ContactPage } from "@/pages/Contact/ContactPage";
import { LoginPage } from "@/pages/Auth/LoginPage";
import { RegisterPage } from "@/pages/Auth/RegisterPage";
import { AdminDashboard } from "@/pages/Admin/AdminDashboard";
import AdminRoute from "@/router/AdminRoute";
// import { ProductDetailsPage } from "@/pages/ProductDetails/ProductDetailsPage";
// import { ConfiguratorPage } from "@/pages/Configurator/ConfiguratorPage";

export const router = createBrowserRouter([
  { path: "/", element: <HomePage /> },
  //   { path: "/products/:productId", element: <ProductDetailsPage /> },
  //   { path: "/configurator/:productId", element: <ConfiguratorPage /> },
  { path: "/about", element: <AboutPage /> },
  { path: "/contact", element: <ContactPage /> },
  { path: "/login", element: <LoginPage /> },
  { path: "/register", element: <RegisterPage /> },
  {
    path: '/admin',
    element: (
      <AdminRoute>
        <AdminDashboard />
      </AdminRoute>
    ),
  },
]);
