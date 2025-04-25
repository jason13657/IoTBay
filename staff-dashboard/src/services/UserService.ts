import { User } from "../models/User";
import { HttpClient } from "../network/HttpClient";

export interface IUserService {
  getUsers(): Promise<User[]>;
  getUserById(id: number): Promise<User>;
  getUsersByEmail(id: string): Promise<User[]>;
  createUser(user: User): Promise<User>;
  updateUser(id: number, user: User): Promise<void>;
  deleteUser(id: number): Promise<void>;
}

export class UserService implements IUserService {
  private httpClient: HttpClient;

  constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  async getUsers(): Promise<User[]> {
    const data = await this.httpClient.get<User[]>("/users");
    return data.map(User.fromJson);
  }

  async getUserById(id: number): Promise<User> {
    const data = await this.httpClient.get<User>(`/users?id=${id}`);
    return User.fromJson(data);
  }

  async getUsersByEmail(email: string): Promise<User[]> {
    const data = await this.httpClient.get<User[]>(`/users?email=${email}`);
    return data.map(User.fromJson);
  }
  async createUser(user: User): Promise<User> {
    const data = await this.httpClient.post<User>("/users", user.toJson());
    return User.fromJson(data);
  }
  async updateUser(id: number, user: User): Promise<void> {
    const data = await this.httpClient.put<User>(`/users?id=${id}`, user.toJson());
  }
  async deleteUser(id: number): Promise<void> {
    await this.httpClient.delete(`/users?id=${id}`);
  }
}
