import { Route, Routes } from "react-router";
import { MainLayout } from "./layout/MainLayout";
import { ProductManage } from "./components/ProductManage";
import { UserManage } from "./components/UserManage";
import { AccessLogManage } from "./components/AccessLogManage";
import { useEffect, useState } from "react";
import { ServiceContext, ServiceContextType } from "./context/ServiceContext";
import { ProductServiceStub } from "./services/stub/ProductServiceStub";

function App() {
  const [services, setServices] = useState<ServiceContextType | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const productService = new ProductServiceStub();

    setServices({
      productService,
    });
  }, []);

  if (error) return <p>Error during service initialization: {error}</p>;
  if (!services) return <p>Loading services...</p>;

  return (
    <ServiceContext.Provider value={services}>
      <div className="max-w-screen-2xl text=center mx-auto">
        <Routes>
          <Route path="manage" element={<MainLayout />}>
            <Route path="products" element={<ProductManage />} />
            <Route path="users" element={<UserManage />} />
            <Route path="accesslog" element={<AccessLogManage />} />s
          </Route>
        </Routes>
      </div>
    </ServiceContext.Provider>
  );
}

export default App;
