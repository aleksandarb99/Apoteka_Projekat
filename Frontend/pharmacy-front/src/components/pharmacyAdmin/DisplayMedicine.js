import React, { useState, useEffect } from "react";

import { Tab, Row, Col, Button, Table, Modal, Alert } from "react-bootstrap";

import axios from "../../app/api";

import "../../styling/pharmacy.css";

import AddingMedicineModal from "./AddingMedicineModal";
import ChangePriceModal from "./ChangePriceModal";

function DisplayMedicine({
  idOfPharmacy,
  priceListId,
  refreshPriceList,
  setRefreshPriceList,
}) {
  const [medicineItems, setMedicineItems] = useState([]);
  const [selectedRowId, setSelectedRowId] = useState(-1);
  const [selectedRowPrice, setSelectedRowPrice] = useState(-1);
  const [addModalShow, setAddModalShow] = useState(false);
  const [changePriceModalShow, setChangePriceModalShow] = useState(false);
  const [removeModalShow, setRemoveModalShow] = useState(false);
  const [showAlert, setShowAlert] = useState(false);
  const [alertText, setAlertText] = useState("");
  const [isAlertGood, SetIsAlertGood] = useState(false);

  async function fetchPriceList() {
    const request = await axios.get(
      `http://localhost:8080/api/pricelist/${priceListId}`
    );
    setMedicineItems(request.data.medicineItems);

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
    const request = await axios
      .post(
        `http://localhost:8080/api/pricelist/${priceListId}/addmedicine/${selectedMedicineId}/${price}`
      )
      .then(() => {
        fetchPriceList();
        setRefreshPriceList(!refreshPriceList);
        displayAlert(
          true,
          "You have successfully added the medicine to the pharmacy."
        );
      })
      .catch(() => {
        displayAlert(
          false,
          "Error! You failed to add the medicine to the pharmacy."
        );
      });
    return request;
  }

  async function removeMedicine() {
    const request = await axios
      .delete(
        `http://localhost:8080/api/pricelist/${priceListId}/removemedicine/${selectedRowId}`
      )
      .then(() => {
        fetchPriceList();
        setRefreshPriceList(!refreshPriceList);
        displayAlert(
          true,
          "You have successfully removed the medicine from the pharmacy."
        );
      })
      .catch(() => {
        displayAlert(
          false,
          "Error! You failed to remove the medicine from the pharmacy."
        );
      });
    return request;
  }

  async function changePrice(price) {
    const request = await axios
      .post(
        `http://localhost:8080/api/pricelist/${priceListId}/changeprice/${selectedRowId}/${price}`
      )
      .then(() => {
        fetchPriceList();
        displayAlert(
          true,
          "You have successfully changed price of the medicine from the pharmacy."
        );
      })
      .catch(() => {
        displayAlert(
          false,
          "Error! You failed to change price of the medicine from the pharmacy."
        );
      });
    return request;
  }

  let displayAlert = (isGood, text) => {
    SetIsAlertGood(isGood);
    setShowAlert(true);
    setAlertText(text);
    setTimeout(function () {
      setShowAlert(false);
      setAlertText("");
    }, 3000);
  };

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
                      handleClick(item.id, item.price);
                    }}
                    className={`${
                      selectedRowId == item.id ? "selectedRow" : "pointer"
                    } `}
                  >
                    <td>{index + 1}</td>
                    <td>{item.medicine.code}</td>
                    <td>{item.medicine.name}</td>
                    <td>{item.medicine.content}</td>
                    <td>{item.medicine.avgGrade}</td>
                    <td>{item.price}$</td>
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

          <Alert show={showAlert} variant={isAlertGood ? "success" : "danger"}>
            <Alert.Heading>
              {isAlertGood ? "Congratulations!" : "You got an error!"}
            </Alert.Heading>
            <p>{alertText}</p>
          </Alert>

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
