import React, { useEffect, useState } from 'react'
import { Col, Container, Form, Row } from 'react-bootstrap';
import PharmacyWithMedicineItem from './PharmacyWithMedicineItem'

const PharmacyWithMedicineList = ({ hidden, pharmacies }) => {
    const [selectedPharmacy, setSelectedPharmacy] = useState();

    return (

        <Container>
            {pharmacies.map((p) => {
                return <PharmacyWithMedicineItem pharmacy={p} onClick={() => { setSelectedPharmacy(p) }}></PharmacyWithMedicineItem>
            })}
        </Container>

    )
}

PharmacyWithMedicineList.defaultProps = {
    pharmacies: []
}

export default PharmacyWithMedicineList
