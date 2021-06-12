import React, { useState } from "react";
import { Row, Form, Button, Col, Card } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import api from "../../app/api";
import moment from "moment";
import { getIdFromToken } from "../../app/jwtTokenUtils";

import "react-datepicker/dist/react-datepicker.css";

import { useToasts } from "react-toast-notifications";

import "../../styling/worker.css";

function IssueMedicine() {
  const [reservedMedicine, setReservedMedicine] = useState(null);
  const [resID, setResID] = useState(null);
  const { addToast } = useToasts();

  const searchRes = () => {
    let bodyFormData = new FormData();
    let workerID = getIdFromToken();
    if (workerID === null) {
      return;
    }
    bodyFormData.append("workerID", workerID);
    bodyFormData.append("resID", resID);

    api
      .post("/api/medicine-reservation/getReservedIssue", bodyFormData)
      .then((resp) => {
        setReservedMedicine(resp.data);
      })
      .catch((resp) => {
        addToast(resp.response.data.message, { appearance: "error" });
        setReservedMedicine(null);
      });
  };

  const issueMed = () => {
    let bodyFormData = new FormData();
    let workerID = getIdFromToken();
    if (workerID === null) {
      return;
    }
    bodyFormData.append("workerID", workerID);
    bodyFormData.append("resID", resID);

    api
      .post("/api/medicine-reservation/issueMedicine", bodyFormData)
      .then((resp) => {
        addToast(resp.data, { appearance: "success" });
        setReservedMedicine(null);
        setResID("");
      })
      .catch((resp) => {
        addToast(resp.response.data.message, { appearance: "error" });
        setReservedMedicine(null);
      });
  };

  return (
    <div className="my__container" style={{ minHeight: "100vh" }}>
      <div>
        <Row className="justify-content-center pt-5">
          <h4 className="my_content_header">Issue medicine</h4>
        </Row>

        <Row className="justify-content-center align-items-center">
          <Form
            onSubmit={(event) => event.preventDefault()}
            className="search_field"
          >
            <Form.Group
              as={Row}
              className="justify-content-center m-3 align-items-center"
            >
              <Form.Label>Search reservation:</Form.Label>

              <Form.Control
                type="text"
                placeholder="Reservation ID"
                value={resID}
                onChange={(event) => setResID(event.target.value)}
              />
              <Row className="mt-3">
                <Button onClick={() => searchRes()}>Search</Button>
              </Row>
            </Form.Group>
          </Form>
        </Row>
      </div>

      <Row className="justify-content-center p-3">
        {reservedMedicine && (
          <Col md={6}>
            <Card fluid className="card_appt_home">
              <Card.Body>
                <Card.Title>
                  Reservation ID: {reservedMedicine.reservationID}
                </Card.Title>
                <Card.Text>
                  Reservation date:{" "}
                  {moment(reservedMedicine.reservationDate).format(
                    "DD MMM YYYY"
                  )}
                </Card.Text>
                <Card.Text>
                  Reservation pickup due date:{" "}
                  {moment(reservedMedicine.pickupDate).format("DD MMM YYYY")}
                </Card.Text>
                <Card.Text>
                  Medicine name: {reservedMedicine.medicineName}
                </Card.Text>
                <Card.Text>
                  Medicine ID: {reservedMedicine.medicineID}
                </Card.Text>
              </Card.Body>
              <Row className="justify-content-center align-items-center m-3">
                <Button onClick={() => issueMed()}>Issue medicine</Button>
              </Row>
            </Card>
          </Col>
        )}
      </Row>
    </div>
  );
}

export default IssueMedicine;
