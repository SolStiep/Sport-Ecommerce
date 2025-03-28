export interface CartItem {
    id: string;
    name: string;
    price: number;
    type: "preset" | "custom";
    selectedOptions: Record<string, string>;
    quantity: number;
}
