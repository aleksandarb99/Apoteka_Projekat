import React, { useState, useEffect } from "react";

import axios from "axios";

import Moment from "react-moment";

import { Button, Tab, Row, Col, Table, Form } from "react-bootstrap";

function AddAppointment({ idOfPharmacy }) {
  const [dermatologists, setDermatologists] = useState([]);
  const [appointments, setAppointments] = useState([]);
  const [startDate, setStartDate] = useState(new Date());
  const [startHour, setStartHour] = useState("10:00");
  const [dermatogistPicked, setDermatogistPicked] = useState(0);
  const [duration, setDuration] = useState(15);
  const [price, setPrice] = useState(2000);
  const [workDaysLabel, setWorkDaysLabel] = useState("");

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

  let changeDate = (date) => {
    let array = date.split("-");
    let d = new Date(
      Number.parseInt(array[0]),
      Number.parseInt(array[1]) - 1,
      Number.parseInt(array[2])
    );
    setStartDate(d);
  };

  useEffect(() => {
    if (dermatologists !== []) {
      let i;
      let flag = false;
      for (i = 0; i < dermatologists.length; i++) {
        if (dermatologists[i].worker.id == dermatogistPicked) {
          let j;
          for (j = 0; j < dermatologists[i].workDays.length; j++) {
            if (
              !dermatologists[i].workDays[j].weekday.localeCompare(
                startDate.toString().substring(0, 3).toUpperCase()
              )
            ) {
              setWorkDaysLabel(
                `Workshedule : ${dermatologists[i].workDays[j].startTime} - ${dermatologists[i].workDays[j].endTime}`
              );
              flag = true;
            }
          }
        }
      }
      if (!flag) {
        setWorkDaysLabel("");
      }
    }
  }, [dermatogistPicked, startDate]);

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
                dermatologists?.map((dermatologist) => (
                  <option value={dermatologist.worker.id}>
                    {dermatologist.worker.lastName}{" "}
                    {dermatologist.worker.firstName}
                  </option>
                ))}
            </Form.Control>
          </Form.Group>

          <Form.Group controlId="datePicker" di>
            <Form.Label>Date</Form.Label>
            <Form.Control
              type="date"
              disabled={dermatogistPicked == 0}
              onChange={(event) => changeDate(event.target.value)}
            />
          </Form.Group>

          <Form.Group controlId="timePicker" di>
            <Form.Label>Start time</Form.Label>
            <Form.Control
              type="time"
              value={startHour}
              disabled={dermatogistPicked == 0}
              onChange={(event) => setStartHour(event.target.value)}
            />
          </Form.Group>

          <Form.Group controlId="durationPicker" di>
            <Form.Label>Duration</Form.Label>
            <Form.Control
              type="number"
              value={duration}
              disabled={dermatogistPicked == 0}
              onChange={(event) => setDuration(event.target.value)}
              min="0"
            />
          </Form.Group>

          <Form.Group controlId="pricePicker" di>
            <Form.Label>Price</Form.Label>
            <Form.Control
              type="number"
              value={price}
              disabled={dermatogistPicked == 0}
              onChange={(event) => setPrice(event.target.value)}
              min="0"
            />
          </Form.Group>

          <Button variant="primary" type="submit">
            Submit
          </Button>
        </Col>
        <Col>
          <h3>{workDaysLabel}</h3>
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
