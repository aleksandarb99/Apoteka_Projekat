import StockItem from "./StockItem";
import api from "../../../app/api";
import { getIdFromToken } from "../../../app/jwtTokenUtils";
import React, { useEffect, useState } from "react";
import { Button, ListGroup } from "react-bootstrap";
import AddEditStockItemModal from "../stock/AddEditStockItemModal";

const SupplierStock = () => {
  const [stock, setStock] = useState([]);
  const [reload, setReload] = useState(false);
  const [showAddEditModal, setShowAddEditModal] = useState(false);

  useEffect(() => {
    async function fetchStock() {
      const response = await api.get(
        `/api/suppliers/stock/${getIdFromToken()}`
      ).catch(() => { });
      setStock(!!response ? response.data : []);
    }
    fetchStock();
  }, [reload]);

  const reloadTable = () => {
    setReload(!reload);
  };

  return (
    <ListGroup style={{ margin: "20px" }}>
      {stock &&
        stock.map((stockItem) => {
          return (
            <ListGroup.Item
              style={{ margin: "10px" }}
              key={stockItem.medicineId}
            >
              {" "}
              {`${stockItem.medicineName}  --  ${stockItem.amount}`}
            </ListGroup.Item>
          );
        })}
      <Button onClick={() => setShowAddEditModal(true)}>Add</Button>
      <AddEditStockItemModal
        show={showAddEditModal}
        onHide={() => setShowAddEditModal(false)}
        onSuccess={reloadTable}
      />
    </ListGroup>
  );
};

export default SupplierStock;
