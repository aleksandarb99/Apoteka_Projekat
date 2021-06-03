import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import { Form } from 'react-bootstrap';

function TextFormGroup({ name, placeholder, minLength, maxLength, defaultValue, required, pattern, onChange, disabled, ...props }) {

    const [errors, setErrors] = useState("")
    const [regex, setRegex] = useState()

    useEffect(() => {
        if (!!pattern) {
            const main = pattern.match(/\/(.+)\/.*/)[1]
            const options = pattern.match(/\/.+\/(.*)/)[1]
            let re = new RegExp(main, options)
            setRegex(re)
        }
        findGroupErrors(defaultValue)
    }, [])

    const findGroupErrors = (fieldText) => {
        let error = '';

        if (required && (!fieldText || fieldText === ''))
            error = 'This field cannot be blank!'

        else if (!!pattern) {
            if (!regex.test(fieldText)) {
                error = 'Input data is not in a valid format'
            }
        }

        else if (!!minLength && fieldText.length < minLength)
            error = 'Minimum number of characters is ' + minLength + '.'

        else if (!!maxLength && fieldText.length > maxLength) {
            error = 'Maximum number of characters is ' + maxLength + '.'
        }

        else
            error = ''

        setErrors(error)
    }

    const handleChange = (event) => {
        onChange(event)
        findGroupErrors(event.target.value)
    }

    return (
        <Form.Group {...props}>
            <Form.Label>{name}</Form.Label>
            <Form.Control
                type="text"
                placeholder={placeholder}
                defaultValue={defaultValue}
                isInvalid={!!errors}
                onChange={handleChange.bind(this)}
                required={required}
                disabled={disabled}
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
    pattern: PropTypes.string
}

TextFormGroup.defaultProps = {
    required: false,
    minLength: 0,
    onChange: () => { },
    maxLength: 100,
    pattern: "",
    disabled: false
}

export default TextFormGroup
