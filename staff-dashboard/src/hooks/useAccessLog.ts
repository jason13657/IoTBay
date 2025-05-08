import { use, useCallback, useEffect, useState } from "react";
import { AccessLog } from "../models/AccessLog";
import { useServices } from "../context/ServiceContext";

export function useAccessLogs() {
  const { accessLogService } = useServices();
  const [accessLogs, setAccessLogs] = useState<AccessLog[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [searchKey, setSearchKey] = useState<string>("");

  useEffect(() => {
    fetchAccessLogs();
  }, [accessLogService, searchKey]);

  const fetchAccessLogs = () => {
    setLoading(true);
    setError(null);
    if (searchKey != "") {
      fetchAccessLogsByUserId(searchKey);
    } else {
      fetchAllAccessLogs();
    }
  };

  const fetchAllAccessLogs = useCallback(() => {
    setLoading(true);
    setError(null);
    accessLogService
      .getLogs()
      .then((data) => {
        setAccessLogs(data);
        setLoading(false);
      })
      .catch((err) => {
        setLoading(false);
        setError("Failed to fetch access logs" + err.message);
      });
  }, [accessLogService]);

  const fetchAccessLogsByUserId = useCallback(
    (userId: string) => {
      const userIdNumber = parseInt(userId);
      if (isNaN(userIdNumber)) {
        setError("Invalid user ID");
        setLoading(false);
        return;
      }
      setLoading(true);
      setError(null);
      accessLogService
        .getLogsByUserId(userIdNumber)
        .then((data) => {
          setAccessLogs(data);
          setLoading(false);
        })
        .catch((err) => {
          setLoading(false);
          setError("Failed to fetch access logs" + err.message);
        });
    },
    [accessLogService]
  );

  return {
    accessLogs,
    loading,
    error,
    setSearchKey,
  };
}
