import React, { useState } from 'react'
import { Button, Form, Modal, Card, Row, Col, Container, ButtonGroup } from 'react-bootstrap'
import PropTypes from 'prop-types'
import axios from 'axios'
import Moment from "react-moment";
import moment from "moment";
import api from "../../app/api";
import {useHistory} from "react-router-dom";
import { getUserTypeFromToken } from '../../app/jwtTokenUtils';

function AppointmentsModal(props) { // id usera, id workera, bool farmaceut, name
    const history = useHistory();

    const [appointments, setAppointments] = useState([]);
    
    const showHandler = () => {
        async function fetchAppointments() {
            let bodyFormData = new FormData();
            bodyFormData.append('patient', props?.info.patient);
            bodyFormData.append('worker', props?.info.worker);
            const request = axios.post("http://localhost:8080/api/appointment/byPatWorker", bodyFormData)
                    .then((resp) => setAppointments(resp.data))
                    .catch((resp) => setAppointments([]));
            // const request = axios.post("http://localhost:8080/api/appointment/byPatWorker", 
            //     { params: {"patient":props?.info.patient, "worker": props?.info.worker}})
            //         .then((resp) => setAppointments(request.data))
            //         .catch((resp) => setAppointments([]));
            return request;
        }
        fetchAppointments();
    }

    const onStart = (appt) => {
        if (!(moment(Date.now()) > moment(appt.start).subtract(15, 'minutes')
            && moment(Date.now()) < moment(appt.start).add(15, 'minutes'))){
            // ne moze da se krene vise od 15 min ranije ili 15 min kasnije
            alert("You can't start this appointment! x");
            return;
        }

        let bodyFormData = new FormData();
        bodyFormData.append('id', appt.id);

        api.post("http://localhost:8080/api/appointment/start_appointment", bodyFormData)
            .then(
                () => {
                    alert("Appointment started!");
                    history.push(
                        {
                            pathname:"/worker/appointment_report",
                            state: {  // location state
                                appointmentID: appt.id 
                            }
                        });
                })
            .catch(() => alert("You can't start this appointment! y"));
    }

    const onCancelAppointment = () => {
        let bodyFormData = new FormData();
        bodyFormData.append('patient', props?.info.patient);
        bodyFormData.append('worker', props?.info.worker);
        axios.post("http://localhost:8080/api/appointment/byPatWorker", bodyFormData)
                    .then((resp) => setAppointments(resp.data))
                    .catch(() => setAppointments([]));
    }

    const onCancel = (appt) => {
        if (!(moment(Date.now()) > moment(appt.start).subtract(15, 'minutes'))){
            // ne moze da se cancelluje ranije od 15 min
            alert("You can't cancel this appointment! x");
            return;
        }

        let bodyFormData = new FormData();
        bodyFormData.append('id', appt.id);

        api.post("http://localhost:8080/api/appointment/cancel_appointment", bodyFormData)
            .then(
                () => {
                    alert("Appointment cancelled!");
                    onCancelAppointment();
                }).catch(() => alert("You can't cancel this appointment! y"));
    }


    return (
        <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered onShow={showHandler}>
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Your upcomming appointments for {props.info.patientName}
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Container>
                    {appointments.length === 0 &&
                    <Row className="justify-content-center m-3 align-items-center"><h3>No result!</h3></Row>
                    }

                    {appointments.map((value, index) => {
                    return (<Row className="justify-content-center m-5 align-items-center" key={index}>
                        <Col>
                            <Card fluid>
                                <Card.Body>
                                    <Card.Title>
                                        Date: <Moment format="DD/MM/yyyy HH:mm">{value.start}</Moment> 
                                    </Card.Title>
                                    <Card.Text>
                                        {getUserTypeFromToken().trim() === 'DERMATOLOGIST' &&
                                            <div>Pharmacy: {value.pharmacy} </div>   
                                        }
                                    </Card.Text>
                                </Card.Body>
                                <Card.Footer>
                                    <ButtonGroup>
                                        <Button onClick={()=>onStart(value)}>Start appointment</Button>
                                        <Button onClick={()=>onCancel(value)}>Patient didn't show up</Button>
                                    </ButtonGroup>
                                </Card.Footer>
                            </Card>
                        </Col>
                        </Row>);
                    })}
                </Container>
            </Modal.Body>
            <Modal.Footer>
            </Modal.Footer>
        </Modal>
    )
}

export default AppointmentsModal
