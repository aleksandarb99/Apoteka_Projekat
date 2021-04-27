import StockItem from "./StockItem"
import api from '../../../app/api'
import { getIdFromToken } from '../../../app/jwtTokenUtils'
import React, { useEffect, useState } from 'react'
import { Button } from "react-bootstrap"
import AddEditStockItemModal from '../stock/AddEditStockItemModal'

const SupplierStock = () => {
    const [stock, setStock] = useState([])
    const [reload, setReload] = useState(false)
    const [showAddEditModal, setShowAddEditModal] = useState(false)

    useEffect(() => {
        async function fetchStock() {
            const response = await api.get(`http://localhost:8080/api/suppliers/stock/${getIdFromToken()}`);
            setStock(response.data);
        }
        fetchStock()
    }, [reload])

    const reloadTable = () => {
        setReload(!reload)
    }

    return (
        <div>
            {stock && stock.map((s) => {
                return <StockItem key={s.medicineId} stockItem={s}></StockItem>
            })}
            <Button onClick={() => setShowAddEditModal(true)}>Add</Button>
            <AddEditStockItemModal show={showAddEditModal} onHide={() => setShowAddEditModal(false)} onSuccess={reloadTable} />
        </div>
    )
}

export default SupplierStock
