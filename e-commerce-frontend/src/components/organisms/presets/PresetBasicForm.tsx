interface Props {
    preset: Preset;
    readOnly?: boolean;
  }
  
  export const PresetBasicForm = ({ preset, readOnly = false }: Props) => {
    const selectedOptions = preset.selectedOptions;
  
    return (
      <div className="space-y-6">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium text-gray-700">
              Preset Name
            </label>
            <input
              type="text"
              value={preset.name}
              readOnly
              className="mt-1 block w-full p-2 border rounded bg-gray-100"
            />
          </div>
  
          <div>
            <label className="block text-sm font-medium text-gray-700">
              Preset Price
            </label>
            <input
              type="text"
              value={`$${preset.price}`}
              readOnly
              className="mt-1 block w-full p-2 border rounded bg-gray-100"
            />
          </div>
        </div>
  
        <div className="mt-4">
          <h3 className="text-lg font-semibold mb-2">Configuration</h3>
          <ul className="list-disc list-inside space-y-1 text-gray-800">
            {Object.entries(selectedOptions).map(([partName, optionName]) => (
              <li key={partName}>
                <span className="font-medium">{partName}:</span> {optionName}
              </li>
            ))}
          </ul>
        </div>
      </div>
    );
  };
  