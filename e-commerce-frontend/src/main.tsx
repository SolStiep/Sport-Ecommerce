import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { RouterProvider } from 'react-router-dom'
import { QueryClientProvider, QueryClient } from "@tanstack/react-query";
import { router } from './router';
import { AuthProvider } from "@/contexts/AuthContext";
import { ProductProvider } from "@/contexts/ProductContext";
import { CategoryProvider } from "@/contexts/CategoryContext";
import { ConfigProvider } from 'antd';
import { Toaster } from 'react-hot-toast';

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: 1,
      refetchOnWindowFocus: false,
    },
  },
});

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <ConfigProvider>
    <AuthProvider>
      <ProductProvider>
        <CategoryProvider>
          <QueryClientProvider client={queryClient}>
            <>
            <RouterProvider router={router} />
            <Toaster position="top-right" toastOptions={{ duration: 3000 }} />
            </>
          </QueryClientProvider>
        </CategoryProvider>
      </ProductProvider>
    </AuthProvider>
    </ConfigProvider>
  </StrictMode>,
)
