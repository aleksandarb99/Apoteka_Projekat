import React, { useState } from 'react'
import { Button, Form, Modal } from 'react-bootstrap'
import api from '../../app/api';
import { getErrorMessage } from '../../app/errorHandler';
import { useToasts } from 'react-toast-notifications';

const AddMedicineFormModal = (props) => {
    const [form, setForm] = useState("");
    const { addToast } = useToasts();

    const handleSubmit = (e) => {
        e.preventDefault();
        e.stopPropagation();

        api.post(`/api/medicine-forms/${form}`)
            .then(() => {
                addToast("Medicine form added successfully", { appearance: "success" })
                props.onHide()
            })
            .catch((err) => {
                addToast(getErrorMessage(err), { appearance: "error" })
            });
    }

    return (
        <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Add medicine form
                </Modal.Title>
            </Modal.Header>

            <Modal.Body>
                <Form onSubmit={handleSubmit}>
                    <Form.Group>
                        <Form.Label>Medicine form</Form.Label>
                        <Form.Control
                            type="text"
                            onChange={(event) => setForm(event.target.value)}
                            required>
                        </Form.Control>
                    </Form.Group>
                    <Button variant="primary" type="submit">Confirm</Button>
                    <Button variant="secondary" onClick={props.onHide}>Cancel</Button>
                </Form>
            </Modal.Body>
        </Modal>
    )
}

export default AddMedicineFormModal
