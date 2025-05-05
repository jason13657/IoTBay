import { Product } from "../models/Product";
import { HttpClient } from "../network/HttpClient";

export interface IProductService {
  getProducts(): Promise<Product[]>;
  getProductById(id: number): Promise<Product>;
  getProductsByName(name: string): Promise<Product[]>;
  createProduct(product: Product): Promise<Product>;
  updateProduct(id: number, product: Product): Promise<void>;
  deleteProduct(id: number): Promise<void>;
}

export class ProductService implements IProductService {
  private httpClient: HttpClient;

  constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  async getProducts(): Promise<Product[]> {
    const data = await this.httpClient.get<Product[]>("/products");
    return data.map(Product.fromJson);
  }

  async getProductById(id: number): Promise<Product> {
    const data = await this.httpClient.get<Product>(`/products?id=${id}`);
    return Product.fromJson(data);
  }

  async getProductsByName(name: string): Promise<Product[]> {
    const data = await this.httpClient.get<Product[]>(`/products?name=${name}`);
    return data.map(Product.fromJson);
  }

  async createProduct(product: Product): Promise<Product> {
    const data = await this.httpClient.post<Product>("/products", product.toJson());
    return Product.fromJson(data);
  }

  async updateProduct(id: number, product: Product): Promise<void> {
    const data = await this.httpClient.put<Product>(`/products?id=${id}`, product.toJson());
  }

  async deleteProduct(id: number): Promise<void> {
    await this.httpClient.delete(`/products?id=${id}`);
  }
}
