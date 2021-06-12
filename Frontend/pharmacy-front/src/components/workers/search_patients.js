import React, { useState, useEffect } from "react";
import {
  Row,
  Form,
  Button,
  Container,
  Col,
  Card,
  ButtonGroup,
} from "react-bootstrap";
import AppointmentsModal from "./appointments_modal";
import "bootstrap/dist/css/bootstrap.min.css";
import api from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";
import { useToasts } from "react-toast-notifications";
import "../../styling/worker.css";

function SearchPatPage() {
  const [patients, setPatients] = useState([]);
  const [fName, setFName] = useState("");
  const [lName, setLName] = useState("");
  const [patient, setPatient] = useState({}); //appointments of currently picked patient
  const [showModal, setShowModal] = useState(false);
  const [currFetchState, setCurrFetchState] = useState("Loading...");
  const { addToast } = useToasts();

  useEffect(() => {
    async function fetchPatients() {
      await api
        .get("/api/patients/all")
        .then((resp) => setPatients(resp.data))
        .catch(() => setPatients([]));
    }
    fetchPatients();
  }, []);

  const formSearch = (event) => {
    event.preventDefault();
    setCurrFetchState("Loading...");
    if (fName.length === 0 && lName.length === 0) {
      api
        .get("/api/patients/all")
        .then((resp) => {
          setPatients(resp.data);
          if (resp.data.length === 0) {
            setCurrFetchState("No result!");
          }
        })
        .catch(() => {
          setPatients([]);
          setCurrFetchState("No result!");
        });
    } else {
      api
        .get("/api/patients/search", {
          params: { firstName: fName, lastName: lName },
        })
        .then((resp) => {
          setPatients(resp.data);
          if (resp.data.length === 0) {
            setCurrFetchState("No result!");
          }
        })
        .catch(() => {
          setPatients([]);
          setCurrFetchState("No result!");
        });
    }
  };

  const resetSearch = function () {
    setCurrFetchState("Loading...");
    api
      .get("/api/patients/all")
      .then((resp) => {
        setPatients(resp.data);
        if (resp.data.length === 0) {
          setCurrFetchState("No result!");
        }
      })
      .catch(() => setPatients([]));
    setFName("");
    setLName("");
  };

  const onShowAppointmentsButton = function (pat_to_show) {
    let id = getIdFromToken();
    if (!id) {
      addToast("Token error!", { appearance: "error" });
      setPatients([]);
      return;
    }
    setPatient({
      patient: pat_to_show?.email,
      worker: id,
      patientName: pat_to_show?.firstName + " " + pat_to_show?.lastName,
    });
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
    setPatient({});
  };

  return (
    <div className="my__container" style={{ minHeight: "100vh" }}>
      <Row className="justify-content-center pt-5 pb-3 pl-3 pr-3 align-items-center">
        <h4 className="my_content_header">Search for registered patients</h4>
      </Row>

      <Row className="justify-content-center m-3">
        <Form onSubmit={formSearch}>
          <Form.Group as={Row} className="align-items-center search_field">
            <Form.Label> First name: </Form.Label>
            <Col>
              <Form.Control
                type="text"
                name="firstName"
                value={fName}
                onChange={(e) => setFName(e.target.value)}
                placeholder="Enter first name..."
              />
            </Col>

            <Form.Label> Last name: </Form.Label>
            <Col>
              <Form.Control
                type="text"
                name="lastName"
                value={lName}
                onChange={(e) => setLName(e.target.value)}
                placeholder="Enter last name..."
              />
            </Col>

            <Col>
              <ButtonGroup size="sm">
                <Button type="submit" variant="primary">
                  {" "}
                  Search{" "}
                </Button>
                <Button variant="primary" onClick={resetSearch}>
                  {" "}
                  Reset search{" "}
                </Button>
              </ButtonGroup>
            </Col>
          </Form.Group>
        </Form>
      </Row>
      <Row className="justify-content-center mt-3 ml-3 mr-3 pb-3">
        <Col md={6}>
          {patients.length === 0 && (
            <Row className="justify-content-center m-3 align-items-center">
              <h3>{currFetchState}</h3>
            </Row>
          )}

          {patients.map((value, index) => {
            console.log(value);
            return (
              <Row
                className="justify-content-center m-5 align-items-center"
                key={index}
              >
                <Col>
                  <Card fluid className="card_appt_home">
                    <Card.Body>
                      <Card.Title>
                        {value.firstName + " " + value.lastName}{" "}
                      </Card.Title>
                      <Card.Footer className="justify-content-right">
                        <Row className="justify-content-center align-items-center">
                          <Button
                            variant="secondary"
                            onClick={() => onShowAppointmentsButton(value)}
                          >
                            Upcomming appointments with{" "}
                            {value.firstName + " " + value.lastName}
                          </Button>
                        </Row>
                      </Card.Footer>
                    </Card.Body>
                  </Card>
                </Col>
              </Row>
            );
          })}
        </Col>
      </Row>
      <AppointmentsModal
        show={showModal}
        info={patient}
        onCloseModal={closeModal}
      ></AppointmentsModal>
    </div>
  );
}

export default SearchPatPage;
