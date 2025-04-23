export class Product {
  id: number;
  categoryId: number;
  name: string;
  description: string;
  price: number;
  stockQuantity: number;
  imageUrl: string;
  createdAt: Date;

  constructor(
    id: number,
    categoryId: number,
    name: string,
    description: string,
    price: number,
    stockQuantity: number,
    imageUrl: string,
    createdAt: Date
  ) {
    this.id = id;
    this.categoryId = categoryId;
    this.name = name;
    this.description = description;
    this.price = price;
    this.stockQuantity = stockQuantity;
    this.imageUrl = imageUrl;
    this.createdAt = createdAt;
  }

  static fromJson(json: any): Product {
    return new Product(
      json.id,
      json.categoryId,
      json.name,
      json.description,
      json.price,
      json.stockQuantity,
      json.imageUrl,
      new Date(json.createdAt)
    );
  }

  toJson(): Record<string, any> {
    return {
      id: this.id,
      categoryId: this.categoryId,
      name: this.name,
      description: this.description,
      price: this.price,
      stockQuantity: this.stockQuantity,
      imageUrl: this.imageUrl,
      createdAt: this.createdAt.toISOString(),
    };
  }
}
