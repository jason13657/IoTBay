import { Route, Routes } from "react-router";
import { MainLayout } from "./layout/MainLayout";
import { ProductManage } from "./components/ProductManage";
import { UserManage } from "./components/UserManage";
import { AccessLogManage } from "./components/AccessLogManage";

function App() {
  return (
    <div className="max-w-screen-2xl text=center mx-auto">
      <Routes>
        <Route path="manage" element={<MainLayout />}>
          <Route path="products" element={<ProductManage />} />
          <Route path="users" element={<UserManage />} />
          <Route path="accesslog" element={<AccessLogManage />} />s
        </Route>
      </Routes>
    </div>
  );
}

export default App;
