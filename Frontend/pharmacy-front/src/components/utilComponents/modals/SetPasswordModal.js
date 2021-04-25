import React from 'react'
import { Modal, Form } from 'react-bootstrap'
import { useDispatch } from 'react-redux'
import PasswordFormGroup from '../formGroups/PasswordFormGroup'
import api from '../../../app/api'
import getIdFromToken from '../../../app/jwtTokenUtils'

// Use when user is logging in for the first time
const SetPasswordModal = () => {

    const [validated, setValidated] = useState(false)
    const [showErrorModal, setShowErrorModal] = useState(false)
    const [passwordSet, setPasswordSet] = useState(false)

    const [form, setForm] = useState({})
    const setField = (field, value) => {
        setForm({
            ...form,
            [field]: value
        })
    }

    const dispatch = useDispatch();

    const handleClose = () => {
        dispatch(logout());
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        event.stopPropagation()

        const f = event.currentTarget;

        if (f.checkValidity() === true) {
            setValidated(true)
            handleSet();
        }
    }

    const handleSet = () => {
        api.put("localhost:8080//api/users/set-password/" + getIdFromToken(), form)
            .then(setPasswordSet(false))
            .catch(setShowErrorModal(true))
    }

    return (
        <Modal {...props} show={passwordSet}>
            <Modal.Header className="justify-content-center" backdrop="static" onHide={handleClose} closeButton>
                <p>Welcome! Please set new password!</p>
            </Modal.Header>
            <Modal.Body className="justify-content-center">
                <Form onSubmit={handleSubmit} validated={validated}>
                    <PasswordFormGroup onChange={(event) => setField('newPassword', event.target.value)}></PasswordFormGroup>
                    <Button className="float-center" variant="outline-secondary" type="submit" >Set Password</Button>
                </Form>
            </Modal.Body>
            <ErrorModal show={showErrorModal} onHide={() => setShowErrorModal(false)} message="Something went wrong."></ErrorModal>
        </Modal >
    )
}

export default SetPasswordModal