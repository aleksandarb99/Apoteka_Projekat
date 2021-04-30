import React, { useState, useEffect } from "react";

import { Tab, Row, Col, Button, Table, Modal, Alert } from "react-bootstrap";

import axios from "../../app/api";

import "../../styling/pharmacy.css";
import AddingWorkerModal from "./AddingWorkerModal";

function DisplayWorkers({ idOfPharmacy }) {
  const [workers, setWorkers] = useState([]);
  const [selectedRowId, setSelectedRowId] = useState(-1);
  const [addModalShow, setAddModalShow] = useState(false);

  async function fetchWorkers() {
    const request = await axios.get(
      `http://localhost:8080/api/workplace/bypharmacyid/${idOfPharmacy}`
    );
    setWorkers(request.data);
    return request;
  }

  async function addWorker(id, dto) {
    const request = await axios.post(
      `http://localhost:8080/api/workplace/addworker/bypharmacyid/${idOfPharmacy}/${id}`,
      dto
    );
    fetchWorkers();
    return request;
  }

  async function removeWorker() {
    const request = await axios
      .delete(
        `http://localhost:8080/api/workplace/removeworker/bypharmacyid/${idOfPharmacy}/${selectedRowId}`
      )
      .catch(() => {
        alert("Cannot delete it");
      });
    fetchWorkers();
    return request;
  }

  useEffect(() => {
    if (idOfPharmacy != undefined) {
      fetchWorkers();
    }
  }, [idOfPharmacy]);

  let handleClick = (requestId) => {
    setSelectedRowId(requestId);
  };

  let handleRemove = () => {
    removeWorker();
    setSelectedRowId(-1);
  };

  let handleAddModalSave = (selectedMedicineId, dto) => {
    setAddModalShow(false);
    addWorker(selectedMedicineId, dto);
  };

  return (
    <Tab.Pane eventKey="second">
      <h1 className="content-header">Pharmacists and dermatologists</h1>
      <Row>
        <Col>
          <Table bordered striped hover variant="dark">
            <thead>
              <tr>
                <th>#</th>
                <th>Type</th>
                <th>Firstname</th>
                <th>Lastname</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Avg. grade</th>
              </tr>
            </thead>
            <tbody>
              {" "}
              {workers?.length != 0 &&
                workers?.map((item, index) => (
                  <tr
                    onClick={() => {
                      handleClick(item.id);
                    }}
                    className={`${
                      selectedRowId == item.id ? "selectedRow" : "pointer"
                    } `}
                  >
                    <td>{index + 1}</td>
                    <td>{item.worker.userType}</td>
                    <td>{item.worker.firstName}</td>
                    <td>{item.worker.lastName}</td>
                    <td>{item.worker.email}</td>
                    <td>{item.worker.telephone}</td>
                    <td>{item.worker.avgGrade}</td>
                  </tr>
                ))}
            </tbody>
          </Table>

          <div className="center">
            <Button
              onClick={() => {
                setAddModalShow(true);
                setSelectedRowId(-1);
              }}
              variant="success"
            >
              Add worker
            </Button>
            <Button
              disabled={selectedRowId == -1}
              onClick={handleRemove}
              variant="danger"
            >
              Remove worker
            </Button>

            <AddingWorkerModal
              workersLength={workers?.length}
              idOfPharmacy={idOfPharmacy}
              show={addModalShow}
              onHide={() => {
                setAddModalShow(false);
              }}
              handleAdd={handleAddModalSave}
            />
          </div>
        </Col>
      </Row>
    </Tab.Pane>
  );
}

export default DisplayWorkers;
