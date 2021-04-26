import { Button } from 'react-bootstrap'
import React from 'react'

const OfferItem = (props) => {

    const canEdit = () => {
        return props.offer.offerState === "PENDING" && props.deliveryDate > Date.now();
    }


    return (
        <div style={{ border: "1px solid black" }}>
            <p>{`Price: ${props.offer.price} -- Due: ${new Date(props.offer.deliveryDate).toLocaleDateString("sr-sp")} -- Status: ${props.offer.offerState}`}</p>
            <Button disabled={!canEdit()} onClick={() => { }}>Edit</Button>
        </div >
    )
}

export default OfferItem
