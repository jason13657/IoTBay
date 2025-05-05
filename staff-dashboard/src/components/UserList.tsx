import { User } from "../models/User";

type Props = { user: User; handleEdit: (user: User) => void; handleDelete: (id: number) => void };

const LIST_ITEM_CLASS = "lex flex md:flex-row border-b py-2 text-center items-center";

const ACTION_BUTTON_GAP = "w-32";

const HEADERS = [
  { label: "ID", key: "id", className: "w-16 px-2" },
  { label: "Email", key: "email", className: "w-48 px-2 text-center" },
  { label: "First Name", key: "firstName", className: "flex-1 px-2" },
  { label: "Last Name", key: "lastName", className: "flex-1 px-2 overflow-x-auto whitespace-nowrap" },
  { label: "Gender", key: "gender", className: "w-24 px-2" },
  { label: "Created At", key: "createdAt", className: "w-48 px-2 text-center" },
  { label: "Updated At", key: "updatedAt", className: "w-48 px-2 text-center" },
  { label: "Role", key: "role", className: "w-32 px-2 text-center" },
  { label: "Is Active", key: "isActive", className: "w-48 px-2 text-center" },
];

export function UserTableHeader() {
  return (
    <li className={LIST_ITEM_CLASS + " font-bold"}>
      {HEADERS.map((header) => (
        <div key={header.label} className={header.className}>
          {header.label}
        </div>
      ))}
      <div className={ACTION_BUTTON_GAP} />
    </li>
  );
}

export function UserList({ user, handleEdit, handleDelete }: Props) {
  return (
    <li className={LIST_ITEM_CLASS}>
      {HEADERS.map((header) => {
        const value = (user as any)[header.key];
        let displayValue = value;

        if (header.key === "createdAt" && value instanceof Date) {
          displayValue = value.toISOString().split("T")[0];
        } else if (header.key === "updatedAt" && value instanceof Date) {
          displayValue = value.toISOString().split("T")[0];
        } else if (header.key === "isActive") {
          displayValue = value ? "Yes" : "No";
        } else if (header.key === "role") {
          displayValue = value.charAt(0).toUpperCase() + value.slice(1);
        }
        return (
          <div key={header.key} className={header.className}>
            {displayValue}
          </div>
        );
      })}
      <div className={ACTION_BUTTON_GAP + " flex gap-2"}>
        <button
          className="border p-1"
          onClick={() => {
            handleEdit(user);
          }}
        >
          Edit
        </button>
        <button
          className="border p-1"
          onClick={() => {
            handleDelete(user.id);
          }}
        >
          Delete
        </button>
      </div>
    </li>
  );
}
