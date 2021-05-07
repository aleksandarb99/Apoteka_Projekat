import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import {
  Row,
  Col,
  Container,
  Table,
  Button,
  Form,
  Alert,
  Nav,
} from "react-bootstrap";

import axios from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";

import "../../styling/pharmaciesAndMedicines.css";
import "../../styling/consultation.css";

function SubscribedPharmacies() {
  const [pharmacies, setPharmacies] = useState([]);
  useEffect(() => {
    async function fetchPharmacies() {
      const request = await axios.get(
        "http://localhost:8080/api/pharmacy/subscribed/patient/" +
          getIdFromToken()
      );
      setPharmacies(request.data);

      return request;
    }
    fetchPharmacies();
  }, []);
  return (
    <Container fluid className="consultation__insight__container">
      <div className="consultation__insight__content">
        <Row className="my__flex">
          <h3>Pharmacies to which you are subscribed</h3>
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
                <th>Name</th>
                <th>Description</th>
                <th>Average grade</th>
                <th>Address</th>
              </tr>
            </thead>
            <tbody>
              {pharmacies &&
                pharmacies.map((p) => (
                  <tr key={p.id}>
                    <td>{p.name}</td>
                    <td>
                      {p.description.length > 30
                        ? p.description.substr(0, 29) + "..."
                        : p.description}
                    </td>
                    <td>{p.avgGrade}</td>
                    <td>{p.address}</td>
                  </tr>
                ))}
            </tbody>
          </Table>
        </Row>
      </div>
    </Container>
  );
}

export default SubscribedPharmacies;
