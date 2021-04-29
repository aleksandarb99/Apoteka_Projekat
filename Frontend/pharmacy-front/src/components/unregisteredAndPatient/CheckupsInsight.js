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

function CheckupsInsight() {
  const [checkups, setCheckups] = useState([]);
  const [dropdownLabel, setDropdownLabel] = useState("History");
  const [sorter, setSorter] = useState("none");
  const [ascDesc, setAscDesc] = useState("none");

  useEffect(() => {
    async function fetchCheckups() {
      if (dropdownLabel === "History") {
        let search_params = new URLSearchParams();
        if (sorter != "none" && ascDesc != "none")
          search_params.append("sort", sorter + ascDesc);
        const request = await axios.get(
          "http://localhost:8080/api/appointment/checkups/history/patient/" +
            getIdFromToken(),
          {
            params: search_params,
          }
        );
        setCheckups(request.data);

        return request;
      }
    }
    fetchCheckups();
  }, [dropdownLabel, sorter, ascDesc]);

  return (
    <Container fluid className="consultation__insight__container">
      <div className="consultation__insight__content">
        <Row style={{ justifyContent: "space-between" }}>
          <Col className="my__flex" md={3} lg={3}>
            <Dropdown>
              <Dropdown.Toggle variant="success" id="dropdown-basic">
                {dropdownLabel}
              </Dropdown.Toggle>

              <Dropdown.Menu>
                <Dropdown.Item
                  onClick={() => {
                    setDropdownLabel("History");
                  }}
                >
                  History
                </Dropdown.Item>
                <Dropdown.Item
                  onClick={() => {
                    setDropdownLabel("Upcoming");
                  }}
                >
                  Upcoming
                </Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>
          </Col>
          <Col className="my__flex" md={6} lg={6}>
            <Form.Label style={{ marginRight: "20px" }}>Sorter: </Form.Label>
            <Dropdown>
              <Dropdown.Toggle variant="success" id="dropdown-basic">
                {sorter}
              </Dropdown.Toggle>

              <Dropdown.Menu>
                <Dropdown.Item
                  onClick={() => {
                    setSorter("none");
                  }}
                >
                  none
                </Dropdown.Item>
                <Dropdown.Item
                  onClick={() => {
                    setSorter("start time");
                  }}
                >
                  start time
                </Dropdown.Item>
                <Dropdown.Item
                  onClick={() => {
                    setSorter("end time");
                  }}
                >
                  end time
                </Dropdown.Item>
                <Dropdown.Item
                  onClick={() => {
                    setSorter("duration");
                  }}
                >
                  duration
                </Dropdown.Item>
                <Dropdown.Item
                  onClick={() => {
                    setSorter("price");
                  }}
                >
                  price
                </Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>
            <Dropdown>
              <Dropdown.Toggle variant="success" id="dropdown-basic">
                {ascDesc}
              </Dropdown.Toggle>

              <Dropdown.Menu>
                <Dropdown.Item
                  onClick={() => {
                    setAscDesc("none");
                  }}
                >
                  none
                </Dropdown.Item>
                <Dropdown.Item
                  onClick={() => {
                    setAscDesc("asc");
                  }}
                >
                  ascending
                </Dropdown.Item>
                <Dropdown.Item
                  onClick={() => {
                    setAscDesc("desc");
                  }}
                >
                  descending
                </Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>
          </Col>
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
              {checkups &&
                checkups.map((c) => (
                  <tr key={c.id}>
                    <td>{moment(c.startTime).format("DD-MM-YYYY HH:mm")}</td>
                    <td>{moment(c.endTime).format("DD-MM-YYYY HH:mm")}</td>
                    <td>{c.duration}</td>
                    <td>{c.price}</td>
                    <td>{c.workerName}</td>
                    <td>{c.pharmacyName}</td>
                  </tr>
                ))}
            </tbody>
          </Table>
        </Row>
      </div>
    </Container>
  );
}

export default CheckupsInsight;
