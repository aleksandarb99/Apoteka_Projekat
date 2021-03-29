import React from 'react'
import TextFormGroup from './TextFormGroup'

function FirstNameFormGroup({ onChange }) {

    return (
        <TextFormGroup
            name={"Phone Number"}
            placeholder={"Enter phone number..."}
            minLength={1}
            maxLength={30}
            required={true}
            onChange={onChange}
            pattern={"/^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-s./0-9]*$/"}
        ></TextFormGroup>
    )
}

export default FirstNameFormGroup
