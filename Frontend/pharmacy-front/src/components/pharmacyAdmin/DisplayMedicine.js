import React, { useState, useEffect } from "react";

import { Tab, Row, Col, Button, Table, Modal, Alert } from "react-bootstrap";

import api from "../../app/api";

import "../../styling/pharmacy.css";

import AddingMedicineModal from "./AddingMedicineModal";
import ChangePriceModal from "./ChangePriceModal";

import { useToasts } from "react-toast-notifications";
import { getErrorMessage } from "../../app/errorHandler";

function DisplayMedicine({
  idOfPharmacy,
  priceListId,
  refreshPriceList,
  setRefreshPriceList,
}) {
  const { addToast } = useToasts();

  const [medicineItems, setMedicineItems] = useState([]);
  const [selectedRowId, setSelectedRowId] = useState(-1);
  const [selectedRowPrice, setSelectedRowPrice] = useState(-1);
  const [addModalShow, setAddModalShow] = useState(false);
  const [changePriceModalShow, setChangePriceModalShow] = useState(false);
  const [removeModalShow, setRemoveModalShow] = useState(false);

  async function fetchPriceList() {
    const request = await api
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
  }, [priceListId]);

  let handleClick = (medicineItemId, price) => {
    setSelectedRowId(medicineItemId);
    setSelectedRowPrice(price);
  };

  async function addMedicine(selectedMedicineId, price) {
    const request = await api
      .post(
        `/api/pricelist/${priceListId}/addmedicine/${selectedMedicineId}/${price}`
      )
      .then((res) => {
        fetchPriceList();
        setRefreshPriceList(!refreshPriceList);
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

  async function removeMedicine() {
    const request = await api
      .delete(`/api/pricelist/${priceListId}/removemedicine/${selectedRowId}`)
      .then((res) => {
        fetchPriceList();
        setRefreshPriceList(!refreshPriceList);
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

  async function changePrice(price) {
    const request = await api
      .post(
        `/api/pricelist/${priceListId}/changeprice/${selectedRowId}/${price}`
      )
      .then((res) => {
        fetchPriceList();
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

  let handleAddModalSave = (selectedMedicineId, price) => {
    setAddModalShow(false);
    addMedicine(selectedMedicineId, price);
  };

  let handleChangePriceModalSave = (price) => {
    setChangePriceModalShow(false);
    if (selectedRowPrice != price) changePrice(price);
    setSelectedRowId(-1);
  };

  let handleAddModalClose = () => {
    setAddModalShow(false);
  };

  let handleChangePriceModalClose = () => {
    setChangePriceModalShow(false);
  };

  let handleRemove = () => {
    removeMedicine();
    setSelectedRowId(-1);
  };

  return (
    <Tab.Pane eventKey="third">
      <h1 className="content-header">Medicine</h1>
      <Row>
        <Col>
          <Table bordered striped hover variant="dark">
            <thead>
              <tr>
                <th>#</th>
                <th>Code</th>
                <th>Name</th>
                <th>Content</th>
                <th>Avg. grade</th>
                <th>Price</th>
                <th>Amount</th>
              </tr>
            </thead>
            <tbody>
              {medicineItems?.length != 0 &&
                medicineItems?.map((item, index) => (
                  <tr
                    onClick={() => {
                      handleClick(item.id, item.price2);
                    }}
                    className={`${selectedRowId == item.id ? "selectedRow" : "pointer"
                      } `}
                  >
                    <td>{index + 1}</td>
                    <td>{item.medicine.code}</td>
                    <td>{item.medicine.name}</td>
                    <td>{item.medicine.content}</td>
                    <td>{item.medicine.avgGrade}</td>
                    <td>{item.price2}$</td>
                    <td>{item.amount}</td>
                  </tr>
                ))}
            </tbody>
          </Table>

          <div className="center">
            <Button
              disabled={selectedRowId == -1}
              variant="secondary"
              onClick={() => {
                setChangePriceModalShow(true);
              }}
            >
              Change price
            </Button>
            <Button
              variant="success"
              onClick={() => {
                setAddModalShow(true);
                setSelectedRowId(-1);
              }}
            >
              Add
            </Button>
            <Button
              disabled={selectedRowId == -1}
              variant="danger"
              onClick={() => {
                setRemoveModalShow(true);
              }}
            >
              Remove
            </Button>
          </div>

          <AddingMedicineModal
            medicineItemsLength={medicineItems?.length}
            idOfPharmacy={idOfPharmacy}
            show={addModalShow}
            onHide={handleAddModalClose}
            handleAdd={handleAddModalSave}
          />
          <ChangePriceModal
            oldPrice={selectedRowPrice}
            show={changePriceModalShow}
            onHide={handleChangePriceModalClose}
            handleChange={handleChangePriceModalSave}
          />
          <Modal
            oldPrice={idOfPharmacy}
            show={removeModalShow}
            onHide={() => {
              setRemoveModalShow(false);
            }}
          >
            <Modal.Header closeButton>
              <Modal.Title>Attention</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              Are you sure you want to delete the medicine?
            </Modal.Body>
            <Modal.Footer>
              <Button
                variant="secondary"
                onClick={() => {
                  setRemoveModalShow(false);
                }}
              >
                Close
              </Button>
              <Button
                variant="primary"
                onClick={() => {
                  handleRemove();
                  setRemoveModalShow(false);
                }}
              >
                Remove
              </Button>
            </Modal.Footer>
          </Modal>
        </Col>
      </Row>
    </Tab.Pane>
  );
}

export default DisplayMedicine;
