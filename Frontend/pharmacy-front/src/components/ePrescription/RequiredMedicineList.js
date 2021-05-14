import React from 'react'
import { Container, ListGroup } from 'react-bootstrap'

const RequiredMedicineList = ({ medicines }) => {
    return (
        <ListGroup style={{ marginBottom: '20px' }}>
            {medicines.map((m) => {
                return <ListGroup.Item>{`Code: ${m.medicineCode} -- ${m.medicineName}, Quantity ${m.quantity} pcs`}</ListGroup.Item>
            })}
        </ListGroup>
    )
}

RequiredMedicineList.defaultProps = {
    medicines: []
}

export default RequiredMedicineList
