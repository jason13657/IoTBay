import { useEffect, useState } from "react";
import { Product } from "../models/Product";
import { useServices } from "../context/ServiceContext";

export function useProducts() {
  const { productService } = useServices();
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    productService
      .getProducts()
      .then((data) => {
        setProducts(data);
        setLoading(false);
      })
      .catch((err) => {
        setError("Failed to fetch products");
        setLoading(false);
      });
  }, [productService]);

  return { products, loading, error };
}
