import React from 'react'
import TextFormGroup from '../formGroups/TextFormGroup'

function AdditionalNotesFormGroup(props) {

    return (
        <TextFormGroup
            {...props}
            name={"Additional notes"}
            placeholder={"Additional notes..."}
            minLength={1}
            maxLength={150}
            onChange={(event) => { props.onChange(event) }}
            required={true}
        ></TextFormGroup>
    )
}

export default AdditionalNotesFormGroup