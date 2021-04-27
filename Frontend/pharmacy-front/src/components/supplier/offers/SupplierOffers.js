import React, { useEffect, useState } from 'react'
import { Form } from 'react-bootstrap'
import api from '../../../app/api'
import { getIdFromToken } from '../../../app/jwtTokenUtils'
import OfferItem from '../offers/OfferItem'

const SupplierOffers = () => {
    const [offers, setOffers] = useState([])
    const [reload, setReload] = useState(false)
    const [currentOfferType, setCurrentOfferType] = useState("ACCEPTED");

    useEffect(() => {
        let id = getIdFromToken()
        async function fetchOffers() {
            const response = await api.get(`http://localhost:8080/api/suppliers/offers/${id}/?type=${currentOfferType}`);
            setOffers(response.data);
        }
        fetchOffers();
    }, [reload, currentOfferType])

    const reloadTable = () => {
        setReload(!reload)
    }

    const updateCurrentOfferType = (event) => {
        setCurrentOfferType(event.target.value);
    }

    return (
        <div>
            <Form.Group controlId="selectOffer">
                <Form.Label>User Type</Form.Label>
                <Form.Control as="select" onChange={updateCurrentOfferType.bind(this)}>
                    <option value="ACCEPTED">Accepted</option>
                    <option value="PENDING">Pending</option>
                    <option value="DENIED">Denied</option>
                </Form.Control>
            </Form.Group>
            {offers && offers.map((o) => {
                return <OfferItem key={o.id} offer={o}></OfferItem>
            })}
        </div>
    )
}

export default SupplierOffers
