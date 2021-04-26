import React, { useEffect, useState } from 'react'
import api from '../../../app/api'
import { getIdFromToken } from '../../../app/jwtTokenUtils'
import OfferItem from '../offers/OfferItem'

const SupplierOffers = () => {
    const [offers, setOffers] = useState([])
    const [reload, setReload] = useState(false)
    const [showEditOfferModal, setEditOfferModal] = useState(false)

    useEffect(() => {
        let id = getIdFromToken()
        async function fetchOffers() {
            const response = await api.get(`http://localhost:8080/api/suppliers/offers/${id}`);
            setOffers(response.data);
        }
        fetchOffers();
    }, [reload])

    const reloadTable = () => {
        setReload(!reload)
    }

    return (
        <div>
            {offers && offers.map((o) => {
                return <OfferItem key={o.id} offer={o}></OfferItem>
            })}
        </div>
    )
}

export default SupplierOffers
