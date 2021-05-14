import React, { useEffect, useState } from 'react'
import { Col, Container, Form, Row } from 'react-bootstrap';
import PharmacyWithMedicineItem from './PharmacyWithMedicineItem'

const PharmacyWithMedicineList = ({ pharmacies, doBuy }) => {

    return (

        <Container>
            {pharmacies.map((p) => {
                return <PharmacyWithMedicineItem pharmacy={p} doBuy={() => { doBuy(p.id) }}></PharmacyWithMedicineItem >
            })}
        </Container >

    )
}

PharmacyWithMedicineList.defaultProps = {
    pharmacies: []
}

export default PharmacyWithMedicineList
