import { useCallback, useEffect, useState } from "react";
import { useServices } from "../context/ServiceContext";
import { User } from "../models/User";

export function useUsers() {
  const { userService } = useServices();
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [searchKey, setSearchKey] = useState("");

  useEffect(() => {
    fetchUsers();
  }, [userService, searchKey]);

  const fetchUsers = useCallback(() => {
    setLoading(true);
    setError(null);
    if (searchKey != "") {
      fetchUsersByEmail(searchKey);
    } else {
      fetchAllUsers();
    }
  }, [userService, searchKey]);

  const fetchAllUsers = useCallback(() => {
    setLoading(true);
    setError(null);
    userService
      .getUsers()
      .then((data) => {
        setUsers(data);
        setLoading(false);
      })
      .catch((err) => {
        setLoading(false);
        setError("Failed to fetch users" + err.message);
      });
  }, [userService]);

  const fetchUsersByEmail = useCallback(
    (email: string) => {
      setLoading(true);
      setError(null);
      userService
        .getUsersByEmail(email)
        .then((data) => {
          setUsers(data);
          setLoading(false);
        })
        .catch((err) => {
          setLoading(false);
          setError("Failed to fetch users" + err.message);
        });
    },
    [userService, searchKey]
  );

  const deleteUser = useCallback(
    (id: number) => {
      setLoading(true);
      setError(null);
      userService
        .deleteUser(id)
        .then(() => {
          fetchUsers();
          setLoading(false);
        })
        .catch((err) => {
          setLoading(false);
          setError("Failed to delete user" + err.message);
        });
    },
    [userService]
  );

  const updateUser = useCallback(
    (id: number, user: User) => {
      setLoading(true);
      setError(null);
      userService
        .updateUser(id, user)
        .then((updatedUser) => {
          setLoading(false);
          fetchUsers();
        })
        .catch((err) => {
          setLoading(false);
          setError("Failed to update user" + err.message);
        });
    },
    [userService]
  );

  return {
    users,
    loading,
    error,
    deleteUser,
    updateUser,
    setSearchKey,
  };
}
