import React, { useState } from 'react'
import { Button, Form } from 'react-bootstrap'
import { useDispatch, useSelector } from 'react-redux'
import { Redirect } from 'react-router'
import { login } from '../../app/slices/userSlice'
import Validator from '../../app/validator'
import { getIdFromToken } from '../../app/jwtTokenUtils'
import { useToasts } from 'react-toast-notifications'
import EmailFormGroup from '../utilComponents/formGroups/EmailFormGroup'
import PasswordFormGroup from '../utilComponents/formGroups/PasswordFormGroup'

function Login() {
    const [form, setForm] = useState(
        {
            email: '',
            password: ''
        }
    )

    const dispatch = useDispatch()
    const user = useSelector(state => state.user)
    const { addToast } = useToasts()

    const setField = (field, value) => {
        setForm({
            ...form,
            [field]: value
        })
    }

    const validateForm = () => {
        return Validator['email'](form['email'])
            && Validator['password'](form['password'])
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        event.stopPropagation()

        if (validateForm()) {
            let loginPromise = dispatch(login(form))
            loginPromise
                .then(() => {
                    if (!getIdFromToken()) {
                        addToast("User not found", { appearance: 'warning' });
                    }
                })
        }
    }

    if (user.user) {
        return <Redirect to='/' />
    }

    return (
        <Form noValidate onSubmit={handleSubmit} className="my__login__form">
            <EmailFormGroup onChange={(event) => setField('email', event.target.value)}></EmailFormGroup>
            <PasswordFormGroup onChange={(event) => setField('password', event.target.value)}></PasswordFormGroup>
            <Button variant="primary" type="submit">Submit</Button>
        </Form>
    )
}

export default Login
