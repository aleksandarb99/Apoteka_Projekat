import React from 'react'
import { Col, Container, FormControl, FormGroup, FormLabel, Row } from 'react-bootstrap'

const ComplaintResponseDetails = ({ complaintResponse }) => {
    return (
        <div className="border border-primary">
            <Container style={{ padding: '10px', border: '1px black' }}>
                <Row className="justify-content-between">
                    <Col md={5}>
                        <FormGroup>
                            <FormLabel>
                                For: {complaintResponse.complaint.complaintOn}
                            </FormLabel>
                        </FormGroup>
                    </Col>
                    <Col md={3}>
                        <FormGroup>
                            <FormLabel>
                                Type: {complaintResponse.complaint.type}
                            </FormLabel>
                        </FormGroup>
                    </Col>
                    <Col md={2}>
                        <FormGroup>
                            <FormLabel>
                                Complaint Date: {new Date(complaintResponse.complaint.date).toLocaleDateString("sr-sp")}
                            </FormLabel>
                        </FormGroup>
                    </Col>
                    <Col md={2}>
                        <FormGroup>
                            <FormLabel>
                                Response Date: {new Date(complaintResponse.date).toLocaleDateString("sr-sp")}
                            </FormLabel>
                        </FormGroup>
                    </Col>
                </Row>
                <FormGroup>
                    <FormLabel>Complaint text</FormLabel>
                    <FormControl
                        as="textarea"
                        value={complaintResponse.complaint.content}
                        disabled />
                </FormGroup>
                <FormGroup>
                    <FormLabel>Response text</FormLabel>
                    <FormControl
                        as="textarea"
                        value={complaintResponse.responseText}
                        disabled />
                </FormGroup>
            </Container>
        </div>
    )
}

export default ComplaintResponseDetails
