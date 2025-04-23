import { Link } from "react-router";

const NAVITEM_CLASS = "text-xl mb-2 text-blue-500 hover:underline transition duration-300 ease-in-out";

export function Navbar() {
  return (
    <nav className="flex gap-10 py-4">
      <Link to="/manage/products" className={NAVITEM_CLASS}>
        Products
      </Link>
      <Link to="/manage/users" className={NAVITEM_CLASS}>
        Users
      </Link>
      <Link to="/manage/accesslog" className={NAVITEM_CLASS}>
        AccessLog
      </Link>
    </nav>
  );
}
