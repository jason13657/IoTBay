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
    return this.products;
  }

  async getProductById(id: number): Promise<Product> {
    const product = this.products.find((p) => p.id === id);
    if (!product) {
      throw new Error(`Product with ID ${id} not found.`);
    }
    return product;
  }

  async getProductsByName(name: string): Promise<Product[]> {
    const filteredProducts = this.products.filter((p) => p.name.toLowerCase().includes(name.toLowerCase()));
    if (filteredProducts.length === 0) {
      throw new Error(`No products found with name ${name}.`);
    }
    return filteredProducts;
  }

  async createProduct(product: Product): Promise<Product> {
    const newId = (this.products.length > 0 ? this.products[this.products.length - 1].id : 0) + 1;
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

  async updateProduct(id: number, product: Product): Promise<Product> {
    const index = this.products.findIndex((p) => p.id === id);
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

  async deleteProduct(id: number): Promise<void> {
    const index = this.products.findIndex((p) => p.id === id);
    if (index === -1) {
      throw new Error(`Product with ID ${id} not found.`);
    }
    const deletedProduct = this.products[index];
    this.products.splice(index, 1);
  }
}
