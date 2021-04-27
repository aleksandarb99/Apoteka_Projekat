import React, { useState } from 'react'
import { Button } from 'react-bootstrap'
import AddEditOfferModal from '../offers/AddEditOfferModal'

const OrderItem = (props) => {
    const [showAddOfferModal, setShowAddOfferModal] = useState(false);

    return (
        <div style={{ border: "1px solid black" }}>
            {props.order.orderItem.map((oi) => {
                return <div key={oi.id}>
                    <p>{`${oi.medicine.code} -- ${oi.medicine.name} -- Amount: ${oi.amount}`}</p>
                </div>
            })}
            <p>{`Due: ${new Date(props.order.deadline).toLocaleDateString("sr-sp")}`}</p>
            <Button onClick={() => { setShowAddOfferModal(true) }}>Create Offer</Button>
            <AddEditOfferModal show={showAddOfferModal} order={props.order} onHide={() => setShowAddOfferModal(false)}></AddEditOfferModal>
        </div>
    )
}

export default OrderItem
