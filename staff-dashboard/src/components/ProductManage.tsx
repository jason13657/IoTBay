import { useState } from "react";
import { useProducts } from "../hooks/useProducts";
import { ProductList, ProductTableHeader } from "./ProductList";
import { SearchBar } from "./SearchBar";
import { Product } from "../models/Product";
import { Modal } from "./utils/Modal";
import { ProductForm } from "./ProductForm";
import { ConfirmAlert } from "./utils/ConfirmAlert";

export function ProductManage() {
  const { products, loading, error, updateProduct, addProduct, deleteProduct, setSearchKey } = useProducts();
  const [editProduct, setEditProduct] = useState<Product | null>(null);
  const [deleteProductId, setDeleteProductId] = useState<number | null>(null);
  const [isAddProduct, setIsAddProduct] = useState(false);

  const handleSearch = (searchKey: string) => {
    setSearchKey(searchKey);
  };

  const handleEdit = (product: Product) => {
    setEditProduct(product);
  };

  const handleUpdate = (product: Product) => {
    updateProduct(product.id, product);
    setEditProduct(null);
  };

  const handleAddProduct = () => {
    setIsAddProduct(true);
  };

  const handleDelete = (id: number) => {
    setDeleteProductId(id);
  };

  const confirmDelete = () => {
    if (deleteProductId) deleteProduct(deleteProductId);
    setDeleteProductId(null);
  };

  const handleAdd = (product: Product) => {
    addProduct(product);
    setIsAddProduct(false);
  };

  if (loading) return <p>Loading products...</p>;
  if (products.length === 0) return <p>No products available.</p>;

  return (
    <div className="flex flex-col">
      <div className="flex justify-between py-1 px-4 items-center">
        <div className="border w-1/2">
          <SearchBar onSearch={handleSearch} />
        </div>
        <div>
          <button onClick={handleAddProduct} className="border-2 border-solid p-2">
            Add new Product
          </button>
        </div>
      </div>
      {error ? (
        <p>Error loading products: {error}</p>
      ) : (
        <ul className="flex flex-col gap-2 p-4">
          <ProductTableHeader />
          {products.map((product) => (
            <ProductList key={product.id} product={product} handleEdit={handleEdit} handleDelete={handleDelete} />
          ))}
        </ul>
      )}
      {editProduct && (
        <Modal
          onClose={() => setEditProduct(null)}
          children={<ProductForm initialProduct={editProduct} onSubmit={handleUpdate} />}
        />
      )}
      {isAddProduct && <Modal onClose={() => setIsAddProduct(false)} children={<ProductForm onSubmit={handleAdd} />} />}
      {deleteProductId && (
        <Modal
          onClose={() => setDeleteProductId(null)}
          children={
            <ConfirmAlert
              title="Delete Product"
              message="Are you sure? Do you want delete this product?"
              onCancel={() => setDeleteProductId(null)}
              onConfirm={confirmDelete}
            />
          }
        />
      )}
    </div>
  );
}
