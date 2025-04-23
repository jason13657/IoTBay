import { Product } from "../models/Product";

export interface IProductService {
  getProducts(): Promise<Product[]>;
  getProductById(id: string): Promise<Product>;
  getProductsByName(name: string): Promise<Product[]>;
  createProduct(product: Product): Promise<Product>;
  updateProduct(id: number, product: Product): Promise<Product>;
  deleteProduct(id: number): Promise<void>;
}
