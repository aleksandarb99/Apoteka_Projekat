import React, { useState, useEffect } from "react";
import { getIdFromToken } from "../../app/jwtTokenUtils";

import {
  Pagination,
  Table,
  Tab,
  Row,
  Col,
  Card,
  Button,
  Alert,
  Spinner,
} from "react-bootstrap";

import Dropdown from "react-bootstrap/Dropdown";

import axios from "../../app/api";

import moment from "moment";

import AddPurchaseOrderModal from "./AddPurchaseOrderModal";
import SelectOfferModal from "./SelectOfferModal";
import EditOrderModal from "./EditOrderModal";
import { useToasts } from "react-toast-notifications";
import { getErrorMessage } from "../../app/errorHandler";

function DisplayPurchaseOrders({
  idOfPharmacy,
  priceListId,
  refresh,
  refreshInq,
  setRefreshInq,
}) {
  const { addToast } = useToasts();
  const [orders, setOrders] = useState([]);
  const [filterValue, setFilterValue] = useState("All");
  const [showedOrders, setShowedOrders] = useState([]);
  const [showedOrder, setShowedOrder] = useState(null);
  const [pagNumber, setPugNummber] = useState(0);
  const [maxPag, setMaxPag] = useState(0);
  const [dropdownLabel, setDropdownLabel] = useState("All");

  const [selectOfferModalShow, setSelectOfferModalShow] = useState(false);
  const [showEditOrderModal, setShowEditOrderModal] = useState(false);

  const [showSpinner, setShowSpinner] = useState(false);

  const [addModalShow, setAddModalShow] = useState(false);
  const [medicineItems, setMedicineItems] = useState([]);

  async function fetchPriceList() {
    const request = await axios
      .get(`/api/pricelist/${priceListId}`)
      .then((res) => {
        setMedicineItems(res.data.medicineItems);
      })
      .catch((err) => {
        addToast(getErrorMessage(err), {
          appearance: "error",
        });
      });

    return request;
  }

  useEffect(() => {
    if (priceListId != undefined) {
      fetchPriceList();
    }
  }, [priceListId, refresh]);

  async function fetchOrders() {
    const request = await axios.get(
      `/api/orders/bypharmacyid/${idOfPharmacy}`,
      { params: { filter: filterValue } }
    );
    setOrders(request.data);
    return request;
  }

  useEffect(() => {
    if (idOfPharmacy != undefined) {
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

  async function addPurchaseOrder(data) {
    let dto = {
      pharmacyId: idOfPharmacy,
      deadline: data.startDate.getTime(),
      items: [...data.orders],
      adminId: getIdFromToken(),
    };

    const request = await axios
      .post(`/api/orders/addorder`, dto)
      .then((res) => {
        fetchOrders();
        addToast(res.data, {
          appearance: "success",
        });
      })
      .catch((err) => {
        addToast(getErrorMessage(err), {
          appearance: "error",
        });
      });
    return request;
  }

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

  let handleAddModalSave = (orders) => {
    setAddModalShow(false);
    addPurchaseOrder(orders);
  };

  let handleAddModalClose = () => {
    setAddModalShow(false);
  };

  async function deletePOrder() {
    const request = await axios
      .delete(`/api/orders/${showedOrder.id}`)
      .then((res) => {
        fetchOrders();
        addToast(res.data, {
          appearance: "success",
        });
      })
      .catch((err) => {
        addToast(getErrorMessage(err), {
          appearance: "error",
        });
      });
    return request;
  }

  let deleteOrder = () => {
    deletePOrder();
  };

  async function selectOrder(selectedOfferId) {
    let dto = {
      selectedOfferId,
      orderId: showedOrder.id,
      adminId: getIdFromToken(),
    };
    setShowSpinner(true);
    const request = await axios
      .post(`/api/suppliers/offers/accept/`, dto)
      .then((res) => {
        setShowSpinner(false);
        filterOrders("All");
        setDropdownLabel("All");
        setRefreshInq(!refreshInq);
        addToast(res.data, {
          appearance: "success",
        });
      })
      .catch((err) => {
        addToast(getErrorMessage(err), {
          appearance: "error",
        });
      });
    return request;
  }

  let handleSelectOfferModalSave = (selectedOfferId) => {
    setSelectOfferModalShow(false);
    selectOrder(selectedOfferId);
  };

  let handleSelectOfferModalClose = () => {
    setSelectOfferModalShow(false);
  };

  async function editOrder(date) {
    const request = await axios
      .put(`/api/orders/${showedOrder.id}/${date.getTime()}/`)
      .then((res) => {
        fetchOrders();
        addToast(res.data, {
          appearance: "success",
        });
      })
      .catch((err) => {
        addToast(getErrorMessage(err), {
          appearance: "error",
        });
      });
    return request;
  }

  let handleEditModalSave = (date) => {
    setShowEditOrderModal(false);
    editOrder(date);
  };

  let handleEditModalClose = () => {
    setShowEditOrderModal(false);
  };

  return (
    <Tab.Pane eventKey="seventh">
      <h1 className="content-header">Purchase orders</h1>
      <hr></hr>
      <Row>
        {" "}
        <Dropdown>
          <Dropdown.Toggle variant="primary" id="dropdown-basic">
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
                filterOrders("OnHold");
                setDropdownLabel("OnHold");
              }}
            >
              On hold
            </Dropdown.Item>

            <Dropdown.Item
              onClick={() => {
                filterOrders("Processed");
                setDropdownLabel("Processed");
              }}
            >
              Ended
            </Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
        <Button
          variant="success"
          size="md"
          onClick={() => {
            setAddModalShow(true);
          }}
        >
          Add purchase order
        </Button>
        <AddPurchaseOrderModal
          medicineList={medicineItems}
          idOfPharmacy={idOfPharmacy}
          show={addModalShow}
          onHide={handleAddModalClose}
          handleAdd={handleAddModalSave}
        />
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
      {showedOrder != null && (
        <Row>
          <Col>
            <Alert variant="secondary">
              <Alert.Heading>Order {showedOrder.id}</Alert.Heading>
            </Alert>
          </Col>
          <Col className="center">
            <Button
              disabled={
                showedOrder == null || showedOrder.adminId != getIdFromToken()
              }
              variant="info"
              size="lg"
              onClick={() => {
                setShowEditOrderModal(true);
              }}
            >
              Edit
            </Button>
            <EditOrderModal
              order={showedOrder}
              show={showEditOrderModal}
              onHide={handleEditModalClose}
              handleEdit={handleEditModalSave}
            />
            <Button
              disabled={
                showedOrder == null ||
                dropdownLabel !== "OnHold" ||
                showedOrder.adminId != getIdFromToken()
              }
              variant="primary"
              size="lg"
              onClick={() => {
                setSelectOfferModalShow(true);
              }}
            >
              Choose offer
            </Button>
            <Button
              disabled={
                showedOrder == null || showedOrder.adminId != getIdFromToken()
              }
              variant="danger"
              size="lg"
              onClick={() => {
                deleteOrder();
              }}
            >
              Delete
            </Button>
          </Col>
        </Row>
      )}
      <Row>
        <Col></Col>
        <Col className="center">
          {" "}
          <Spinner
            style={{ display: showSpinner ? "block" : "none" }}
            animation="border"
          />
          <hr></hr>
        </Col>
        <Col></Col>
      </Row>
      <Row>
        <Col>
          {showedOrder && (
            <Table striped bordered variant="dark">
              <thead>
                <tr>
                  <th>#</th>
                  <th>Code</th>
                  <th>Name</th>
                  <th>Amount</th>
                </tr>
              </thead>
              <tbody>
                <SelectOfferModal
                  orderId={showedOrder.id}
                  show={selectOfferModalShow}
                  onHide={handleSelectOfferModalClose}
                  handleSelect={handleSelectOfferModalSave}
                />
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
