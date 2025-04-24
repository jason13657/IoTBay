import { useAccessLogs } from "../hooks/useAccessLog";
import { AccessLogList, AccessLogTableHeader } from "./AccessLogList";
import { SearchBar } from "./SearchBar";

export function AccessLogManage() {
  const { accessLogs, loading, error, setSearchKey } = useAccessLogs();

  const handleSearch = (searchKey: string) => {
    setSearchKey(searchKey);
  };

  if (loading) return <p>Loading logs...</p>;
  if (accessLogs.length === 0) return <p>No logs available.</p>;

  return (
    <div className="flex flex-col">
      <div className="flex justify-between py-1 px-4 items-center">
        <div className="border w-1/2">
          <SearchBar onSearch={handleSearch} />
        </div>
      </div>
      {error ? (
        <p>Error loading access log: {error}</p>
      ) : (
        <ul className="flex flex-col gap-2 p-4">
          <AccessLogTableHeader />
          {accessLogs.map((accessLog) => (
            <AccessLogList key={accessLog.id} accessLog={accessLog} />
          ))}
        </ul>
      )}
    </div>
  );
}
