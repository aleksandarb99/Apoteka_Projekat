import React, { useState } from 'react'
import { Button, Col, Container, Form, FormGroup, FormLabel, Row } from 'react-bootstrap'
import api from '../../app/api'
import { getIdFromToken } from '../../app/jwtTokenUtils'
import { useToasts } from "react-toast-notifications"

const ComplaintResponse = ({ complaint, onSuccessfulSubmit }) => {
    const [responseText, setResponseText] = useState()
    const { addToast } = useToasts();


  const handleSubmit = (e) => {
    e.preventDefault();
    e.stopPropagation();

        if (!responseText) {
            addToast("Please fill out response text", { appearance: "warning" });
            return;
        }

        let data = {
            "responseText": responseText,
            "complaintId": complaint.id,
            "adminId": getIdFromToken(),
            "date": Date.now(),
        }

        let url = `/api/complaint-responses/${complaint.id}`;
        api.post(url, JSON.stringify(data))
            .then(() => {
                onSuccessfulSubmit()
                addToast("Successfully submitted", { appearance: "success" });
            })
            .catch((err) => {
                addToast("Error. Response not submitted!", { appearance: "success" });
            })
    }

    return (
        <Form onSubmit={handleSubmit}>
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
                        rows={5}
                        value={complaint.content}
                        disabled />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Response text</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={5}
                        onChange={(event) => { setResponseText(event.target.value) }}
                        required />
                </Form.Group>
                <Button type="submit">Submit</Button>
            </Container>
        </Form>
    )
}

export default ComplaintResponse;
