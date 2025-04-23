import { Product } from "../../models/Product";
import { IProductService } from "../ProductService";

export class ProductServiceStub implements IProductService {
  private products: Product[] = [
    new Product(
      1,
      101,
      "Product A",
      "Description A",
      99.99,
      50,
      "https://example.com/imgA.jpg",
      new Date("2025-01-01")
    ),
    new Product(
      2,
      102,
      "Product B",
      "Description B",
      199.99,
      20,
      "https://example.com/imgB.jpg",
      new Date("2025-02-01")
    ),
  ];

  async getProducts(): Promise<Product[]> {
    return Promise.resolve(this.products);
  }

  async getProductById(id: string): Promise<Product> {
    const product = this.products.find((p) => p.id === parseInt(id));
    if (!product) {
      throw new Error(`Product with ID ${id} not found.`);
    }
    return product;
  }

  async createProduct(product: Product): Promise<Product> {
    const newId = this.products.length + 1;
    const newProduct = new Product(
      newId,
      product.categoryId,
      product.name,
      product.description,
      product.price,
      product.stockQuantity,
      product.imageUrl,
      product.createdAt
    );
    this.products.push(newProduct);
    return newProduct;
  }

  async updateProduct(id: string, product: Product): Promise<Product> {
    const index = this.products.findIndex((p) => p.id === parseInt(id));
    if (index === -1) {
      throw new Error(`Product with ID ${id} not found.`);
    }
    const updatedProduct = new Product(
      this.products[index].id,
      product.categoryId,
      product.name,
      product.description,
      product.price,
      product.stockQuantity,
      product.imageUrl,
      product.createdAt
    );
    this.products[index] = updatedProduct;
    return updatedProduct;
  }

  async deleteProduct(id: string): Promise<void> {
    const index = this.products.findIndex((p) => p.id === parseInt(id));
    if (index === -1) {
      throw new Error(`Product with ID ${id} not found.`);
    }
    const deletedProduct = this.products[index];
    this.products.splice(index, 1);
  }
}
