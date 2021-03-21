import React from "react";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

import Map from "ol/Map";
import OSM from "ol/source/OSM";
import TileLayer from "ol/layer/Tile";
import View from "ol/View";
import { fromLonLat } from "ol/proj";

import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import ListGroup from "react-bootstrap/ListGroup";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import Pagination from "react-bootstrap/Pagination";

import MedicineComponent from "./MedicineComponent";

import "../styling/pharmacy.css";

function PharmacyProfile() {
  const [details, setPharmacyDetails] = useState({});
  const [pagNumber, setPugNummber] = useState(0);
  const [maxPag, setMaxPag] = useState(0);
  const [showedAppointment, setShowedAppointment] = useState([]);

  let { id } = useParams();

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

  useEffect(() => {
    return calculateMaxPag();
  }, [details]);

  let calculateMaxPag = () => {
    let maxNumber = Math.floor(details?.appointments?.length / 4) - 1;
    if (details?.appointments?.length / 4 - 1 > maxNumber) {
      maxNumber = maxNumber + 1;
    }
    setMaxPag(maxNumber);
  };

  useEffect(() => {
    return new Map({
      target: "mapCol",
      layers: [
        new TileLayer({
          source: new OSM(),
        }),
      ],

      view: new View({
        center: fromLonLat([
          details?.location?.longitude,
          details?.location?.latitude,
        ]),
        zoom: 10,
        minZoom: 5,
        maxZoom: 12,
      }),
    });
  }, []);

  useEffect(() => {
    let first = pagNumber * 4;
    let max =
      details?.appointments?.length < first + 4
        ? details?.appointments?.length
        : first + 4;
    setShowedAppointment(details?.appointments?.slice(first, max));
  }, [details, pagNumber]);

  useEffect(() => {
    async function fetchPharmacy() {
      const request = await axios.get(
        `http://localhost:8080/api/pharmacy/${id}`
      );
      setPharmacyDetails(request.data);
      return request;
    }
    fetchPharmacy();
  }, [id]);

  return (
    <main>
      <Container fluid>
        <Row>
          <Col>
            <h1>{details?.name}</h1>
            <h5>Description: {details?.description}</h5>
            <h5>
              Address: {details?.location?.street},{details?.location?.city},
              {details?.location?.country}
            </h5>{" "}
            <h5>Avarage rating: {details?.avgGrade}</h5>
            <h5>Pharmacy workers:</h5>
            <ListGroup>
              {details?.workplaces?.map((worker) => (
                <ListGroup.Item>
                  {worker.dermatologist == null
                    ? worker.pharmacist.firstName
                    : worker.dermatologist.firstName}
                  {worker.dermatologist == null
                    ? worker.pharmacist.lastName
                    : worker.dermatologist.lastName}
                  {worker.dermatologist == null
                    ? worker.pharmacist.email
                    : worker.dermatologist.email}
                </ListGroup.Item>
              ))}
            </ListGroup>
          </Col>
          <Col id="mapCol"></Col>
        </Row>
      </Container>
      <div className="row2">
        <p>Medicines</p>
        <div className="card_row">
          {details?.priceList?.medicineItems.map((medicine) => (
            <MedicineComponent
              key={medicine.id}
              name={medicine.medicine.name}
              code={medicine.medicine.code}
              content={medicine.medicine.content}
              avgGrade={medicine.medicine.avgGrade}
            />
          ))}
        </div>
      </div>
      <div className="row2">
        <p>Free appointments</p>
        <Row>
          {showedAppointment &&
            showedAppointment.map((a) => (
              <Col lg={3} md={4} sm={6}>
                <Card>
                  <Card.Header as="h5">Appointment</Card.Header>
                  <Card.Body>
                    <Card.Title>{a?.appointmentType}</Card.Title>
                    <Card.Text>
                      Time: {a?.startTime} - {a?.endTime}
                    </Card.Text>
                    {a?.appointmentType === "CHECKUP" ? (
                      <Card.Text>
                        Dermatologist: {a?.worker?.firstName}{" "}
                        {a?.worker?.lastName}
                      </Card.Text>
                    ) : (
                      <Card.Text>
                        Pharmacist: {a?.worker?.firstName} {a?.worker?.lastName}
                      </Card.Text>
                    )}

                    <Button variant="primary">Reserve</Button>
                  </Card.Body>
                </Card>
              </Col>
            ))}
        </Row>
        <Row>
          <Col>
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
      </div>
    </main>
  );
}

export default PharmacyProfile;
