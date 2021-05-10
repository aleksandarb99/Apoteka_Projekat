import React, { useEffect, useState } from 'react'
import { Col, Container, Form, Row, Button } from 'react-bootstrap'
import { Typeahead } from 'react-bootstrap-typeahead'
import api from '../../app/api';

const MedicineSearchAndFilter = () => {

    const [singleSelectionType, setSingleSelectionType] = useState([]);
    const [singleSelectionForm, setSingleSelectionForm] = useState([]);
    const [medTypes, setMedTypes] = useState([]);
    const [medForms, setMedForms] = useState([]);

    useEffect(() => {
        api.get(`http://localhost:8080/api/medicine-types/`)
            .then((res) => {
                let dummy = { id: -1, name: "Prikaži sve" }
                let d = res.data
                d.push(dummy)
                setMedTypes(d)

                setSingleSelectionType([dummy])
            });
        api.get(`http://localhost:8080/api/medicine-forms/`)
            .then((res) => {
                let dummy = { id: -1, name: "Prikaži sve" }
                let d = res.data
                d.push(dummy)
                setMedForms(d)

                setSingleSelectionForm([dummy])
            });
    }, [])

    const handleSubmit = (event) => {
        event.preventDefault()
        event.stopPropagation()
    }


    return (
        <Form onSubmit={handleSubmit}>
            <Container style={{ marginBottom: '20px' }}>
                <Row>
                    <Col md={5}>
                        <Form.Group>
                            <Form.Label>Name:</Form.Label>
                            <Form.Control type="text" />
                        </Form.Group>
                    </Col>
                    <Col md={3}>
                        <Form.Group>
                            <Form.Label>Type:</Form.Label>
                            <Typeahead
                                id="medTypeTypeahead"
                                labelKey={(option) => `${option.name}`}
                                onChange={setSingleSelectionType}
                                options={medTypes}
                                placeholder={"Select type..."}
                                selected={singleSelectionType}
                            />
                        </Form.Group>
                    </Col>
                    <Col md={3}>
                        <Form.Group>
                            <Form.Label>Form:</Form.Label>
                            <Typeahead
                                id="medFormTypeahead"
                                labelKey={(option) => `${option.name}`}
                                onChange={setSingleSelectionForm}
                                options={medForms}
                                placeholder={"Select form..."}
                                selected={singleSelectionForm}
                            />
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
