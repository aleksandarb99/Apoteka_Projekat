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
import ReactStars from "react-rating-stars-component";
import { StarFill } from "react-bootstrap-icons";
import moment from "moment";

import axios from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";

import "../../styling/pharmaciesAndMedicines.css";
import "../../styling/consultation.css";
import { reset } from "ol/transform";

function Rating() {
  const [dropdownLabel, setDropdownLabel] = useState("Dermatologist");
  const [entities, setEntitites] = useState([]);
  const [selectedEntity, setSelectedEntity] = useState(null);
  const [rating, setRating] = useState(null);
  const [enabledRating, setEnabledRating] = useState(false);
  var spans = document.querySelectorAll("span");

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
        url = "http://localhost:8080/api/pharmacy/all-pharmacies/patient/";
      }

      const request = await axios.get(url + getIdFromToken());
      setEntitites(request.data);

      return request;
    }
    fetchEntities();
  }, [dropdownLabel]);

  const updateSelectedEntity = (selectedEntity) => {
    setSelectedEntity(selectedEntity);

    axios
      .get(
        "http://localhost:8080/api/rating/" +
          dropdownLabel.toLowerCase() +
          "/" +
          selectedEntity.id +
          "/patient/" +
          getIdFromToken() +
          "/grade"
      )
      .then((res) => {
        setRating(res.data);
      });
  };

  const ratingChanged = (newRating) => {
    console.log(newRating);
    //let spans = document.querySelectorAll("span");
    for (let span of spans) {
      span.classList.remove("my__color__star");
    }
  };

  const reset = () => {
    //let spans = document.querySelectorAll("span");
    for (let span of spans) {
      span.classList.add("my__color__star");
    }
  };

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
                  setSelectedEntity(null);
                  setRating(null);
                  setEnabledRating(false);
                  reset();
                }}
              >
                Dermatologist
              </Dropdown.Item>
              <Dropdown.Item
                onClick={() => {
                  setDropdownLabel("Pharmacist");
                  setSelectedEntity(null);
                  setRating(null);
                  setEnabledRating(false);
                  reset();
                }}
              >
                Pharmacist
              </Dropdown.Item>
              <Dropdown.Item
                onClick={() => {
                  setDropdownLabel("Medicine");
                  setSelectedEntity(null);
                  setRating(null);
                  setEnabledRating(false);
                  reset();
                }}
              >
                Medicine
              </Dropdown.Item>
              <Dropdown.Item
                onClick={() => {
                  setDropdownLabel("Pharmacy");
                  setSelectedEntity(null);
                  setRating(null);
                  setEnabledRating(false);
                  reset();
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
                  <tr
                    key={e.id}
                    onClick={() => updateSelectedEntity(e)}
                    className={
                      selectedEntity?.id === e.id
                        ? "my__row__selected my__table__row"
                        : "my__table__row"
                    }
                  >
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
                  <tr
                    key={e.id}
                    onClick={() => updateSelectedEntity(e)}
                    className={
                      selectedEntity?.id === e.id
                        ? "my__row__selected my__table__row"
                        : "my__table__row"
                    }
                  >
                    <td>{e.medicineCode}</td>
                    <td>{e.name}</td>
                    <td>{e.avgGrade}</td>
                  </tr>
                ))}
            </tbody>
          </Table>
        </Row>
        <Row style={{ display: dropdownLabel == "Pharmacy" ? "flex" : "none" }}>
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
                <th>Address</th>
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
                    <td>{e.name}</td>
                    <td>{e.avgGrade}</td>
                    <td>{e.address}</td>
                  </tr>
                ))}
            </tbody>
          </Table>
        </Row>
        <Row
          className="my__flex"
          style={{
            display:
              rating != null && rating !== "not graded" ? "flex" : "none",
          }}
        >
          <p className="my__flex" style={{ marginBottom: "0" }}>
            You have graded last time{" "}
            {rating && moment(rating?.date).format("DD-MM-YYYY HH:mm")}:{" "}
            {[...Array(rating && rating?.grade)].map(() => (
              <StarFill style={{ width: "1.5rem" }} />
            ))}
          </p>
          <Button variant="info" onClick={() => setEnabledRating(true)}>
            Change grade
          </Button>
        </Row>
        <Row
          className="my__flex"
          style={{
            display:
              (rating != null && rating === "not graded") || enabledRating
                ? "flex"
                : "none",
          }}
        >
          <ReactStars
            count={5}
            onChange={ratingChanged}
            size={48}
            activeColor="green"
          />
        </Row>
      </div>
    </Container>
  );
}

export default Rating;
