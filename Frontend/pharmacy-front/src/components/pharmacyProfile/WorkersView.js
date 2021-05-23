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
} from "react-bootstrap";
import { StarFill } from "react-bootstrap-icons";

import axios from "../../app/api";

import "../../styling/pharmaciesAndMedicines.css";

function WorkersView({ pharmacyId }) {
  const [workers, setWorkers] = useState([]);
  const [pagNumber, setPugNummber] = useState(0);
  const [maxPag, setMaxPag] = useState(0);
  const [showedWorkers, setShowedWorkers] = useState([]);

  useEffect(() => {
    if (pharmacyId != undefined) {
      async function fetchWorkers() {
        const request = await axios.get(
          `http://localhost:8080/api/workplace/bypharmacyid/${pharmacyId}`
        );
        setWorkers(request.data);

        return request;
      }
      fetchWorkers();
    }
  }, [pharmacyId]);

  useEffect(() => {
    let maxNumber = Math.floor(workers?.length / 12) - 1;
    if (workers?.length / 12 - 1 > maxNumber) {
      maxNumber = maxNumber + 1;
    }
    setMaxPag(maxNumber);
  }, [workers]);

  useEffect(() => {
    let first = pagNumber * 12;
    let max = workers.length < first + 12 ? workers?.length : first + 12;
    setShowedWorkers(workers?.slice(first, max));
  }, [workers, pagNumber]);

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
    <Tab.Pane eventKey="forth">
      <Container fluid>
        <Row>
          {showedWorkers &&
            showedWorkers.map((worker, index) => (
              <Col className="my__flex" key={index} lg={3} md={6} sm={12}>
                <Card className="my__card" style={{ width: "18rem" }}>
                  <Card.Body>
                    <Card.Title>
                      {worker?.worker?.lastName} {worker?.worker?.firstName}
                    </Card.Title>
                    <Card.Text>{worker?.worker?.email}</Card.Text>
                    <Card.Text>{worker?.worker?.telephone}</Card.Text>
                  </Card.Body>
                  <ListGroup className="list-group-flush">
                    <ListGroupItem className="my__flex">
                      {worker?.worker?.role.name}
                    </ListGroupItem>
                    <ListGroupItem className="my__flex">
                      {worker &&
                        [...Array(Math.ceil(worker?.worker?.avgGrade))].map(
                          () => <StarFill className="my__star" />
                        )}
                    </ListGroupItem>
                  </ListGroup>
                </Card>
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

export default WorkersView;
