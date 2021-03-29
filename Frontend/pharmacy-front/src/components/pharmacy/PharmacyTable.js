import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { Button, Container, Table } from 'react-bootstrap'
import DeleteModal from '../utilComponents/DeleteModal';
import AddPharmacyModal from './AddPharmacyModal';
import EditPharmacyModal from './EditPharmacyModal';

function PharmacyTable(props) {

    const [reload, setReload] = useState(false);
    const [selected, setSelected] = useState({});

    const [pharmacies, setPharmacies] = useState([]);
    const [showAddModal, setShowAddModal] = useState(false);
    const [showEditModal, setShowEditModal] = useState(false);
    const [showDeleteModal, setShowDeleteModal] = useState(false);

    useEffect(() => {
        async function fetchData() {
            const response = await axios.get("http://localhost:8080/api/pharmacy/all");
            setPharmacies(response.data);
        }
        fetchData();
    }, [reload]);

    const reloadTable = () => {
        setReload(!reload)
    }

    const deletePharmacy = () => {
        axios
            .delete("http://localhost:8080/api/pharmacy/" + selected.id)
            .then(() => {
                reloadTable()
                alert("Pharmacy deleted successfully")
                setShowDeleteModal(false)
            })
    }

    const updateSelected = (selectedPharmacy) => {
        setSelected(selectedPharmacy)
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
                        <tr onClick={() => updateSelected(pharmacy)} key={pharmacy.id}>
                            <td>{pharmacy.name}</td>
                            <td>{pharmacy.description}</td>
                            <td>
                                <Button onClick={() => setShowEditModal(true)}>Edit</Button>
                                <Button variant="info">Details</Button>
                                <Button variant="danger" onClick={() => setShowDeleteModal(true)}>Delete</Button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>

            <AddPharmacyModal show={showAddModal} onHide={() => setShowAddModal(false)} onSuccess={reloadTable} />
            <EditPharmacyModal show={showEditModal} pharmacy={selected} onHide={() => setShowEditModal(false)} onSuccess={reloadTable} />
            <DeleteModal title={"Remove " + selected.name} show={showDeleteModal} onHide={() => setShowDeleteModal(false)} onDelete={deletePharmacy} />
        </Container>
    )
}

export default PharmacyTable
