import React from 'react'
import TextFormGroup from './TextFormGroup'

function FirstNameFormGroup() {
    let a = 0;
    return (
        <TextFormGroup
            name={"First Name"}
            placeholder={"Enter first name..."}
            minLength={1}
            maxLength={30}
            required={true}
        ></TextFormGroup>
    )
}

export default FirstNameFormGroup
