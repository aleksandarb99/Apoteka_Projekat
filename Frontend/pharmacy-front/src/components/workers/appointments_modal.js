import React, { useState, useEffect } from "react";
import {
  Button,
  Modal,
  Card,
  Row,
  Col,
  Container,
  ButtonGroup,
} from "react-bootstrap";
import Moment from "react-moment";
import moment from "moment";
import api from "../../app/api";
import { useHistory } from "react-router-dom";
import { getUserTypeFromToken } from "../../app/jwtTokenUtils";
import { useToasts } from "react-toast-notifications";

function AppointmentsModal(props) {
  // id usera, id workera, bool farmaceut, name
  const history = useHistory();
  const { addToast } = useToasts();

  const [appointments, setAppointments] = useState([]);

  const showHandler = () => {
    async function fetchAppointments() {
      let bodyFormData = new FormData();
      bodyFormData.append("patient", props?.info.patient);
      bodyFormData.append("worker", props?.info.worker);
      const request = api
        .post("/api/appointment/byPatWorker", bodyFormData)
        .then((resp) => setAppointments(resp.data))
        .catch(() => setAppointments([]));
      return request;
    }
    fetchAppointments();
  };

  const onStart = (appt) => {
    if (!(moment(Date.now()) > moment(appt.start).subtract(15, "minutes"))) {
      // ne moze da se krene vise od 15 min ranije
      addToast("You can't start this appointment yet!", {
        appearance: "error",
      });
      return;
    }

    let bodyFormData = new FormData();
    bodyFormData.append("id", appt.id);

    api
      .post("/api/appointment/start_appointment", bodyFormData)
      .then(() => {
        history.push({
          pathname: "/worker/appointment_report",
          state: {
            // location state
            appointmentID: appt.id,
          },
        });
      })
      .catch(() =>
        addToast("You can't start this appointment yet!", {
          appearance: "error",
        })
      );
  };

  const onCancelAppointment = () => {
    let bodyFormData = new FormData();
    bodyFormData.append("patient", props?.info.patient);
    bodyFormData.append("worker", props?.info.worker);
    api
      .post("/api/appointment/byPatWorker", bodyFormData)
      .then((resp) => setAppointments(resp.data))
      .catch(() => setAppointments([]));
  };

  const onCancel = (appt) => {
    if (!(moment(Date.now()) > moment(appt.start).add(5, "minutes"))) {
      // ne moze da se cancelluje pre 5 min od pocetka
      addToast("You can't cancel this appointment yet!", {
        appearance: "error",
      });
      return;
    }

    let bodyFormData = new FormData();
    bodyFormData.append("id", appt.id);

    api
      .post("/api/appointment/cancel_appointment", bodyFormData)
      .then(() => {
        addToast("Appointment cancelled! Patient didn't show up!", {
          appearance: "info",
        });
        onCancelAppointment();
      })
      .catch(() =>
        addToast("You can't cancel this appointment yet!", {
          appearance: "error",
        })
      );
  };

  return (
    <Modal
      {...props}
      aria-labelledby="contained-modal-title-vcenter"
      centered
      onShow={showHandler}
      onHide={() => {
        props.onCloseModal();
        setAppointments([]);
      }}
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Your upcomming appointments for {props.info.patientName}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Container>
          {appointments.length === 0 && (
            <Row className="justify-content-center m-3 align-items-center">
              <h3>No result!</h3>
            </Row>
          )}

          {appointments.map((value, index) => {
            return (
              <Row
                className="justify-content-center m-5 align-items-center"
                key={index}
              >
                <Col>
                  <Card fluid>
                    <Card.Body>
                      <Card.Title>
                        Date:{" "}
                        <Moment format="DD/MM/yyyy HH:mm">{value.start}</Moment>
                      </Card.Title>
                      <Card.Text>
                        {getUserTypeFromToken().trim() === "DERMATOLOGIST" && (
                          <div>Pharmacy: {value.pharmacy} </div>
                        )}
                      </Card.Text>
                    </Card.Body>
                    <Card.Footer>
                      <ButtonGroup>
                        <Button onClick={() => onStart(value)}>
                          Start appointment
                        </Button>
                        <Button onClick={() => onCancel(value)}>
                          Patient didn't show up
                        </Button>
                      </ButtonGroup>
                    </Card.Footer>
                  </Card>
                </Col>
              </Row>
            );
          })}
        </Container>
      </Modal.Body>
      <Modal.Footer></Modal.Footer>
    </Modal>
  );
}

export default AppointmentsModal;
