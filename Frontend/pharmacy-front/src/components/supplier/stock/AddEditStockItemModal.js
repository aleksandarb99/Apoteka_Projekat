import React, { useEffect, useState } from 'react'
import { Button, Form, Modal } from 'react-bootstrap';
import api from '../../../app/api';
import { Typeahead } from 'react-bootstrap-typeahead';
import { getIdFromToken } from '../../../app/jwtTokenUtils';

const AddEditStockItemModal = (props) => {
    const [singleSelection, setSingleSelection] = useState([]);
    const [amount, setAmount] = useState(1);
    const [options, setOptions] = useState([])

    useEffect(() => {
        async function fetchMedicine() {
            const response = await api.get(`http://localhost:8080/api/medicine/`);
            setOptions(response.data);
        }
        fetchMedicine()
    }, [])

    const handleSubmit = (e) => {
        e.preventDefault();
        e.stopPropagation();

        let data = {}
        data.medicineId = singleSelection[0].id;
        data.medicineName = singleSelection[0].name;
        data.amount = amount;
        api.post(`http://localhost:8080/api/suppliers/stock/${getIdFromToken()}`, data)
            .then(props.onSuccess());
    }

    return (
        <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Add medicine to stock
                </Modal.Title>
            </Modal.Header>

            <Modal.Body>
                <Form onSubmit={handleSubmit}>
                    <Form.Group>
                        <Form.Label>Medicine</Form.Label>
                        <Typeahead
                            labelKey={(option) => `${option.code} -- ${option.name}`}
                            onChange={setSingleSelection}
                            options={options}
                            placeholder="Select a medicine..."
                            selected={singleSelection}
                        />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Amount</Form.Label>
                        <Form.Control
                            type="number"
                            onChange={(event) => setAmount(event.target.value)}
                            defaultValue={1}
                            min={1}
                            max={100}
                            step={1}
                            required
                        />
                    </Form.Group>
                    <Button variant="primary" type="submit">Confirm</Button>
                    <Button variant="secondary" onClick={props.onHide}>Cancel</Button>
                </Form>
            </Modal.Body>
        </Modal>
    )
}

export default AddEditStockItemModal
