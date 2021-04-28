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

function ConsultationsInsight() {
  const [finishedConsultations, setFinishedConsultations] = useState([]);
  const [selectedConsultation, setSelectedConsultation] = useState({});
  const [dropdownLabel, setDropdownLabel] = useState("History");

  useEffect(() => {
    async function fetchFinishedConsultations() {
      setFinishedConsultations([]);

      if (dropdownLabel === "History") {
        const request = await axios.get(
          "http://localhost:8080/api/appointment/history/patient/" +
            getIdFromToken()
        );
        setFinishedConsultations(request.data);

        return request;
      }
    }
    fetchFinishedConsultations();
  }, [dropdownLabel]);

  const updateSelectedConsultation = (selectedConsultation) => {
    setSelectedConsultation(selectedConsultation);
  };

  return (
    <Container fluid className="consultation__insight__container">
      <div className="consultation__insight__content">
        <Row>
          <Dropdown>
            <Dropdown.Toggle variant="success" id="dropdown-basic">
              {dropdownLabel}
            </Dropdown.Toggle>

            <Dropdown.Menu>
              <Dropdown.Item
                onClick={() => {
                  // filterOrders("All");
                  setDropdownLabel("History");
                }}
              >
                History
              </Dropdown.Item>
              <Dropdown.Item
                onClick={() => {
                  // filterOrders("InProgress");
                  setDropdownLabel("Upcoming");
                }}
              >
                Upcoming
              </Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
        </Row>
        <Row>
          <Table
            striped
            bordered
            variant="light"
            className="my__table__pharmacies"
          >
            <thead>
              <tr>
                <th>Start time</th>
                <th>End time</th>
                <th>Duration</th>
                <th>Price</th>
                <th>Pharmacist</th>
                <th>Pharmacy</th>
              </tr>
            </thead>
            <tbody>
              {finishedConsultations &&
                finishedConsultations.map((fc) => (
                  <tr
                    key={fc.id}
                    onClick={() => updateSelectedConsultation(fc)}
                    className={
                      selectedConsultation.id === fc.id
                        ? "my__row__selected my__table__row"
                        : "my__table__row"
                    }
                  >
                    <td>{moment(fc.startTime).format("DD:MM:YYYY HH:mm")}</td>
                    <td>{moment(fc.endTime).format("DD:MM:YYYY HH:mm")}</td>
                    <td>{fc.duration}</td>
                    <td>{fc.price}</td>
                    <td>{fc.workerName}</td>
                    <td>{fc.pharmacyName}</td>
                  </tr>
                ))}
            </tbody>
          </Table>
        </Row>
      </div>
    </Container>
  );
}

export default ConsultationsInsight;
