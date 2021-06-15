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
    const [code, setCode] = useState();
    const [name, setName] = useState();
    const [content, setContent] = useState();
    const [medicineTypeName, setMedicineTypeName] = useState();
    const [medicineFormName, setMedicineFormName] = useState();
    const [sideEffects, setSideEffects] = useState();
    const [recipeRequired, setRecipeRequired] = useState();
    const [dailyIntake, setDailyIntake] = useState();
    const [points, setPoints] = useState();
    const [manufacturerName, setManufacturerName] = useState();
    const [additionalNotes, setAdditionalNotes] = useState();
    const [alternativeMedicine, setAlternativeMedicine] = useState();

    const [multiSelections, setMultiSelections] = useState([]);
    const [medicines, setMedicines] = useState([]);
    const [medTypes, setMedTypes] = useState([]);
    const [medForms, setMedForms] = useState([]);
    const [manufacturers, setManufacturers] = useState([]);
    const { addToast } = useToasts();

    useEffect(() => {
        fetchMedicine();
        fetchTypesAndForms();
        fetchManufacturers();
        setMultiSelections(props.medicine['substitutes'] || [])
        setName(props.medicine.name)
        setCode(props.medicine.code)
        setContent(props.medicine.content)
        setMedicineTypeName(props.medicine.medicineType ? props.medicine.medicineType.name : "")
        setMedicineFormName(props.medicine.medicineForm ? props.medicine.medicineForm.name : "")
        setSideEffects(props.medicine.sideEffects)
        setRecipeRequired(props.medicine.recipeRequired)
        setDailyIntake(props.medicine.dailyIntake)
        setPoints(props.medicine.points)
        setManufacturerName(props.medicine.manufacturer ? props.medicine.manufacturer.name : "")
        setAdditionalNotes(props.medicine.additionalNotes)
        setAlternativeMedicine(props.medicine.alternativeMedicine)
    }, [props.medicine])

    const validateForm = () => {
        return Validator['medicineName'](name)
            && Validator['medicineCode'](code)
            && Validator['medicineContent'](content)
            && Validator['sideEffects'](sideEffects)
            && Validator['additionalNotes'](additionalNotes, false)
    }

    function fetchMedicine() {
        api.get(`/api/medicine/`)
            .then((res) => {
                setMedicines(res.data);
            });
    }

    function fetchTypesAndForms() {
        api.get(`/api/medicine-types/`)
            .then((res) => {
                setMedTypes(res.data)
            });
        api.get(`/api/medicine-forms/`)
            .then((res) => {
                setMedForms(res.data)
            });
    }

    function fetchManufacturers() {
        api.get(`/api/manufacturers/`)
            .then((res) => {
                setManufacturers(res.data)
            });
    }

    const showHandler = () => {
        if (!props.pharmacy) return;
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        event.stopPropagation()

        if (validateForm()) {
            sendPutRequest()
        }
    }

    const sendPutRequest = () => {
        let data = {
            code: code,
            name: name,
            content: content,
            medicineTypeName: medicineTypeName,
            medicineFormName: medicineFormName,
            sideEffects: sideEffects,
            recipeRequired: recipeRequired,
            dailyIntake: dailyIntake,
            points: points,
            manufacturerName: manufacturerName,
            additionalNotes: additionalNotes,
            substitutes: multiSelections
        }
        axios
            .put('/api/medicine/' + props.medicine.id, data)
            .then(() => {
                props.onSuccess()
                props.onHide()
                addToast("Medicine updated successfully.", { appearance: 'success' });
            })
            .catch((err) => {
                addToast(getErrorMessage(err), { appearance: 'error' })
            })
    }


    return (
        <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered onShow={showHandler}>
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
                                onChange={(event) => setName(event.target.value)}
                                defaultValue={!!props.medicine.name ? props.medicine.name : ""} value={name} />
                        </Col>
                        <Col md={6}>
                            <MedicineCodeFormGroup
                                onChange={(event) => setCode(event.target.value)}
                                defaultValue={!!props.medicine.code ? props.medicine.code : ""} value={code} />
                        </Col>
                    </Row>
                    <MedicineContentFormGroup
                        onChange={(event) => setContent(event.target.value)}
                        defaultValue={!!props.medicine.content ? props.medicine.content : ""} />
                    <Row>
                        <Col>
                            <Form.Group>
                                <Form.Label>Medicine Type *</Form.Label>
                                <Form.Control as="select" custom onChange={(event) => setMedicineTypeName(event.target.value)} defaultValue={!!props.medicine && !!props.medicine.medicineType ? props.medicine.medicineType.name : ""} value={medicineTypeName}>
                                    <option value="">Select....</option>
                                    {medTypes.map((mt) => {
                                        return <option key={mt.id} value={mt.name}>{mt.name}</option>
                                    })}
                                </Form.Control>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group>
                                <Form.Label>Medicine Form *</Form.Label>
                                <Form.Control as="select" custom onChange={(event) => setMedicineFormName(event.target.value)} defaultValue={!!props.medicine.medicineForm ? props.medicine.medicineForm.name : ""} value={medicineFormName}>
                                    <option value="">Select....</option>
                                    {medForms.map((mf) => {
                                        return <option key={mf.id} value={mf.name}>{mf.name}</option>
                                    })}
                                </Form.Control>
                            </Form.Group>
                        </Col>
                    </Row>
                    <SideEffectsFormGroup
                        onChange={(event) => setSideEffects(event.target.value)}
                        defaultValue={!!props.medicine.sideEffects ? props.medicine.sideEffects : ""} value={sideEffects} />
                    <Row>
                        <Col md={4}>
                            <Form.Group controlId="userTypeSelect">
                                <Form.Label>Recipe Required</Form.Label>
                                <Form.Control
                                    as="select"
                                    onChange={(event) => setRecipeRequired(event.target.value)}
                                    defaultValue={props.medicine.recipeRequired}
                                    value={recipeRequired}>

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
                                    onChange={(event) => setDailyIntake(event.target.value)}
                                    step="0.1"
                                    min="0"
                                    max="10"
                                    defaultValue={props.medicine.dailyIntake}
                                    value={dailyIntake}>
                                </Form.Control>
                            </Form.Group>
                        </Col>
                        <Col md={4}>
                            <Form.Group>
                                <Form.Label>Points</Form.Label>
                                <Form.Control
                                    type="number"
                                    onChange={(event) => setPoints(event.target.value)}
                                    defaultValue={props.medicine.points}
                                    min={0}
                                    max={10000}
                                    step={1}
                                    defaultValue={props.medicine.dailyIntake}
                                    value={points}
                                />
                            </Form.Group>
                        </Col>
                    </Row>
                    <Form.Group>
                        <Form.Label>Manufacturer *</Form.Label>
                        <Form.Control as="select" custom onChange={(event) => setManufacturerName(event.target.value)} defaultValue={!!props.medicine.manufacturer ? props.medicine.manufacturer : ""} value={manufacturerName}>
                            <option value="">Select....</option>
                            {manufacturers.map((mt) => {
                                return <option key={mt.id} value={mt.name}>{mt.name}</option>
                            })}
                        </Form.Control>
                    </Form.Group>
                    <AdditionalNotesFormGroup
                        onChange={(event) => setAdditionalNotes(event.target.value)}
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

export default EditMedicineModal;
