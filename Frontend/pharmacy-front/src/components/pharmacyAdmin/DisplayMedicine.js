import React, { useState, useEffect } from "react";

import { Tab, Row, Col, Button, Table } from "react-bootstrap";

import "../../styling/pharmacy.css";

function DisplayMedicine({ idOfPharmacy, medicineItems }) {
  const [selectedRowId, setSelectedRowId] = useState(-1);

  let handleClick = (medicineItemId) => {
    setSelectedRowId(medicineItemId);
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
            <Button variant="success">Add</Button>
            <Button variant="danger">Remove</Button>
          </div>
        </Col>
      </Row>
    </Tab.Pane>
  );
}

export default DisplayMedicine;
