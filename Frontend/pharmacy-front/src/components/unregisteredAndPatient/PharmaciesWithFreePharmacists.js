import React, { useEffect, useState } from "react";

import {
  Row,
  Col,
  Container,
  Card,
  ListGroup,
  ListGroupItem,
  Pagination,
  Table,
  Button,
  Form,
  Alert,
} from "react-bootstrap";
import { StarFill } from "react-bootstrap-icons";

import axios from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";

import "../../styling/pharmaciesAndMedicines.css";

function PharmaciesWithFreePharmacists() {
  const [startDate, setStartDate] = useState(new Date());
  const [startHour, setStartHour] = useState(null);
  const [requestedDate, setRequestedDate] = useState(null);
  const [pharmacies, setPharmacies] = useState([]);
  const [workers, setWorkers] = useState([]);
  const [chosenPharmacy, setChosenPharmacy] = useState(null);
  const [pagNumber, setPugNummber] = useState(0);
  const [maxPag, setMaxPag] = useState(0);
  const [showedPharmacies, setShowedPharmacies] = useState([]);
  const [selectedWorker, setSelectedWorker] = useState({});
  const [reloadPharmacies, setReloadPharmacies] = useState(false);
  const [dateInPastAlert, setDateInPastAlert] = useState(false);

  useEffect(() => {
    if (requestedDate == null) return;

    async function fetchPharmacies() {
      let search_params = new URLSearchParams();
      search_params.append("date", requestedDate);
      const request = await axios.get(
        "http://localhost:8080/api/pharmacy/all/free-pharmacists/",
        { params: search_params }
      );
      setPharmacies(request.data);

      return request;
    }
    fetchPharmacies();
  }, [reloadPharmacies, requestedDate]);

  useEffect(() => {
    if (chosenPharmacy == null) return;
    async function fetchWorkers() {
      let search_params = new URLSearchParams();
      search_params.append("date", requestedDate);
      search_params.append("id", chosenPharmacy);
      const request = await axios.get(
        "http://localhost:8080/api/workers/all/free-pharmacists/pharmacy",
        { params: search_params }
      );
      setWorkers(request.data);

      return request;
    }
    fetchWorkers();
  }, [chosenPharmacy]);

  useEffect(() => {
    let maxNumber = Math.floor(pharmacies?.length / 12) - 1;
    if (pharmacies?.length / 12 - 1 > maxNumber) {
      maxNumber = maxNumber + 1;
    }
    setMaxPag(maxNumber);
  }, [pharmacies]);

  useEffect(() => {
    let first = pagNumber * 12;
    let max = pharmacies.length < first + 12 ? pharmacies?.length : first + 12;
    setShowedPharmacies(pharmacies?.slice(first, max));
  }, [pharmacies, pagNumber]);

  let handleSlideLeft = () => {
    if (pagNumber !== 0) {
      setPugNummber(pagNumber - 1);
    }
  };

  let handleSlideRight = () => {
    if (pagNumber !== maxPag) {
      setPugNummber(pagNumber + 1);
    }
  };

  const updateSelectedWorker = (selectedWorker) => {
    setSelectedWorker(selectedWorker);
  };

  const createReservation = () => {
    // setSuccessAlert(false);
    axios
      .post(
        `http://localhost:8080/api/appointment/reserve-consultation/pharmacy/${chosenPharmacy}/pharmacist/${
          selectedWorker.id
        }/patient/${getIdFromToken()}/date/${requestedDate}/`
      )
      .then((res) => {
        if (res.data === "reserved") alert("success");
        else alert("fail");
        setReloadPharmacies(!reloadPharmacies);
      });
    setChosenPharmacy(null);
    setSelectedWorker({});
  };

  const changeDate = (date) => {
    let array = date.split("-");
    let d = new Date(
      Number.parseInt(array[0]),
      Number.parseInt(array[1]) - 1,
      Number.parseInt(array[2])
    );
    setStartDate(d);
  };

  const createRequestedDate = () => {
    let date = startDate;
    let hour = startHour;
    let array = hour.split(":");
    date.setHours(Number.parseInt(array[0]), Number.parseInt(array[1]), 0);
    setRequestedDate(date.getTime());
    if (date.getTime()) {
      if (date.getTime() > new Date()) {
        setDateInPastAlert(false);
      } else {
        setDateInPastAlert(true);
        return;
      }
    }
  };

  return (
    <Container
      fluid
      style={{
        backgroundColor: "rgba(162, 211, 218, 0.897)",
        minHeight: "100vh",
        paddingTop: "30px",
      }}
    >
      <Row>
        <Col className="my__flex">
          <h3>Choose a date and time of consultation</h3>
        </Col>
      </Row>
      <Row>
        <Col className="my__flex">
          <Form.Group controlId="datePicker" di>
            <Form.Label>Date</Form.Label>
            <Form.Control
              type="date"
              onChange={(event) => changeDate(event.target.value)}
            />
          </Form.Group>
          <Form.Group controlId="timePicker" di>
            <Form.Label>Start time</Form.Label>
            <Form.Control
              type="time"
              value={startHour}
              onChange={(event) => setStartHour(event.target.value)}
            />
          </Form.Group>
          <Button
            variant="info"
            onClick={createRequestedDate}
            disabled={startDate == null || startHour == null}
          >
            Search
          </Button>
        </Col>
      </Row>
      <Row className="my__flex">
        <Col md={4} lg={4}>
          {dateInPastAlert && (
            <Alert variant="danger">Choose a day from the future</Alert>
          )}
        </Col>
      </Row>
      <Row>
        <Col md={12} lg={12} className="my__flex">
          {requestedDate != null &&
            showedPharmacies.length === 0 &&
            dateInPastAlert == false && (
              <Alert variant="danger">
                There's no available pharmacies/pharmacists at required date and
                time!
              </Alert>
            )}
        </Col>
        {showedPharmacies &&
          showedPharmacies.map((pharmacy) => {
            return (
              <Col className="my__flex" key={pharmacy.id} lg={3} md={6} sm={12}>
                <Card
                  className="my__card"
                  style={{ width: "18rem" }}
                  onClick={() => {
                    setChosenPharmacy(pharmacy.id);
                    setSelectedWorker({});
                  }}
                >
                  <Card.Body>
                    <Card.Title>{pharmacy.name}</Card.Title>
                  </Card.Body>
                  <ListGroup className="list-group-flush">
                    <ListGroupItem className="my__flex">
                      {pharmacy.address}
                    </ListGroupItem>
                    <ListGroupItem className="my__flex">
                      {[...Array(Math.ceil(pharmacy.avgGrade))].map(() => (
                        <StarFill className="my__star" />
                      ))}
                    </ListGroupItem>
                    <ListGroupItem className="my__flex">
                      Price: {pharmacy.consultationPrice}
                    </ListGroupItem>
                  </ListGroup>
                </Card>
              </Col>
            );
          })}
      </Row>

      {showedPharmacies.length > 0 && dateInPastAlert == false && (
        <Row className="my__row__pagination">
          <Col className="my__flex">
            <Pagination size="lg">
              <Pagination.Prev
                disabled={pagNumber === 0}
                onClick={handleSlideLeft}
              />
              <Pagination.Item disabled>{pagNumber}</Pagination.Item>
              <Pagination.Next
                disabled={pagNumber === maxPag}
                onClick={handleSlideRight}
              />
            </Pagination>
          </Col>
        </Row>
      )}

      <Row>
        <h4
          style={{
            display:
              workers.length == 0 && chosenPharmacy !== null ? "block" : "none",
            textAlign: "center",
            width: "100%",
          }}
        >
          No available pharmacists at selected date and pharmacy!
        </h4>
        <Table
          striped
          bordered
          variant="light"
          style={{
            display:
              chosenPharmacy !== null && workers.length > 0 ? "table" : "none",
            width: "50%",
          }}
          className="my__table__pharmacies"
        >
          <thead>
            <tr>
              <th>Name</th>
              <th>Average grade</th>
            </tr>
          </thead>
          <tbody>
            {workers &&
              workers.map((w) => (
                <tr
                  key={w.id}
                  onClick={() => updateSelectedWorker(w)}
                  className={
                    selectedWorker.id === w.id
                      ? "my__row__selected my__table__row"
                      : "my__table__row"
                  }
                >
                  <td>{w.name}</td>
                  <td>{w.avgGrade}</td>
                </tr>
              ))}
          </tbody>
        </Table>
      </Row>
      <Row>
        <Button
          variant="info"
          onClick={createReservation}
          style={{
            display:
              Object.keys(selectedWorker).length === 0 || chosenPharmacy == null
                ? "none"
                : "inline-block",
            margin: "auto",
          }}
        >
          Reserve
        </Button>
      </Row>
    </Container>
  );
}

export default PharmaciesWithFreePharmacists;
