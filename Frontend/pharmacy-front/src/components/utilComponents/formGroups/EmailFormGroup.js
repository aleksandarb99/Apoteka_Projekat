import React from 'react'
import TextFormGroup from './TextFormGroup'

function EmailFormGroup(props) {

    return (
        <TextFormGroup
            {...props}
            name={"E-mail"}
            placeholder={"Enter e-mail..."}
            minLength={1}
            maxLength={30}
            required={true}
            onChange={(event) => props.onChange(event)}
            pattern={"/^(([^<>()[\\]\\.,;:\\s@\"]+(\\.[^<>()[\\]\\.,;:\\s@\"]+)*)|(\".+\"))@(([^<>()[\\]\\.,;:\\s@\"]+\\.)+[^<>()[\\]\\.,;:\\s@\"]{2,})$/i"}
        ></TextFormGroup>
    )
}

export default EmailFormGroup
