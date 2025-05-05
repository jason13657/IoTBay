import { Auth } from "../models/Auth";

export interface IAuthService {
  whoAmI(): Promise<Auth>;
}
