import React, { useState, useEffect } from "react";

import Moment from "react-moment";

import { Pagination, Table, Tab, Row, Col, Card } from "react-bootstrap";

import Dropdown from "react-bootstrap/Dropdown";

import axios from "axios";

function DisplayPurchaseOrders({ idOfPharmacy }) {
  const [orders, setOrders] = useState([]);
  const [filterValue, setFilterValue] = useState("All");
  const [showedOrders, setShowedOrders] = useState([]);
  const [showedOrder, setShowedOrder] = useState(null);
  const [pagNumber, setPugNummber] = useState(0);
  const [maxPag, setMaxPag] = useState(0);

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
    setFilterValue(param);
  };

  useEffect(() => {
    let maxNumber = Math.floor(orders?.length / 12) - 1;
    if (orders?.length / 12 - 1 > maxNumber) {
      maxNumber = maxNumber + 1;
    }
    setMaxPag(maxNumber);
  }, [orders]);

  useEffect(() => {
    let first = pagNumber * 12;
    let max = orders.length < first + 12 ? orders?.length : first + 12;
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
            State filter
          </Dropdown.Toggle>

          <Dropdown.Menu>
            <Dropdown.Item
              onClick={() => {
                filterOrders("All");
              }}
            >
              All
            </Dropdown.Item>
            <Dropdown.Item
              onClick={() => {
                filterOrders("InProgress");
              }}
            >
              In progress
            </Dropdown.Item>
            <Dropdown.Item
              onClick={() => {
                filterOrders("Processed");
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
            <Col className="my__flex" key={index} lg={3} md={6} sm={12}>
              <Card
                onClick={() => {
                  setShowedOrder(order);
                }}
                className="my__card"
                style={{ width: "22rem", height: "10rem" }}
              >
                <Card.Body>
                  <Card.Title>Order {order.id}</Card.Title>
                  <Card.Text>Deadline:</Card.Text>
                  <Card.Text>
                    <Moment format="DD.MM.yyyy" unix>
                      {order.deadline}
                    </Moment>{" "}
                    <Moment format="hh:mm" unix>
                      {order.deadline}
                    </Moment>
                  </Card.Text>
                </Card.Body>
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
            <Table striped bordered>
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
