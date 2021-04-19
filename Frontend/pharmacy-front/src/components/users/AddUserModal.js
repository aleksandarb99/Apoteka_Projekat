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
import ErrorModal from '../utilComponents/modals/ErrorModal'
import SuccessModal from '../utilComponents/modals/SuccessModal'
import axios from 'axios'

function AddUserModal(props) {

    const [form, setForm] = useState({})
    const [validated, setValidated] = useState(false)

    const [showErrorModal, setShowErrorModal] = useState(false);
    const [showSuccessModal, setShowSuccessModal] = useState(false);

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
        const newForm = convertForm(form);
        console.log(newForm)
        axios
            .post('http://localhost:8080/api/users/', newForm)
            .then(() => {
                setForm({})
                props.onSuccess()
                props.onHide()
                setShowSuccessModal(true);
            })
            .catch(() => {
                setShowErrorModal(true);
            })
    }

    const convertForm = () => {
        let address = {
            'city': form['city'],
            'street': form['street'],
            'country': form['country'],
        }

        let newForm = {
            ...form,
            'address': address,
            'userType': props.usertype
        }

        delete newForm['city']
        delete newForm['street']
        delete newForm['country']

        return newForm
    }

    return (
        <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Add new {props.usertype}
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form noValidate validated={validated} onSubmit={handleSubmit}>
                    <FirstNameFormGroup onChange={(event) => setField('firstName', event.target.value)} />
                    <LastNameFormGroup onChange={(event) => setField('lastName', event.target.value)} />
                    <EmailFormGroup onChange={(event) => setField('email', event.target.value)}></EmailFormGroup>
                    <PasswordFormGroup onChange={(event) => setField('password', event.target.value)}></PasswordFormGroup>
                    <PhoneNumberFormGroup onChange={(event) => setField('telephone', event.target.value)}></PhoneNumberFormGroup>
                    <CityFormGroup onChange={(event) => setField('city', event.target.value)}></CityFormGroup>
                    <StreetFormGroup onChange={(event) => setField('street', event.target.value)}></StreetFormGroup>
                    <CountryFormGroup onChange={(event) => setField('country', event.target.value)}></CountryFormGroup>
                    <Button variant="primary" type="submit">Submit</Button>
                </Form>
            </Modal.Body>
            <Modal.Footer>
            </Modal.Footer>
            <ErrorModal show={showErrorModal} onHide={() => setShowErrorModal(false)} message="Something went wrong. User registration failed."></ErrorModal>
            <SuccessModal show={showSuccessModal} onHide={() => setShowSuccessModal(false)} message="User added successfully."></SuccessModal>
        </Modal>
    )
}

AddUserModal.propTypes = {
    usertype: PropTypes.string.isRequired
}
export default AddUserModal
