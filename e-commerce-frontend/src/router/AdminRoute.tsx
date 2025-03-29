import { Navigate } from 'react-router-dom';
import { useAuth } from '@/contexts/AuthContext';
import React from 'react';

interface AdminRouteProps {
  children: React.ReactNode;
}

const AdminRoute: React.FC<AdminRouteProps> = ({ children }) => {
  const { user } = useAuth();

  
  if (!user) {
    return <Navigate to="/login" />;
  }

  if (user.role !== 'ADMIN') {
    return <Navigate to="/unauthorized" />;
  }
  return children;
};

export default AdminRoute;
