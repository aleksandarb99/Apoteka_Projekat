import React, { useState, useEffect } from "react";
import { Row, Col, Navbar, Nav, NavDropdown } from "react-bootstrap";
import AppointmentStartModal from "../appointment_start_modal";
import "bootstrap/dist/css/bootstrap.min.css";
import api from "../../../app/api";
import { getUserTypeFromToken } from '../../../app/jwtTokenUtils'
import { getIdFromToken } from '../../../app/jwtTokenUtils'

import { Card } from "react-bootstrap";
import moment from "moment";
import { Link } from "react-router-dom";

function PharmHomePage() {
  const [appointments, setAppointments] = useState([]); 
  const [showModal, setShowModal] = useState(false);
  const [startAppt, setStartAppt] = useState({});

  useEffect(() => {
    //todo hardkodovano
    async function fetchAppointments() {
      let id = getIdFromToken();
      if (!id){
        alert("invalid user!")
        setAppointments([]);
        return;
      }
      await api
        .get(
          "http://localhost:8080/api/appointment/workers_upcoming?id=" + id + "&page=0&size=10"
        )
        .then((resp) => setAppointments(resp.data))
        .catch(() => setAppointments([]));
    }
    fetchAppointments();
  }, []);

  const initiateAppt = (appointment) => {
    if (
      !(moment(Date.now()) > moment(appointment.start).subtract(15, "minutes"))
    ) {
      // nikako ga ne mozemo zapoceti vise od 15 minuta ranije
      alert("You can't initiate this appointment yet!");
      return;
    }
    setStartAppt(appointment);
    setShowModal(true);
  };

  const onCancelMethod = () => {
    setShowModal(false);
    let id_farm = getIdFromToken();
    if (!id_farm){
      alert("invalid user!");
      return;
    }
    api
      .get("http://localhost:8080/api/appointment/workers_upcoming", {
        params: { id: id_farm, page: 0, size: 10 },
      })
      .then((resp) => setAppointments(resp.data))
      .catch(() => setAppointments([])); //resetujemo prikaz
  };

  return (
    <div>
      {/* <Navbar bg="dark" variant="dark">
        <Navbar.Brand href="#home">Dermatologist</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="m-auto">
            <Nav.Link href="#home">Home</Nav.Link>
            <Nav.Link href="#link">Search patients</Nav.Link>
            <Nav.Link href="#link">Work callendar</Nav.Link>
            <Nav.Link href="#link">Examined patients</Nav.Link>
            <Nav.Link href="#link">Request a vacation</Nav.Link>
            <Nav.Link href="#home">Profile</Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Navbar> */}

      <Row className="justify-content-center m-3 align-items-center">
        <h2>Upcomming appointments</h2>
      </Row>

      {appointments.length === 0 && (
        <Row className="justify-content-center m-3 align-items-center">
          <h3>There are no upcomming appointments!</h3>
        </Row>
      )}

      {appointments.map((value, index) => {
        return (
          <Row
            className="justify-content-center m-5 align-items-center"
            key={index}
          >
            <Col md={8}>
              <Card>
                <Card.Body>
                  <Card.Title>
                    {moment(value.start).format("DD MMM YYYY hh:mm a")}{" "}
                  </Card.Title>
                  <Card.Subtitle className="mb-2">
                    Patient: {value.patient}
                  </Card.Subtitle>
                  <Card.Subtitle className="mb-2">
                    Price: {value.price}
                  </Card.Subtitle>
                  <Card.Link
                    as={Link}
                    to="#"
                    onClick={() => initiateAppt(value)}
                  >
                     Appointment
                  </Card.Link>
                </Card.Body>
              </Card>
            </Col>
          </Row>
        );
      })}
      <AppointmentStartModal
        show={showModal}
        onCancelMethod={onCancelMethod}
        appointment={startAppt}
        onHide={() => {
          setShowModal(false);
          setStartAppt({});
        }}
      ></AppointmentStartModal>
    </div>
  );
}

export default PharmHomePage;