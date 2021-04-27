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
import "../../styling/consultation.css";

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
  const [sorter, setSorter] = useState("none");
  const [reload, setReload] = useState(false);
  const [sorter2, setSorter2] = useState("none");
  const [reload2, setReload2] = useState(false);

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
      setChosenPharmacy(null);
      setSelectedWorker({});
      setWorkers([]);

      return request;
    }
    fetchPharmacies();
  }, [reloadPharmacies, requestedDate, reload]);

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
  }, [chosenPharmacy, reload2]);

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
    setWorkers([]);
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

  const formSearch = (event) => {
    event.preventDefault();

    if (sorter === "none") return;

    let search_params = new URLSearchParams();

    if (sorter === "ascPrice") {
      search_params.append("sort", "consultationPrice,asc");
    }
    if (sorter === "descPrice") {
      search_params.append("sort", "consultationPrice,desc");
    }
    if (sorter === "ascGrade") {
      search_params.append("sort", "avgGrade,asc");
    }
    if (sorter === "descGrade") {
      search_params.append("sort", "avgGrade,desc");
    }

    search_params.append("date", requestedDate);

    axios
      .get("http://localhost:8080/api/pharmacy/all/free-pharmacists/", {
        params: search_params,
      })
      .then((resp) => setPharmacies(resp.data))
      .catch(setPharmacies([]));
  };

  const updateSorting = (event) => {
    setSorter(event.target.value);
  };

  const formSearch2 = (event) => {
    event.preventDefault();

    if (sorter2 === "none") return;

    let search_params = new URLSearchParams();

    if (sorter2 === "ascGrade") {
      search_params.append("sort", "avgGrade,asc");
    }
    if (sorter2 === "descGrade") {
      search_params.append("sort", "avgGrade,desc");
    }

    search_params.append("date", requestedDate);
    search_params.append("id", chosenPharmacy);
    axios
      .get("http://localhost:8080/api/workers/all/free-pharmacists/pharmacy", {
        params: search_params,
      })
      .then((resp) => setWorkers(resp.data))
      .catch(setWorkers([]));
  };

  const updateSorting2 = (event) => {
    setSorter2(event.target.value);
  };

  return (
    <Container
      fluid
      // style={{
      //   backgroundColor: "rgba(162, 211, 218, 0.897)",
      //   minHeight: "100vh",
      //   paddingTop: "30px",
      //   paddingBottom: "30px",
      // }}
      className="reserve__consultation__container"
    >
      <div className="reserve__consultation__content">
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
        <Row
          className="justify-content-center mt-5"
          style={{ display: pharmacies.length === 0 ? "none" : "flex" }}
        >
          <Form onSubmit={formSearch}>
            <Form.Group as={Row} className="align-items-center">
              <Col className="my__flex" md={6} lg={6}>
                <Form.Label style={{ marginRight: "20px" }}>
                  Sorter:{" "}
                </Form.Label>
                <Form.Control
                  as="select"
                  value={sorter}
                  onChange={updateSorting.bind(this)}
                  name="sorter"
                >
                  <option value="none">none</option>
                  <option value="ascPrice">Price (ascending)</option>
                  <option value="descPrice">Price (descending)</option>
                  <option value="ascGrade">Pharmacy grade (ascending)</option>
                  <option value="descGrade">Pharmacy grade (descending)</option>
                </Form.Control>
              </Col>
              <Col className="justify-content-center" md={6} lg={6}>
                <Button type="submit" variant="primary">
                  {" "}
                  Sort{" "}
                </Button>
                <Button
                  variant="primary"
                  onClick={() => {
                    setReload(!reload);
                  }}
                >
                  {" "}
                  Reset{" "}
                </Button>
              </Col>
            </Form.Group>
          </Form>
        </Row>
        <Row>
          <Col md={12} lg={12} className="my__flex">
            {requestedDate != null &&
              showedPharmacies.length === 0 &&
              dateInPastAlert == false && (
                <Alert variant="danger">
                  There's no available pharmacies/pharmacists at required date
                  and time!
                </Alert>
              )}
          </Col>
          {showedPharmacies &&
            showedPharmacies.map((pharmacy) => {
              return (
                <Col
                  className="my__flex"
                  key={pharmacy.id}
                  lg={3}
                  md={6}
                  sm={12}
                >
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
                workers.length == 0 &&
                chosenPharmacy !== null &&
                !dateInPastAlert
                  ? "block"
                  : "none",
              textAlign: "center",
              width: "100%",
            }}
          >
            No available pharmacists at selected date and pharmacy!
          </h4>
        </Row>
        <Row
          className="justify-content-center mt-5"
          style={{
            display:
              requestedDate != null &&
              showedPharmacies.length === 0 &&
              dateInPastAlert == false
                ? "none"
                : "flex",
          }}
        >
          <Form
            onSubmit={formSearch2}
            style={{ display: workers.length === 0 ? "none" : "block" }}
          >
            <Form.Group as={Row} className="align-items-center">
              <Col className="my__flex" md={6} lg={6}>
                <Form.Label style={{ marginRight: "20px" }}>
                  Sorter:{" "}
                </Form.Label>
                <Form.Control
                  as="select"
                  value={sorter2}
                  onChange={updateSorting2.bind(this)}
                  name="sorter2"
                >
                  <option value="none">none</option>
                  <option value="ascGrade">Pharmacist grade (ascending)</option>
                  <option value="descGrade">
                    Pharmacist grade (descending)
                  </option>
                </Form.Control>
              </Col>
              <Col className="justify-content-center" md={6} lg={6}>
                <Button type="submit" variant="primary">
                  {" "}
                  Sort{" "}
                </Button>
                <Button
                  variant="primary"
                  onClick={() => {
                    setReload2(!reload2);
                  }}
                >
                  {" "}
                  Reset{" "}
                </Button>
              </Col>
            </Form.Group>
          </Form>
        </Row>
        <Row>
          <Table
            striped
            bordered
            variant="light"
            style={{
              display:
                chosenPharmacy !== null &&
                workers.length > 0 &&
                !dateInPastAlert
                  ? "table"
                  : "none",
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
                Object.keys(selectedWorker).length === 0 ||
                chosenPharmacy == null ||
                dateInPastAlert
                  ? "none"
                  : "inline-block",
              margin: "auto",
            }}
          >
            Reserve
          </Button>
        </Row>
      </div>
    </Container>
  );
}

export default PharmaciesWithFreePharmacists;
