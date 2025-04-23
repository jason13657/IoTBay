import { User, UserRole } from "../../model/User";
import { IUserService } from "../UserService";

export class UserServiceStub implements IUserService {
  private users: User[] = [
    new User(
      1,
      "admin@example.com",
      "Admin",
      "User",
      "admin123",
      "Male",
      "Blue",
      new Date("1990-01-01"),
      new Date("2025-01-01T10:00:00"),
      new Date("2025-01-01T10:00:00"),
      UserRole.CUSTOMER,
      true
    ),
    new User(
      2,
      "staff@example.com",
      "Staff",
      "Member",
      "staff123",
      "Female",
      "Red",
      new Date("1995-05-15"),
      new Date("2025-02-01T11:00:00"),
      new Date("2025-02-01T11:00:00"),
      UserRole.STAFF,
      true
    ),
  ];

  async getUsers(): Promise<User[]> {
    return Promise.resolve(this.users);
  }

  async getUserById(id: string): Promise<User> {
    const user = this.users.find((u) => u.id === parseInt(id));
    if (!user) {
      throw new Error(`User with ID ${id} not found.`);
    }
    return Promise.resolve(user);
  }

  async createUser(user: User): Promise<User> {
    const newId = this.users.length + 1;
    const newUser = new User(
      newId,
      user.email,
      user.firstName,
      user.lastName,
      user.password,
      user.gender,
      user.favoriteColor,
      user.dateOfBirth,
      user.createdAt,
      user.updatedAt,
      user.role,
      user.isActive
    );
    this.users.push(newUser);
    return Promise.resolve(newUser);
  }

  async updateUser(id: string, user: User): Promise<User> {
    const index = this.users.findIndex((u) => u.id === parseInt(id));
    if (index === -1) {
      throw new Error(`User with ID ${id} not found.`);
    }
    const updatedUser = new User(
      this.users[index].id,
      user.email,
      user.firstName,
      user.lastName,
      user.password,
      user.gender,
      user.favoriteColor,
      user.dateOfBirth,
      user.createdAt,
      user.updatedAt,
      user.role,
      user.isActive
    );
    this.users[index] = updatedUser;
    return Promise.resolve(updatedUser);
  }

  deleteUser(id: string): void {
    const index = this.users.findIndex((u) => u.id === parseInt(id));
    if (index === -1) {
      throw new Error(`User with ID ${id} not found.`);
    }
    this.users.splice(index, 1);
  }
}
