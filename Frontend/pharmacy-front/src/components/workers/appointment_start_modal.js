import React, { useState } from 'react'
import { Button, Form, Modal, Card, Row, Col, Container } from 'react-bootstrap'
import PropTypes from 'prop-types'
import axios from 'axios'
import moment from "moment";
import {useHistory} from "react-router-dom";

function AppointmentStartModal(props) { // prosledis appointment
    const history = useHistory();

    const onStart = () => {
        // if (!(moment(Date.now()).subtract(15, 'minutes') < props.appointment.start 
        //     && moment(Date.now()).add(15, 'minutes') > props.appointment.start)){
        //     // 3 sata da bi mogao da se cancelluje i kasnije, ali svakako nece moci da se zapocne ranije
        //     alert("You can't start this appointment!");
        //     return;
        // }
        history.push("/worker/appointment_report");
        // api.post("http://localhost:8080/api/appointment/start", {params: {'id': props.appointment.id}})
        //     .then(
        //         (resp) => {
        //             alert("Appointment started!");
                    
        //         })
        //     .catch(() => alert("You can't start this appointment!"));
    }

    const onCancel = () => {

    }

    return (
        <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Initialize appointment for {props.appointment.patient}
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Container>
                    <Button onClick={onStart}>Start appointment</Button>
                    <Button onClick={onCancel}>Cancel appointment</Button>
                </Container>
            </Modal.Body>
            <Modal.Footer>
            </Modal.Footer>
        </Modal>
    )
}

export default AppointmentStartModal
