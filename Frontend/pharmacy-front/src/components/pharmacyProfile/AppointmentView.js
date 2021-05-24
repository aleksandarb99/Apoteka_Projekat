import React, { useState, useEffect } from "react";
import {
  Tab,
  Row,
  Col,
  Container,
  Card,
  ListGroup,
  ListGroupItem,
  Pagination,
  Button,
  Form,
} from "react-bootstrap";

import axios from "./../../app/api";
import moment from "moment";
import { getIdFromToken, getUserTypeFromToken } from "../../app/jwtTokenUtils";

import "../../styling/pharmaciesAndMedicines.css";
import { useToasts } from "react-toast-notifications";

function AppointmentView({ pharmacyId }) {
  const [reload, setReload] = useState(false);
  const [appointsments, setAppointsments] = useState([]);
  const [pagNumber, setPugNummber] = useState(0);
  const [maxPag, setMaxPag] = useState(0);
  const [showedAppointsments, setShowedAppointsments] = useState([]);
  const [sorter, setSorter] = useState("none");
  const [points, setPoints] = useState({});
  const [category, setCategory] = useState({});
  const { addToast } = useToasts();

  useEffect(() => {
    async function fetchPoints() {
      if (getIdFromToken() == null) return;
      const request = await axios.get(
        "http://localhost:8080/api/patients/" + getIdFromToken() + "/points"
      );
      setPoints(request.data);
      return request;
    }
    fetchPoints();
  }, []);

  useEffect(() => {
    async function fetchCategory() {
      if (getIdFromToken() == null) return;
      const request = await axios.get(
        "http://localhost:8080/api/ranking-category/points/" + points
      );
      setCategory(request.data);

      return request;
    }
    fetchCategory();
  }, [points]);

  useEffect(() => {
    if (pharmacyId != undefined) {
      async function fetchAppointsments() {
        const request = await axios.get(
          `http://localhost:8080/api/appointment/bypharmacyid/${pharmacyId}`
        );
        setAppointsments(request.data);

        return request;
      }
      fetchAppointsments();
    }
  }, [pharmacyId, reload]);

  useEffect(() => {
    let maxNumber = Math.floor(appointsments?.length / 12) - 1;
    if (appointsments?.length / 12 - 1 > maxNumber) {
      maxNumber = maxNumber + 1;
    }
    setMaxPag(maxNumber);
  }, [appointsments, reload]);

  useEffect(() => {
    let first = pagNumber * 12;
    let max =
      appointsments.length < first + 12 ? appointsments?.length : first + 12;
    setShowedAppointsments(appointsments?.slice(first, max));
  }, [appointsments, pagNumber, reload]);

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

  const reserveAppointment = (a) => {
    axios
      .post(
        "http://localhost:8080/api/appointment/reserve/" +
          a.id +
          "/patient/" +
          getIdFromToken()
      )
      .then((res) => {
        addToast(res.data, { appearance: "success" });
        setReload(!reload);
      })
      .catch((err) => {
        addToast(
          err.response.data.message == undefined
            ? err.response.data
            : err.response.data.message,
          {
            appearance: "error",
          }
        );
        setReload(!reload);
      });
  };

  const formSearch = (event) => {
    event.preventDefault();

    if (sorter === "none") return;

    let search_params = new URLSearchParams();

    if (sorter === "ascPrice") {
      search_params.append("sort", "price,asc");
    }
    if (sorter === "descPrice") {
      search_params.append("sort", "price,desc");
    }
    if (sorter === "ascGrade") {
      search_params.append("sort", "worker.avgGrade,asc");
    }
    if (sorter === "descGrade") {
      search_params.append("sort", "worker.avgGrade,desc");
    }

    axios
      .get(
        `http://localhost:8080/api/appointment/bypharmacyid/${pharmacyId}/sort`,
        {
          params: search_params,
        }
      )
      .then((resp) => setAppointsments(resp.data))
      .catch(setAppointsments([]));
  };

  const updateSorting = (event) => {
    setSorter(event.target.value);
  };

  return (
    <Tab.Pane eventKey="third">
      <Container fluid>
        <Row
          className="justify-content-center m-3"
          style={{ display: appointsments.length == 0 ? "none" : "flex" }}
        >
          <Form onSubmit={formSearch}>
            <Form.Group as={Row} className="align-items-center">
              <Col>
                <Form.Label>Choose sorter: </Form.Label>
                <Form.Control
                  as="select"
                  value={sorter}
                  onChange={updateSorting.bind(this)}
                  name="sorter"
                >
                  <option value="none">none</option>
                  <option value="ascPrice">Price (ascending)</option>
                  <option value="descPrice">Price (descending)</option>
                  <option value="ascGrade">
                    Dermatologist grade (ascending)
                  </option>
                  <option value="descGrade">
                    Dermatologist grade (descending)
                  </option>
                </Form.Control>
              </Col>
              <Col className="justify-content-center">
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
          {showedAppointsments &&
            showedAppointsments.map((appointsment, index) => (
              <Col className="my__flex" key={index} lg={3} md={6} sm={12}>
                <Card className="my__card" style={{ width: "18rem" }}>
                  <Card.Body>
                    <Card.Title>{appointsment?.appointmentType}</Card.Title>
                    <Card.Text>
                      {moment(appointsment.startTime).format("DD MMM YYYY")}
                    </Card.Text>
                    <Card.Text>
                      {moment(appointsment.startTime).format("hh:mm a")} -{" "}
                      {moment(appointsment.endTime).format("hh:mm a")}
                    </Card.Text>
                  </Card.Body>
                  <ListGroup className="list-group-flush">
                    <ListGroupItem
                      className="my__flex"
                      style={{
                        display:
                          category === "" || Object.keys(category).length === 0
                            ? "flex"
                            : "none",
                      }}
                    >
                      {appointsment.price}
                    </ListGroupItem>
                    <ListGroupItem
                      className="my__flex"
                      style={{
                        display:
                          category !== "" && Object.keys(category).length !== 0
                            ? "flex"
                            : "none",
                      }}
                    >
                      <span style={{ textDecoration: "line-through" }}>
                        {appointsment.price}
                      </span>
                      {"   ->   "}
                      {(appointsment.price * (100 - category.discount)) / 100}
                    </ListGroupItem>
                    <ListGroupItem className="my__flex">
                      {appointsment?.worker?.lastName}{" "}
                      {appointsment?.worker?.firstName}
                    </ListGroupItem>
                    <ListGroupItem className="my__flex">
                      <Button
                        variant="secondary"
                        onClick={() => reserveAppointment(appointsment)}
                        style={{
                          display:
                            getUserTypeFromToken() === "PATIENT"
                              ? "block"
                              : "none",
                        }}
                      >
                        Reserve
                      </Button>
                    </ListGroupItem>
                  </ListGroup>
                </Card>
              </Col>
            ))}
        </Row>

        <Row
          className="my__row__pagination"
          style={{ display: appointsments.length == 0 ? "none" : "flex" }}
        >
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
        <h3
          style={{
            display: appointsments.length == 0 ? "block" : "none",
            textAlign: "center",
            marginTop: "50px",
          }}
        >
          There's no empty checkups!
        </h3>
      </Container>
    </Tab.Pane>
  );
}

export default AppointmentView;
