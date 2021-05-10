import React, { useState, useEffect } from "react";
import  {Row, Form, Button, Container, Col, Card, Modal, ButtonGroup, InputGroup} from "react-bootstrap";
import DatePicker from "react-datepicker";
import AppointmentsModal from "./appointments_modal";
import 'bootstrap/dist/css/bootstrap.min.css';
import api from "../../app/api";
import moment from "moment";
import { getUserTypeFromToken } from '../../app/jwtTokenUtils';
import { getIdFromToken } from '../../app/jwtTokenUtils';

import "react-datepicker/dist/react-datepicker.css";

function IssueMedicine() {
    const [reservedMedicine, setReservedMedicine] = useState(null);
    const [resID, setResID] = useState(null);
    const [errorMessage, setErrorMessage] = useState(null);

    const searchRes = () => {
        let bodyFormData = new FormData();
        let workerID = getIdFromToken();
        if (workerID === null){
            return;
        }
        bodyFormData.append('workerID', workerID);
        bodyFormData.append('resID', resID);

        api.post("http://localhost:8080/api/medicine-reservation/getReservedIssue", bodyFormData)
            .then(
                (resp) => {
                    setReservedMedicine(resp.data);
                    setErrorMessage(null);
                })
            .catch((resp) => { setErrorMessage(resp.response.data.message); setReservedMedicine(null); });
    }

    const issueMed = () => {
        let bodyFormData = new FormData();
        let workerID = getIdFromToken();
        if (workerID === null){
            return;
        }
        bodyFormData.append('workerID', workerID);
        bodyFormData.append('resID', resID);

        api.post("http://localhost:8080/api/medicine-reservation/issueMedicine", bodyFormData)
            .then(
                (resp) => {
                    alert(resp.data);
                    setReservedMedicine(null);
                    setResID('');
                })
            .catch((resp) => { alert(resp.response.data.message); setReservedMedicine(null); });
    }
    
    return (
            <Col>
                <div style={{backgroundColor: '#83CEC2'}}>
                    <Row className="justify-content-center" >
                        <h4 style={{marginTop: '30px'}}>Issue medicine</h4>
                    </Row>

                    <Row className="justify-content-center align-items-center"  >
                        <Form onSubmit={(event)=> event.preventDefault()}>
                            <Form.Group as={Row} className="justify-content-center m-3 align-items-center">
                                
                                <Form.Label>Search reservation:</Form.Label>
                                <Form.Control type="text" placeholder="Reservation ID" value={resID} onChange={(event) => setResID(event.target.value)}/>
                                <Button onClick={()=> searchRes() }>Search</Button>
                                    
                            </Form.Group>
                        </Form>
                    </Row>
                </div>

                <Row className="justify-content-center m-3">
                    {(reservedMedicine && !errorMessage) &&
                        <Col md={6}>
                            <Card fluid>
                                <Card.Body>
                                    <Card.Title>Reservation ID: {reservedMedicine.reservationID}</Card.Title>
                                    <Card.Text>Reservation date: {moment(reservedMedicine.reservationDate).format("DD MMM YYYY")}</Card.Text>
                                    <Card.Text>Reservation pickup due date: {moment(reservedMedicine.pickupDate).format("DD MMM YYYY")}</Card.Text>
                                    <Card.Text>Medicine name: {reservedMedicine.medicineName}</Card.Text>
                                    <Card.Text>Medicine ID: {reservedMedicine.medicineID}</Card.Text>
                                </Card.Body>
                                <Button onClick={() => issueMed()}>Issue medicine</Button>
                            </Card>
                        </Col>
                    }

                    {(!reservedMedicine && errorMessage) &&
                        <Col md={4}>
                            <h4>{errorMessage}</h4>
                        </Col>
                    }

                </Row>
            </Col>
        
    );
}

export default IssueMedicine;