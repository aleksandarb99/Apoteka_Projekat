import React from 'react'
import TextFormGroup from './TextFormGroup'

function CountryFormGroup(props) {

    return (
        <TextFormGroup
            {...props}
            name={"Country"}
            placeholder={"Enter country..."}
            minLength={1}
            maxLength={100}
            onChange={(event) => { props.onChange(event) }}
            required={true}
        ></TextFormGroup>
    )
}

export default CountryFormGroup
