import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import { Form } from 'react-bootstrap';

function TextFormGroup({ name, placeholder, minLength, maxLength, defaultValue, required }) {

    const [errors, setErrors] = useState("")

    useEffect(() => {
        findGroupErrors()
    }, [])

    const findGroupErrors = (fieldText) => {

        if (!fieldText || fieldText === '')
            setErrors('This field cannot be blank!')

        else if (!!minLength && fieldText.length < minLength)
            setErrors('Minimum number of characters is ' + minLength + ' !')

        else if (!!maxLength && fieldText.length > maxLength)
            setErrors('Maximum number of characters is ' + maxLength + ' !')

        else
            setErrors('')
    }

    const handleChange = (event) => {
        findGroupErrors(event.target.value)
    }

    return (
        <Form.Group>
            <Form.Label>{name}</Form.Label>
            <Form.Control
                type="text"
                placeholder={placeholder}
                defaultValue={defaultValue}
                isInvalid={!!errors}
                onChange={handleChange.bind(this)}
                required={required}
            />
            <Form.Control.Feedback type='invalid'>
                {errors}
            </Form.Control.Feedback>
        </Form.Group>
    )
}


TextFormGroup.propTypes = {
    name: PropTypes.string.isRequired,
    placeholder: PropTypes.string,
    minLength: PropTypes.number,
    maxLength: PropTypes.number,
    defaultValue: PropTypes.string,
    required: PropTypes.bool,
    onChange: PropTypes.func
}

TextFormGroup.defaultProps = {
    required: false,
    minLength: 0,
    maxLength: 100
}

export default TextFormGroup
