import React from 'react'
import { Button } from 'react-bootstrap'

const MedicineRow = ({ medicine, onClick, editClick, detailsClick, deleteClick }) => {
    return (
        <tr onClick={onClick} key={medicine.id}>
            <td>{medicine.name}</td>
            <td>{medicine.code}</td>
            <td>{medicine.content}</td>
            <td>
                <Button onClick={editClick}>Edit</Button>
                <Button variant="info" onClick={() => window.open(`http://localhost:8080/api/medicine/${medicine.id}/get-pdf`, "_blank")} > Details</Button>
                <Button variant="danger" onClick={deleteClick}>Delete</Button>
            </td>
        </tr>
    )
}

export default MedicineRow
