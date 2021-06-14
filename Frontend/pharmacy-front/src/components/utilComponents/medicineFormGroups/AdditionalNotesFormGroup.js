import React from 'react'
import TextFormGroup from '../formGroups/TextFormGroup'

function AdditionalNotesFormGroup(props) {

    return (
        <TextFormGroup
            {...props}
            name={"Additional notes"}
            placeholder={"Additional notes..."}
            minLength={0}
            maxLength={250}
            onChange={(event) => { props.onChange(event) }}
            required={false}
        ></TextFormGroup>
    )
}

export default AdditionalNotesFormGroup