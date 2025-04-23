import { createContext, useContext } from "react";
import { IProductService } from "../services/ProductService";

export type ServiceContextType = {
  productService: IProductService;
};

export const ServiceContext = createContext<ServiceContextType | null>(null);

export const useServices = (): ServiceContextType => {
  const context = useContext(ServiceContext);
  if (!context) {
    throw new Error("useServices must be used within a ServiceProvider");
  }
  return context;
};
