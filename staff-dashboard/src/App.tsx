import { Route, Routes } from "react-router";
import { MainLayout } from "./layout/MainLayout";
import { useEffect, useState } from "react";
import { ServiceContext, ServiceContextType } from "./context/ServiceContext";
import { ProductManageScreen } from "./screens/ProductManageScreen";
import { UserManageScreen } from "./screens/UserManageScreen";
import { AccessLogManageScreen } from "./screens/AccessLogManageScreen";
import { AuthServiceStub } from "./services/stub/AuthServiceStub";
import { WithStaffUser } from "./components/WithStaffUser";
import { HttpClient } from "./network/HttpClient";
import { ProductService } from "./services/ProductService";
import { UserService } from "./services/UserService";
import { AccessLogService } from "./services/AccessLogService";

function App() {
  const [services, setServices] = useState<ServiceContextType | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const httpClient = new HttpClient("/api/manage");

    const productService = new ProductService(httpClient);
    const userService = new UserService(httpClient);
    const accessLogService = new AccessLogService(httpClient);
    const authService = new AuthServiceStub();

    setServices({
      productService,
      userService,
      accessLogService,
      authService,
    });
  }, []);

  if (error) return <p>Error during service initialization: {error}</p>;
  if (!services) return <p>Loading services...</p>;

  return (
    <ServiceContext.Provider value={services}>
      <WithStaffUser>
        <div className="max-w-screen-2xl text=center mx-auto">
          <Routes>
            <Route path="manage" element={<MainLayout />}>
              <Route path="products" element={<ProductManageScreen />} />
              <Route path="users" element={<UserManageScreen />} />
              <Route path="accesslog" element={<AccessLogManageScreen />} />s
            </Route>
          </Routes>
        </div>
      </WithStaffUser>
    </ServiceContext.Provider>
  );
}

export default App;
