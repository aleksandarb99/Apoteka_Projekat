import React, { useEffect, useState } from "react";
import { Button } from "react-bootstrap";
import api from "../../../app/api";
import { getIdFromToken } from "../../../app/jwtTokenUtils";
import OrderItem from "./OrderItem";
import AddEditOfferModal from "../offers/AddEditOfferModal";

const NewOrders = () => {
  const [orders, setOrders] = useState([]);
  const [reload, setReload] = useState(false);
  const [message, setMessage] = useState("");

  useEffect(() => {
    async function fetchOrders() {
      const response = await api.get(
        `/api/orders/without-offers/${getIdFromToken()}`
      ).catch(() => { });
      setOrders(!!response ? response.data : []);
    }
    fetchOrders();
  }, [reload]);

  const reloadTable = () => {
    setReload(!reload);
  };

  useEffect(() => {
    setMessage(
      Array.isArray(orders) && orders.length ? "" : "No orders to show"
    );
  }, [orders]);

  return (
    <div>
      {orders &&
        orders.map((o) => {
          return (
            <OrderItem
              key={o.id}
              order={o}
              onSuccess={() => reloadTable()}
            ></OrderItem>
          );
        })}
      <p>{message}</p>
    </div>
  );
};

export default NewOrders;
