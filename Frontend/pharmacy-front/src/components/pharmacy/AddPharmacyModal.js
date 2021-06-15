import React, { useEffect, useState } from 'react'
import { Button, Form, Modal } from 'react-bootstrap'
import Map from '../utilComponents/MapElement'
import { useToasts } from 'react-toast-notifications';
import { getErrorMessage } from '../../app/errorHandler';
import api from '../../app/api'
import { Token, Typeahead } from 'react-bootstrap-typeahead';

function AddPharmacyModal(props) {

    const [form, setForm] = useState({
        pharmacyAdmin: {
            id: -1,
            name: ''
        },
        name: '',
        description: '',
        pointsForAppointment: 0,
        address: {
            city: '',
            street: '',
            country: '',
            location: {
                latitude: 0,
                longitude: 0
            }
        }
    })
    const [address, setAddress] = useState({});
    const [errors, setErrors] = useState({});
    const [pharmacyAdmins, setPharmacyAdmins] = useState([]);
    const [multiSelections, setMultiSelections] = useState([]);
    const { addToast } = useToasts();

    useEffect(() => {
        async function fetchData() {
            await api
                .get('http://localhost:8080/api/users/?type=PHARMACY_ADMIN')
                .then((res) => {
                    setPharmacyAdmins(res.data);
                }).catch(() => { });
        }
        fetchData();
    }, [])

    const setField = (field, value) => {
        setForm({
            ...form,
            [field]: value
        })

        if (!!errors[field]) setErrors({
            ...errors,
            [field]: null
        })
    }

    const validate = () => {
        const { name, description } = form
        const newErrors = {}
        // name errors
        if (!name || name === '') newErrors.name = 'Name cannot be blank!'
        else if (name.length > 40) newErrors.name = 'Name is too long!'
        // Description errors
        if (!description || description === '') newErrors.description = 'Description cannot be blank!'
        else if (description.length > 300) newErrors.description = 'Description is too long!'

        return newErrors
    }

    const validateAddress = () => {
        const { city, street, country } = address;

        // City errors
        if (!city || city === '') return false;
        // Street errors
        if (!street || street === '') return false;
        // Country errors
        if (!country || country === '') return false;

        return true;
    }

    const validateAdmin = () => {
        return (!!multiSelections || multiSelections.length === 0)
    }

    const handleSubmit = e => {
        e.preventDefault()
        e.stopPropagation()
        const newErrors = validate()

        if (Object.keys(newErrors).length > 0) {
            setErrors(newErrors)
        } else {
            if (!validateAddress())
                addToast("Please select valid address", { appearance: 'warning' })
            else if (!validateAdmin())
                addToast("Please select pharmacy admins", { appearance: 'warning' })
            else
                sendPostRequest()
        }
    }

    const sendPostRequest = () => {
        let data = convertForm();
        api
            .post('http://localhost:8080/api/pharmacy/', data)
            .then(() => {
                setForm({})
                props.onSuccess()
                props.onHide()
                addToast("Pharmacy added successfully.", { appearance: 'success' });
            })
            .catch((err) => {
                addToast(getErrorMessage(err), { appearance: 'error' })
            })
    }

    const convertForm = () => {
        let data = {}
        data.name = form.name;
        data.description = form.description;
        data.address = address
        data.admins = multiSelections.map(pa => { return { 'id': pa.id, 'name': `${pa.firstName} ${pa.lastName}` } })
        return data;
    }

    return (
        <Modal {...props} aria-labelledby="contained-modal-title-vcenter" dialogClassName="modal-50w" centered>
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Add new pharmacy
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group>
                        <Form.Label>
                            Admins
                        </Form.Label>
                        <Typeahead
                            id="basic-typeahead-multiple-admins"
                            labelKey={(option) => `${option.firstName} ${option.lastName}`}
                            multiple
                            onChange={setMultiSelections}
                            options={pharmacyAdmins}
                            placeholder="Select admins..."
                            selected={multiSelections}
                            renderToken={(option, { onRemove }, index) => (
                                <Token
                                    key={index}
                                    onRemove={onRemove}
                                    option={option}>
                                    {`${option.firstName} ${option.lastName}`}
                                </Token>
                            )}
                        />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Name</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="Name"
                            isInvalid={!!errors.name}
                            onChange={e => setField('name', e.target.value)}
                        />
                        <Form.Control.Feedback type='invalid'>
                            {errors.name}
                        </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group controlId="pharmacyDescription">
                        <Form.Label>Description</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="Description"
                            isInvalid={!!errors.description}
                            onChange={e => setField('description', e.target.value)}
                        />
                        <Form.Control.Feedback type='invalid'>
                            {errors.description}
                        </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group>
                        <Form.Label>Points for appointment</Form.Label>
                        <Form.Control
                            type="number"
                            onChange={(event) => setField('pointsForAppointment', event.target.value)}
                            defaultValue={0}
                            min={0}
                            max={10000}
                            step={1}
                        />
                    </Form.Group>

                    <Map onChange={(address) => setAddress(address)}></Map>

                    <Button variant="primary" onClick={handleSubmit}>Submit</Button>
                </Form>
            </Modal.Body>
            <Modal.Footer>
            </Modal.Footer>
        </Modal>
    )
}

export default AddPharmacyModal;
