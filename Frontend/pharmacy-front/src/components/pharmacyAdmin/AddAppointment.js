import React, { useState, useEffect } from "react";

import axios from "../../app/api";

import moment from "moment";

import { Button, Tab, Row, Col, Table, Form, Alert } from "react-bootstrap";

function AddAppointment({ idOfPharmacy }) {
  const [dermatologists, setDermatologists] = useState([]);
  const [appointments, setAppointments] = useState([]);
  const [startDate, setStartDate] = useState(new Date());
  const [startHour, setStartHour] = useState("10:00");
  const [dermatogistPicked, setDermatogistPicked] = useState(0);
  const [duration, setDuration] = useState(15);
  const [price, setPrice] = useState(2000);
  const [workDaysLabel, setWorkDaysLabel] = useState("");
  const [refresh, setRefresh] = useState(true);

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
  }, [startDate, dermatogistPicked, refresh]);

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
              let d1 = new Date();
              let d2 = new Date();
              d1.setHours(dermatologists[i].workDays[j].startTime);
              d2.setHours(dermatologists[i].workDays[j].endTime);

              setWorkDaysLabel(
                `Workshedule : ${moment(d1.getTime()).format(
                  "hh:00 a"
                )} - ${moment(d2.getTime()).format("hh:00 a")}`
              );
              flag = true;
              break;
            }
          }
        }
      }
      if (!flag) {
        if (dermatogistPicked != 0) {
          setWorkDaysLabel("It's a non-working day");
        } else {
          setWorkDaysLabel("");
        }
      }
    }
  }, [dermatogistPicked, startDate]);

  let sendAppointment = () => {
    let date = startDate;
    let hour = startHour;
    let array = hour.split(":");
    date.setHours(Number.parseInt(array[0]), Number.parseInt(array[1]), 0);
    let long = date.getTime();
    let request = { duration, price: price, startTime: long };
    console.log(request);

    axios
      .post(
        `http://localhost:8080/api/appointment/${idOfPharmacy}/${dermatogistPicked}`,
        request
      )
      .then(() => {
        alert("Appointment added successfully");
        reloadForm();
      })
      .catch(() => {
        alert("Appointment is not added successfully");
      });
  };

  let reloadForm = () => {
    setStartHour("10:00");
    setDuration(15);
    setRefresh(!refresh);
    setPrice(2000);
  };

  return (
    <Tab.Pane eventKey="fourth">
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

          {workDaysLabel == "It's a non-working day" && (
            <Alert variant="warning">{workDaysLabel}</Alert>
          )}

          <Form.Group controlId="timePicker" di>
            <Form.Label>Start time</Form.Label>
            <Form.Control
              type="time"
              value={startHour}
              disabled={dermatogistPicked == 0}
              onChange={(event) => setStartHour(event.target.value)}
            />
          </Form.Group>

          {workDaysLabel != "" && workDaysLabel != "It's a non-working day" && (
            <Alert variant="info">{workDaysLabel}</Alert>
          )}

          <Form.Group controlId="durationPicker" di>
            <Form.Label>Duration (in minutes)</Form.Label>
            <Form.Control
              type="number"
              value={duration}
              disabled={dermatogistPicked == 0}
              onChange={(event) =>
                setDuration(Number.parseInt(event.target.value))
              }
              min="0"
            />
          </Form.Group>

          <Form.Group controlId="pricePicker" di>
            <Form.Label>Price</Form.Label>
            <Form.Control
              type="number"
              value={price}
              disabled={dermatogistPicked == 0}
              onChange={(event) =>
                setPrice(Number.parseInt(event.target.value))
              }
              min="0"
            />
          </Form.Group>

          <Button
            disabled={dermatogistPicked == 0}
            variant="primary"
            type="submit"
            onClick={sendAppointment}
          >
            Submit
          </Button>
        </Col>
        <Col>
          {appointments.length != 0 && (
            <div>
              <Alert variant="primary">
                <Alert.Heading>Appointments on that date</Alert.Heading>
                <p>
                  These are already created meetings that day. Try to find free
                  time for your meeting.
                </p>
              </Alert>
              <Table bordered striped variant="dark">
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
                          {moment(appointment.startTime).format("hh:mm a")}
                        </td>
                        <td>{moment(appointment.endTime).format("hh:mm a")}</td>
                        <td>{appointment.appointmentState}</td>
                      </tr>
                    ))}
                </tbody>
              </Table>
            </div>
          )}
        </Col>
      </Row>
    </Tab.Pane>
  );
}

export default AddAppointment;
