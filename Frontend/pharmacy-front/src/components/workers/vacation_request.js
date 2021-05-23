import React, { useState, useEffect } from "react";
import  {Row, Button,  Col, Card} from "react-bootstrap";
import VacationModal from "./vacation_modal";
import 'bootstrap/dist/css/bootstrap.min.css';
import api from "../../app/api";
import moment from "moment";
import { getIdFromToken } from '../../app/jwtTokenUtils';
import { useToasts } from "react-toast-notifications";

function VacationRequest() {
    const [requests, setRequests] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const { addToast } = useToasts();

    useEffect(() => {
        let userID = getIdFromToken();
        if (!userID){
            addToast("No user id in token! error", { appearance: "error" });
            return;
        }
        api.get("http://localhost:8080/api/vacation/getVacationsFromWorker?id=" + userID)
            .then((resp)=> setRequests(resp.data))
            .catch(()=> setRequests([]));
    }, [])

    const onBtnClick = ()=>{
        setShowModal(true);
    }

    const onCreateRequest = ()=>{
        let userID = getIdFromToken();
        if (!userID){
            addToast("No user id in token! error", { appearance: "error" });
            return;
        }
        api.get("http://localhost:8080/api/vacation/getVacationsFromWorker?id=" + userID)
            .then((resp)=> setRequests(resp.data))
            .catch(()=> setRequests([])); //todo da se stavi da se dodaje u listu, a ne novi zahtev
        setShowModal(false);
    }

    return (

        <Row className="justify-content-center ml-5 mb-4 mt-5 align-items-center">
            <Col md={6}>
                <Row className="justify-content-center ml-5 mb-4 mt-5 align-items-center">
                    <h2>Requests for vacations</h2>
                    <Button onClick={onBtnClick}>Create new</Button>
                </Row>

                {requests.map((value, index) => {
                    return (
                        <Row className="justify-content-center ml-5 mb-4 align-items-center" key={index} >
                            <Col>
                                <Card fluid>
                                    <Card.Body>
                                        <Card.Title>{'From: ' + moment(value.start).format("DD MMM YYYY") + " | To: " + moment(value.end).format("DD MMM YYYY")}</Card.Title>
                                        <Card.Text>{'Type: ' + value.absenceType}</Card.Text>
                                        <Card.Text>{'Status: ' + value.requestState}</Card.Text>
                                        {value.requestState.trim() === 'CANCELLED' &&
                                            <Card.Text>Reason for decline: {value.declineText}</Card.Text>
                                        }
                                    </Card.Body>
                                </Card>
                            </Col>
                        </Row>);
                })}
            </Col>

            <VacationModal show={showModal} onCreateMethod={onCreateRequest} onHide={() => {setShowModal(false)}}></VacationModal>
        </Row>

    );
}

export default VacationRequest;