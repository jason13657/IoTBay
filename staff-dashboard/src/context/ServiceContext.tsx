import { createContext, useContext } from "react";
import { IProductService } from "../services/ProductService";
import { IUserService } from "../services/UserService";
import { IAccessLogService } from "../services/AccessLogService";
import { IAuthService } from "../services/AuthService";

export type ServiceContextType = {
  productService: IProductService;
  userService: IUserService;
  accessLogService: IAccessLogService;
  authService: IAuthService;
};

export const ServiceContext = createContext<ServiceContextType | null>(null);

export const useServices = (): ServiceContextType => {
  const context = useContext(ServiceContext);
  if (!context) {
    throw new Error("useServices must be used within a ServiceProvider");
  }
  return context;
};
