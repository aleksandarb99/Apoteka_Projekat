import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { Button, Container, Form, Row, Table } from 'react-bootstrap'
import AddUserModal from './AddUserModal';
import EditUserModal from './EditUserModal';
import DeleteModal from '../utilComponents/modals/DeleteModal';
import PropTypes from 'prop-types';
import UserRow from './UserRow';
import ErrorModal from '../utilComponents/modals/ErrorModal'
import SuccessModal from '../utilComponents/modals/SuccessModal'

function UserTable({ initialUserType }) {

    const [reload, setReload] = useState(false);
    const [selected, setSelected] = useState({});

    const [users, setUsers] = useState([]);
    const [currentUserType, setCurrentUserType] = useState("");
    const [showAddModal, setShowAddModal] = useState(false);
    const [showEditModal, setShowEditModal] = useState(false);
    const [showDeleteModal, setShowDeleteModal] = useState(false);

    const [showErrorModal, setShowErrorModal] = useState(false);
    const [showSuccessModal, setShowSuccessModal] = useState(false);

    useEffect(() => {
        async function fetchData() {
            const response = await axios.get('http://localhost:8080/api/users/?type=' + currentUserType);
            setUsers(response.data);
        }
        fetchData();
    }, [reload, currentUserType]);

    useEffect(function () {
        setCurrentUserType(initialUserType)
    }, [initialUserType])

    const updateCurrentUserType = (event) => {
        setCurrentUserType(event.target.value);
    }

    const reloadTable = () => {
        setReload(!reload)
    }

    const getFormattedUserType = () => {
        switch (currentUserType) {
            case "PHARMACIST":
                return "Pharmacist"
            case "DERMATOLOGIST":
                return "Dermatologist"
            case "ADMIN":
                return "System Admin"
            case "PHARMACY_ADMIN":
                return "Pharmacy Admin"
            default:
                return ""
        }
    }

    const deleteUser = () => {
        axios
            .delete("http://localhost:8080/api/users/" + selected.id)
            .then(() => {
                reloadTable()
                setShowDeleteModal(false)
                setShowSuccessModal(true);
            })
            .catch(() => {
                setShowErrorModal(true);
            })
    }

    const updateSelected = (selectedUser) => {
        setSelected(selectedUser)
    };

    return (
        <Container style={{ marginTop: '10px' }} className="justify-content-center">
            <Row className="justify-content-md-between">
                <Form.Group controlId="userTypeSelect">
                    <Form.Label>User Type</Form.Label>
                    <Form.Control as="select" onChange={updateCurrentUserType.bind(this)}>
                        <option value="PHARMACIST">Pharmacist</option>
                        <option value="DERMATOLOGIST">Dermatologist</option>
                        <option value="PHARMACY_ADMIN">Pharmacy Admin</option>
                        <option value="ADMIN">System Admin</option>
                    </Form.Control>
                </Form.Group>
                <Button variant="secondary" style={{ float: 'right', margin: '20px' }} onClick={() => setShowAddModal(true)}>Add new {getFormattedUserType()}</Button>
            </Row>
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>E Mail</th>
                        <th>Phone number</th>
                        <th>Address</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {users.map((user) => (
                        <UserRow
                            key={user.id}
                            user={user}
                            onClick={() => updateSelected(user)}
                            editClick={() => setShowEditModal(true)}
                            deleteClick={() => setShowDeleteModal(true)}>
                        </UserRow>
                    ))}
                </tbody>
            </Table>
            <AddUserModal show={showAddModal} onHide={() => setShowAddModal(false)} onSuccess={reloadTable} usertype={currentUserType} />
            <DeleteModal title={"Remove " + selected.firstName + " " + selected.lastName} show={showDeleteModal} onHide={() => setShowDeleteModal(false)} onDelete={deleteUser} />
            <EditUserModal show={showEditModal} user={selected} onHide={() => setShowEditModal(false)} onSuccess={reloadTable} usertype={currentUserType} />
            <ErrorModal show={showErrorModal} onHide={() => setShowErrorModal(false)} message="Something went wrong."></ErrorModal>
            <SuccessModal show={showSuccessModal} onHide={() => setShowSuccessModal(false)} message="User deleted successfully."></SuccessModal>
        </Container>
    )
}

UserTable.propTypes = {
    userType: PropTypes.oneOf(['DERMATOLOGIST', 'PHARMACIST', 'PHARMACY_ADMIN', 'ADMIN'])
}

export default UserTable
