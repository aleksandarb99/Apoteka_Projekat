import React, { useState } from 'react'
import { Button, Col, Container, Form, FormGroup, FormLabel, Row } from 'react-bootstrap'
import api from '../../app/api'
import { getIdFromToken } from '../../app/jwtTokenUtils'

const ComplaintResponse = ({ complaint }) => {
    const [responseText, setResponseText] = useState()

    const handleSubmit = (e) => {
        e.preventDefault();
        e.stopPropagation();

        if (!responseText) {
            alert(`Please fill out response text`)
            return;
        }

        let data = {
            "responseText": responseText,
            "complaintId": complaint.id,
            "adminId": getIdFromToken(),
            "date": Date.now(),
        }

        let url = `http://localhost:8080/api/complaint-responses/${complaint.id}`;
        api.post(url, JSON.stringify(data))
            .then(() => {
                alert("Successfully submitted");
            })
            .catch((err) => {
                console.log(err)
                alert("Error. Response not submitted")
            })
    }

    return (
        <div className="border border-primary">
            <Form onSubmit={handleSubmit}>
                <Container style={{ padding: '10px', border: '1px black' }}>
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
        </div>

    )
}

export default ComplaintResponse
