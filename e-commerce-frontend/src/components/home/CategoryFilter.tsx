import { Category } from "@/types/category";
import { FiXCircle } from "react-icons/fi";

interface Props {
  categories: Category[];
  selected: string[];
  onChange: (id: string) => void;
  onClear: () => void;
}

export const CategoryFilter = ({ categories, selected, onChange, onClear }: Props) => {
  return (
    <div className="flex flex-wrap gap-3 mb-6 justify-center items-center">
      {categories.map((cat) => (
        <button
          key={cat.id}
          onClick={() => onChange(cat.id)}
          className={`px-4 py-2 rounded-full border transition ${
            selected.includes(cat.id)
              ? "bg-blue-600 text-white border-blue-600"
              : "border-gray-300 text-gray-700 hover:bg-gray-100"
          }`}
        >
          {cat.name}
        </button>
      ))}

      {selected.length > 0 && (
        <button
          onClick={onClear}
          className="flex items-center gap-1 ml-4 text-sm px-2 py-1 rounded-full hover:border hover:border-lime-300 text-lime-700 hover:bg-lime-50"
        >
          <FiXCircle />
          Clear Filters
        </button>
      )}
    </div>
  );
};
