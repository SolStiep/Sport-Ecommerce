export const Summary = ({
  product,
  selectedOptions,
  totalPrice,
}: {
  product: Product;
  selectedOptions: Record<string, string>;
  totalPrice: number;
}) => {
  const calculateTotalPrice = () => {
    let sum = 0;
    for (const part of product.parts) {
      const selectedOptionId = selectedOptions[part.id];
      const option = part.options.find((opt) => opt.id === selectedOptionId);
      if (option) sum += option.price;
    }
    return sum;
  };

  const displayPrice =
    totalPrice > 0 ? totalPrice : calculateTotalPrice();

  return (
    <div className="mb-6 p-4 bg-gray-100 rounded-lg">
      <h2 className="text-xl font-semibold mb-4 text-stone-500">
        Summary of your custom {product.name}
      </h2>
      <ul className="list-disc list-inside">
        {product.parts.map((part) => {
          const selectedOptionId = selectedOptions[part.id];
          const selectedOption = part.options.find(
            (opt) => opt.id === selectedOptionId
          );
          return (
            <li key={part.id} className="text-lg">
              {part.name}:{" "}
              {selectedOption ? selectedOption.name : "Not selected"}
            </li>
          );
        })}
      </ul>
      <p className="text-xl font-bold mt-4">Total: ${displayPrice.toFixed(2)}</p>
    </div>
  );
};
