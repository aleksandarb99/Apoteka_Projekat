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
        // name: '',
        // code: '',
        // content: '',
        // sideEffects: '',
        // additionalNotes: '',
        // recipeRequired: 'REQUIRED',
        // medicineType: '',
        // medicineForm: '',
        // manufacturer: '',
        // dailyIntake: 0,
        // points: 0,
        // manufacturer: '',
        // substitutes: []
    })
    const [multiSelections, setMultiSelections] = useState([]);
    const [medicines, setMedicines] = useState([]);
    const [medTypes, setMedTypes] = useState([]);
    const [manufacturers, setManufacturers] = useState([]);
    const [medForms, setMedForms] = useState([]);
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

    function fetchTypesAndForms() {
        api.get(`http://localhost:8080/api/medicine-types/`)
            .then((res) => {
                setMedTypes(res.data)
                setField('medicineType', res.data[0].name || '');
            });
        api.get(`http://localhost:8080/api/medicine-forms/`)
            .then((res) => {
                setMedForms(res.data)
                setField('medicineForm', res.data[0].name || '');
            });
    }

    function fetchManufacturers() {
        api.get(`http://localhost:8080/api/manufacturers/`)
            .then((res) => {
                setManufacturers(res.data)
                setField('manufacturer', res.data[0].name || '');
            });
    }


    const showHandler = () => {
        setForm({
            ...form,
            recipeRequired: 'REQUIRED',
            dailyIntake: 0,
            points: 0,
            substitutes: []
        })
        fetchMedicine();
        fetchTypesAndForms();
        fetchManufacturers();
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

        if (!form['medicineType'] || !form['medicineForm'] || !form['manufacturer'] || !form['required']) {
            addToast("Please fill required fields", { appearance: 'error' })
            return
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
                            <Form.Group>
                                <Form.Label>Medicine Type *</Form.Label>
                                <Form.Control as="select" custom onChange={(event) => setField('medicineType', event.target.value)} defaultValue={form['medicineType']}>
                                    <option value="">Select...</option>
                                    {medTypes.map((mt) => {
                                        return <option value={mt.name}>{mt.name}</option>
                                    })}
                                </Form.Control>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group>
                                <Form.Label>Medicine Form *</Form.Label>
                                <Form.Control as="select" custom onChange={(event) => setField('medicineForm', event.target.value)} defaultValue={form['medicineForm']}>
                                    <option value="">Select...</option>
                                    {medForms.map((mf) => {
                                        return <option value={mf.name}>{mf.name}</option>
                                    })}
                                </Form.Control>
                            </Form.Group>
                        </Col>
                    </Row>
                    <SideEffectsFormGroup onChange={(event) => setField('sideEffects', event.target.value)} />
                    <Row>
                        <Col md={4}>
                            <Form.Group controlId="userTypeSelect">
                                <Form.Label>Recipe Required *</Form.Label>
                                <Form.Control as="select" onChange={(event) => setField('recipeRequired', event.target.value)}>
                                    <option value="">Select...</option>
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
                    <Form.Group>
                        <Form.Label>Manufacturer *</Form.Label>
                        <Form.Control as="select" custom onChange={(event) => setField('manufacturer', event.target.value)} value={form['manufacturer']}>
                            <option value="">Select....</option>
                            {manufacturers.map((mt) => {
                                return <option value={mt.name}>{mt.name}</option>
                            })}
                        </Form.Control>
                    </Form.Group>
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

export default AddMedicineModal;
