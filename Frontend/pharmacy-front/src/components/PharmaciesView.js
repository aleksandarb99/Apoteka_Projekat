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
  Nav,
} from "react-bootstrap";
import { StarFill } from "react-bootstrap-icons";

import axios from "axios";

import "./../styling/pharmaciesAndMedicines.css";

function PharmaciesView() {
  const [pharmacies, setPharmacies] = useState([]);
  const [pagNumber, setPugNummber] = useState(0);
  const [maxPag, setMaxPag] = useState(0);
  const [showedPharmacies, setShowedPharmacies] = useState([]);

  useEffect(() => {
    async function fetchPharmacies() {
      const request = await axios.get("http://localhost:8080/api/pharmacy/");
      setPharmacies(request.data);

      return request;
    }
    fetchPharmacies();
  }, []);

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
        <Row>
          {showedPharmacies &&
            showedPharmacies.map((pharmacy, index) => (
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
            ))}
          <Col className="my__flex" lg={3} md={6} sm={12}>
            <Card className="my__card" style={{ width: "18rem" }}>
              <Card.Body>
                <Card.Title>Card Title</Card.Title>
                <Card.Text>
                  Some quick example text to build on the card title and make up
                  the bulk of the card's content.
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>
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
