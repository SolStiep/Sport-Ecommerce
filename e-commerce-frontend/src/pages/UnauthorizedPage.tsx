import { Link } from "react-router-dom";

export const UnauthorizedPage = () => {
  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
      <div className="bg-white shadow-lg rounded-lg p-8 max-w-md w-full text-center">
        <h1 className="text-4xl font-bold text-red-600 mb-4">403</h1>
        <h2 className="text-2xl font-semibold text-gray-800 mb-2">Access Denied</h2>
        <p className="text-gray-600 mb-6">
            You don't have permission to access this section. Please contact an administrator if you believe this is an error.
        </p>
        <Link
          to="/"
          className="inline-block bg-stone-500 text-white px-5 py-2 rounded hover:bg-stone-700 transition"
        >
          Back to Home
        </Link>
      </div>
    </div>
  );
};
