import React, { useEffect, useState } from 'react'
import { Col, Container, Form, Row } from 'react-bootstrap';
import PharmacyWithMedicineItem from './PharmacyWithMedicineItem'

const PharmacyWithMedicineList = ({ hidden, pharmacies }) => {
    const [sortBy, setSortBy] = useState("totalPrice");
    const [sortOrder, setSortOrder] = useState("ASC");
    const [selectedPharmacy, setSelectedPharmacy] = useState();

    useEffect(() => {

    }, [sortBy, sortOrder]);

    return (
        <Form hidden={hidden}>
            <Container>
                <Row className="align-content-between">
                    <Col md={{ span: 3, offset: 2 }}>
                        <Form.Group>
                            <Form.Label>Sort by</Form.Label>
                            <Form.Control as="select" onChange={(e) => { setSortBy(e.target.value) }}>
                                <option value="totalPrice" selected>Total price</option>
                                <option value="name">Pharmacy name</option>
                                <option value="avgGrade">Pharmacy grade</option>
                                <option value="addressCity">City</option>
                                <option value="street">Street</option>
                            </Form.Control>
                        </Form.Group>
                    </Col>
                    <Col md={{ span: 3, offset: 2 }}>
                        <Form.Group>
                            <Form.Label>Order</Form.Label>
                            <Form.Control as="select" onChange={(e) => { setSortBy(e.target.value) }}>
                                <option value="ASC" selected>Ascending</option>
                                <option value="DESC">Descending</option>
                            </Form.Control>
                        </Form.Group>
                    </Col>
                </Row>
            </Container>
            {pharmacies.map((p) => {
                return <PharmacyWithMedicineItem pharmacy={p} onClick={() => { setSelectedPharmacy(p) }}></PharmacyWithMedicineItem>
            })}
        </Form>
    )
}

PharmacyWithMedicineList.defaultProps = {
    pharmacies: []
}

export default PharmacyWithMedicineList
