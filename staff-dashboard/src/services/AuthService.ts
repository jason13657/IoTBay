import { Auth } from "../models/Auth";
import { HttpClient } from "../network/HttpClient";

export interface IAuthService {
  whoAmI(): Promise<Auth>;
}

export class AuthService implements IAuthService {
  private httpClient: HttpClient;

  constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
  }

  async whoAmI(): Promise<Auth> {
    const data = await this.httpClient.get<Auth>("/me");
    return data;
  }
}
