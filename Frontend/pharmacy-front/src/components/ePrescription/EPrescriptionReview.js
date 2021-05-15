import React, { useEffect, useState } from "react";

import { Row, Table } from "react-bootstrap";
import Dropdown from "react-bootstrap/Dropdown";
import moment from "moment";

import axios from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";

import "../../styling/pharmaciesAndMedicines.css";
import "../../styling/consultation.css";

function EPrescriptionReview() {
  const [entities, setEntitites] = useState([]);
  const [selectedEntity, setSelectedEntity] = useState(null);
  const [sorter, setSorter] = useState("none");

  useEffect(() => {
    async function fetchEntities() {
      const request = await axios
        .get("http://localhost:8080/api/e-recipes/patient/" + getIdFromToken())
        .then((res) => {
          setEntitites(res.data);
        })
        .catch(() => {
          alert("This should not happen!");
        });

      return request;
    }
    fetchEntities();
  }, []);

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

  return (
    <div className="consultation__insight__content">
      <Row
        style={{
          justifyContent: "center",
          marginTop: "50px",
          display: entities.length === 0 ? "flex" : "none",
        }}
      >
        <h3>You don't have any e-prescriptions!</h3>
      </Row>
      <Row
        style={{
          justifyContent: "center",
          marginTop: "50px",
          display: entities.length === 0 ? "none" : "flex",
        }}
      >
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
      </Row>
      <Row
        style={{
          justifyContent: "space-between",
          display: entities.length === 0 ? "none" : "flex",
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
