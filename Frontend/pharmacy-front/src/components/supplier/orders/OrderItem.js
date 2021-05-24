import React, { useState } from 'react'
import { Button, Col, Container, FormGroup, FormLabel, Row } from 'react-bootstrap'
import AddEditOfferModal from '../offers/AddEditOfferModal'
import OrderMedicineList from './OrderMedicineList';

const OrderItem = ({ order, onSuccess }) => {
    const [showAddOfferModal, setShowAddOfferModal] = useState(false);

    return (
        <Container className="border border-primary" style={{ borderRadius: '10px', padding: '10px', marginTop: '10px', marginBottom: '10px', backgroundColor: 'white' }}>
            <Row className="justify-content-between">
                <Col md={4}>
                    <FormGroup>
                        <FormLabel>
                            Status: {order.orderState}
                        </FormLabel>
                    </FormGroup>
                </Col>
                <Col md={2}>
                    <FormGroup>
                        <FormLabel>
                            Deadline: {new Date(order.deadline).toLocaleDateString("sr-sp")}
                        </FormLabel>
                    </FormGroup>
                </Col>
            </Row>
            <Row>
                <Col>
                    <OrderMedicineList orderItems={order.orderItem}></OrderMedicineList>
                </Col>
            </Row>
            <Row>
                <Col md={{ offset: 0, span: 2 }}>
                    <Button onClick={() => { setShowAddOfferModal(true) }} style={{ width: '100%' }}>Create Offer</Button>
                </Col>
            </Row>
            <AddEditOfferModal show={showAddOfferModal} order={order} onHide={() => setShowAddOfferModal(false)} onSuccess={() => { onSuccess() }}></AddEditOfferModal>
        </Container>

    )
}

export default OrderItem
