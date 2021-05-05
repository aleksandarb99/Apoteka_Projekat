import React, { useEffect, useState } from "react";

import {
  Row,
  Col,
  Container,
  Table,
  Button,
  Form,
  Alert,
} from "react-bootstrap";
import Dropdown from "react-bootstrap/Dropdown";

import axios from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";

import moment from "moment";

import "../../styling/pharmaciesAndMedicines.css";
import "../../styling/consultation.css";

function Rating() {
  const [dropdownLabel, setDropdownLabel] = useState("Dermatologist");
  const [entities, setEntitites] = useState([]);

  useEffect(() => {
    async function fetchEntities() {
      let url;
      if (dropdownLabel === "Dermatologist") {
        url = "http://localhost:8080/api/workers/all-dermatologists/patient/";
      } else if (dropdownLabel === "Pharmacist") {
        url = "http://localhost:8080/api/workers/all-pharmacists/patient/";
      } else if (dropdownLabel === "Medicine") {
        url = "http://localhost:8080/api/medicine/all-medicines/patient/";
      } else {
        url = "http://localhost:8080/api/workers/all-dermatologists/patient/";
      }

      const request = await axios.get(url + getIdFromToken());
      setEntitites(request.data);

      return request;
    }
    fetchEntities();
  }, [dropdownLabel]);

  return (
    <Container fluid className="consultation__insight__container">
      <div className="consultation__insight__content">
        <Row className="my__flex">
          <h3>Rating procedure</h3>
        </Row>
        <Row>
          <Dropdown>
            <Dropdown.Toggle variant="success" id="dropdown-basic">
              {dropdownLabel}
            </Dropdown.Toggle>

            <Dropdown.Menu>
              <Dropdown.Item
                onClick={() => {
                  setDropdownLabel("Dermatologist");
                }}
              >
                Dermatologist
              </Dropdown.Item>
              <Dropdown.Item
                onClick={() => {
                  setDropdownLabel("Pharmacist");
                }}
              >
                Pharmacist
              </Dropdown.Item>
              <Dropdown.Item
                onClick={() => {
                  setDropdownLabel("Medicine");
                }}
              >
                Medicine
              </Dropdown.Item>
              <Dropdown.Item
                onClick={() => {
                  setDropdownLabel("Pharmacy");
                }}
              >
                Pharmacy
              </Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
        </Row>
        <Row
          style={{
            display:
              dropdownLabel == "Dermatologist" || dropdownLabel == "Pharmacist"
                ? "flex"
                : "none",
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
                <th>Name</th>
                <th>Average grade</th>
              </tr>
            </thead>
            <tbody>
              {entities &&
                entities.map((e) => (
                  <tr key={e.id}>
                    <td>{e.name}</td>
                    <td>{e.avgGrade}</td>
                  </tr>
                ))}
            </tbody>
          </Table>
        </Row>
        <Row style={{ display: dropdownLabel == "Medicine" ? "flex" : "none" }}>
          <Table
            striped
            bordered
            variant="light"
            className="my__table__pharmacies"
          >
            <thead>
              <tr>
                <th>Medicine code</th>
                <th>Name</th>
                <th>Average grade</th>
              </tr>
            </thead>
            <tbody>
              {entities &&
                entities.map((e) => (
                  <tr key={e.id}>
                    <td>{e.medicineCode}</td>
                    <td>{e.name}</td>
                    <td>{e.avgGrade}</td>
                  </tr>
                ))}
            </tbody>
          </Table>
        </Row>
      </div>
    </Container>
  );
}

export default Rating;
