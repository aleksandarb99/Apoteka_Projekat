import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { Button, Container, Row, Table } from 'react-bootstrap';
import MedicineRow from "./MedicineRow"
import AddMedicineModal from "./AddMedicineModal"

function MedicineTable() {

    const [reload, setReload] = useState(false);
    const [selected, setSelected] = useState({});

    const [medicine, setMedicine] = useState([]);

    const [showAddModal, setShowAddModal] = useState(false);
    const [showEditModal, setShowEditModal] = useState(false);
    const [showDeleteModal, setShowDeleteModal] = useState(false);

    useEffect(() => {
        async function fetchData() {
            const response = await axios.get('http://localhost:8080/api/medicine/');
            setMedicine(response.data);
        }
        fetchData();
    }, [reload]);

    const reloadTable = () => {
        setReload(!reload)
    }

    const updateSelected = (selectedMedicine) => {
        setSelected(selectedMedicine)
    };

    return (
        <Container style={{ marginTop: '10px' }} className="justify-content-center">
            <Row className="justify-content-md-between">
                <Button variant="secondary" style={{ float: 'right', margin: '20px' }} onClick={() => setShowAddModal(true)}>Add new medicine</Button>
            </Row>
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Code</th>
                        <th>Ingredients</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {medicine.map((medi) => (
                        <MedicineRow
                            key={medi.id}
                            medicine={medi}
                            onClick={() => updateSelected(medi)}
                            editClick={() => setShowEditModal(true)}
                            deleteClick={() => setShowDeleteModal(true)}>
                        </MedicineRow>
                    ))}
                </tbody>
            </Table>
            <AddMedicineModal show={showAddModal} onHide={() => setShowAddModal(false)} onSuccess={reloadTable} />
        </Container>
    )
}

export default MedicineTable
