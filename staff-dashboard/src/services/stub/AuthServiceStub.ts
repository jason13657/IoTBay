import { Auth } from "../../models/Auth";
import { IAuthService } from "../AuthService";

export class AuthServiceStub implements IAuthService {
  private auth: Auth = {
    id: 1,
    email: "staff@example.com",
    firstName: "John",
    lastName: "Doe",
    role: "staff",
  };

  async whoAmI(): Promise<Auth> {
    return this.auth;
  }
}
