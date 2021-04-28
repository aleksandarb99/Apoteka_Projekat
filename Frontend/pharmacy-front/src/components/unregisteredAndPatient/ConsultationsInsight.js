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
  const [consultations, setConsultations] = useState([]);
  const [dropdownLabel, setDropdownLabel] = useState("History");
  const [sorter, setSorter] = useState("none");
  const [ascDesc, setAscDesc] = useState("none");
  const [reload, setReload] = useState(false);

  useEffect(() => {
    async function fetchConsultations() {
      if (dropdownLabel === "History") {
        let search_params = new URLSearchParams();
        if (sorter != "none" && ascDesc != "none")
          search_params.append("sort", sorter + ascDesc);
        const request = await axios.get(
          "http://localhost:8080/api/appointment/history/patient/" +
            getIdFromToken(),
          {
            params: search_params,
          }
        );
        setConsultations(request.data);

        return request;
      } else {
        let search_params = new URLSearchParams();
        if (sorter != "none" && ascDesc != "none")
          search_params.append("sort", sorter + ascDesc);
        const request = await axios.get(
          "http://localhost:8080/api/appointment/upcoming/patient/" +
            getIdFromToken(),
          {
            params: search_params,
          }
        );
        setConsultations(request.data);

        return request;
      }
    }
    fetchConsultations();
  }, [dropdownLabel, sorter, ascDesc, reload]);

  const cancelConsultation = (id) => {
    axios
      .put("http://localhost:8080/api/appointment/cancel-consultation/" + id)
      .then((res) => {
        if (res.data == "canceled") alert("success");
        else alert("fail");
        setReload(!reload);
      });
  };

  function differenceInMinutes(startTime) {
    let today = new Date().getTime();
    if ((startTime - today) / 60000 < 1440) return false;
    return true;
  }

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
                <th></th>
              </tr>
            </thead>
            <tbody>
              {consultations &&
                consultations.map((fc) => (
                  <tr key={fc.id}>
                    <td>{moment(fc.startTime).format("DD-MM-YYYY HH:mm")}</td>
                    <td>{moment(fc.endTime).format("DD-MM-YYYY HH:mm")}</td>
                    <td>{fc.duration}</td>
                    <td>{fc.price}</td>
                    <td>{fc.workerName}</td>
                    <td>{fc.pharmacyName}</td>
                    <td>
                      <Button
                        variant="danger"
                        onClick={() => cancelConsultation(fc.id)}
                        style={{
                          display:
                            dropdownLabel === "Upcoming" &&
                            differenceInMinutes(fc.startTime)
                              ? "inline-block"
                              : "none",
                        }}
                      >
                        Cancel
                      </Button>
                    </td>
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
