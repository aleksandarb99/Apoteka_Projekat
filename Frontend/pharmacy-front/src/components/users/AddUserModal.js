import React, { useState } from 'react'
import { Button, Form, Modal } from 'react-bootstrap'
import PropTypes from 'prop-types'
import FirstNameFormGroup from '../utilComponents/formGroups/FirstNameFormGroup'
import LastNameFormGroup from '../utilComponents/formGroups/LastNameFormGroup'
import EmailFormGroup from '../utilComponents/formGroups/EmailFormGroup'
import PhoneNumberFormGroup from '../utilComponents/formGroups/PhoneNumberFormGroup'
import PasswordFormGroup from '../utilComponents/formGroups/PasswordFormGroup'
import CityFormGroup from '../utilComponents/formGroups/CityFormGroup'
import StreetFormGroup from '../utilComponents/formGroups/StreetFormGroup'
import CountryFormGroup from '../utilComponents/formGroups/CountryFormGroup'

function AddUserModal(props) {

    const [form, setForm] = useState({})
    const [validated, setValidated] = useState(false)

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

        if (f.checkValidity() === false) {
            alert('format error - user')
        } else {
            alert('send request')
            setValidated(true);
            console.log(form);
        }

    }

    return (
        <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Add new {props.userType}
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form noValidate validated={validated} onSubmit={handleSubmit}>
                    <FirstNameFormGroup onChange={(event) => setField('firstName', event.target.value)} />
                    <LastNameFormGroup onChange={(event) => setField('lastName', event.target.value)} />
                    <EmailFormGroup onChange={(event) => setField('email', event.target.value)}></EmailFormGroup>
                    <PasswordFormGroup onChange={(event) => setField('email', event.target.value)}></PasswordFormGroup>
                    <PhoneNumberFormGroup onChange={(event) => setField('telephone', event.target.value)}></PhoneNumberFormGroup>
                    <CityFormGroup onChange={(event) => setField('city', event.target.value)}></CityFormGroup>
                    <StreetFormGroup onChange={(event) => setField('street', event.target.value)}></StreetFormGroup>
                    <CountryFormGroup onChange={(event) => setField('country', event.target.value)}></CountryFormGroup>
                    <Button variant="primary" type="submit">Submit</Button>
                </Form>
            </Modal.Body>
            <Modal.Footer>
            </Modal.Footer>
        </Modal>
    )
}

AddUserModal.propTypes = {
    userType: PropTypes.string.isRequired
}
export default AddUserModal
