import { Product } from "../models/Product";

type Props = {
  product: Product;
  handleEdit: (product: Product) => void;
  handleDelete: (id: number) => void;
};

const LIST_ITEM_CLASS = "lex flex md:flex-row border-b py-2 text-center items-center";

const ACTION_BUTTON_GAP = "w-16";

const HEADERS = [
  { label: "ID", key: "id", className: "w-16 px-2" },
  { label: "Category ID", key: "categoryId", className: "w-32 px-2" },
  { label: "Name", key: "name", className: "flex-1 px-2" },
  { label: "Description", key: "description", className: "flex-1 px-2 overflow-x-auto whitespace-nowrap" },
  { label: "Price", key: "price", className: "w-24 px-2" },
  { label: "Stock Qty", key: "stockQuantity", className: "w-32 px-2 text-center" },
  { label: "Image URL", key: "imageUrl", className: "w-48 px-2 overflow-x-auto whitespace-nowrap" },
  { label: "Created At", key: "createdAt", className: "w-48 px-2 text-center" },
];

export function ProductTableHeader() {
  return (
    <li className={LIST_ITEM_CLASS + " font-bold"}>
      {HEADERS.map((header) => (
        <div key={header.label} className={header.className}>
          {header.label}
        </div>
      ))}
      <div className={ACTION_BUTTON_GAP} />
    </li>
  );
}

export function ProductList({ product, handleEdit, handleDelete }: Props) {
  return (
    <li className={LIST_ITEM_CLASS}>
      {HEADERS.map((header) => {
        const value = (product as any)[header.key];
        let displayValue = value;

        if (header.key === "price") {
          displayValue = `$${value.toFixed(2)}`;
        } else if (header.key === "createdAt" && value instanceof Date) {
          displayValue = value.toISOString().split("T")[0];
        }

        return (
          <div key={header.key} className={header.className}>
            {displayValue}
          </div>
        );
      })}
      <div className={ACTION_BUTTON_GAP + " flex gap-2"}>
        <button
          className="border p-1"
          onClick={() => {
            handleEdit(product);
          }}
        >
          Edit
        </button>
        <button
          className="border p-1"
          onClick={() => {
            handleDelete(product.id);
          }}
        >
          Delete
        </button>
      </div>
    </li>
  );
}
