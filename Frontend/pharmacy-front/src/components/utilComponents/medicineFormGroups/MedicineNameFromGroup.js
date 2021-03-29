import React from 'react'
import TextFormGroup from '../formGroups/TextFormGroup'

function MedicineNameFormGroup(props) {

    return (
        <TextFormGroup
            {...props}
            name={"Medicine Name"}
            placeholder={"Enter medicine name..."}
            minLength={1}
            maxLength={30}
            onChange={(event) => { props.onChange(event) }}
            required={true}
        ></TextFormGroup>
    )
}

export default MedicineNameFormGroup