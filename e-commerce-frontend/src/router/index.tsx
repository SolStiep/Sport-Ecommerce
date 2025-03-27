import { createBrowserRouter } from "react-router-dom";
import { HomePage } from "@/pages/Home/HomePage";
// import { ProductDetailsPage } from "@/pages/ProductDetails/ProductDetailsPage";
// import { ConfiguratorPage } from "@/pages/Configurator/ConfiguratorPage";

export const router = createBrowserRouter([
  { path: "/", element: <HomePage /> },
//   { path: "/products/:productId", element: <ProductDetailsPage /> },
//   { path: "/configurator/:productId", element: <ConfiguratorPage /> },
]);
