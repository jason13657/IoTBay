import { useState } from "react";
import { Product } from "../models/Product";

type Props = {
  initialProduct?: Product;
  onSubmit: (product: Product) => void;
};

export function ProductForm({ initialProduct, onSubmit }: Props) {
  const [form, setForm] = useState<Product>(initialProduct || new Product(0, 0, "", "", 0, 0, "", new Date()));

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setForm(
      (prev) =>
        new Product(
          name === "id" ? Number(value) : prev.id,
          name === "categoryId" ? Number(value) : prev.categoryId,
          name === "name" ? value : prev.name,
          name === "description" ? value : prev.description,
          name === "price" ? Number(value) : prev.price,
          name === "stockQuantity" ? Number(value) : prev.stockQuantity,
          name === "imageUrl" ? value : prev.imageUrl,
          name === "createdAt" ? new Date(value) : prev.createdAt
        )
    );
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(form);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4 p-4 border rounded shadow-md w-7/12 mx-auto bg-white">
      <div>
        <label className="block text-sm font-medium">ID</label>
        <input
          type="number"
          name="id"
          value={form.id}
          onChange={handleChange}
          className="w-full border rounded px-3 py-2"
          disabled={true}
        />
      </div>

      <div>
        <label className="block text-sm font-medium">Category ID</label>
        <input
          type="number"
          name="categoryId"
          value={form.categoryId}
          onChange={handleChange}
          className="w-full border rounded px-3 py-2"
          required
        />
      </div>

      <div>
        <label className="block text-sm font-medium">Name</label>
        <input
          type="text"
          name="name"
          value={form.name}
          onChange={handleChange}
          className="w-full border rounded px-3 py-2"
          required
        />
      </div>

      <div>
        <label className="block text-sm font-medium">Description</label>
        <textarea
          name="description"
          value={form.description}
          onChange={handleChange}
          className="w-full border rounded px-3 py-2"
        />
      </div>

      <div>
        <label className="block text-sm font-medium">Price</label>
        <input
          type="number"
          step="0.01"
          name="price"
          value={form.price}
          onChange={handleChange}
          className="w-full border rounded px-3 py-2"
          required
        />
      </div>

      <div>
        <label className="block text-sm font-medium">Stock Quantity</label>
        <input
          type="number"
          name="stockQuantity"
          value={form.stockQuantity}
          onChange={handleChange}
          className="w-full border rounded px-3 py-2"
          required
        />
      </div>

      <div>
        <label className="block text-sm font-medium">Image URL</label>
        <input
          type="text"
          name="imageUrl"
          value={form.imageUrl}
          onChange={handleChange}
          className="w-full border rounded px-3 py-2"
        />
      </div>

      <div>
        <label className="block text-sm font-medium">Created At</label>
        <input
          type="date"
          name="createdAt"
          value={form.createdAt.toISOString().split("T")[0]}
          onChange={handleChange}
          className="w-full border rounded px-3 py-2"
          required
        />
      </div>

      <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
        Submit
      </button>
    </form>
  );
}
