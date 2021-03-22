import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { Button, Form, Modal } from 'react-bootstrap'

function EditPharmacyModal(props) {

    const [form, setForm] = useState({})
    const [errors, setErrors] = useState({})

    const showHandler = () => {
        let defaultForm = { 'name': props.pharmacy.name,
                            'description': props.pharmacy.description,
                            'location': props.pharmacy.location }
        setForm(defaultForm)
        console.log(form)
        findFormErrors();
    }

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
        }
      }

    const sendPostRequest = () => {
        console.log(form)
        axios
            .put('http://localhost:8080/api/pharmacy/' + props.pharmacy.id, form)
            .then(() => {
                setForm({})
                alert('Pharmacy updated successfully')
                props.onSuccess()
                props.onHide()
            })
    }

    return (
        <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered onShow={showHandler}>
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
                                defaultValue={props.pharmacy.name}
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
                                defaultValue={props.pharmacy.description}
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
                                defaultValue={props.pharmacy.location}
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

export default EditPharmacyModal
