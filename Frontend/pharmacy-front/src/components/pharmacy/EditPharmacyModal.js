import axios from 'axios'
import React, { useState } from 'react'
import { Button, Form, Modal } from 'react-bootstrap'
import Location from '../utilComponents/Location'
import { useToasts } from 'react-toast-notifications';
import { getErrorMessage } from '../../app/errorHandler';

function EditPharmacyModal(props) {

    const [form, setForm] = useState({})
    const [errors, setErrors] = useState({})
    const [address, setAddress] = useState({});
    const { addToast } = useToasts();

    const showHandler = () => {
        let defaultForm = {
            'name': props.pharmacy.name,
            'description': props.pharmacy.description,
        }
        setAddress(props.pharmacy.address)
        setForm(defaultForm)
        findFormErrors();
    }

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

    const findFormErrors = () => {
        const { name, description, location } = form
        const { city, street, country } = address
        const newErrors = {}
        // name errors
        if (!name || name === '') newErrors.name = 'Name cannot be blank!'
        else if (name.length > 40) newErrors.name = 'Name is too long!'
        // Description errors
        if (!description || description === '') newErrors.description = 'Description cannot be blank!'
        else if (description.length > 180) newErrors.description = 'Description is too long!'
        // City errors
        if (!city || city === '') newErrors.city = 'City cannot be blank!'
        else if (city.length > 40) newErrors.city = 'City name is too long!'
        // Street errors
        if (!street || street === '') newErrors.street = 'Street cannot be blank!'
        else if (street.length > 60) newErrors.street = 'Street name is too long!'
        // Country errors
        if (!country || country === '') newErrors.country = 'Country cannot be blank!'
        else if (country.length > 40) newErrors.country = 'Country name is too long!'

        return newErrors
    }

    const handleSubmit = e => {
        e.preventDefault()
        const newErrors = findFormErrors()

        if (Object.keys(newErrors).length > 0) {
            setErrors(newErrors)
        } else {
            sendPostRequest()
        }
    }

    const sendPostRequest = () => {
        let data = convertForm();
        console.log(data)
        axios
            .put('http://localhost:8080/api/pharmacy/' + props.pharmacy.id, data)
            .then(() => {
                setForm({})
                addToast("Pharmacy updated successfully.", { appearance: 'success' });
                props.onSuccess()
                props.onHide()
            })
            .catch((err) => {
                addToast(getErrorMessage(err), { appearance: 'error' })
            })
    }

    const convertForm = () => {
        let data = {}
        data.id = props.pharmacy.id
        data.name = form.name;
        data.description = form.description;
        data.pointsForAppointment = form.pointsForAppointment;
        data.address = address
        return data;
    }

    return (
        <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered onShow={showHandler}>
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Edit pharmacy
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group>
                        <Form.Label>Name</Form.Label>
                        <Form.Control
                            type="text"
                            defaultValue={props.pharmacy.name}
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
                            defaultValue={props.pharmacy.description}
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
                            defaultValue={props.pharmacy.pointsForAppointment}
                            min={0}
                            max={100.00}
                            step={0.01}
                        />
                    </Form.Group>

                    <Location onChange={(address) => setAddress(address)} defAddress={props.pharmacy.address}></Location>

                    <Button variant="primary" onClick={handleSubmit}>Submit</Button>
                </Form>
            </Modal.Body>
            <Modal.Footer>
            </Modal.Footer>
        </Modal>
    )
}

export default EditPharmacyModal
