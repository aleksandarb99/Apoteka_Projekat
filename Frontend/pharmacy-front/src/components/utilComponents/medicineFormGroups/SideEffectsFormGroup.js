import React from 'react'
import TextFormGroup from '../formGroups/TextFormGroup'

function SideEffectsFormGroup(props) {

    return (
        <TextFormGroup
            {...props}
            name={"Side effects"}
            placeholder={"Enter side effects..."}
            minLength={1}
            maxLength={50}
            onChange={(event) => { props.onChange(event) }}
            required={true}
        ></TextFormGroup>
    )
}

export default SideEffectsFormGroup