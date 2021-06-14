import React, { useState } from 'react'
import { Modal, Form, Button, Row } from 'react-bootstrap'
import { useDispatch } from 'react-redux'
import PasswordFormGroup from '../formGroups/PasswordFormGroup'
import api from '../../../app/api'
import { getIdFromToken } from '../../../app/jwtTokenUtils'
import { logout } from '../../../app/slices/userSlice'
import Validator from '../../../app/validator'

// Use when user is logging in for the first time
const SetPasswordModal = (props) => {
    const [errorMessage, setErrorMessage] = useState('');
    const [password, setPassword] = useState('');
    const [repeatPassword, setRepeatPassword] = useState('');

    const dispatch = useDispatch();

    const handleClose = () => {
        dispatch(logout());
    };

    const validate = () => {
        return Validator['password'](password) && Validator['password'](repeatPassword);
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        event.stopPropagation()

        if (validate()) {
            if (password !== repeatPassword) {
                setErrorMessage('Passwords do not match');
            } else {
                setErrorMessage('');
                handleSet();
            }
        }
    }

    const handleSet = () => {
        let data = {
            newPassword: password
        }
        api.put("http://localhost:8080/api/users/set-password/" + getIdFromToken(), data)
            .then(() => {
                props.onPasswordSet()
            })
            .catch(() => {
                setErrorMessage('Oops! Something went wrong, try again!')
            });
    }

    return (
        <Modal {...props} onHide={handleClose} backdrop="static">
            <Modal.Header className="justify-content-center" backdrop="static" closeButton>
                <p>Welcome! Please set new password!</p>
            </Modal.Header>
            <Modal.Body className="justify-content-center">
                <Form noValidate onSubmit={handleSubmit}>
                    <PasswordFormGroup onChange={(event) => setPassword(event.target.value)}></PasswordFormGroup>
                    <PasswordFormGroup name="Repeat password" onChange={(event) => setRepeatPassword(event.target.value)}></PasswordFormGroup>
                    <Button className="float-center" variant="outline-secondary" type="submit" >Set Password</Button>
                </Form>
                {errorMessage.length > 0 &&
                    <Row className="justify-content-center m-3">
                        <p className="text-danger">{errorMessage}</p>
                    </Row>
                }
            </Modal.Body>
        </Modal >
    )
}

SetPasswordModal.defaultProps = {
    isPasswordSet: false,
};

export default SetPasswordModal;
