import React, { useState, useEffect } from "react";

import "react-datepicker/dist/react-datepicker.css";
import DatePicker from "react-datepicker";
import TimePicker from "react-time-picker";

import axios from "axios";

import Moment from "react-moment";

import { Tab, Row, Col, Table, Form } from "react-bootstrap";

function AddAppointment({ idOfPharmacy }) {
  const [dermatologists, setDermatologists] = useState([]);
  const [appointments, setAppointments] = useState([]);
  const [startDate, setStartDate] = useState(new Date());
  const [startHour, setStartHour] = useState("10:00");
  const [dermatogistPicked, setDermatogistPicked] = useState(0);

  useEffect(() => {
    if (idOfPharmacy != undefined) {
      async function fetchDermatologists() {
        const request = await axios.get(
          `http://localhost:8080/api/workplace/dermatologists/bypharmacyid/${idOfPharmacy}`
        );
        setDermatologists(request.data);
        return request;
      }
      fetchDermatologists();
    }
  }, [idOfPharmacy]);

  useEffect(() => {
    if (dermatogistPicked != 0) {
      async function fetchAppointments() {
        const request = await axios.get(
          `http://localhost:8080/api/appointment/all/bydermatologistid/${dermatogistPicked}`,
          { params: { date: startDate.getTime() } }
        );
        setAppointments(request.data);
        return request;
      }
      fetchAppointments();
    } else {
      setAppointments([]);
    }
  }, [startDate, dermatogistPicked]);

  return (
    <Tab.Pane eventKey="fifth">
      <h1 className="content-header">Add appointment</h1>
      <hr></hr>
      <Row>
        <Col lg={4} md={4} sm={12}>
          <Form.Group controlId="dermatologistSelect">
            <Form.Label>Dermatologist</Form.Label>
            <Form.Control
              as="select"
              onChange={(event) => setDermatogistPicked(event.target.value)}
              defaultValue="0"
            >
              <option value="0">Not Selected</option>
              {dermatologists != [] &&
                dermatologists?.map((dermatologist, index) => (
                  <option value={dermatologist.worker.id}>
                    {dermatologist.worker.lastName}{" "}
                    {dermatologist.worker.firstName}
                  </option>
                ))}
            </Form.Control>
          </Form.Group>

          <DatePicker
            disabled={dermatogistPicked == 0}
            selected={startDate}
            onChange={(date) => setStartDate(date)}
          />

          <TimePicker
            disabled={dermatogistPicked == 0}
            onChange={setStartHour}
            value={startHour}
          />
        </Col>
        <Col>
          <h3>Appointments on that date</h3>
          {appointments != [] && (
            <Table striped bordered>
              <thead>
                <tr>
                  <th>#</th>
                  <th>Start time</th>
                  <th>End time</th>
                  <th>State</th>
                </tr>
              </thead>
              <tbody>
                {appointments != [] &&
                  appointments.map((appointment, index) => (
                    <tr>
                      <td>{index + 1}</td>
                      <td>
                        <Moment format="hh:mm" unix>
                          {appointment.startTime}
                        </Moment>
                      </td>
                      <td>
                        <Moment format="hh:mm" unix>
                          {appointment.endTime}
                        </Moment>
                      </td>
                      <td>{appointment.appointmentState}</td>
                    </tr>
                  ))}
              </tbody>
            </Table>
          )}
        </Col>
      </Row>
    </Tab.Pane>
  );
}

export default AddAppointment;
