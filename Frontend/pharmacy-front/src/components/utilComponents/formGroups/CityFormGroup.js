import React from 'react'
import TextFormGroup from './TextFormGroup'

function CityFormGroup(props) {

    return (
        <TextFormGroup
            {...props}
            name={"City"}
            placeholder={"Enter city..."}
            minLength={1}
            maxLength={100}
            onChange={(event) => { props.onChange(event) }}
            required={true}
        ></TextFormGroup>
    )
}

export default CityFormGroup
