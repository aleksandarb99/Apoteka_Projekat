import React, { useState } from "react";

import { Tab, Row, Col, Button, Table } from "react-bootstrap";

import "../../styling/pharmacy.css";

import AddingMedicineModal from "./AddingMedicineModal";

function DisplayMedicine({ idOfPharmacy, medicineItems }) {
  const [selectedRowId, setSelectedRowId] = useState(-1);
  const [addModalShow, setAddModalShow] = useState(false);

  let handleClick = (medicineItemId) => {
    setSelectedRowId(medicineItemId);
  };

  let handleAddModalSave = () => {
    setAddModalShow(false);
  };

  let handleAddModalClose = () => {
    setAddModalShow(false);
  };

  return (
    <Tab.Pane eventKey="third">
      <h1 className="content-header">Medicine</h1>
      <Row>
        <Col>
          <Table bordered>
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
                    className={`${selectedRowId == item.id && "selectedRow"}`}
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
              }}
            >
              Add
            </Button>
            <Button disabled={selectedRowId == -1} variant="danger">
              Remove
            </Button>
          </div>
          <AddingMedicineModal
            idOfPharmacy={idOfPharmacy}
            show={addModalShow}
            onHide={handleAddModalClose}
            handleAdd={handleAddModalSave}
          />
        </Col>
      </Row>
    </Tab.Pane>
  );
}

export default DisplayMedicine;
