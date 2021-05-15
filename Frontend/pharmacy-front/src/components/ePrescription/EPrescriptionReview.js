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
          setEntitites([]);
        });

      return request;
    }
    fetchEntities();
  }, []);

  const updateSelectedEntity = (selectedEntity) => {
    setSelectedEntity(selectedEntity);

    // axios
    //   .get(
    //     "http://localhost:8080/api/rating/" +
    //       dropdownLabel.toLowerCase() +
    //       "/" +
    //       selectedEntity.id +
    //       "/patient/" +
    //       getIdFromToken() +
    //       "/grade"
    //   )
    //   .then((res) => {
    //     setRating(res.data);
    //   });
  };

  return (
    <div>
      <Row style={{ justifyContent: "center", marginTop: "50px" }}>
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
                setSorter(" Dispensing date (ascending)");
              }}
            >
              Dispensing date (ascending)
            </Dropdown.Item>
            <Dropdown.Item
              onClick={() => {
                setSorter("Dispensing date (descending)");
              }}
            >
              Dispensing date (descending)
            </Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
      </Row>
      <Row style={{ justifyContent: "space-between" }}>
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
                  key={e.id}
                  onClick={() => updateSelectedEntity(e)}
                  className={
                    selectedEntity?.id === e.id
                      ? "my__row__selected my__table__row"
                      : "my__table__row"
                  }
                >
                  <td>{e.code}</td>
                  <td>{e.state}</td>
                  <td>
                    {moment(e.prescriptionDate).format("DD-MM-YYYY HH:mm")}
                  </td>
                  <td>{moment(e.dispensingDate).format("DD-MM-YYYY HH:mm")}</td>
                </tr>
              ))}
          </tbody>
        </Table>
      </Row>
    </div>
  );
}

export default EPrescriptionReview;
