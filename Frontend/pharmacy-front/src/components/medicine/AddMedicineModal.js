import axios from 'axios'
import React, { useState } from 'react'
import { Button, Form, Modal } from 'react-bootstrap'
import MedicineNameFormGroup from "../utilComponents/medicineFormGroups/MedicineNameFromGroup"
import MedicineCodeFormGroup from "../utilComponents/medicineFormGroups/MedicineCodeFormGroup"
import MedicineContentFormGroup from "../utilComponents/medicineFormGroups/MedicineContentFormGroup"
import SideEffectsFormGroup from "../utilComponents/medicineFormGroups/SideEffectsFormGroup"
import AdditionalNotesFormGroup from "../utilComponents/medicineFormGroups/AdditionalNotesFormGroup"
import { useToasts } from 'react-toast-notifications';
import { getErrorMessage } from '../../app/errorHandler'

function AddMedicineModal(props) {

    // TODO add default value
    const [form, setForm] = useState({})
    const [validated, setValidated] = useState(false)
    const { addToast } = useToasts();

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
                addToast("Medicine added successfully.", { appearance: 'success' });
            })
            .catch((err) => {
                addToast(getErrorMessage(err), { appearance: 'error' })
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
                    <MedicineNameFormGroup onChange={(event) => setField('name', event.target.value)} />
                    <MedicineCodeFormGroup onChange={(event) => setField('code', event.target.value)} />
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

                    <Form.Group>
                        <Form.Label>Points</Form.Label>
                        <Form.Control
                            type="number"
                            onChange={(event) => setField('points', event.target.value)}
                            defaultValue={0}
                            min={0}
                            max={100.00}
                            step={0.01}
                        />
                    </Form.Group>

                    <AdditionalNotesFormGroup onChange={(event) => setField('additionalNotes', event.target.value)} />
                    <Button variant="primary" type="submit">Submit</Button>
                </Form>
            </Modal.Body>
            <Modal.Footer>
            </Modal.Footer>
        </Modal>
    )
}

export default AddMedicineModal
