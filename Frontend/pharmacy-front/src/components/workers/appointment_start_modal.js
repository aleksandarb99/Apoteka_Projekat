import React, { useState } from 'react'
import { Button, Form, Modal, Card, Row, Col, Container } from 'react-bootstrap'
import PropTypes from 'prop-types'
import api from '../../app/api';
import moment from "moment";
import {useHistory} from "react-router-dom";

function AppointmentStartModal(props) { // prosledis appointment
    const history = useHistory();

    const onStart = () => {
        if (!(moment(Date.now()) > moment(props.appointment.start).subtract(15, 'minutes')
            && moment(Date.now()) < moment(props.appointment.start).add(15, 'minutes'))){
            // ne moze da se krene vise od 15 min ranije ili 15 min kasnije
            alert("You can't start this appointment! x");
            return;
        }

        let bodyFormData = new FormData();
        bodyFormData.append('id', props.appointment.id);

        api.post("http://localhost:8080/api/appointment/start_appointment", bodyFormData)
            .then(
                () => {
                    alert("Appointment started!");
                    history.push("/worker/appointment_report");
                })
            .catch(() => alert("You can't start this appointment! y"));
    }

    const onCancel = () => {
        if (!(moment(Date.now()) > moment(props.appointment.start).subtract(15, 'minutes'))){
            // ne moze da se cancelluje ranije od 15 min
            alert("You can't cancel this appointment! x");
            return;
        }

        let bodyFormData = new FormData();
        bodyFormData.append('id', props.appointment.id);
        console.log(props.appointment);
        console.log(props.appointment.id);

        api.post("http://localhost:8080/api/appointment/cancel_appointment", bodyFormData)
            .then(
                () => {
                    alert("Appointment cancelled!");
                    props.onCancelMethod();
                }).catch(() => alert("You can't cancel this appointment! y"));
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
