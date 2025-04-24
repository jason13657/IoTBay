import { User } from "../models/User";

export interface IUserService {
  getUsers(): Promise<User[]>;
  getUserById(id: number): Promise<User>;
  getUsersByEmail(id: string): Promise<User[]>;
  createUser(user: User): Promise<User>;
  updateUser(id: number, user: User): Promise<User>;
  deleteUser(id: number): Promise<void>;
}
