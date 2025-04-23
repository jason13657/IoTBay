import { useState } from "react";

type Props = {
  onSearch: (query: string) => void;
};

export function SearchBar({ onSearch }: Props) {
  const [query, setQuery] = useState("");

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setQuery(e.target.value);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSearch(query);
  };

  return (
    <form onSubmit={handleSubmit} className="flex">
      <input
        type="text"
        placeholder="Search..."
        value={query}
        onChange={handleInputChange}
        style={{ padding: "0.5rem", flex: 1 }}
      />
      <button type="submit" className="p-2">
        Search
      </button>
    </form>
  );
}
