import { ReactNode } from "react";
import { useAuth } from "../hooks/useAuth";

type Props = {
  children: ReactNode;
};

export function WithStaffUser({ children }: Props) {
  const { auth, isStaff } = useAuth();

  if (!auth) {
    return <p>Please log in to access this page.</p>;
  }

  if (!isStaff) {
    return <p>403 Forbidden - Access denied: Staff only area.</p>;
  }

  return <>{children}</>;
}
