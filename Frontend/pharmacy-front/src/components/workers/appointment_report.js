import React, { useState, useEffect } from "react";
import {Form, Row, Col, Button} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Link} from "react-router-dom";
import { Rainbow } from "react-bootstrap-icons";
import TherapyMedicineModal from "./appointment_report_therapy_modal";
import api from '../../app/api';
import { useLocation } from 'react-router-dom';
import moment from "moment";



function AppointmentReport() {
    const [selectedMedicine, setSelectedMedicine] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [currAppt, setCurrAppt] = useState(null);

    const location = useLocation();

    useEffect(() => {
        let bodyFormData = new FormData();
        let appt_id = location.state.appointmentID;
        bodyFormData.append('id', appt_id);

        api.post("http://localhost:8080/api/appointment/getApptForReport", bodyFormData)
            .then(
                (resp) => {
                    setCurrAppt(resp.data);
                })
            .catch(() => alert("Couldn't get ids!"));
    }, [])

    const onAdd = (medItem) => {
        for (let i = 0; i < selectedMedicine.length; i++){
            if (selectedMedicine[i].medicineID === medItem.medicineID){
                alert('That medicine is already added to therapy!');
                return;
            }
        }
        selectedMedicine.push(medItem);
        setShowModal(false);
    }

    const hideModal = () => {
        setShowModal(false);
    }

    const addTherapy = () => {
        let appointment_id = currAppt.id; 
        api.post('http://localhost:8080/api/appointment/addTherapy', { apptId: appointment_id, medicineList: selectedMedicine })
            .then(()=> alert("uspeh"))  //todo ovde redirekcija na finalizaciju 
            .catch(() => alert("Couldn't add therapy, no appointment with sent id!"));
    }
  
    return (
    <div>
        <Row className="justify-content-center mt-5 mb-2 align-items-center">
            <h2>Appointment report for: {currAppt?.patient}</h2>
        </Row>
        <Row className="justify-content-center mb-5 align-items-center">
            <h3>{currAppt?.pharmacy}</h3>
        </Row>
        <Row className="justify-content-center mb-3 align-items-center">
            <Col md={8}>
                <Row className="justify-content-center align-items-center">
                    <Col>
                        <p className="text-center">Date: {moment(currAppt?.start).format("DD MMM YYYY")}</p>
                    </Col>
                    <Col>
                        <p className="text-center">
                            Time: 
                            {moment(currAppt?.start).format("hh:mm a")}
                            -{moment(currAppt?.end).format("hh:mm a")}</p>
                    </Col>
                    <Col>
                        <p className="text-center">Price: {currAppt?.price}</p>
                    </Col>
                </Row>
            </Col>
        </Row>
        <Row className="justify-content-center mb-5 align-items-center">
            <Col md={8}>
                <Form>
                    <Form.Group as={Row} className="justify-content-center align-items-center">
                        <Form.Label>Input information about the consultation</Form.Label>
                        <Form.Control as="textarea" rows="8"  name="address"/>
                    </Form.Group>
                </Form>
            </Col>
        </Row>
        <Row className="justify-content-center mb-5 align-items-center">
            <Col md={8}>
                <Row className="justify-content-center align-items-center">
                    Therapy medicine
                    <Button size="sm" onClick={(event) => setShowModal(true)}>Add new</Button>
                </Row>
                {(selectedMedicine ? selectedMedicine : []).map((value, index) => {
                    return (
                        <Row className="justify-content-center align-items-center mt-3 ml=5">
                            <div>Medicine: {value.code} - {value.name}. Duration of therapy(in days): {value.duration}</div>
                            <Button onClick={() => { 
                                let newArray = [];
                                for (let i = 0; i < selectedMedicine.length; i++){
                                    if (i != index)
                                        newArray.push(selectedMedicine[i]);
                                }
                                setSelectedMedicine(newArray); 
                            }}>Remove</Button>
                        </Row>
                        );
                })}
            </Col>
        </Row>
        <Row className="justify-content-center m-5 align-items-center">
            <Button onClick={addTherapy}> End appointment </Button>
        </Row>
        <TherapyMedicineModal show={showModal} appt={currAppt} onHideModal={hideModal} onAddMedicine={onAdd}></TherapyMedicineModal>
    </div>
    );
}

export default AppointmentReport;