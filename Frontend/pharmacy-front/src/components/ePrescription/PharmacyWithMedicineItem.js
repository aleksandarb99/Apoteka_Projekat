import React, { useEffect, useState } from "react";
import { Button, Card, Col, Container, Row } from "react-bootstrap";
import api from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";

const PharmacyWithMedicineItem = ({ pharmacy, doBuy }) => {
  const [points, setPoints] = useState({});
  const [category, setCategory] = useState({});

  useEffect(() => {
    async function fetchPoints() {
      const request = await api.get(
        "/api/patients/" + getIdFromToken() + "/points"
      );
      setPoints(request.data);
      return request;
    }
    fetchPoints();
  }, []);

  useEffect(() => {
    async function fetchCategory() {
      const request = await api.get("/api/ranking-category/points/" + points);
      setCategory(request.data);

      return request;
    }
    fetchCategory();
  }, [points]);

  return (
    <Card style={{ marginBottom: "20px" }}>
      <Card.Header>
        <Container fluid>
          <Row>
            <Col md={{ span: 3, offset: 0 }}>{`${pharmacy.name}`}</Col>
            <Col md={{ span: 4, offset: 5 }}>
              <div style={{ float: "right" }}>
                {`${pharmacy.addressStreet}, ${pharmacy.addressCity}`}
              </div>
            </Col>
          </Row>
        </Container>
      </Card.Header>
      <Card.Body>
        <Container>{`Grade: ${pharmacy.avgGrade}`}</Container>
      </Card.Body>
      <Card.Footer className="align-content-end">
        <Container fluid>
          <Row>
            <Col md={{ span: 3, offset: 0 }}>
              {`Total price: ${pharmacy.totalPrice}`}
            </Col>
            <Col md={{ span: 3, offset: 1 }}>
              {!!category && category.discount != 0
                ? `Total price with discount: ${
                    pharmacy.totalPrice * (1 - category.discount / 100)
                  }`
                : ""}
            </Col>
            <Col md={{ span: 2, offset: 3 }}>
              <Button
                style={{ width: "100%" }}
                onClick={() => {
                  doBuy(pharmacy);
                }}
              >
                Buy
              </Button>
            </Col>
          </Row>
        </Container>
      </Card.Footer>
    </Card>
  );
};

export default PharmacyWithMedicineItem;
