import { useCallback, useEffect, useState } from "react";
import { Product } from "../models/Product";
import { useServices } from "../context/ServiceContext";

export function useProducts() {
  const { productService } = useServices();
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [searchKey, setSearchKey] = useState("");

  useEffect(() => {
    fetchProducts();
  }, [productService, searchKey]);

  const fetchProducts = useCallback(() => {
    setLoading(true);
    setError(null);
    if (searchKey != "") {
      fetchProductsByName(searchKey);
    } else {
      fetchAllProducts();
    }
  }, [productService, searchKey]);

  const fetchAllProducts = useCallback(() => {
    setLoading(true);
    setError(null);
    productService
      .getProducts()
      .then((data) => {
        setProducts(data);
        setLoading(false);
      })
      .catch((err) => {
        setLoading(false);
        setError("Failed to fetch products" + err.message);
      });
  }, [productService]);

  const fetchProductsByName = useCallback(
    (name: string) => {
      setLoading(true);
      setError(null);
      productService
        .getProductsByName(name)
        .then((data) => {
          setProducts(data);
          setLoading(false);
        })
        .catch((err) => {
          setLoading(false);
          setError("Failed to fetch products" + err.message);
        });
    },
    [productService]
  );

  const updateProduct = useCallback(
    (id: number, product: Product) => {
      setLoading(true);
      setError(null);
      productService
        .updateProduct(id, product)
        .then((updatedProduct) => {
          setLoading(false);
          fetchProducts();
        })
        .catch((err) => {
          setLoading(false);
          setError("Failed to update product" + err.message);
        });
    },
    [productService]
  );

  const addProduct = useCallback(
    (product: Product) => {
      setLoading(true);
      setError(null);
      productService
        .createProduct(product)
        .then((newProduct) => {
          setLoading(false);
          fetchProducts();
        })
        .catch((err) => {
          setLoading(false);
          setError("Failed to add product" + err.message);
        });
    },
    [productService]
  );

  const deleteProduct = useCallback(
    (id: number) => {
      setLoading(true);
      setError(null);
      productService
        .deleteProduct(id)
        .then(() => {
          setLoading(false);
          fetchProducts();
        })
        .catch((err) => {
          setLoading(false);
          setError("Failed to delete product" + err.message);
        });
    },
    [productService]
  );

  return { products, loading, error, updateProduct, addProduct, deleteProduct, fetchProductsByName, setSearchKey };
}
