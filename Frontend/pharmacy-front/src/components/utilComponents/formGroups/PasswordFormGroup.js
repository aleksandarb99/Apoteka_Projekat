import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import { Form } from 'react-bootstrap';

function PasswordFormGroup({ name, minLength, maxLength, onChange, ...props }) {

    const [errors, setErrors] = useState("")

    const findGroupErrors = (fieldText) => {
        let error = '';
        if (!fieldText || fieldText === '')
            error = 'Password cannot be blank!'
        else if (!!minLength && fieldText.length < minLength)
            error = 'Minimum number of characters is ' + minLength + '.'
        else if (!!maxLength && fieldText.length > maxLength)
            error = 'Maximum number of characters is ' + maxLength + '.'
        else
            error = ''
        setErrors(error)
    }

    useEffect(findGroupErrors)

    const handleChange = (event) => {
        onChange(event)
        findGroupErrors(event.target.value)
    }

    return (
        <Form.Group {...props}>
            <Form.Label>{name}</Form.Label>
            <Form.Control
                type="password"
                isInvalid={!!errors}
                onChange={handleChange.bind(this)}
                required={true}
            />
            <Form.Control.Feedback type='invalid'>
                {errors}
            </Form.Control.Feedback>
        </Form.Group>
    )
}

PasswordFormGroup.propTypes = {
    name: PropTypes.string.isRequired,
    minLength: PropTypes.number,
    maxLength: PropTypes.number,
}

PasswordFormGroup.defaultProps = {
    minLength: 6,
    onChange: () => { },
    maxLength: 255,
    name: "Password"
}

export default PasswordFormGroup
