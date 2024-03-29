import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

import {
  Tab,
  Row,
  Col,
  Container,
  Card,
  Nav,
  ListGroup,
  ListGroupItem,
  Pagination,
} from "react-bootstrap";

import api from "../../app/api";

import { StarFill } from "react-bootstrap-icons";

import "../../styling/pharmaciesAndMedicines.css";

import { useToasts } from "react-toast-notifications";
import { getErrorMessage } from "../../app/errorHandler";

function MedicinesView({ priceListId, pharmacyId }) {
  const { addToast } = useToasts();

  const [medicines, setMedicines] = useState([]);
  const [pagNumber, setPugNummber] = useState(0);
  const [maxPag, setMaxPag] = useState(0);
  const [showedMedicines, setShowedMedicines] = useState([]);

  useEffect(() => {
    if (priceListId != undefined) {
      async function fetchPriceList() {
        const request = await api
          .get(`/api/pricelist/${priceListId}`)
          .then((res) => {
            setMedicines(res.data.medicineItems);
          })
          .catch((err) => {
            addToast(getErrorMessage(err), {
              appearance: "error",
            });
          });
        return request;
      }
      fetchPriceList();
    }
  }, [priceListId]);

  useEffect(() => {
    if (medicines.length != 0) {
      let maxNumber = Math.floor(medicines?.length / 12) - 1;
      if (medicines?.length / 12 - 1 > maxNumber) {
        maxNumber = maxNumber + 1;
      }
      setMaxPag(maxNumber);
    }
  }, [medicines]);

  useEffect(() => {
    let first = pagNumber * 12;
    let max = medicines?.length < first + 12 ? medicines?.length : first + 12;
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
    <Tab.Pane eventKey="second">
      <Container fluid>
        <Row>
          {showedMedicines &&
            showedMedicines.map((medicine, index) => (
              <Col className="my__flex" key={index} lg={3} md={6} sm={12}>
                <Nav.Link
                  as={Link}
                  className="my__nav__link__card"
                  to={`/medicine/${medicine?.medicine?.id}/pharmacy/${pharmacyId}/price/${medicine?.price2}`}
                >
                  <Card className="my__card" style={{ width: "18rem" }}>
                    <Card.Body>
                      <Card.Title>{medicine?.medicine?.name}</Card.Title>
                      <Card.Text>#{medicine?.medicine?.code}</Card.Text>
                      <Card.Text>{medicine?.medicine?.content}</Card.Text>
                    </Card.Body>
                    <ListGroup className="list-group-flush">
                      <ListGroupItem className="my__flex">
                        {medicine?.price2}$
                      </ListGroupItem>
                      <ListGroupItem className="my__flex">
                        {medicines &&
                          [
                            ...Array(Math.ceil(medicine?.medicine?.avgGrade)),
                          ].map(() => <StarFill className="my__star" />)}
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
