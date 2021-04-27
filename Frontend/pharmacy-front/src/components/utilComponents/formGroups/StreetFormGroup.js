import React from 'react'
import TextFormGroup from './TextFormGroup'

function StreetFormGroup(props) {

    return (
        <TextFormGroup
            {...props}
            name={"Street"}
            placeholder={"Enter street..."}
            minLength={1}
            maxLength={150}
            onChange={(event) => { props.onChange(event) }}
            required={true}
        ></TextFormGroup>
    )
}

export default StreetFormGroup
