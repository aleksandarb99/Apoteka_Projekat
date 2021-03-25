import axios from 'axios'
import React, { useState } from 'react'
import { Button, Form, Modal } from 'react-bootstrap'

function AddPharmacyModal(props) {

    const [form, setForm] = useState({})
    const [errors, setErrors] = useState({})

    const setField = (field, value) => {
        setForm({
            ...form,
            [field]: value
        })

        if ( !!errors[field] ) setErrors({
            ...errors,
            [field]: null
          })
    }

    const findFormErrors = () => {
        const {name, description, location } = form
        const newErrors = {}
        // name errors
        if ( !name || name === '' ) newErrors.name = 'Name cannot be blank!'
        else if ( name.length > 40 ) newErrors.name = 'Name is too long!'
        // Description errors
        if ( !description || description === '' ) newErrors.description = 'Description cannot be blank!'
        else if ( description.length > 60 ) newErrors.description = 'Description is too long!'
        // Location errors
        if ( !location || location === '' ) newErrors.location = 'Location cannot be blank!'
        else if ( location.length > 40 ) newErrors.location = 'Location is too long!'

        return newErrors
    }

    const handleSubmit = e => {
        e.preventDefault()
        const newErrors = findFormErrors()

        if ( Object.keys(newErrors).length > 0 ) {
            setErrors(newErrors)
        } else {
            sendPostRequest()
            alert('Pharmacy added successfully')
        }
      }

    const sendPostRequest = () => {
        console.log(form)
        axios
            .post('http://localhost:8080/api/pharmacy/', form)
            .then(() => {
                setForm({})
                props.onSuccess()
                props.onHide()
            })
    }

    return (
        <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Add new pharmacy
                </Modal.Title>
            </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group>
                            <Form.Label>Name</Form.Label>
                            <Form.Control 
                                type="text" 
                                placeholder="Name"
                                isInvalid={ !!errors.name }
                                onChange={ e => setField('name', e.target.value) }
                            />
                            <Form.Control.Feedback type='invalid'>
                                { errors.name }
                            </Form.Control.Feedback>
                        </Form.Group>

                        <Form.Group controlId="pharmacyDescription">
                            <Form.Label>Description</Form.Label>
                            <Form.Control 
                                type="text" 
                                placeholder="Description"
                                isInvalid={ !!errors.description }
                                onChange={ e => setField('description', e.target.value) }
                            />
                            <Form.Control.Feedback type='invalid'>
                                { errors.description }
                            </Form.Control.Feedback>
                        </Form.Group>

                        <Form.Group controlId="pharmacyLocation">
                            <Form.Label>Location</Form.Label>
                            <Form.Control 
                                type="text" 
                                placeholder="Location"
                                isInvalid={ !!errors.location }
                                onChange={ e => setField('location', e.target.value) }
                            />
                            <Form.Control.Feedback type='invalid'>
                                { errors.location }
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Button variant="primary" onClick={handleSubmit}>Submit</Button>
                    </Form>
                </Modal.Body>
                <Modal.Footer>                    
                </Modal.Footer>
        </Modal>
    )
}

export default AddPharmacyModal
