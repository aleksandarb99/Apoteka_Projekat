import React, { useState, useEffect } from "react";
import { Row, Col, Navbar, Nav, NavDropdown } from "react-bootstrap";
import AppointmentStartModal from "../appointment_start_modal";
import "bootstrap/dist/css/bootstrap.min.css";
import api from "../../../app/api";
import { getUserTypeFromToken } from "../../../app/jwtTokenUtils";
import { getIdFromToken } from "../../../app/jwtTokenUtils";
import SetPasswordModal from "../../utilComponents/modals/SetPasswordModal";
import "../../../styling/worker.css";

import { useToasts } from "react-toast-notifications";

import { Card } from "react-bootstrap";
import moment from "moment";
import { Link } from "react-router-dom";

function PharmHomePage() {
  const [appointments, setAppointments] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [startAppt, setStartAppt] = useState({});

  const [loadingPWChanged, setLoadingPWChanged] = useState(true);
  const [showModalPWChange, setShowModalPWChange] = useState(false);

  const [loadingAppts, setLoadingAppts] = useState(true);

  const { addToast } = useToasts();

  useEffect(() => {
    async function fetchAppointments() {
      let id = getIdFromToken();
      if (!id) {
        addToast("Invalid user!", { appearance: "error" });
        setAppointments([]);
        return;
      }
      await api
        .get("/api/appointment/workers_upcoming?id=" + id + "&page=0&size=10")
        .then((resp) => {
          setAppointments(resp.data);
          setLoadingAppts(false);
        })
        .catch(() => {
          setAppointments([]);
          setLoadingAppts(false);
        });
    }
    let id = getIdFromToken();
    api.get("/api/users/" + id).then((res) => {
      if (!res.data.passwordChanged) {
        setShowModalPWChange(true);
        setLoadingPWChanged(false);
      } else {
        setShowModalPWChange(false);
        setLoadingPWChanged(false);
      }
    }).catch(() => { });;
    fetchAppointments();
  }, []);

  const initiateAppt = (appointment) => {
    if (
      !(moment(Date.now()) > moment(appointment.start).subtract(15, "minutes"))
    ) {
      // nikako ga ne mozemo zapoceti vise od 15 minuta ranije
      addToast(
        "You can't initiate appointment until at least 15 minutes before!",
        { appearance: "error" }
      );
      return;
    }
    setStartAppt(appointment);
    setShowModal(true);
  };

  const onCancelMethod = () => {
    setShowModal(false);
    let id_farm = getIdFromToken();
    if (!id_farm) {
      addToast("Invalid user!", { appearance: "error" });
      return;
    }
    api
      .get("/api/appointment/workers_upcoming", {
        params: { id: id_farm, page: 0, size: 10 },
      })
      .then((resp) => setAppointments(resp.data))
      .catch(() => setAppointments([])); //resetujemo prikaz
  };

  return (
    <div className="my__container" style={{ minHeight: "100vh" }}>
      <Row className="justify-content-center pt-5 pb-3 pl-3 pr-3 align-items-center">
        <h2 className="my_content_header">Upcomming appointments</h2>
      </Row>

      {appointments.length === 0 && !loadingAppts && (
        <Row className="justify-content-center m-3 align-items-center">
          <h3>There are no upcomming appointments!</h3>
        </Row>
      )}

      {loadingPWChanged ? (
        <Row className="justify-content-center m-5 align-items-center">
          <h3>Checking user data...</h3>
        </Row>
      ) : (
        <div>
          {appointments.map((value, index) => {
            return (
              <Row
                className="justify-content-center p-4 align-items-center"
                key={index}
              >
                <Col md={7}>
                  <Card className="card_appt_home">
                    <Card.Body>
                      <Card.Title>
                        Appointment date:{" "}
                        {moment(value.start).format("DD MMM YYYY")}
                        <span style={{ float: "right" }}>
                          Time: {moment(value.start).format("hh:mm a")} -{" "}
                          {moment(value.end).format("hh:mm a")}
                        </span>
                      </Card.Title>
                      <hr
                        style={{
                          color: "black",
                          backgroundColor: "black",
                          height: 1,
                        }}
                      />
                      <Card.Text className="mb-2">
                        Patient: {value.patient}
                      </Card.Text>
                      <Card.Text className="mb-2">
                        Price: {value.price}
                      </Card.Text>

                      <Card.Text style={{ textAlign: "center" }}>
                        <Card.Link
                          as={Link}
                          to="#"
                          onClick={() => initiateAppt(value)}
                        >
                          Start appointment
                        </Card.Link>
                      </Card.Text>
                    </Card.Body>
                  </Card>
                </Col>
              </Row>
            );
          })}{" "}
        </div>
      )}

      <AppointmentStartModal
        show={showModal}
        onCancelMethod={onCancelMethod}
        appointment={startAppt}
        onHide={() => {
          setShowModal(false);
          setStartAppt({});
        }}
      ></AppointmentStartModal>

      <SetPasswordModal
        show={showModalPWChange}
        onPasswordSet={() => {
          setShowModalPWChange(false);
        }}
      ></SetPasswordModal>
    </div>
  );
}

export default PharmHomePage;
