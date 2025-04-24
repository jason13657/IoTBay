import { AccessLog } from "../models/AccessLog";

type Props = { accessLog: AccessLog };

const LIST_ITEM_CLASS = "lex flex md:flex-row border-b py-2 text-center items-center";

const HEADERS = [
  { label: "ID", key: "id", className: "w-16 px-2" },
  { label: "User ID", key: "userId", className: "w-48 px-2 text-center" },
  { label: "Action", key: "action", className: "flex-1 px-2 overflow-x-auto whitespace-nowrap" },
  { label: "Timestamp", key: "timestamp", className: "w-60 px-2 text-center" },
];

export function AccessLogTableHeader() {
  return (
    <li className={LIST_ITEM_CLASS + " font-bold"}>
      {HEADERS.map((header) => (
        <div key={header.label} className={header.className}>
          {header.label}
        </div>
      ))}
    </li>
  );
}
export function AccessLogList({ accessLog }: Props) {
  return (
    <li className={LIST_ITEM_CLASS}>
      {HEADERS.map((header) => {
        const value = (accessLog as any)[header.key];
        let displayValue = value;

        if (header.key === "timestamp" && value instanceof Date) {
          displayValue = value.toISOString();
        }
        return (
          <div key={header.key} className={header.className}>
            {displayValue}
          </div>
        );
      })}
    </li>
  );
}
