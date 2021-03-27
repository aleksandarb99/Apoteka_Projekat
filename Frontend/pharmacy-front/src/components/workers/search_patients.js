import React, { useState, useEffect } from "react";
import  {Row, Form, Button, Container, Col} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from "axios";

function SearchPatPage() {
  const [patients, setPatients] = useState([]); 

//   useEffect(() => {
//     async function fetchPatients() {
//       const request = await axios.get("http://localhost:8080/api/patients/all");
//       setPatients(request.data);

//       return request;
//     }
//     fetchPatients();
//   }, []);

  return (
    
      <Container style={{height: '90vh'}}>
        <Row className="justify-content-center m-3">
          <h4>Search patients</h4>
        </Row>

        <Row className="justify-content-center m-3">
          <Form>
              <Form.Group as={Row} className="align-items-center">
                  <Form.Label> First name: </Form.Label>
                  <Col>
                  <Form.Control type="text" placeholder="Enter first name..." />
                  </Col>

                  <Form.Label> Last name: </Form.Label>
                  <Col>
                  <Form.Control type="text" placeholder="Enter last name..." />
                  </Col>

                  <Col className="justify-content-center">
                  <Button variant="primary" type="submit" > Search </Button>
                  </Col>
              </Form.Group>
              
            </Form>
        </Row>
        
      </Container>
    
  );
}

export default SearchPatPage;