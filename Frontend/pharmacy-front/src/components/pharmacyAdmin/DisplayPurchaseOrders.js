import React, { useState, useEffect } from "react";

import { Pagination, Table, Tab, Row, Col, Card } from "react-bootstrap";

import Dropdown from "react-bootstrap/Dropdown";

import axios from "axios";

import moment from "moment";

function DisplayPurchaseOrders({ idOfPharmacy }) {
  const [orders, setOrders] = useState([]);
  const [filterValue, setFilterValue] = useState("All");
  const [showedOrders, setShowedOrders] = useState([]);
  const [showedOrder, setShowedOrder] = useState(null);
  const [pagNumber, setPugNummber] = useState(0);
  const [maxPag, setMaxPag] = useState(0);
  const [dropdownLabel, setDropdownLabel] = useState("All");

  useEffect(() => {
    if (idOfPharmacy != undefined) {
      async function fetchOrders() {
        const request = await axios.get(
          `http://localhost:8080/api/orders/bypharmacyid/${idOfPharmacy}`,
          { params: { filter: filterValue } }
        );
        setOrders(request.data);
        return request;
      }
      fetchOrders();
    }
  }, [idOfPharmacy, filterValue]);

  let filterOrders = (param) => {
    setShowedOrder(null);
    setPugNummber(0);
    setFilterValue(param);
  };

  useEffect(() => {
    let maxNumber = Math.floor(orders?.length / 4) - 1;
    if (orders?.length / 4 - 1 > maxNumber) {
      maxNumber = maxNumber + 1;
    }
    setMaxPag(maxNumber);
  }, [orders]);

  useEffect(() => {
    let first = pagNumber * 4;
    let max = orders.length < first + 4 ? orders?.length : first + 4;
    setShowedOrders(orders?.slice(first, max));
  }, [orders, pagNumber]);

  let handleSlideLeft = () => {
    if (pagNumber !== 0) {
      setPugNummber(pagNumber - 1);
    }
  };

  let handleSlideRight = () => {
    if (pagNumber !== maxPag) {
      setPugNummber(pagNumber + 1);
    }
  };

  return (
    <Tab.Pane eventKey="eight">
      <h1 className="content-header">Purchase orders</h1>
      <hr></hr>
      <Row>
        {" "}
        <Dropdown>
          <Dropdown.Toggle variant="success" id="dropdown-basic">
            Filter : {dropdownLabel}
          </Dropdown.Toggle>

          <Dropdown.Menu>
            <Dropdown.Item
              onClick={() => {
                filterOrders("All");
                setDropdownLabel("All");
              }}
            >
              All
            </Dropdown.Item>
            <Dropdown.Item
              onClick={() => {
                filterOrders("InProgress");
                setDropdownLabel("InProgress");
              }}
            >
              In progress
            </Dropdown.Item>
            <Dropdown.Item
              onClick={() => {
                filterOrders("Processed");
                setDropdownLabel("Processed");
              }}
            >
              Processed
            </Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
      </Row>
      <Row>
        {showedOrders != [] &&
          showedOrders?.map((order, index) => (
            <Col className="my__flex" key={index} lg={3} md={6} sm={4}>
              <Card
                onClick={() => {
                  setShowedOrder(order);
                }}
                className="my__card"
                style={{ width: "22rem", height: "10rem" }}
              >
                <Card.Body>
                  <Card.Title>Order {order.id}</Card.Title>
                </Card.Body>
                <Card.Footer>
                  {moment(order.deadline).format("DD MMM YYYY   hh:mm a")}
                </Card.Footer>
              </Card>
            </Col>
          ))}
      </Row>
      <Row className="my__row__pagination">
        <Col className="my__flex">
          <Pagination size="lg">
            <Pagination.Prev
              disabled={pagNumber === 0}
              onClick={handleSlideLeft}
            />
            <Pagination.Item disabled>{pagNumber}</Pagination.Item>
            <Pagination.Next
              disabled={pagNumber === maxPag}
              onClick={handleSlideRight}
            />
          </Pagination>
        </Col>
      </Row>
      <Row>
        <Col>
          {showedOrder && (
            <Table striped bordered variant="light">
              <thead>
                <tr>
                  <th>#</th>
                  <th>Code</th>
                  <th>Name</th>
                  <th>Amount</th>
                </tr>
              </thead>
              <tbody>
                {showedOrder &&
                  showedOrder.orderItem.map((item, index) => (
                    <tr>
                      <td>{index + 1}</td>
                      <td>{item.medicine.code}</td>
                      <td>{item.medicine.name}</td>
                      <td>{item.amount}</td>
                    </tr>
                  ))}
              </tbody>
            </Table>
          )}
        </Col>
      </Row>
    </Tab.Pane>
  );
}

export default DisplayPurchaseOrders;
