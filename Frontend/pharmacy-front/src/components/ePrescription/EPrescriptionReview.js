import React, { useEffect, useState } from "react";

import { Row, Table, Form } from "react-bootstrap";
import Dropdown from "react-bootstrap/Dropdown";
import moment from "moment";

import api from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";

import "../../styling/pharmaciesAndMedicines.css";
import "../../styling/consultation.css";
import { getErrorMessage } from '../../app/errorHandler';
import { useToasts } from "react-toast-notifications";

function EPrescriptionReview() {
  const [entities, setEntitites] = useState([]);
  const [selectedEntity, setSelectedEntity] = useState(null);
  const [reload, setReload] = useState(false);
  const [reload2, setReload2] = useState(false);
  const [sorter, setSorter] = useState("none");
  const [filter, setFilter] = useState("none");
  const { addToast } = useToasts();

  useEffect(() => {
    async function fetchEntities() {
      const request = await api
        .get("/api/e-recipes/patient/" + getIdFromToken())
        .then((res) => {
          setEntitites(res.data);
          setReload2(!reload2);
        })
        .catch((err) => {
          addToast(getErrorMessage(err), { appearance: "error" });
        });

      return request;
    }
    fetchEntities();
  }, [reload]);

  useEffect(() => {
    doFiltering();
  }, [reload2]);

  const updateSelectedEntity = (selectedEntity) => {
    setSelectedEntity(selectedEntity);
  };

  const doSorting = (sortParam) => {
    if (sortParam === "none") return;

    entities.sort(function (e1, e2) {
      return e1.prescriptionDate - e2.prescriptionDate;
    });

    if (sortParam === "desc") {
      entities.reverse();
    }
  };

  const doFiltering = () => {
    if (filter === "none") {
      return;
    }

    let filtered = [];
    for (let i = 0; i < entities.length; i++) {
      if (filter === "Processed" && entities[i].state === "PROCESSED") {
        filtered.push(entities[i]);
      } else if (filter === "New" && entities[i].state === "NEW") {
        filtered.push(entities[i]);
      } else if (filter === "Rejected" && entities[i].state === "REJECTED") {
        filtered.push(entities[i]);
      }
    }

    setEntitites(filtered);
  };

  return (
    <div className="consultation__insight__content">
      <Row
        style={{
          justifyContent: "center",
          marginTop: "50px",
          display: entities.length === 0 && filter === "none" ? "flex" : "none",
        }}
      >
        <h3>You don't have any e-prescriptions!</h3>
      </Row>
      <Row
        style={{
          justifyContent: "center",
          marginTop: "50px",
          display: entities.length === 0 && filter === "none" ? "none" : "flex",
        }}
      >
        <Form.Label style={{ marginRight: "20px" }}>Sorter: </Form.Label>
        <Dropdown>
          <Dropdown.Toggle variant="success" id="dropdown-basic">
            {sorter}
          </Dropdown.Toggle>

          <Dropdown.Menu>
            <Dropdown.Item
              onClick={() => {
                setSorter("none");
                doSorting("none");
              }}
            >
              none
            </Dropdown.Item>
            <Dropdown.Item
              onClick={() => {
                setSorter("Dispensing date (ascending)");
                doSorting("asc");
              }}
            >
              Dispensing date (ascending)
            </Dropdown.Item>
            <Dropdown.Item
              onClick={() => {
                setSorter("Dispensing date (descending)");
                doSorting("desc");
              }}
            >
              Dispensing date (descending)
            </Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
        <Form.Label style={{ marginRight: "20px" }}>Filter: </Form.Label>
        <Dropdown>
          <Dropdown.Toggle variant="success" id="dropdown-basic">
            {filter}
          </Dropdown.Toggle>

          <Dropdown.Menu>
            <Dropdown.Item
              onClick={() => {
                setFilter("none");
                setReload(!reload);
                setSelectedEntity(null);
              }}
            >
              none
            </Dropdown.Item>
            <Dropdown.Item
              onClick={() => {
                setFilter("Processed");
                setReload(!reload);
                setSelectedEntity(null);
              }}
            >
              Processed
            </Dropdown.Item>
            <Dropdown.Item
              onClick={() => {
                setFilter("New");
                setReload(!reload);
                setSelectedEntity(null);
              }}
            >
              New
            </Dropdown.Item>
            <Dropdown.Item
              onClick={() => {
                setFilter("Rejected");
                setReload(!reload);
                setSelectedEntity(null);
              }}
            >
              Rejected
            </Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
      </Row>
      <Row
        style={{
          justifyContent: "space-between",
          display: entities.length === 0 && filter === "none" ? "none" : "flex",
        }}
      >
        <Table
          striped
          bordered
          variant="light"
          className="my__table__pharmacies"
        >
          <thead>
            <tr>
              <th>Code</th>
              <th>State</th>
              <th>Prescription date</th>
              <th>Dispensing date</th>
            </tr>
          </thead>
          <tbody>
            {entities &&
              entities.map((e) => (
                <tr
                  key={e.code}
                  onClick={() => updateSelectedEntity(e)}
                  className={
                    selectedEntity?.code === e.code
                      ? "my__row__selected my__table__row"
                      : "my__table__row"
                  }
                >
                  <td>{e.code}</td>
                  <td>{e.state}</td>
                  <td>
                    {moment(e.prescriptionDate).format("DD-MM-YYYY HH:mm:ss")}
                  </td>
                  <td>
                    {e.dispensingDate != null
                      ? moment(e.dispensingDate).format("DD-MM-YYYY HH:mm:ss")
                      : "/"}
                  </td>
                </tr>
              ))}
          </tbody>
        </Table>
      </Row>
      <Row
        style={{
          justifyContent: "center",
          marginTop: "50px",
          display: selectedEntity == null ? "none" : "flex",
        }}
      >
        <h3>Medicines of e-prescription</h3>
      </Row>
      <Row style={{ display: selectedEntity == null ? "none" : "flex" }}>
        <Table
          striped
          bordered
          variant="light"
          className="my__table__pharmacies"
        >
          <thead>
            <tr>
              <th>Code</th>
              <th>Name</th>
              <th>Quanitiy</th>
            </tr>
          </thead>
          <tbody>
            {selectedEntity &&
              selectedEntity.eRecipeItems.map((e) => (
                <tr key={e.medicineCode}>
                  <td>{e.medicineCode}</td>
                  <td>{e.medicineName}</td>
                  <td>{e.quantity}</td>
                </tr>
              ))}
          </tbody>
        </Table>
      </Row>
    </div>
  );
}

export default EPrescriptionReview;
