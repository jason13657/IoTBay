import { useState } from "react";
import { User, UserRole } from "../models/User";

type Props = {
  initialUser?: User;
  onSubmit: (user: User) => void;
};

export function UserForm({ initialUser, onSubmit }: Props) {
  const [form, setForm] = useState<User>(
    initialUser || new User(0, "", "", "", "", "", "", new Date(), new Date(), new Date(), UserRole.CUSTOMER, true)
  );

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value, type } = e.target;
    const isCheckbox = type === "checkbox";
    const checked = isCheckbox ? (e.target as HTMLInputElement).checked : undefined;

    setForm(
      (prev) =>
        new User(
          prev.id,
          name === "email" ? value : prev.email,
          name === "firstName" ? value : prev.firstName,
          name === "lastName" ? value : prev.lastName,
          name === "password" ? value : prev.password,
          name === "gender" ? value : prev.gender,
          name === "favoriteColor" ? value : prev.favoriteColor,
          name === "dateOfBirth" ? new Date(value) : prev.dateOfBirth,
          prev.createdAt,
          new Date(),
          name === "role" ? (value as UserRole) : prev.role,
          name === "isActive" ? !!checked : prev.isActive
        )
    );
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(form);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4 p-4 border rounded shadow-md w-7/12 mx-auto bg-white">
      <div>
        <label className="block text-sm font-medium">Email</label>
        <input
          type="email"
          name="email"
          value={form.email}
          onChange={handleChange}
          className="w-full border rounded px-3 py-2"
          required
        />
      </div>

      <div>
        <label className="block text-sm font-medium">First Name</label>
        <input
          type="text"
          name="firstName"
          value={form.firstName}
          onChange={handleChange}
          className="w-full border rounded px-3 py-2"
          required
        />
      </div>

      <div>
        <label className="block text-sm font-medium">Last Name</label>
        <input
          type="text"
          name="lastName"
          value={form.lastName}
          onChange={handleChange}
          className="w-full border rounded px-3 py-2"
          required
        />
      </div>

      <div>
        <label className="block text-sm font-medium">Password</label>
        <input
          type="password"
          name="password"
          value={form.password}
          onChange={handleChange}
          className="w-full border rounded px-3 py-2"
          required
        />
      </div>

      <div>
        <label className="block text-sm font-medium">Gender</label>
        <input
          type="text"
          name="gender"
          value={form.gender}
          onChange={handleChange}
          className="w-full border rounded px-3 py-2"
        />
      </div>

      <div>
        <label className="block text-sm font-medium">Favorite Color</label>
        <input
          type="text"
          name="favoriteColor"
          value={form.favoriteColor}
          onChange={handleChange}
          className="w-full border rounded px-3 py-2"
        />
      </div>

      <div>
        <label className="block text-sm font-medium">Date of Birth</label>
        <input
          type="date"
          name="dateOfBirth"
          value={form.dateOfBirth.toISOString().split("T")[0]}
          onChange={handleChange}
          className="w-full border rounded px-3 py-2"
          required
        />
      </div>

      <div>
        <label className="block text-sm font-medium">Role</label>
        <select
          name="role"
          value={form.role}
          onChange={handleChange}
          className="w-full border rounded px-3 py-2"
          required
        >
          <option value={UserRole.STAFF}>Staff</option>
          <option value={UserRole.CUSTOMER}>Customer</option>
        </select>
      </div>

      <div>
        <label className="block text-sm font-medium">Is Active</label>
        <input type="checkbox" name="isActive" checked={form.isActive} onChange={handleChange} className="ml-2" />
      </div>

      <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
        Submit
      </button>
    </form>
  );
}
