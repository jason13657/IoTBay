import { use, useCallback, useEffect, useState } from "react";
import { useServices } from "../context/ServiceContext";
import { Auth } from "../models/Auth";

export function useAuth() {
  const { authService } = useServices();
  const [auth, setAuth] = useState<Auth | null>();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [isStaff, setIsStaff] = useState(false);

  useEffect(() => {
    fetchAuth();
  }, [authService]);

  const fetchAuth = useCallback(() => {
    setLoading(true);
    setError(null);
    authService
      .whoAmI()
      .then((data) => {
        setAuth(data);
        setLoading(false);
        setIsStaff(data.role === "staff");
      })
      .catch((err) => {
        setAuth(null);
        setLoading(false);
        setError("Failed to fetch auth" + err.message);
      });
  }, [authService]);

  return { auth, loading, error, isStaff, fetchAuth };
}
