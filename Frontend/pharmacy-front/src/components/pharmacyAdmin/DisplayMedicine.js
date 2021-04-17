import React, { useState, useEffect } from "react";

import { Tab, Row, Col, Button, Table, Modal, Alert } from "react-bootstrap";

import axios from "axios";

import "../../styling/pharmacy.css";

import AddingMedicineModal from "./AddingMedicineModal";

function DisplayMedicine({ idOfPharmacy, priceListId }) {
  const [medicineItems, setMedicineItems] = useState([]);
  const [selectedRowId, setSelectedRowId] = useState(-1);
  const [addModalShow, setAddModalShow] = useState(false);
  const [removeModalShow, setRemoveModalShow] = useState(false);
  const [alertShow, setAlertShow] = useState(false);

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

  let handleClick = (medicineItemId) => {
    setSelectedRowId(medicineItemId);
  };

  async function addMedicine(selectedMedicineId) {
    const request = await axios
      .post(
        `http://localhost:8080/api/pricelist/${priceListId}/addmedicine/${selectedMedicineId}`
      )
      .then(() => {
        fetchPriceList();
      })
      .catch(() => {
        alert("Failed to add");
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
      })
      .catch(() => {
        alert("Failed to remove");
      });
    return request;
  }

  let handleAddModalSave = (selectedMedicineId) => {
    setAddModalShow(false);
    addMedicine(selectedMedicineId);
    setAlertShow(true);
  };

  let handleAddModalClose = () => {
    setAddModalShow(false);
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
          <Table bordered variant="light">
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
                      handleClick(item.id);
                    }}
                    className={`${
                      selectedRowId == item.id && "selectedRow"
                    } pointer`}
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
          <Alert show={alertShow} variant="success">
            <Alert.Heading>Reminder!</Alert.Heading>
            <p>Don't forget to set the price of the medicine.</p>
            <hr />
            <div className="d-flex justify-content-end">
              <Button
                onClick={() => setAlertShow(false)}
                variant="outline-success"
              >
                Close me!
              </Button>
            </div>
          </Alert>
          <AddingMedicineModal
            medicineItemsLength={medicineItems?.length}
            idOfPharmacy={idOfPharmacy}
            show={addModalShow}
            onHide={handleAddModalClose}
            handleAdd={handleAddModalSave}
          />
          <Modal
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
