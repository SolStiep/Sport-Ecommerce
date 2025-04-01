import { useState, useRef, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  FiShoppingCart,
  FiLogOut,
  FiList,
  FiGrid,
  FiSettings,
  FiFileText,
} from "react-icons/fi";

import { useAuth } from "@/contexts/AuthContext";
import { useCart } from "@/contexts/CartContext";
import { Badge } from "@/components/atoms/Badge";

export const Navbar = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const [menuOpen, setMenuOpen] = useState(false);
  const [adminMenuOpen, setAdminMenuOpen] = useState(false);
  const menuRef = useRef<HTMLDivElement>(null);
  const adminRef = useRef<HTMLDivElement>(null);
  const { cartItems } = useCart();

  const totalQuantity = cartItems.reduce((acc, item) => acc + item.quantity, 0);

  const handleCartClick = (e: React.MouseEvent) => {
    if (!user) {
      e.preventDefault();
      navigate("/login");
    }
  };

  useEffect(() => {
    const handleClickOutside = (e: MouseEvent) => {
      if (menuRef.current && !menuRef.current.contains(e.target as Node)) {
        setMenuOpen(false);
      }
      if (adminRef.current && !adminRef.current.contains(e.target as Node)) {
        setAdminMenuOpen(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  return (
    <nav className="bg-white shadow-sm p-4 flex justify-between items-center">
      <Link to="/" className="text-2xl font-bold text-stone-700">
        BIKE STORE
      </Link>

      <div className="space-x-4 flex items-center relative">
        <Link to="/" className="text-stone-700 hover:text-black">
          Shop
        </Link>
        <Link to="/about" className="text-stone-700 hover:text-black">
          About
        </Link>
        <Link to="/contact" className="text-stone-700 hover:text-black">
          Contact
        </Link>

        <Link
          to={user ? "/cart" : "#"}
          onClick={handleCartClick}
          className="relative text-stone-700 hover:text-black"
        >
          <FiShoppingCart size={18} />
          <Badge count={totalQuantity} />
        </Link>

        {user ? (
          <div className="relative" ref={menuRef}>
            <button
              onClick={() => setMenuOpen((prev) => !prev)}
              className="text-sm text-stone-900 hover:underline"
            >
              Hello, {user.name}
            </button>

            {menuOpen && (
              <div className="absolute right-0 mt-2 w-52 bg-white border border-gray-200 rounded shadow-md z-10">
                {user?.role === "ADMIN" && (
                  <>
                    <Link
                      to="/admin"
                      className="flex items-center px-4 py-2 text-sm text-stone-700 hover:bg-gray-100"
                      onClick={() => setMenuOpen(false)}
                    >
                      <FiGrid className="mr-2" /> Products
                    </Link>
                    <Link
                      to="/admin/presets"
                      className="flex items-center px-4 py-2 text-sm text-stone-700 hover:bg-gray-100"
                      onClick={() => setMenuOpen(false)}
                    >
                      <FiSettings className="mr-2" /> Presets
                    </Link>
                    <Link
                      to="/admin/orders"
                      className="flex items-center px-4 py-2 text-sm text-stone-700 hover:bg-gray-100"
                      onClick={() => setMenuOpen(false)}
                    >
                      <FiFileText className="mr-2" /> Orders
                    </Link>
                  </>
                )}
                <Link
                  to="/orders"
                  className="flex items-center px-4 py-2 text-sm text-stone-700 hover:bg-gray-100"
                  onClick={() => setMenuOpen(false)}
                >
                  <FiList className="mr-2" /> View Orders
                </Link>
                <button
                  onClick={() => {
                    logout();
                    setMenuOpen(false);
                  }}
                  className="flex w-full items-center px-4 py-2 text-sm text-red-600 hover:bg-gray-100"
                >
                  <FiLogOut className="mr-2" /> Logout
                </button>
              </div>
            )}
          </div>
        ) : (
          <Link to="/login" className="text-blue-600 hover:underline text-sm">
            Login
          </Link>
        )}
      </div>
    </nav>
  );
};
