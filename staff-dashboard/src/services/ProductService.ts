import { Product } from "../models/Product";

export interface IProductService {
  getProducts(): Promise<Product[]>;
  getProductById(id: string): Promise<Product>;
  createProduct(product: Product): Promise<Product>;
  updateProduct(id: string, product: Product): Promise<Product>;
  deleteProduct(id: string): void;
}
