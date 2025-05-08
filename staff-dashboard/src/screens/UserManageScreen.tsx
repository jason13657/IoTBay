import { useState } from "react";
import { useUsers } from "../hooks/useUsers";
import { SearchBar } from "../components/SearchBar";
import { UserList, UserTableHeader } from "../components/UserList";
import { Modal } from "../components/utils/Modal";
import { ConfirmAlert } from "../components/utils/ConfirmAlert";
import { User } from "../models/User";
import { UserForm } from "../components/UserForm";

export function UserManageScreen() {
  const { users, error, setSearchKey, deleteUser, updateUser } = useUsers();
  const [editUser, setEditUser] = useState<User | null>(null);
  const [deleteUserId, setDeleteUserId] = useState<number | null>(null);

  const handleSearch = (searchKey: string) => {
    setSearchKey(searchKey);
  };

  const handleEdit = (user: User) => {
    setEditUser(user);
  };

  const handleDelete = (id: number) => {
    setDeleteUserId(id);
  };

  const confirmDelete = () => {
    if (deleteUserId) {
      deleteUser(deleteUserId);
    }
    setDeleteUserId(null);
  };

  const handleUpdate = (user: User) => {
    updateUser(user.id, user);
    setEditUser(null);
  };

  return (
    <div className="fkex flex-col">
      <div className="flex justify-between py-1 px-4 items-center">
        <div className="border w-1/2">
          <SearchBar onSearch={handleSearch} />
        </div>
      </div>
      {error ? (
        <p>Error loading products: {error}</p>
      ) : (
        <ul className="flex flex-col gap-2 p-4">
          <UserTableHeader />
          {users.map((user) => (
            <UserList key={user.id} user={user} handleEdit={handleEdit} handleDelete={handleDelete} />
          ))}
        </ul>
      )}
      {editUser && (
        <Modal
          onClose={() => setEditUser(null)}
          children={<UserForm initialUser={editUser} onSubmit={handleUpdate} />}
        />
      )}
      {deleteUserId && (
        <Modal
          onClose={() => setDeleteUserId(null)}
          children={
            <ConfirmAlert
              title="Delete user"
              message="Are you sure? Do you want delete this user?"
              onCancel={() => setDeleteUserId(null)}
              onConfirm={confirmDelete}
            />
          }
        />
      )}
    </div>
  );
}
