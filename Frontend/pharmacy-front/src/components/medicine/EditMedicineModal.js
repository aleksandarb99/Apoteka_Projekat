import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { Button, Col, Form, Modal, Row } from 'react-bootstrap'
import MedicineNameFormGroup from "../utilComponents/medicineFormGroups/MedicineNameFromGroup"
import MedicineCodeFormGroup from "../utilComponents/medicineFormGroups/MedicineCodeFormGroup"
import MedicineContentFormGroup from "../utilComponents/medicineFormGroups/MedicineContentFormGroup"
import SideEffectsFormGroup from "../utilComponents/medicineFormGroups/SideEffectsFormGroup"
import AdditionalNotesFormGroup from "../utilComponents/medicineFormGroups/AdditionalNotesFormGroup"
import { useToasts } from 'react-toast-notifications';
import { getErrorMessage } from '../../app/errorHandler';
import Validator from '../../app/validator'
import api from '../../app/api'
import { Token, Typeahead } from 'react-bootstrap-typeahead'

function EditMedicineModal(props) {

    const [form, setForm] = useState({})
    const [multiSelections, setMultiSelections] = useState([]);
    const [medicines, setMedicines] = useState([]);
    const [medTypes, setMedTypes] = useState([]);
    const [medForms, setMedForms] = useState([]);
    const { addToast } = useToasts();

    const setField = (field, value) => {
        setForm({
            ...form,
            [field]: value
        })
    }

    useEffect(() => {
        fetchMedicine();
        fetchTypesAndForms();
        setMultiSelections(props.medicine['substitutes'] || [])
        console.log(props.medicine)
        setForm({ ...props.medicine })
    }, [props.medicine])

    const validateForm = () => {
        return Validator['medicineName'](form['name'])
            && Validator['medicineCode'](form['code'])
            && Validator['medicineContent'](form['content'])
            && Validator['sideEffects'](form['sideEffects'])
            && Validator['additionalNotes'](form['additionalNotes'], false)
    }

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
            });
        api.get(`http://localhost:8080/api/medicine-forms/`)
            .then((res) => {
                setMedForms(res.data)
            });
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        event.stopPropagation()

        console.log(multiSelections)

        if (validateForm()) {
            sendPutRequest()
        }
    }

    const sendPutRequest = () => {
        let data = {
            ...form,
            substitutes: multiSelections
        }
        axios
            .put('http://localhost:8080/api/medicine/' + props.medicine.id, data)
            .then(() => {
                setForm({})
                props.onSuccess()
                props.onHide()
                addToast("Medicine updated successfully.", { appearance: 'success' });
            })
            .catch((err) => {
                addToast(getErrorMessage(err), { appearance: 'error' })
            })
    }

    return (
        <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Edit {props.medicine.name}
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form onSubmit={handleSubmit}>
                    <Row>
                        <Col md={6}>
                            <MedicineNameFormGroup
                                onChange={(event) => setField('name', event.target.value)}
                                defaultValue={!!props.medicine.name ? props.medicine.name : ""} />
                        </Col>
                        <Col md={6}>
                            <MedicineCodeFormGroup
                                onChange={(event) => setField('code', event.target.value)}
                                defaultValue={!!props.medicine.code ? props.medicine.code : ""} />
                        </Col>
                    </Row>
                    <MedicineContentFormGroup
                        onChange={(event) => setField('content', event.target.value)}
                        defaultValue={!!props.medicine.content ? props.medicine.content : ""} />
                    <Row>
                        <Col>
                            <Form.Group>
                                <Form.Control as="select" custom onChange={(event) => setField('medicineType', event.target.value)} value={props.medicine.medicineType}>
                                    {medTypes.map((mt) => {
                                        return <option key={mt.id} value={mt.name}>{mt.name}</option>
                                    })}
                                </Form.Control>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group>
                                <Form.Control as="select" custom onChange={(event) => setField('medicineForm', event.target.value)} value={props.medicine.medicineForm}>
                                    {medForms.map((mf) => {
                                        return <option key={mf.id} value={mf.name}>{mf.name}</option>
                                    })}
                                </Form.Control>
                            </Form.Group>
                        </Col>
                    </Row>
                    <SideEffectsFormGroup
                        onChange={(event) => setField('sideEffects', event.target.value)}
                        defaultValue={!!props.medicine.sideEffects ? props.medicine.sideEffects : ""} />
                    <Row>
                        <Col md={4}>
                            <Form.Group controlId="userTypeSelect">
                                <Form.Label>Recipe Required</Form.Label>
                                <Form.Control
                                    as="select"
                                    onChange={(event) => setField('recipeRequired', event.target.value)}
                                    defaultValue={props.medicine.recipeRequired}>

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
                                    step="0.1"
                                    min="0"
                                    max="10"
                                    defaultValue={props.medicine.dailyIntake}>
                                </Form.Control>
                            </Form.Group>
                        </Col>
                        <Col md={4}>
                            <Form.Group>
                                <Form.Label>Points</Form.Label>
                                <Form.Control
                                    type="number"
                                    onChange={(event) => setField('points', event.target.value)}
                                    defaultValue={props.medicine.points}
                                    min={0}
                                    max={10000}
                                    step={1}
                                    defaultValue={props.medicine.dailyIntake}
                                />
                            </Form.Group>
                        </Col>
                    </Row>
                    <AdditionalNotesFormGroup
                        onChange={(event) => setField('additionalNotes', event.target.value)}
                        defaultValue={props.medicine.additionalNotes} />
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
            </Modal.Body >
            <Modal.Footer>
            </Modal.Footer>
        </Modal >
    )
}

export default EditMedicineModal
