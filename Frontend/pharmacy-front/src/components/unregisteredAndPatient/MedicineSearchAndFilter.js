import React, { useEffect, useState } from "react";
import { Col, Container, Form, Row, Button } from "react-bootstrap";
import { Typeahead } from "react-bootstrap-typeahead";
import api from "../../app/api";

const MedicineSearchAndFilter = (props) => {
    const [medicineType, setMedicineType] = useState("");
    const [medicineForm, setMedicineForm] = useState("");
    const [medTypes, setMedTypes] = useState([]);
    const [medForms, setMedForms] = useState([]);
    const [medName, setMedName] = useState("");

    useEffect(() => {
        api.get(`/api/medicine-types/`).then((res) => {
            setMedTypes(res.data);
        }).catch(() => { });
        api.get(`/api/medicine-forms/`).then((res) => {
            setMedForms(res.data);
        }).catch(() => { });
    }, []);

    const handleSubmit = (event) => {
        event.preventDefault();
        event.stopPropagation();

        props.updateParams(generateSearchParams());
    };

    const generateSearchParams = () => {
        let searchParam = `name:${medName},medicineType:${medicineType},medicineForm:${medicineForm}`
        return searchParam
    }

    return (
        <Form onSubmit={handleSubmit}>
            <Container style={{ marginBottom: '20px' }}>
                <Row>
                    <Col md={5}>
                        <Form.Group>
                            <Form.Label>Name:</Form.Label>
                        </Form.Group>
                    </Col>
                    <Col md={3}>
                        <Form.Group>
                            <Form.Label>Type:</Form.Label>
                        </Form.Group>
                    </Col>
                    <Col md={3}>
                        <Form.Group>
                            <Form.Label>Form:</Form.Label>
                        </Form.Group>

                    </Col>
                </Row>
                <Row>
                    <Col md={5}>
                        <Form.Group>
                            <Form.Control
                                type="text"
                                onChange={(event) => { setMedName(event.target.value) }} />
                        </Form.Group>
                    </Col>
                    <Col md={3}>
                        <Form.Group>
                            <Form.Control as="select" custom onChange={(event) => { setMedicineType(event.target.value) }}>
                                <option value="">Show all</option>
                                {medTypes.map((mt) => {
                                    return <option value={mt.name}>{mt.name}</option>
                                })}
                            </Form.Control>
                        </Form.Group>
                    </Col>
                    <Col md={3}>
                        <Form.Group>
                            <Form.Control as="select" custom onChange={(event) => { setMedicineForm(event.target.value) }}>
                                <option value="">Show all</option>
                                {medForms.map((mf) => {
                                    return <option value={mf.name}>{mf.name}</option>
                                })}
                            </Form.Control>
                        </Form.Group>
                    </Col>
                    <Col md={1}>
                        <Button type="submit">Submit</Button>
                    </Col>
                </Row>
            </Container>
        </Form >
    )
}

export default MedicineSearchAndFilter
