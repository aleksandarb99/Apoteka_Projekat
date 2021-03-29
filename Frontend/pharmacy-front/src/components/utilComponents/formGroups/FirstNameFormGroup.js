import React from 'react'
import TextFormGroup from './TextFormGroup'

function FirstNameFormGroup(props) {

    return (
        <TextFormGroup
            {...props}
            name={"First Name"}
            placeholder={"Enter first name..."}
            minLength={1}
            maxLength={30}
            onChange={(event) => { props.onChange(event) }}
            required={true}
        ></TextFormGroup>
    )
}

export default FirstNameFormGroup
