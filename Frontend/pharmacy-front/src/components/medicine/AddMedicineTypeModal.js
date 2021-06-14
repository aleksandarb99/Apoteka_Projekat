import React, { useState } from 'react'
import { Button, Form, Modal } from 'react-bootstrap'
import api from '../../app/api';
import { getErrorMessage } from '../../app/errorHandler';
import { useToasts } from 'react-toast-notifications';

const AddMedicineTypeModal = (props) => {
    const [type, setType] = useState("");
    const { addToast } = useToasts();

    const handleSubmit = (e) => {
        e.preventDefault();
        e.stopPropagation();

        let data = { name: type }
        api.post(`http://localhost:8080/api/medicine-types/`, data)
            .then(() => {
                addToast("Medicine type added successfully", { appearance: "success" })
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
                    Add medicine type
                </Modal.Title>
            </Modal.Header>

            <Modal.Body>
                <Form onSubmit={handleSubmit}>
                    <Form.Group>
                        <Form.Label>Medicine type</Form.Label>
                        <Form.Control
                            type="text"
                            onChange={(event) => setType(event.target.value)}
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

export default AddMedicineTypeModal
