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

function SearchExaminedPatPage() {
    const [patients, setPatients] = useState([]);
    const [fName, setFName] = useState("");
    const [lName, setLName] = useState(""); 
    const [patient, setPatient] = useState({});  //appointments of currently picked patient
    const [showModal, setShowModal] = useState(false);

    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);

    const [sort1, setSort1] = useState('none');
    const [sort2, setSort2] = useState('none');
    const [sort3, setSort3] = useState('none');
    const [sortWay1, setSortWay1] = useState('none');
    const [sortWay2, setSortWay2] = useState('none');
    const [sortWay3, setSortWay3] = useState('none');

    const [currUserType, setCurrUserType] = useState('PHARMACIST');

    useEffect(() => {
        async function fetchPatients() {
            let id = getIdFromToken();
            if (!id){
                alert("invalid user!")
                setPatients([]);
                return;
            }
            let user_type= getUserTypeFromToken().trim();
            if (user_type !== 'DERMATOLOGIST' && user_type !== 'PHARMACIST'){
                alert("invalid user_type!")
                return;
            }
            setCurrUserType(user_type);
            const request = await api.get("http://localhost:8080/api/patients/getAllExaminedPatients?workerID=" + id)
                .then((resp)=>setPatients(resp.data)).catch(setPatients([]));
            return request;
        }
        fetchPatients();
    }, []);

    const formSearch = event => {
        event.preventDefault();
        let id = getIdFromToken();
        if (!id){
            alert("invalid user!")
            setPatients([]);
            return;
        }
        let search_params = new URLSearchParams();
        if (fName.length != 0){
            search_params.append('firstName', fName);
        }
        if (lName.length != 0){
            search_params.append('firstName', lName);
        }
        if (startDate){
            search_params.append('lowerTime', Math.floor(startDate.getTime()));
        }
        if (endDate){
            if (startDate){
                if (startDate.getTime() >= endDate.getTime()){
                    alert("Invalid date params!");
                    return;
                }
            }
            search_params.append('upperTime', Math.floor(endDate.getTime()));
        }

        if (sort1 !== 'none' && (sort1 === sort2 || sort1 === sort3)){
            alert("Invalid sort params!");
            return;
        }else if (sort2 !== 'none' && (sort2 === sort1 || sort2 == sort3)){
            alert("Invalid sort params!");
            return;
        }

        if (sort1 !== 'none'){
            if (sortWay1 === 'none'){
                alert("First sorter direction not specified!");
                return;
            }else{
                search_params.append('sort', sort1 + ',' + sortWay1);
            }
        }else if (sortWay1 !== 'none'){
            alert("First sorter not specified!");
            return;
        }

        if (sort2 !== 'none'){
            if (sortWay2 === 'none'){
                alert("Second sorter direction not specified!");
                return;
            }else{
                search_params.append('sort', sort2 + ',' + sortWay2);
            }
        }else if (sortWay2 !== 'none'){
            alert("Second sorter not specified!");
            return;
        }

        if (sort3 !== 'none'){
            if (sortWay3 === 'none'){
                alert("Third sorter direction not specified!");
                return;
            }else{
                search_params.append('sort', sort3 + ',' + sortWay3);
            }
        }else if (sortWay3 !== 'none'){
            alert("Third sorter not specified!");
            return;
        }        
        search_params.append('workerID', id);

        api.get("http://localhost:8080/api/patients/getExaminedPatients", 
                { params: search_params}).then((resp)=>setPatients(resp.data)).catch(setPatients([]));
    }

    const resetSearch = function() {
        let id = getIdFromToken();
        if (!id){
            alert("invalid user!")
            setPatients([]);
            return;
        }
        api.get("http://localhost:8080/api/patients/getAllExaminedPatients?workerID=" + id).then((resp)=>setPatients(resp.data)).catch(() => setPatients([]));
        setFName("");
        setLName("");
        setStartDate(null);
        setEndDate(null);
        setSort1('none');
        setSortWay1('none');
        setSort2('none');
        setSortWay2('none');
        setSort3('none');
        setSortWay3('none');
    }

    // const onShowAppointmentsButton = function(pat_to_show){
    //     let id = getIdFromToken();
    //         if (!id){
    //             alert("invalid user!")
    //             setPatients([]);
    //             return;
    //         }
    //     setPatient({
    //     "patient": pat_to_show?.email,
    //     "worker": id, 
    //     "patientName": pat_to_show?.firstName + " " + pat_to_show?.lastName
    //     });
    //     setShowModal(true);
    // }


    return (
        <Container >
            <Row className="justify-content-center m-3">
            <h4>Search examined patients</h4>
            </Row>

            <Row className="justify-content-center m-3">
                <Form onSubmit={formSearch}>
                    <Form.Group as={Row} className="align-items-center">
                         
                        <Col>
                            <Row className="justify-content-center m-3">
                                <InputGroup>
                                    <InputGroup.Prepend>
                                        <InputGroup.Text>First name:</InputGroup.Text>
                                    </InputGroup.Prepend>
                                    <Form.Control type="text" name="firstName" value={fName} onChange={(e)=>setFName(e.target.value)}
                                        placeholder="Enter first name..." />
                                </InputGroup>
                            </Row> 

                            <Row className="justify-content-center m-3">
                                <InputGroup>
                                    <InputGroup.Prepend>
                                        <InputGroup.Text>From date:</InputGroup.Text>
                                    </InputGroup.Prepend>
                                    <Form.Control
                                        as={DatePicker} 
                                        closeOnScroll={true}
                                        selected={startDate} 
                                        dateFormat="dd/MM/yyyy" 
                                        onChange={date => setStartDate(date)}
                                        isClearable/>
                                </InputGroup>
                            </Row>
                        </Col>

                        
                        <Col>
                            <Row className="justify-content-center m-3">
                                <InputGroup>
                                    <InputGroup.Prepend>
                                        <InputGroup.Text>Last name:</InputGroup.Text>
                                    </InputGroup.Prepend>
                                    <Form.Control type="text" name="lastName" value={lName} onChange={(e)=>setLName(e.target.value)}
                                        placeholder="Enter last name..." />
                                </InputGroup>  
                                 
                            </Row>

                            <Row className="justify-content-center m-3">
                                <InputGroup>
                                    <InputGroup.Prepend>
                                        <InputGroup.Text>To date:</InputGroup.Text>
                                    </InputGroup.Prepend>
                                    <Form.Control 
                                        as={DatePicker} 
                                        closeOnScroll={true}
                                        selected={endDate} 
                                        dateFormat="dd/MM/yyyy" 
                                        onChange={date => setEndDate(date)}
                                        isClearable/>
                                </InputGroup>
                            </Row>
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} className="align-items-center">
                        <Col>
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text>Sort by:</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control as="select" 
                                value={sort1} 
                                onChange={(event)=> setSort1(event.target.value)}
                                name="sort1">
                                <option value="none">none</option>
                                <option value="firstName">first name</option>
                                <option value="lastName">last name</option>
                                <option value="startTime">date</option>
                            </Form.Control>
                            <Form.Control as="select" 
                                value={sortWay1} 
                                onChange={(event)=> setSortWay1(event.target.value)}
                                name="sortWay1">
                                <option value="none">none</option>
                                <option value="asc">ascending</option>
                                <option value="desc">descending</option>
                            </Form.Control>
                        </InputGroup>
                        </Col>

                        <Col>
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text>then by:</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control as="select" 
                                value={sort2} 
                                onChange={(event)=> setSort2(event.target.value)}
                                name="sort1">
                                <option value="none">none</option>
                                <option value="firstName">first name</option>
                                <option value="lastName">last name</option>
                                <option value="startTime">date</option>
                            </Form.Control>
                            <Form.Control as="select" 
                                value={sortWay2} 
                                onChange={(event)=> setSortWay2(event.target.value)}
                                name="sortWay1">
                                <option value="none">none</option>
                                <option value="asc">ascending</option>
                                <option value="desc">descending</option>
                            </Form.Control>
                        </InputGroup>
                        </Col>

                        <Col>
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text>then by:</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control as="select" 
                                value={sort3} 
                                onChange={(event)=> setSort3(event.target.value)}
                                name="sort1">
                                <option value="none">none</option>
                                <option value="firstName">first name</option>
                                <option value="lastName">last name</option>
                                <option value="startTime">date</option>
                            </Form.Control>
                            <Form.Control as="select" 
                                value={sortWay3} 
                                onChange={(event)=> setSortWay3(event.target.value)}
                                name="sortWay1">
                                <option value="none">none</option>
                                <option value="asc">ascending</option>
                                <option value="desc">descending</option>
                            </Form.Control>
                        </InputGroup>
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} className="justify-content-center m-3">
                        <Col md="auto">
                            <ButtonGroup>
                                <Button type="submit" variant="primary" > Search </Button>
                                <Button variant="primary" onClick={resetSearch}> Reset search </Button>
                            </ButtonGroup>
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
                        <Card.Text>{"Last appointment: " + moment(value.appointmentStart).format("DD MMM YYYY hh:mm a")} </Card.Text>
                        {currUserType === 'DERMATOLOGIST' &&
                            <Card.Text>{value.pharmacy} </Card.Text>
                        }
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