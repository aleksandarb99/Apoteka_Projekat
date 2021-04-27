import { Button } from 'react-bootstrap'
import React, { useEffect, useState } from 'react'
import AddEditOfferModal from '../offers/AddEditOfferModal'
import api from '../../../app/api';

const OfferItem = (props) => {
    const [showEditOfferModal, setShowEditOfferModal] = useState(false);
    const [order, setOrder] = useState({})
    const [refresh, setRefresh] = useState(false)

    useEffect(() => {
        async function fetchOrder() {
            const res = await api.get(`http://localhost:8080/api/orders/${props.offer.orderId}`);
            setOrder(res.data)
        }
        fetchOrder();
    }, [refresh])

    const refreshTable = () => {
        setRefresh(!refresh);
    }

    const canEdit = () => {
        return props.offer.offerState === "PENDING" && props.offer.deliveryDate > Date.now();
    }


    return (
        <div style={{ border: "1px solid black" }}>
            <p>{`Price: ${props.offer.price} -- Due: ${new Date(props.offer.deliveryDate).toLocaleDateString("sr-sp")} -- Status: ${props.offer.offerState}`}</p>
            <Button disabled={!canEdit()} onClick={() => { setShowEditOfferModal(true) }}>Edit</Button>
            <AddEditOfferModal show={showEditOfferModal} order={order} offer={props.offer} onHide={() => setShowEditOfferModal(false)} onSuccess={() => refreshTable()}></AddEditOfferModal>
        </div >
    )
}

export default OfferItem
