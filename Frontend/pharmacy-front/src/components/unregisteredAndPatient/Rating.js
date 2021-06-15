import React, { useEffect, useState } from "react";

import { Row, Container, Table, Button, Alert } from "react-bootstrap";
import Dropdown from "react-bootstrap/Dropdown";
import ReactStars from "react-rating-stars-component";
import { StarFill } from "react-bootstrap-icons";
import moment from "moment";

import api from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";

import "../../styling/pharmaciesAndMedicines.css";
import "../../styling/consultation.css";
import { useToasts } from "react-toast-notifications";
import { getErrorMessage } from "../../app/errorHandler";

function Rating() {
  const [dropdownLabel, setDropdownLabel] = useState("Dermatologist");
  const [entities, setEntitites] = useState([]);
  const [selectedEntity, setSelectedEntity] = useState(null);
  const [rating, setRating] = useState(null);
  const [enabledRating, setEnabledRating] = useState(false);
  const [reload, setReload] = useState(false);
  const { addToast } = useToasts();
  var spans = document.querySelectorAll("span");

  useEffect(() => {
    async function fetchEntities() {
      let url;
      if (dropdownLabel === "Dermatologist") {
        url = "/api/workers/all-dermatologists/patient/";
      } else if (dropdownLabel === "Pharmacist") {
        url = "/api/workers/all-pharmacists/patient/";
      } else if (dropdownLabel === "Medicine") {
        url = "/api/medicine/all-medicines/patient/";
      } else {
        url = "/api/pharmacy/all-pharmacies/patient/";
      }

      const request = await api
        .get(url + getIdFromToken())
        .then((res) => {
          setEntitites(res.data);
        })
        .catch(() => {
          setEntitites([]);
        });

      return request;
    }
    fetchEntities();
  }, [dropdownLabel, reload]);

  const updateSelectedEntity = (selectedEntity) => {
    setSelectedEntity(selectedEntity);

    api
      .get(
        "/api/rating/" +
        dropdownLabel.toLowerCase() +
        "/" +
        selectedEntity.id +
        "/patient/" +
        getIdFromToken() +
        "/grade"
      )
      .then((res) => {
        setRating(res.data);
      }).catch(() => { });
  };

  const ratingChanged = (newRating) => {
    for (let span of spans) {
      span.classList.remove("my__color__star");
    }

    let forSend = {
      id: -1,
      grade: newRating,
      gradedID: selectedEntity.id,
      date: new Date().getTime(),
      patientId: getIdFromToken(),
      type: dropdownLabel.toUpperCase(),
    };

    if (enabledRating === false) {
      api
        .post("/api/rating/", forSend)
        .then((res) => {
          addToast(res.data, { appearance: "success" });
          setEnabledRating(false);
          updateSelectedEntity(selectedEntity);
          setReload(!reload);
        })
        .catch((err) => {
          addToast(getErrorMessage(err), { appearance: "error" });
        });
    } else {
      forSend.id = rating.id;
      api
        .put("/api/rating/", forSend)
        .then((res) => {
          addToast(res.data, { appearance: "success" });
          setEnabledRating(false);
          updateSelectedEntity(selectedEntity);
          setReload(!reload);
        })
        .catch((err) => {
          addToast(getErrorMessage(err), { appearance: "error" });
        });
    }
  };

  const reset = () => {
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
              (dropdownLabel == "Dermatologist" ||
                dropdownLabel == "Pharmacist") &&
                entities.length > 0
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
        <Row
          style={{
            display:
              dropdownLabel == "Medicine" && entities.length > 0
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
        <Row
          style={{
            display:
              dropdownLabel == "Pharmacy" && entities.length > 0
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
          <Button
            disabled={enabledRating}
            variant="info"
            onClick={() => setEnabledRating(true)}
          >
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
        <Row
          style={{
            justifyContent: "center",
            display: entities.length === 0 ? "flex" : "none",
          }}
        >
          <Alert transition={true} variant="warning">
            There's no {dropdownLabel.toLowerCase()} to grade!
          </Alert>
        </Row>
      </div>
    </Container>
  );
}

export default Rating;
