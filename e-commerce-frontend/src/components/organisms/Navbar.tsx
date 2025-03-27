import { Link } from "react-router-dom";

export const Navbar = () => {
  return (
    <nav className="bg-white shadow-sm p-4 flex justify-between items-center">
      <Link to="/" className="text-2xl font-bold text-blue-600">BIKE STORE</Link>
      <div className="space-x-4">
        <Link to="/" className="text-gray-600 hover:text-black">Shop</Link>
        <Link to="/about" className="text-gray-600 hover:text-black">About</Link>
        <Link to="/contact" className="text-gray-600 hover:text-black">Contact</Link>
        <Link to="/cart" className="text-gray-600 hover:text-black">🛒</Link>
      </div>
    </nav>
  );
};
