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
} from "react-bootstrap";
import { StarFill } from "react-bootstrap-icons";

import axios from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";

import "../../styling/pharmaciesAndMedicines.css";
import "../../styling/consultation.css";

import { useToasts } from "react-toast-notifications";
import { getErrorMessage } from "../../app/errorHandler";

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
  const [sorter, setSorter] = useState("none");
  const [reload, setReload] = useState(false);
  const [sorter2, setSorter2] = useState("none");
  const [reload2, setReload2] = useState(false);
  const [points, setPoints] = useState({});
  const [category, setCategory] = useState({});
  const { addToast } = useToasts();

  useEffect(() => {
    async function fetchPoints() {
      const request = await axios.get(
        "/api/patients/" + getIdFromToken() + "/points"
      );
      setPoints(request.data);
      return request;
    }
    fetchPoints();
  }, []);

  useEffect(() => {
    async function fetchCategory() {
      const request = await axios.get("/api/ranking-category/points/" + points);
      setCategory(request.data);

      return request;
    }
    fetchCategory();
  }, [points]);

  useEffect(() => {
    if (requestedDate == null) return;

    async function fetchPharmacies() {
      let search_params = new URLSearchParams();
      search_params.append("date", requestedDate);
      const request = await axios.get("/api/pharmacy/all/free-pharmacists/", {
        params: search_params,
      });
      if (request.status == 404) {
        addToast(request.data, { appearance: "error" });
        setPharmacies([]);
      }
      if (request.status == 200) {
        if (request.data.length == 0) {
          addToast("There's no available pharmacists!", {
            appearance: "warning",
          });
        }
        setPharmacies(request.data);
      }
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
      search_params.append("id", chosenPharmacy.id);
      const request = await axios.get(
        "/api/workers/all/free-pharmacists/pharmacy",
        { params: search_params }
      );
      if (request.status == 404) {
        addToast(request.data, { appearance: "error" });
        setWorkers([]);
      }
      if (request.status == 200) {
        if (request.data.length == 0) {
          addToast("There's no available pharmacists!", {
            appearance: "warning",
          });
        }
        setWorkers(request.data);
      }

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
    axios
      .post(
        `/api/appointment/reserve-consultation/pharmacy/${chosenPharmacy.id
        }/pharmacist/${selectedWorker.id
        }/patient/${getIdFromToken()}/date/${requestedDate}/`
      )
      .then((res) => {
        addToast(res.data, { appearance: "success" });
        setRequestedDate(null);
        setPharmacies([]);
        setReloadPharmacies(!reloadPharmacies);
      })
      .catch((err) => {
        addToast(getErrorMessage(err), { appearance: "error" });
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
    if (date.getTime()) {
      if (date.getTime() < new Date()) {
        addToast("Choose a date from the future!", { appearance: "warning" });
      } else {
        setRequestedDate(date.getTime());
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
      .get("/api/pharmacy/all/free-pharmacists/", {
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
    search_params.append("id", chosenPharmacy.id);
    axios
      .get("/api/workers/all/free-pharmacists/pharmacy", {
        params: search_params,
      })
      .then((resp) => setWorkers(resp.data))
      .catch(setWorkers([]));
  };

  const updateSorting2 = (event) => {
    setSorter2(event.target.value);
  };

  return (
    <Container fluid className="reserve__consultation__container">
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
        <Row
          className="justify-content-center mt-5"
          style={{ display: pharmacies.length == 0 ? "none" : "flex" }}
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
                      setChosenPharmacy(pharmacy);
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

        {showedPharmacies.length > 0 && (
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
        <Row
          className="justify-content-center mt-5"
          style={{
            display:
              requestedDate != null && showedPharmacies.length === 0
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
                chosenPharmacy !== null && workers.length > 0
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
        <p
          style={{
            textAlign: "center",
            display: category == "" ? "none" : "block",
          }}
        >
          You have a discount of {category?.discount}%
        </p>
        <p
          style={{
            textAlign: "center",
            fontSize: "1.3rem",
            display:
              category == "" || chosenPharmacy == null ? "none" : "block",
          }}
        >
          Total price:{" "}
          <span style={{ textDecoration: "line-through" }}>
            {chosenPharmacy?.consultationPrice}
          </span>
          {"   ->   "}
          {(chosenPharmacy?.consultationPrice * (100 - category.discount)) /
            100}
        </p>
        <Row>
          <Button
            variant="info"
            onClick={createReservation}
            style={{
              display:
                Object.keys(selectedWorker).length === 0 ||
                  chosenPharmacy == null
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
