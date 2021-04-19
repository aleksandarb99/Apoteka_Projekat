import axios from 'axios'
import React, { useState } from 'react'
import { Button, Form, Modal } from 'react-bootstrap'
import MedicineNameFormGroup from "../utilComponents/medicineFormGroups/MedicineNameFromGroup"
import MedicineCodeFormGroup from "../utilComponents/medicineFormGroups/MedicineCodeFormGroup"
import MedicineContentFormGroup from "../utilComponents/medicineFormGroups/MedicineContentFormGroup"
import SideEffectsFormGroup from "../utilComponents/medicineFormGroups/SideEffectsFormGroup"
import AdditionalNotesFormGroup from "../utilComponents/medicineFormGroups/AdditionalNotesFormGroup"
import ErrorModal from '../utilComponents/modals/ErrorModal'

function AddMedicineModal(props) {

    const [form, setForm] = useState({})
    const [validated, setValidated] = useState(false)

    const [showErrorModal, setShowErrorModal] = useState(false);

    const setField = (field, value) => {
        setForm({
            ...form,
            [field]: value
        })
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        event.stopPropagation()

        const f = event.currentTarget;

        if (f.checkValidity() === true) {
            setValidated(true)
            sendPostRequest()
        }
    }

    const sendPostRequest = () => {
        axios
            .post('http://localhost:8080/api/medicine/', form)
            .then(() => {
                setForm({})
                props.onSuccess()
                props.onHide()
                alert('Medicine added successfully')
            })
            .catch(() => {
                setShowErrorModal(true);
            })
    }

    return (
        <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Add new medicine
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form noValidate validated={validated} onSubmit={handleSubmit}>
                    <MedicineNameFormGroup onChange={(event) => setField('code', event.target.value)} />
                    <MedicineCodeFormGroup onChange={(event) => setField('name', event.target.value)} />
                    <MedicineContentFormGroup onChange={(event) => setField('content', event.target.value)} />
                    <SideEffectsFormGroup onChange={(event) => setField('sideEffects', event.target.value)} />

                    <Form.Group controlId="userTypeSelect">
                        <Form.Label>Recipe Required</Form.Label>
                        <Form.Control as="select" onChange={(event) => setField('recipeRequired', event.target.value)}>
                            <option value="REQUIRED">Required</option>
                            <option value="NOTREQUIRED">Not required</option>
                        </Form.Control>
                    </Form.Group>

                    <Form.Group controlId="userTypeSelect">
                        <Form.Label>Daily Intake</Form.Label>
                        <Form.Control
                            type="number"
                            onChange={(event) => setField('dailyIntake', event.target.value)}
                            step="0.1"
                            min="0"
                            max="10">
                        </Form.Control>
                    </Form.Group>

                    <AdditionalNotesFormGroup onChange={(event) => setField('additionalNotes', event.target.value)} />
                    <Button variant="primary" type="submit">Submit</Button>
                </Form>
            </Modal.Body>
            <Modal.Footer>
            </Modal.Footer>
            <ErrorModal show={showErrorModal} onHide={() => setShowErrorModal(false)} message="Something went wrong."></ErrorModal>
        </Modal>
    )
}

export default AddMedicineModal
