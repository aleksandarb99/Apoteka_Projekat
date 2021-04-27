import React from 'react'
import TextFormGroup from './TextFormGroup'

function LastNameFormGroup(props) {

    return (
        <TextFormGroup
            {...props}
            name={"Last Name"}
            placeholder={"Enter last name..."}
            minLength={1}
            maxLength={60}
            onChange={(event) => { props.onChange(event) }}
            required={true}
        ></TextFormGroup>
    )
}

export default LastNameFormGroup
