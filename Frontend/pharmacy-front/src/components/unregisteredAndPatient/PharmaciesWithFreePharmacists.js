import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import {
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

import axios from "../../app/api";

import "../../styling/pharmaciesAndMedicines.css";

function PharmaciesWithFreePharmacists() {
  const [pharmacies, setPharmacies] = useState([]);
  const [pagNumber, setPugNummber] = useState(0);
  const [maxPag, setMaxPag] = useState(0);
  const [showedPharmacies, setShowedPharmacies] = useState([]);

  useEffect(() => {
    async function fetchPharmacies() {
      let search_params = new URLSearchParams();
      search_params.append("date", 1619699400000);
      const request = await axios.get(
        "http://localhost:8080/api/pharmacy/all/free-pharmacists/",
        { params: search_params }
      );
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
                <Nav.Link
                  as={Link}
                  className="my__nav__link__card"
                  to={`/pharmacy/${pharmacy.id}`}
                >
                  <Card className="my__card" style={{ width: "18rem" }}>
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
  );
}

export default PharmaciesWithFreePharmacists;
