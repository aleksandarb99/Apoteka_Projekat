import React, { useEffect, useState } from 'react'
import { Button } from 'react-bootstrap'
import api from '../../../app/api'
import { getIdFromToken } from '../../../app/jwtTokenUtils'
import OrderItem from './OrderItem'

const NewOrders = () => {
    const [orders, setOrders] = useState([])
    const [reload, setReload] = useState(false)
    const [showAddOfferModal, setAddOfferModal] = useState(false)

    useEffect(() => {
        async function fetchOrders() {
            const response = await api.get(`http://localhost:8080/api/orders/without-offers/${getIdFromToken()}`);
            setOrders(response.data);
        }
        fetchOrders()
    }, [reload])

    const reloadTable = () => {
        setReload(!reload)
    }

    return (
        <div>
            {orders && orders.map((o) => {
                return <OrderItem key={o.id} order={o} onChange={reloadTable}></OrderItem>
            })}
        </div>
    )
}

export default NewOrders
