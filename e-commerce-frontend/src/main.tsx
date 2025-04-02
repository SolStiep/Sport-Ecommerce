import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import { RouterProvider } from "react-router-dom";
import { QueryClientProvider, QueryClient } from "@tanstack/react-query";
import { router } from "./router";
import { AuthProvider } from "@/contexts/AuthContext";
import { CartProvider } from "@/contexts/CartContext";
import { ProductProvider } from "@/contexts/ProductContext";
import { PresetProvider } from "@/contexts/PresetContext";
import { CategoryProvider } from "@/contexts/CategoryContext";
import { ConfigProvider } from "antd";
import { Toaster } from "react-hot-toast";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: 1,
      refetchOnWindowFocus: false,
    },
  },
});

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <ConfigProvider>
      <CartProvider>
        <AuthProvider>
          <ProductProvider>
            <CategoryProvider>
              <PresetProvider>
                {/* <ReactQueryDevtools initialIsOpen={false} /> */}
                <QueryClientProvider client={queryClient}>
                  <>
                    <RouterProvider router={router} />
                    <Toaster
                      position="top-center"
                      toastOptions={{ duration: 3000 }}
                    />
                  </>
                </QueryClientProvider>
              </PresetProvider>
            </CategoryProvider>
          </ProductProvider>
        </AuthProvider>
      </CartProvider>
    </ConfigProvider>
  </StrictMode>
);
