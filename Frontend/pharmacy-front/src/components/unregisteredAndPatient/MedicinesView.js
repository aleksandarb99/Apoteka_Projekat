import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

import {
  Tab,
  Row,
  Col,
  Container,
  Card,
  ListGroup,
  ListGroupItem,
  Nav,
  Pagination,
} from "react-bootstrap";
import { StarFill } from "react-bootstrap-icons";

import axios from "../../app/api";

import "../../styling/pharmaciesAndMedicines.css";
import MedicineSearchAndFilter from "./MedicineSearchAndFilter";

function MedicinesView() {
  const [medicines, setMedicines] = useState([]);
  const [pagNumber, setPugNummber] = useState(0);
  const [maxPag, setMaxPag] = useState(0);
  const [showedMedicines, setShowedMedicines] = useState([]);
  const [searchParams, setSearchParams] = useState("");

  useEffect(() => {
    async function fetchMedicines() {
      const request = await axios.get("/api/medicine/");
      setMedicines(request.data);

      return request;
    }
    fetchMedicines();
  }, []);

  useEffect(() => {
    async function fetchMedicines() {
      const request = await axios.get(`/api/medicine?search=${searchParams}`);
      setMedicines(request.data);

      return request;
    }
    if (!!searchParams) {
      fetchMedicines();
    }
  }, [searchParams]);

  function updateSearchParams(newParams) {
    setSearchParams(newParams);
  }

  useEffect(() => {
    let maxNumber = Math.floor(medicines?.length / 12) - 1;
    if (medicines?.length / 12 - 1 > maxNumber) {
      maxNumber = maxNumber + 1;
    }
    setMaxPag(maxNumber);
  }, [medicines]);

  useEffect(() => {
    let first = pagNumber * 12;
    let max = medicines.length < first + 12 ? medicines?.length : first + 12;
    setShowedMedicines(medicines?.slice(first, max));
  }, [medicines, pagNumber]);

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
    <Tab.Pane eventKey="third">
      <Container fluid>
        <MedicineSearchAndFilter
          updateParams={(newParams) => {
            updateSearchParams(newParams);
          }}
        ></MedicineSearchAndFilter>
        <Row>
          {showedMedicines &&
            showedMedicines.map((medicine, index) => (
              <Col className="my__flex" key={index} lg={3} md={6} sm={12}>
                <Nav.Link
                  as={Link}
                  className="my__nav__link__card"
                  to={`/medicine/${medicine.id}/pharmacy/-1/price/-1`}
                >
                  <Card className="my__card" style={{ width: "18rem" }}>
                    <Card.Body>
                      <Card.Title>{medicine.name}</Card.Title>
                      <Card.Text>#{medicine.code}</Card.Text>
                      <Card.Text>{medicine.content}</Card.Text>
                    </Card.Body>
                    <ListGroup className="list-group-flush">
                      <ListGroupItem className="my__flex">
                        {[...Array(Math.ceil(medicine.avgGrade))].map(() => (
                          <StarFill className="my__star" />
                        ))}
                      </ListGroupItem>
                    </ListGroup>
                  </Card>
                </Nav.Link>
              </Col>
            ))}
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

export default MedicinesView;
