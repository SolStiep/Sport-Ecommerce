import React, { useState } from "react";
import { Table, Input, Button } from "antd";
import { ColumnsType } from "antd/es/table";

const { Search } = Input;

interface GenericTableProps<T> {
  data: T[];
  columns: ColumnsType<T>;
  rowKey: keyof T | ((record: T) => string);
  loading?: boolean;
}

export function GenericTable<T extends object>({
  data,
  columns,
  rowKey,
  loading = false,
}: GenericTableProps<T>) {
  const [searchText, setSearchText] = useState("");

  const handleSearch = (value: string) => {
    setSearchText(value.toLowerCase());
  };

  const deepSearch = (obj: any): string => {
    if (obj === null || obj === undefined) {
      return "";
    } else if (typeof obj === "string" || typeof obj === "number") {
      return String(obj).toLowerCase();
    } else if (typeof obj === "object") {
      return Object.values(obj).map(deepSearch).join(" ");
    }
    return "";
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setSearchText(value.toLowerCase());
  };

  const filteredData = data.filter((item) =>
    deepSearch(item).includes(searchText)
  );

  return (
    <div>
      <Search
        placeholder="Buscar..."
        onSearch={handleSearch}
        size="small"
        style={{ marginBottom: 8, width: 200 }}
        allowClear
      />
      <Table
        dataSource={filteredData}
        columns={columns}
        rowKey={rowKey}
        loading={loading}
        pagination={{ pageSize: 10 }}
        size="small"
      />
    </div>
  );
}
