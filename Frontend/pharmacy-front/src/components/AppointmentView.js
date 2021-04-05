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
} from "react-bootstrap";

import axios from "axios";

import "./../styling/pharmaciesAndMedicines.css";

function AppointmentView({ pharmacyId }) {
  const [appointsments, setAppointsments] = useState([]);
  const [pagNumber, setPugNummber] = useState(0);
  const [maxPag, setMaxPag] = useState(0);
  const [showedAppointsments, setShowedAppointsments] = useState([]);

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
  }, [pharmacyId]);

  useEffect(() => {
    let maxNumber = Math.floor(appointsments?.length / 12) - 1;
    if (appointsments?.length / 12 - 1 > maxNumber) {
      maxNumber = maxNumber + 1;
    }
    setMaxPag(maxNumber);
  }, [appointsments]);

  useEffect(() => {
    let first = pagNumber * 12;
    let max =
      appointsments.length < first + 12 ? appointsments?.length : first + 12;
    setShowedAppointsments(appointsments?.slice(first, max));
  }, [appointsments, pagNumber]);

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
        <Row>
          {showedAppointsments &&
            showedAppointsments.map((appointsment, index) => (
              <Col className="my__flex" key={index} lg={3} md={6} sm={12}>
                <Card className="my__card" style={{ width: "18rem" }}>
                  <Card.Body>
                    <Card.Title>{appointsment?.appointmentType}</Card.Title>
                    <Card.Text>
                      {new Date(appointsment?.startTime).getDay()}.{" "}
                      {new Date(appointsment?.startTime).getMonth()}.
                      {new Date(appointsment?.startTime).getFullYear()}
                    </Card.Text>
                    <Card.Text>
                      {new Date(appointsment?.startTime).getHours()}:
                      {new Date(appointsment?.startTime).getMinutes()} -{" "}
                      {new Date(appointsment?.endTime).getHours()} :
                      {new Date(appointsment?.endTime).getMinutes()}
                    </Card.Text>
                  </Card.Body>
                  <ListGroup className="list-group-flush">
                    <ListGroupItem className="my__flex">
                      {appointsment?.worker?.lastName}{" "}
                      {appointsment?.worker?.firstName}
                    </ListGroupItem>
                    <ListGroupItem className="my__flex">
                      <Button variant="secondary">Reserve</Button>
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

export default AppointmentView;
