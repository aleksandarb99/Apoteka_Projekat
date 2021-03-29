import React, { useState } from 'react'
import { Button, Form, Modal, Card, Row, Col, Container } from 'react-bootstrap'
import PropTypes from 'prop-types'
import axios from 'axios'
import Moment from "react-moment";

function AppointmentsModal(props) { // id usera, id workera, bool farmaceut, name

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
                                Date: <Moment format="DD/MM/yyyy hh:mm" unix>{value.startTime}</Moment> 
                            </Card.Title>
                            <Card.Link className="mb-2"> Start appointment</Card.Link>
                        </Card.Body>
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
