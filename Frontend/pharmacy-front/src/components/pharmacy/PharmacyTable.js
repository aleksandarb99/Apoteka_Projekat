import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { Button, Col, Container, Row, Table } from 'react-bootstrap'
import AddPharmacyModal from './AddPharmacyModal';

function PharmacyTable(props) {

    const [reload, setReload] = useState(false);

    const [pharmacies, setPharmacies] = useState([]);
    const [showAddModal, setShowAddModal] = useState(false);
    const [showUpdateModal, setShowUpdateModal] = useState(false);
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [showEditModal, setShowEditModal] = useState(false);

    useEffect(async () => {
            const request = await axios.get("http://localhost:8080/api/pharmacy/all");
            setPharmacies(request.data);
    }, [reload]);

    const reloadTable = () => {
        setReload(!reload)
    }

    return (
        <Container style={{ marginTop: '100px' }}>
            <Button variant="secondary" style={{ float: 'right', margin: '20px' }} onClick={() => setShowAddModal(true)}>Add new pharmacy</Button>
            <Table striped bordered hover>
                <thead>
                    <tr>
                    <th>Name</th>
                    <th>Descriptioin</th>
                    <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {pharmacies.map((pharmacy) => (
                        <tr>
                        <td>{pharmacy.name}</td>
                        <td>{pharmacy.description}</td>
                        <td>
                            <Button>Edit</Button> 
                            <Button variant="info">Details</Button> 
                            <Button variant="danger">Delete</Button>
                        </td>
                        </tr>
                    ))}
                </tbody>
            </Table>
            
            <AddPharmacyModal show={showAddModal} onHide={() => setShowAddModal(false)} onSuccess = {reloadTable}/>
      </Container>
    )
}

export default PharmacyTable
