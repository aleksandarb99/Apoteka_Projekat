import React, { useState, useEffect } from "react";
import {
  Col,
  Container,
  Form,
  FormGroup,
  FormLabel,
  Row,
} from "react-bootstrap";
import api from "../../app/api";

const ComplaintDetails = ({ complaint }) => {
  const [response, setResponse] = useState("");

  useEffect(() => {
    async function fetchResponse() {
      api.get(`/api/complaint-responses/${complaint.id}`).then((res) => {
        console.log(res);
        setResponse(res.data ? res.data.responseText : "");
      });
    }
    fetchResponse();
  }, []);

    return (
        <Container className="border border-primary" style={{ borderRadius: '10px', padding: '10px', marginTop: '10px', marginBottom: '10px', backgroundColor: 'white' }}>
            <Row className="justify-content-between">
                <Col md={6}>
                    <FormGroup>
                        <FormLabel>
                            For: {complaint.complaintOn}
                        </FormLabel>
                    </FormGroup>
                </Col>
                <Col md={4}>
                    <FormGroup>
                        <FormLabel>
                            Type: {complaint.type}
                        </FormLabel>
                    </FormGroup>
                </Col>
                <Col md={2}>
                    <FormGroup>
                        <FormLabel>
                            Date: {new Date(complaint.date).toLocaleDateString("sr-sp")}
                        </FormLabel>
                    </FormGroup>
                </Col>
            </Row>
            <Form.Group>
                <Form.Label>Complaint text</Form.Label>
                <Form.Control
                    as="textarea"
                    value={complaint.content}
                    disabled />
            </Form.Group>
            <Form.Group>
                <Form.Label>Response text</Form.Label>
                <Form.Control
                    as="textarea"
                    defaultValue={response}
                    value={response}
                    disabled />
            </Form.Group>
        </Container>
    )
}

export default ComplaintDetails