import React from 'react'
import TextFormGroup from './TextFormGroup'

function FirstNameFormGroup(props) {

    return (
        <TextFormGroup
            {...props}
            name={"Phone Number"}
            placeholder={"Enter phone number..."}
            minLength={1}
            maxLength={30}
            required={true}
            onChange={(event) => props.onChange(event)}
            pattern={"/^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$/"}
        ></TextFormGroup>
    )
}

export default FirstNameFormGroup
