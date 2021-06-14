import React from 'react'
import TextFormGroup from '../formGroups/TextFormGroup'

function MedicineCodeFormGroup(props) {

    return (
        <TextFormGroup
            {...props}
            name={"Medicine Code *"}
            placeholder={"Enter medicine code..."}
            minLength={1}
            maxLength={30}
            onChange={(event) => { props.onChange(event) }}
            required={true}
        ></TextFormGroup>
    )
}

export default MedicineCodeFormGroup