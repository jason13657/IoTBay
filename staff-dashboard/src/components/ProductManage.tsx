import { useProducts } from "../hooks/useProducts";
import { ProductList, ProductTableHeader } from "./ProductList";
import { SearchBar } from "./SearchBar";

export function ProductManage() {
  const { products, loading, error } = useProducts();

  const handleSearch = (searchTerm: string) => {
    console.log("Searching for:", searchTerm);
  };

  if (loading) return <p>Loading products...</p>;
  if (error) return <p>Error loading products: {error}</p>;
  if (products.length === 0) return <p>No products available.</p>;

  return (
    <div className="flex flex-col">
      <div className="flex justify-between py-1 px-4 items-center">
        <div className="border w-1/2">
          <SearchBar onSearch={handleSearch} />
        </div>
        <div>
          <button className="border-2 border-solid p-2">Add new Product</button>
        </div>
      </div>
      <ul className="flex flex-col gap-2 p-4">
        <ProductTableHeader />
        {products.map((product) => (
          <ProductList product={product} />
        ))}
      </ul>
    </div>
  );
}
