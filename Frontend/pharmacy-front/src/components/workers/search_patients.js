import React, { useState, useEffect } from "react";
import  {Row, Form, Button, Container, Col, Card, Modal} from "react-bootstrap";
import AppointmentsModal from "./appointments_modal";
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from "axios";

function SearchPatPage() {
  const [patients, setPatients] = useState([]);
  const [fName, setFName] = useState("");
  const [lName, setLName] = useState(""); 
  const [patient, setPatient] = useState({});  //appointments of currently picked patient
  const [showModal, setShowModal] = useState(false);

  useEffect(() => {
    async function fetchPatients() {
      const request = await axios.get("http://localhost:8080/api/patients/all");
      setPatients(request.data);

      return request;
    }
    fetchPatients();
  }, []);

  const formSearch = event => {
    event.preventDefault();
    console.log("fn" + fName);
    console.log("fn" + lName);
    if (fName.length === 0 &&  lName.length === 0 ){
      axios.get("http://localhost:8080/api/patients/all").then((resp)=>setPatients(resp.data));
    }else{
      axios.get("http://localhost:8080/api/patients/search", 
            { params: {firstName:fName, lastName:lName}}).then((resp)=>setPatients(resp.data));
    } 
  }

  const resetSearch = function() {
    axios.get("http://localhost:8080/api/patients/all").then((resp)=>setPatients(resp.data)).catch((resp) => setPatients([]));
    setFName("");
    setLName("");
  }

  const onShowAppointmentsButton = function(pat_to_show){
    setPatient({
      "patient": pat_to_show?.email,
      "worker": 5, //TODO promeniti, hardkodovano
      "patientName": pat_to_show?.firstName + " " + pat_to_show?.lastName
    });
    console.log(pat_to_show);
    setShowModal(true);
  }

  return (
    
      <Container>
        <Row className="justify-content-center m-3">
          <h4>Search patients</h4>
        </Row>

        <Row className="justify-content-center m-3">
          <Form onSubmit={formSearch}>
              <Form.Group as={Row} className="align-items-center">
                  <Form.Label> First name: </Form.Label>
                  <Col>
                  <Form.Control type="text" name="firstName" value={fName} onChange={(e)=>setFName(e.target.value)}
                          placeholder="Enter first name..." />
                  </Col>

                  <Form.Label> Last name: </Form.Label>
                  <Col>
                  <Form.Control type="text" name="lastName" value={lName} onChange={(e)=>setLName(e.target.value)}
                          placeholder="Enter last name..." />
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
              console.log(value);
              return (<Row className="justify-content-center m-5 align-items-center" key={index}>
                <Col>
                <Card fluid>
                  <Card.Body>
                    <Card.Title>{value.firstName + " " + value.lastName} </Card.Title>
                    <Button variant="secondary" onClick={() => onShowAppointmentsButton(value)}> Upcomming appointments </Button>
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

export default SearchPatPage;