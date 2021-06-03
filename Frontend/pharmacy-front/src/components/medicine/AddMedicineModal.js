import axios from 'axios'
import React, { Fragment, useEffect, useState } from 'react'
import { Button, Col, Container, Form, Modal, Row } from 'react-bootstrap'
import MedicineNameFormGroup from "../utilComponents/medicineFormGroups/MedicineNameFromGroup"
import MedicineCodeFormGroup from "../utilComponents/medicineFormGroups/MedicineCodeFormGroup"
import MedicineContentFormGroup from "../utilComponents/medicineFormGroups/MedicineContentFormGroup"
import SideEffectsFormGroup from "../utilComponents/medicineFormGroups/SideEffectsFormGroup"
import AdditionalNotesFormGroup from "../utilComponents/medicineFormGroups/AdditionalNotesFormGroup"
import { useToasts } from 'react-toast-notifications';
import { getErrorMessage } from '../../app/errorHandler'
import { Token, Typeahead } from 'react-bootstrap-typeahead'
import "react-bootstrap-typeahead/css/Typeahead.css";
import api from '../../app/api'
import Validator from '../../app/validator'

function AddMedicineModal(props) {

    const [form, setForm] = useState({
        name: '',
        code: '',
        content: '',
        sideEffects: '',
        additionalNotes: '',
        recipeRequired: 'REQUIRED',
        dailyIntake: 0,
        points: '',
        substitutes: []
    })
    const [multiSelections, setMultiSelections] = useState([]);
    const [medicines, setMedicines] = useState([]);
    const { addToast } = useToasts();

    const setField = (field, value) => {
        setForm({
            ...form,
            [field]: value
        })
    }

    const validateForm = () => {
        return Validator['medicineName'](form['name'])
            && Validator['medicineCode'](form['code'])
            && Validator['medicineContent'](form['content'])
            && Validator['sideEffects'](form['sideEffects'])
            && Validator['additionalNotes'](form['additionalNotes'], false)
    }

    useEffect(() => {
        fetchMedicine()
    }, [])

    function fetchMedicine() {
        api.get(`http://localhost:8080/api/medicine/`)
            .then((res) => {
                setMedicines(res.data);
            });
    }

    const showHandler = () => {
        fetchMedicine();
        setForm({
            ...form,
            name: '',
            code: '',
            content: '',
            sideEffects: '',
            additionalNotes: '',
            recipeRequired: 'REQUIRED',
            dailyIntake: 0,
            points: 0,
            substitutes: []
        })
        setMultiSelections([])
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        event.stopPropagation()

        if (validateForm()) {
            sendPostRequest()
        }
    }

    const sendPostRequest = () => {
        let data = {
            ...form,
            substitutes: multiSelections
        }
        axios
            .post('http://localhost:8080/api/medicine/', data)
            .then(() => {
                setForm({})
                props.onSuccess()
                props.onHide()
                addToast("Medicine added successfully.", { appearance: 'success' });
            })
            .catch((err) => {
                addToast(getErrorMessage(err), { appearance: 'error' })
            })
    }

    return (
        <Modal {...props} centered onShow={showHandler}>
            <Modal.Header closeButton>
                <Modal.Title>
                    Add new medicine
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form onSubmit={handleSubmit}>
                    <Row>
                        <Col md={6}>
                            <MedicineNameFormGroup onChange={(event) => setField('name', event.target.value)} />
                        </Col>
                        <Col md={6}>
                            <MedicineCodeFormGroup onChange={(event) => setField('code', event.target.value)} />
                        </Col>
                    </Row>
                    <MedicineContentFormGroup onChange={(event) => setField('content', event.target.value)} />
                    <Row>
                        <Col>
                        </Col>
                        <Col>
                        </Col>
                    </Row>
                    <SideEffectsFormGroup onChange={(event) => setField('sideEffects', event.target.value)} />
                    <Row>
                        <Col md={4}>
                            <Form.Group controlId="userTypeSelect">
                                <Form.Label>Recipe Required</Form.Label>
                                <Form.Control as="select" onChange={(event) => setField('recipeRequired', event.target.value)}>
                                    <option value="REQUIRED">Required</option>
                                    <option value="NOTREQUIRED">Not required</option>
                                </Form.Control>
                            </Form.Group>
                        </Col>
                        <Col md={4}>
                            <Form.Group controlId="userTypeSelect">
                                <Form.Label>Daily Intake</Form.Label>
                                <Form.Control
                                    type="number"
                                    onChange={(event) => setField('dailyIntake', event.target.value)}
                                    defaultValue={0}
                                    step="0.1"
                                    min="0"
                                    max="10">
                                </Form.Control>
                            </Form.Group>
                        </Col>
                        <Col md={4}>
                            <Form.Group>
                                <Form.Label>Points</Form.Label>
                                <Form.Control
                                    type="number"
                                    onChange={(event) => setField('points', event.target.value)}
                                    defaultValue={0}
                                    min={0}
                                    max={10000}
                                    step={1}
                                />
                            </Form.Group>
                        </Col>
                    </Row>
                    <AdditionalNotesFormGroup onChange={(event) => setField('additionalNotes', event.target.value)} />
                    <Form.Group>
                        <Form.Label>Medicine substitutes</Form.Label>
                        <Typeahead
                            id="basic-typeahead-multiple"
                            labelKey={(option) => `${option.code} -- ${option.name}`}
                            multiple
                            onChange={setMultiSelections}
                            options={medicines}
                            placeholder="Select substitutes..."
                            selected={multiSelections}
                            renderToken={(option, { onRemove }, index) => (
                                <Token
                                    key={index}
                                    onRemove={onRemove}
                                    option={option}>
                                    {`${option.name}`}
                                </Token>
                            )}
                        />
                    </Form.Group>
                    <Button variant="primary" type="submit">Submit</Button>
                </Form>
            </Modal.Body>
        </Modal >
    )
}

export default AddMedicineModal
