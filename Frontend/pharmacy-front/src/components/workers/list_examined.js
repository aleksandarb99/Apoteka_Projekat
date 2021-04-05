import React, { useState, useEffect } from "react";
import  {Row, Form, Button, Container, Col, Card, Modal} from "react-bootstrap";
import DatePicker from "react-datepicker";
import AppointmentsModal from "./appointments_modal";
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from "axios";
import moment from "moment";

import "react-datepicker/dist/react-datepicker.css";

function SearchExaminedPatPage() {
    const [patients, setPatients] = useState([]);
    const [fName, setFName] = useState("");
    const [lName, setLName] = useState(""); 
    const [patient, setPatient] = useState({});  //appointments of currently picked patient
    const [showModal, setShowModal] = useState(false);
    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const [fnSort, setFnSort] = useState('none');
    const [lnSort, setLnSort] = useState('none');
    const [dateSort, setDateSort] = useState('none');

    useEffect(() => {
        async function fetchPatients() {
            //todo da ne bude vise worker id kad se sredi jwt
        const request = await axios.get("http://localhost:8080/api/patients/getAllExaminedPatients?workerID=5")
            .then((resp)=>setPatients(resp.data)).catch(setPatients([]));
        return request;
        }
        fetchPatients();
    }, []);

    const formSearch = event => {
        event.preventDefault();
        let search_params = new URLSearchParams();
        if (fName.length != 0){
            search_params.append('firstName', fName);
        }
        if (lName.length != 0){
            search_params.append('firstName', lName);
        }
        if (startDate){
            search_params.append('lowerTime', Math.floor(startDate.getTime()/1000));
        }
        if (endDate){
            search_params.append('upperTime', Math.floor(endDate.getTime()/1000));
        }
        if (fnSort === 'asc' || fnSort === 'desc'){
            search_params.append('sort', 'firstName,' + fnSort);
        }
        if (lnSort === 'asc' || lnSort === 'desc'){
            search_params.append('sort', 'lastName,' + lnSort);
        }
        if (dateSort === 'asc' || dateSort === 'desc'){
            search_params.append('sortDate', dateSort);
        }
        search_params.append('workerID', 5); //todo otkucati kad dodje jwt

        axios.get("http://localhost:8080/api/patients/getExaminedPatients", 
                { params: search_params}).then((resp)=>setPatients(resp.data)).catch(setPatients([]));
    }

    const resetSearch = function() {
        axios.get("http://localhost:8080/api/patients/getAllExaminedPatients?workerID=5").then((resp)=>setPatients(resp.data)).catch((resp) => setPatients([]));
        setFName("");
        setLName("");
        setFnSort("none");
        setLnSort("none");
        setDateSort("none")
    }

    const onShowAppointmentsButton = function(pat_to_show){
        setPatient({
        "patient": pat_to_show?.email,
        "worker": 5, //TODO promeniti, hardkodovano
        "patientName": pat_to_show?.firstName + " " + pat_to_show?.lastName
        });
        setShowModal(true);
    }

    const updateSorting = (event) => {
        let type = event.target.name;
        let val = event.target.value;

        if (type=="fnSort"){
            setFnSort(val);
        }else if (type=="lnSort"){
            setLnSort(val);
        }else{
            setDateSort(val);
        }
        console.log(type + " | " + val);
    } 

    return (
        <Container style={{height: '90vh'}}>
            <Row className="justify-content-center m-3">
            <h4>Search examined patients</h4>
            </Row>

            <Row className="justify-content-center m-3">
                <Form onSubmit={formSearch}>
                    <Form.Group as={Row} className="align-items-center">
                        
                        <Col>
                            <Form.Label> First name: </Form.Label>
                            <Form.Control type="text" name="firstName" value={fName} onChange={(e)=>setFName(e.target.value)}
                                placeholder="Enter first name..." />
                                
                            <Form.Label>Sort by first name: </Form.Label>
                            <Form.Control as="select" 
                                value={fnSort} 
                                onChange={updateSorting.bind(this)}
                                name="fnSort">

                                <option value="none">none</option>
                                <option value="asc">ascending</option>
                                <option value="desc">descending</option>
                            </Form.Control>
                        </Col>

                        
                        <Col>
                            <Form.Label> Last name: </Form.Label>
                            <Form.Control type="text" name="lastName" value={lName} onChange={(e)=>setLName(e.target.value)}
                                placeholder="Enter last name..." />

                            <Form.Label>Sort by last name: </Form.Label>
                            <Form.Control as="select" 
                                value={lnSort} 
                                onChange={updateSorting.bind(this)} 
                                name="lnSort">

                                <option value="none">none</option>
                                <option value="asc">ascending</option>
                                <option value="desc">descending</option>
                            </Form.Control>
                        </Col>

                        <Col>
                            <Form.Label> From: </Form.Label>
                            <DatePicker 
                                closeOnScroll={true}
                                selected={startDate} 
                                dateFormat="dd/MM/yyyy" 
                                onChange={date => setStartDate(date)}
                                isClearable/>

                            <Form.Label>Sort by date: </Form.Label>
                            <Form.Control as="select" 
                                value={dateSort} 
                                onChange={updateSorting.bind(this)}
                                name="dateSort">

                                <option value="none">none</option>
                                <option value="asc">ascending</option>
                                <option value="desc">descending</option>
                            </Form.Control>
                        </Col>

                        <Col>
                            <Form.Label> To: </Form.Label>
                            <DatePicker 
                                closeOnScroll={true}
                                selected={endDate} 
                                dateFormat="dd/MM/yyyy" 
                                onChange={date => setEndDate(date)}
                                isClearable/>
                        </Col>

                        <Col className="justify-content-center">
                            
                            <Button type="submit" variant="primary" > Search </Button>
                            <Button variant="primary" onClick={resetSearch}> Reset search </Button>
                        
                        </Col>
                    </Form.Group>
                
                </Form>
            </Row>

            <Row className="justify-content-center m-3">
            <Col md={8}>
                {patients.length === 0 &&
                <Row className="justify-content-center m-3 align-items-center"><h3>No result!</h3></Row>
                }

                {patients.map((value, index) => {
                return (<Row className="justify-content-center m-5 align-items-center" key={index}>
                    <Col>
                    <Card fluid>
                    <Card.Body>
                        <Card.Title>{value.firstName + " " + value.lastName} </Card.Title>
                        <Card.Text>{"Last appointment: " + moment.unix(value.appointmentStart).format("DD MMM YYYY hh:mm a")} </Card.Text>
                        {/*izmeni ovaj deo da kasnije bude da se prikazu svi sastanci*/}
                        {/* <Button variant="secondary" onClick={() => onShowAppointmentsButton(value)}> Upcomming appointments </Button> */}
                    </Card.Body>
                    </Card>
                    </Col>
                    </Row>);
                })}
            </Col>
            </Row>
            <AppointmentsModal show={showModal} info={patient} onHide={() => {setShowModal(false); setPatient({})}}></AppointmentsModal>
            
        </Container>
        
    );
}

export default SearchExaminedPatPage;