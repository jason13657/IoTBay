import { User } from "../models/User";

export interface IUserService {
  getUsers(): Promise<User[]>;
  getUserById(id: string): Promise<User>;
  createUser(user: User): Promise<User>;
  updateUser(id: string, user: User): Promise<User>;
  deleteUser(id: string): void;
}
