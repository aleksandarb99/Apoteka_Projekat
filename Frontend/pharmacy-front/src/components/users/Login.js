import axios from 'axios'
import React, { useState } from 'react'
import { Button, Form } from 'react-bootstrap'
import EmailFormGroup from '../utilComponents/formGroups/EmailFormGroup'
import PasswordFormGroup from '../utilComponents/formGroups/PasswordFormGroup'

function Login() {
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

        if (f.checkValidity() === true) {
            setValidated(true)
            sendPostRequest()
        }
    }

    const sendPostRequest = () => {
        axios
            .post('http://localhost:8080/authenticate/', form)
            .then(() => {
                setForm({})
                alert('Login')
            })
            .catch(() => {
                setValidated(false)
                alert('Server error')
            })
    }

    return (
        <Form noValidate validated={validated} onSubmit={handleSubmit}>
            <EmailFormGroup onChange={(event) => setField('email', event.target.value)}></EmailFormGroup>
            <PasswordFormGroup onChange={(event) => setField('password', event.target.value)}></PasswordFormGroup>
            <Button variant="primary" type="submit">Submit</Button>
        </Form>
    )
}

export default Login
