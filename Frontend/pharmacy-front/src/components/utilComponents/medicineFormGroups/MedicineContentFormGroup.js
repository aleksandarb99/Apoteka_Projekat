import React from 'react'
import TextFormGroup from '../formGroups/TextFormGroup'

function MedicineContentFormGroup(props) {

    return (
        <TextFormGroup
            {...props}
            name={"Medicine Content"}
            placeholder={"Enter medicine content..."}
            minLength={1}
            maxLength={200}
            onChange={(event) => { props.onChange(event) }}
            required={true}
        ></TextFormGroup>
    )
}

export default MedicineContentFormGroup