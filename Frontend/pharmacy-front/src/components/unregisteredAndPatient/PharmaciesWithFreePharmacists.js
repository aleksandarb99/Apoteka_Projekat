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
} from "react-bootstrap";
import { StarFill } from "react-bootstrap-icons";

import axios from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";

import "../../styling/pharmaciesAndMedicines.css";

function PharmaciesWithFreePharmacists() {
  const [pharmacies, setPharmacies] = useState([]);
  const [workers, setWorkers] = useState([]);
  const [chosenPharmacy, setChosenPharmacy] = useState(null);
  const [pagNumber, setPugNummber] = useState(0);
  const [maxPag, setMaxPag] = useState(0);
  const [showedPharmacies, setShowedPharmacies] = useState([]);
  const [selectedWorker, setSelectedWorker] = useState({});
  const [reloadPharmacies, setReloadPharmacies] = useState(false);

  useEffect(() => {
    async function fetchPharmacies() {
      let search_params = new URLSearchParams();
      search_params.append("date", 1619700300000);
      const request = await axios.get(
        "http://localhost:8080/api/pharmacy/all/free-pharmacists/",
        { params: search_params }
      );
      setPharmacies(request.data);

      return request;
    }
    fetchPharmacies();
  }, [reloadPharmacies]);

  useEffect(() => {
    if (chosenPharmacy == null) return;
    async function fetchWorkers() {
      let search_params = new URLSearchParams();
      search_params.append("date", 1619700300000);
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
    console.log(selectedWorker);
    setSelectedWorker(selectedWorker);
  };

  const createReservation = () => {
    //setSuccessAlert(false);
    // if (pickupDate) {
    //   if (pickupDate > new Date()) {
    //     setShowAlert(false);
    //   } else {
    //     setSuccessAlert(false);
    //     setShowAlert(true);
    //     return;
    //   }
    // }
    axios
      .post(
        `http://localhost:8080/api/appointment/reserve-consultation/pharmacy/${chosenPharmacy}/pharmacist/${
          selectedWorker.id
        }/patient/${getIdFromToken()}/date/${1619700300000}/`
      )
      .then((res) => {
        if (res.data === "reserved") alert("success");
        else alert("fail");
        setReloadPharmacies(!reloadPharmacies);
      });

    setChosenPharmacy(null);
    setSelectedWorker({});
    //setPickupDate(null);
  };

  return (
    <Container
      fluid
      style={{
        backgroundColor: "rgba(162, 211, 218, 0.897)",
        minHeight: "100vh",
      }}
    >
      <Row>
        {showedPharmacies.length === 0 && (
          <Row className="justify-content-center m-3 align-items-center">
            <h3>No result!</h3>
          </Row>
        )}
        {showedPharmacies &&
          showedPharmacies.map((pharmacy) => {
            return (
              <Col className="my__flex" key={pharmacy.id} lg={3} md={6} sm={12}>
                <Card
                  className="my__card"
                  style={{ width: "18rem" }}
                  onClick={() => setChosenPharmacy(pharmacy.id)}
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
