import React from 'react'
import { Button, Modal, Container } from 'react-bootstrap'
import api from '../../app/api';
import moment from "moment";
import {useHistory} from "react-router-dom";
import { useToasts } from "react-toast-notifications";



function AppointmentStartModal(props) { // prosledis appointment
    const history = useHistory();
    const { addToast } = useToasts();

    const onStart = () => {
        if (!(moment(Date.now()) > moment(props.appointment.start).subtract(15, 'minutes'))){
            // ne moze da se krene vise od 15 min ranije
            addToast("You can't start this appointment yet!", { appearance: "error" });
            return;
        }

        let bodyFormData = new FormData();
        bodyFormData.append('id', props.appointment.id);

        api.post("http://localhost:8080/api/appointment/start_appointment", bodyFormData)
            .then(
                () => {
                    history.push(
                        {
                            pathname:"/worker/appointment_report",
                            state: {  // location state
                                appointmentID: props.appointment.id 
                            }
                        });
                })
            .catch(() => addToast("You can't start this appointment!", { appearance: "error" }));
    }

    const onCancel = () => {
        if (!(moment(Date.now()) > moment(props.appointment.start).add(5, 'minutes'))){
            // ne moze da se cancelluje dok ne prodje bar 5 minuta od pocetka sastanka TODO check na backu
            addToast("You can't cancel this appointment yet!", { appearance: "error" });
            return;
        }

        let bodyFormData = new FormData();
        bodyFormData.append('id', props.appointment.id);

        api.post("http://localhost:8080/api/appointment/cancel_appointment", bodyFormData)
            .then(
                () => {
                    addToast("Appointment cancelled! Patient didn't show up!", { appearance: "info" });
                    props.onCancelMethod();
                }).catch(() => addToast("You can't cancel this appointment yet!", { appearance: "error" }));
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
                    <Button onClick={onCancel}>Patient didn't show up</Button>
                </Container>
            </Modal.Body>
            <Modal.Footer>
            </Modal.Footer>
        </Modal>
    )
}

export default AppointmentStartModal
