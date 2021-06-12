import React from 'react'
import { ListGroup } from 'react-bootstrap'

const OrderMedicineList = ({ orderItems }) => {
    return (
        <ListGroup style={{ marginBottom: '20px' }}>
            {orderItems.map((oi) => {
                return <ListGroup.Item>{`Code: ${oi.medicine.code} -- ${oi.medicine.name}   Amount ${oi.amount} pcs`}</ListGroup.Item>
            })}
        </ListGroup>
    )
}

OrderMedicineList.defaultProps = {
    orderItems: []
}

export default OrderMedicineList
