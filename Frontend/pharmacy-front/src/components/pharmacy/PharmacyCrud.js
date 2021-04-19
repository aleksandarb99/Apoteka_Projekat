import React from 'react'
import PharmacyTable from './PharmacyTable'
import PharmacySearchPanel from './PharmacySearchPanel'

const PharmacyCrud = props => {
    return (
        <div>
            <PharmacySearchPanel></PharmacySearchPanel>
            <PharmacyTable> </PharmacyTable>
        </div>
    )
}

export default PharmacyCrud
