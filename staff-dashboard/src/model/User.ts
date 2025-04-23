export enum UserRole {
  STAFF = "staff",
  CUSTOMER = "customer",
}

export class User {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  password: string;
  gender: string;
  favoriteColor: string;
  dateOfBirth: Date;
  createdAt: Date;
  updatedAt: Date;
  role: UserRole;
  isActive: boolean;

  constructor(
    id: number,
    email: string,
    firstName: string,
    lastName: string,
    password: string,
    gender: string,
    favoriteColor: string,
    dateOfBirth: Date,
    createdAt: Date,
    updatedAt: Date,
    role: UserRole,
    isActive: boolean
  ) {
    this.id = id;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.gender = gender;
    this.favoriteColor = favoriteColor;
    this.dateOfBirth = dateOfBirth;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.role = role;
    this.isActive = isActive;
  }

  static fromJson(json: any): User {
    return new User(
      json.id,
      json.email,
      json.firstName,
      json.lastName,
      json.password,
      json.gender,
      json.favoriteColor,
      new Date(json.dateOfBirth),
      new Date(json.createdAt),
      new Date(json.updatedAt),
      User.parseRole(json.role),
      json.isActive !== undefined ? json.isActive : true
    );
  }

  toJson(): any {
    return {
      id: this.id,
      email: this.email,
      firstName: this.firstName,
      lastName: this.lastName,
      password: this.password,
      gender: this.gender,
      favoriteColor: this.favoriteColor,
      dateOfBirth: this.dateOfBirth.toISOString().split("T")[0],
      createdAt: this.createdAt.toISOString(),
      updatedAt: this.updatedAt.toISOString(),
      role: this.role,
      isActive: this.isActive,
    };
  }

  private static parseRole(roleStr: string): UserRole {
    switch (roleStr) {
      case UserRole.STAFF:
        return UserRole.STAFF;
      case UserRole.CUSTOMER:
        return UserRole.CUSTOMER;
      default:
        throw new Error(`Invalid role value: ${roleStr}`);
    }
  }
}
