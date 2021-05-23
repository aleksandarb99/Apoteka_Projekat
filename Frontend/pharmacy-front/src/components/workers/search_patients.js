import React, { useState, useEffect } from "react";
import  {Row, Form, Button, Container, Col, Card, ButtonGroup} from "react-bootstrap";
import AppointmentsModal from "./appointments_modal";
import 'bootstrap/dist/css/bootstrap.min.css';
import api from "../../app/api";
import { getIdFromToken } from '../../app/jwtTokenUtils';
import { useToasts } from "react-toast-notifications";

function SearchPatPage() {
  const [patients, setPatients] = useState([]);
  const [fName, setFName] = useState("");
  const [lName, setLName] = useState(""); 
  const [patient, setPatient] = useState({});  //appointments of currently picked patient
  const [showModal, setShowModal] = useState(false);
  const [currFetchState, setCurrFetchState] = useState('Loading...');
  const { addToast } = useToasts();

  useEffect(() => {
    async function fetchPatients() {
      await api.get("http://localhost:8080/api/patients/all")
                    .then((resp) => setPatients(resp.data))
                    .catch(()=> setPatients([]));
    }
    fetchPatients();
  }, []);

  const formSearch = event => {
    event.preventDefault();
    setCurrFetchState('Loading...');
    if (fName.length === 0 &&  lName.length === 0 ){
      api.get("http://localhost:8080/api/patients/all").then((resp)=> 
        {
          setPatients(resp.data);
          if (resp.data.length === 0){
            setCurrFetchState('No result!');
          }
        })
        .catch(() => {setPatients([]); setCurrFetchState('No result!');});
    }else{
      api.get("http://localhost:8080/api/patients/search", { params: {firstName:fName, lastName:lName}})
          .then((resp)=> 
            {
              setPatients(resp.data);
              if (resp.data.length === 0){
                setCurrFetchState('No result!');
              }
            })
          .catch(() => {setPatients([]); setCurrFetchState('No result!');});
    } 
  }

  const resetSearch = function() {
    setCurrFetchState('Loading...');
    api.get("http://localhost:8080/api/patients/all").then((resp)=> 
      {
        setPatients(resp.data);
        if (resp.data.length === 0){
          setCurrFetchState('No result!');
        }
      }).catch(() => setPatients([]));
    setFName("");
    setLName("");
  }

  const onShowAppointmentsButton = function(pat_to_show){
    let id = getIdFromToken();
    if (!id){
        addToast("Token error!", { appearance: "error" });
        setPatients([]);
        return;
    }
    setPatient({
      "patient": pat_to_show?.email,
      "worker": id, 
      "patientName": pat_to_show?.firstName + " " + pat_to_show?.lastName
    });
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

                  <Col>
                  <ButtonGroup size="sm">
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
              <Row className="justify-content-center m-3 align-items-center"><h3>{currFetchState}</h3></Row>
            }

            {patients.map((value, index) => {
              console.log(value);
              return (<Row className="justify-content-center m-5 align-items-center" key={index}>
                <Col>
                <Card fluid>
                  <Card.Body>
                    <Card.Title>{value.firstName + " " + value.lastName} </Card.Title>
                    <Button variant="secondary" onClick={() => onShowAppointmentsButton(value)}> Upcomming appointments with {value.firstName + ' ' + value.lastName}</Button>
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