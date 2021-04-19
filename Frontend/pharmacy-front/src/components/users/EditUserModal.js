import React, { useState } from 'react'
import { Button, Form, Modal } from 'react-bootstrap'
import PropTypes from 'prop-types'
import FirstNameFormGroup from '../utilComponents/formGroups/FirstNameFormGroup'
import LastNameFormGroup from '../utilComponents/formGroups/LastNameFormGroup'
import EmailFormGroup from '../utilComponents/formGroups/EmailFormGroup'
import PhoneNumberFormGroup from '../utilComponents/formGroups/PhoneNumberFormGroup'
import CityFormGroup from '../utilComponents/formGroups/CityFormGroup'
import StreetFormGroup from '../utilComponents/formGroups/StreetFormGroup'
import CountryFormGroup from '../utilComponents/formGroups/CountryFormGroup'
import ErrorModal from '../utilComponents/modals/ErrorModal';
import SuccessModal from '../utilComponents/modals/SuccessModal'
import axios from 'axios'

function EditUserModal(props) {

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

    const showHandler = () => {
        let newForm = {
            ...props.user,
            'city': props.user.address.city,
            'street': props.user.address.street,
            'country': props.user.address.country
        }
        setForm(newForm)
    }

    const sendPostRequest = () => {
        const newForm = convertForm(form);
        console.log(newForm)
        axios
            .put('http://localhost:8080/api/users/' + props.user.id, newForm)
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
            ...props.user.address,
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
        <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered onShow={showHandler}>
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Edit {props.user.firstName + ' ' + props.user.lastName}
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form noValidate validated={validated} onSubmit={handleSubmit}>
                    <FirstNameFormGroup
                        onChange={(event) => setField('firstName', event.target.value)}
                        defaultValue={!!props.user.firstName ? props.user.firstName : ""} />
                    <LastNameFormGroup
                        onChange={(event) => setField('lastName', event.target.value)}
                        defaultValue={!!props.user.lastName ? props.user.lastName : ""} />
                    <EmailFormGroup
                        onChange={(event) => setField('email', event.target.value)}
                        defaultValue={!!props.user.email ? props.user.email : ""}
                        disabled={true} />
                    <PhoneNumberFormGroup
                        onChange={(event) => setField('telephone', event.target.value)}
                        defaultValue={!!props.user.telephone ? props.user.telephone : ""} />
                    <CityFormGroup
                        onChange={(event) => setField('city', event.target.value)}
                        defaultValue={!!props.user.address ? props.user.address.city : ""} />
                    <StreetFormGroup
                        onChange={(event) => setField('street', event.target.value)}
                        defaultValue={!!props.user.address ? props.user.address.street : ""} />
                    <CountryFormGroup
                        onChange={(event) => setField('country', event.target.value)}
                        defaultValue={!!props.user.address ? props.user.address.country : ""} />
                    <Button variant="primary" type="submit">Submit</Button>
                </Form>
            </Modal.Body>
            <Modal.Footer>
            </Modal.Footer>
            <ErrorModal show={showErrorModal} onHide={() => setShowErrorModal(false)} message="Something went wrong."></ErrorModal>
            <SuccessModal show={showSuccessModal} onHide={() => setShowSuccessModal(false)} message="User updated successfully."></SuccessModal>
        </Modal>
    )
}

EditUserModal.propTypes = {
    usertype: PropTypes.string.isRequired,
    user: PropTypes.object.isRequired
}
export default EditUserModal
