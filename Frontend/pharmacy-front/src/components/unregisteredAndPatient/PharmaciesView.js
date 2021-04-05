import React, { useState, useEffect } from "react";
import {
  Tab,
  Row,
  Col,
  Container,
  Card,
  Accordion,
  ListGroup,
  ListGroupItem,
  Pagination,
  Nav,
  Form,
  Button,
} from "react-bootstrap";
import { StarFill, Search, Reply } from "react-bootstrap-icons";

import axios from "axios";

import "../../styling/pharmaciesAndMedicines.css";

function PharmaciesView() {
  const [pharmacies, setPharmacies] = useState([]);
  const [pagNumber, setPugNummber] = useState(0);
  const [maxPag, setMaxPag] = useState(0);
  const [showedPharmacies, setShowedPharmacies] = useState([]);
  const [fsearch, setFSearch] = useState("");
  const [filterGrade, setFilterGrade] = useState("");
  const [filterDistance, setFilterDistance] = useState("");

  useEffect(() => {
    async function fetchPharmacies() {
      const request = await axios.get("http://localhost:8080/api/pharmacy/");
      setPharmacies(request.data);

      return request;
    }
    fetchPharmacies();
  }, []);

  const formSearch = (event) => {
    event.preventDefault();

    if (fsearch.length === 0) {
      axios
        .get("http://localhost:8080/api/pharmacy/")
        .then((resp) => setPharmacies(resp.data));
    } else {
      axios
        .get("http://localhost:8080/api/pharmacy/search", {
          params: { searchValue: fsearch },
        })
        .then((resp) => setPharmacies(resp.data));
    }
  };

  const formFilter = (event) => {
    event.preventDefault();

    if (filterGrade === "" && filterDistance === "") return;

    axios.get("https://api.ipify.org?format=json").then((res) => {
      axios
        .get(
          "https://api.ipgeolocation.io/ipgeo?apiKey=4939eda48b984abfbb45b4b69f1b6923&ip=" +
            res.data.ip
        )
        .then((res) => {
          axios
            .get("http://localhost:8080/api/pharmacy/filter", {
              params: {
                gradeValue: filterGrade,
                distanceValue: filterDistance,
                longitude: res.data.longitude,
                latitude: res.data.latitude,
              },
            })
            .then((resp) => setPharmacies(resp.data));
        });
    });
  };

  const resetSearch = function () {
    axios
      .get("http://localhost:8080/api/pharmacy/")
      .then((resp) => setPharmacies(resp.data))
      .catch(() => setPharmacies([]));
    setFSearch("");
  };

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

  return (
    <Tab.Pane eventKey="first">
      <Container fluid>
        <Row className="justify-content-center mt-5 mb-0">
          <Form onSubmit={formSearch} style={{ width: "100%" }}>
            <Form.Group
              as={Row}
              className="justify-content-center align-items-center"
            >
              <Col lg={6}>
                <Form.Control
                  type="text"
                  name="searchValue"
                  value={fsearch}
                  onChange={(e) => setFSearch(e.target.value)}
                  placeholder="Enter name or place..."
                />
              </Col>

              <Col className="justify-content-center" lg={2}>
                <Button type="submit" className="my__search__buttons">
                  <Search />
                </Button>
                <Button className="my__search__buttons" onClick={resetSearch}>
                  Reset <Reply />
                </Button>
              </Col>
            </Form.Group>
          </Form>
        </Row>
        <Row>
          <Accordion style={{ width: "100%" }}>
            <Accordion.Toggle
              as={Button}
              className="my__search__buttons"
              eventKey="0"
            >
              Filter
            </Accordion.Toggle>
            <Accordion.Collapse eventKey="0">
              <div>
                <Form onSubmit={formFilter} className="my__div__filter">
                  <Form.Group controlId="gradeSelect">
                    <Form.Label>Grade</Form.Label>
                    <Form.Control
                      as="select"
                      onChange={(event) => setFilterGrade(event.target.value)}
                      defaultValue=""
                    >
                      <option value="">Not Selected</option>
                      <option value="LOW">Low</option>
                      <option value="MEDIUM">Medium</option>
                      <option value="HIGH">High</option>
                    </Form.Control>
                  </Form.Group>
                  <Form.Group controlId="distanceSelect">
                    <Form.Label>Distance</Form.Label>
                    <Form.Control
                      as="select"
                      onChange={(event) =>
                        setFilterDistance(event.target.value)
                      }
                      defaultValue={""}
                    >
                      <option value={""}>Not Selected</option>
                      <option value="5LESS">5km(Less)</option>
                      <option value="10LESS">10km(Less)</option>
                      <option value="10HIGHER">10km(Higher)</option>
                    </Form.Control>
                  </Form.Group>
                  <Button type="submit" className="my__search__buttons">
                    <Search />
                  </Button>
                </Form>
              </div>
            </Accordion.Collapse>
          </Accordion>
        </Row>
        <Row>
          {showedPharmacies.length === 0 && (
            <Row className="justify-content-center m-3 align-items-center">
              <h3>No result!</h3>
            </Row>
          )}
          {showedPharmacies &&
            showedPharmacies.map((pharmacy, index) => {
              return (
                <Col className="my__flex" key={index} lg={3} md={6} sm={12}>
                  <Nav.Link
                    className="my__nav__link__card"
                    href={`/pharmacy/${pharmacy.id}`}
                  >
                    <Card className="my__card" style={{ width: "18rem" }}>
                      <Card.Body>
                        <Card.Title>{pharmacy.name}</Card.Title>
                        <Card.Text>{pharmacy.description}</Card.Text>
                      </Card.Body>
                      <ListGroup className="list-group-flush">
                        <ListGroupItem className="my__flex">
                          {[...Array(Math.ceil(pharmacy.avgGrade))].map(() => (
                            <StarFill className="my__star" />
                          ))}
                        </ListGroupItem>
                        <ListGroupItem className="my__flex">
                          {pharmacy.address.street}
                        </ListGroupItem>
                      </ListGroup>
                    </Card>
                  </Nav.Link>
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
      </Container>
    </Tab.Pane>
  );
}

export default PharmaciesView;
