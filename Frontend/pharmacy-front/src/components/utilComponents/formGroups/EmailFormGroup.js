import React from 'react'
import TextFormGroup from './TextFormGroup'

function EmailFormGroup({ onChange }) {

    return (
        <TextFormGroup
            name={"E-mail"}
            placeholder={"Enter e-mail..."}
            minLength={1}
            maxLength={30}
            required={true}
            onChange={onChange}
            pattern={"/^(([^<>()[\\]\\.,;:\\s@\"]+(\\.[^<>()[\\]\\.,;:\\s@\"]+)*)|(\".+\"))@(([^<>()[\\]\\.,;:\\s@\"]+\\.)+[^<>()[\\]\\.,;:\\s@\"]{2,})$/i"}
        ></TextFormGroup>
    )
}

export default EmailFormGroup
