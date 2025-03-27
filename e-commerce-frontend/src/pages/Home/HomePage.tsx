import { useQuery } from "@tanstack/react-query";
import { getCategories } from "@/services/categories";
import { Link } from "react-router-dom";
import { Category } from "../../types/category";

export const HomePage = () => {
  const { data: categories, isLoading } = useQuery({
    queryKey: ["categories"],
    queryFn: async () => {
        const res = await getCategories();
        console.log("response from API", res);
        return res;
      },
  });

  if (isLoading) return <p>Loading...</p>;

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold mb-4">Find Your Perfect Bike</h1>
      <p className="mb-6 text-gray-600">Explore our range of high-quality bicycles for every type of rider.</p>
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
        {categories?.map((cat: Category) => (
          <Link
            to={`/products/${cat.id}`}
            key={cat.id}
            className="border rounded-md p-4 hover:bg-gray-100 text-center"
          >
            <p className="text-lg font-medium">{cat.name}</p>
          </Link>
        ))}
      </div>
    </div>
  );
};
