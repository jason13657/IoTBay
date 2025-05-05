import { Outlet } from "react-router";
import { Link } from "react-router";
import { Navbar } from "../components/Navbar";

export function MainLayout() {
  return (
    <div className="flex flex-col min-h-screen">
      <div className="bg-gray-200 p-4">
        <h1 className="text-3xl font-bold my-4">IOT Bay Staff Dashboard</h1>
        <Navbar />
      </div>
      <Outlet />
    </div>
  );
}
