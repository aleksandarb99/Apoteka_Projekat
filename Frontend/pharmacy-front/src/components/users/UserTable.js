import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { Button, Container, Form, Row, Table } from 'react-bootstrap'
import AddUserModal from './AddUserModal';
import EditUserModal from './EditUserModal';
import DeleteModal from '../utilComponents/DeleteModal';
import PropTypes from 'prop-types';
import UserRow from './UserRow';

function UserTable({ initialUserType }) {

    const [reload, setReload] = useState(false);
    const [selected, setSelected] = useState({});

    const [users, setUsers] = useState([]);
    const [currentUserType, setCurrentUserType] = useState("");
    const [showAddModal, setShowAddModal] = useState(false);
    const [showEditModal, setShowEditModal] = useState(false);
    const [showDeleteModal, setShowDeleteModal] = useState(false);

    useEffect(async () => {
        const request = await axios.get('http://localhost:8080/api/users/?type=' + currentUserType);
        setUsers(request.data);
    }, [reload, currentUserType]);

    useEffect(function () {
        setCurrentUserType(initialUserType)
    }, [])

    const updateCurrentUserType = (event) => {
        setCurrentUserType(event.target.value);
    }

    const reloadTable = () => {
        setReload(!reload)
    }

    const getFormattedUserType = () => {
        switch (currentUserType) {
            case "pharmacist":
                return "Pharmacist"
            case "dermatologist":
                return "Dermatologist"
            case "systemAdmin":
                return "System Admin"
            case "pharmacyAdmin":
                return "Pharmacy Admin"
        }
    }

    const deleteUser = () => {
        axios
            .delete("http://localhost:8080/api/users/" + selected.id)
            .then(() => {
                reloadTable()
                alert("User deleted successfully")
                setShowDeleteModal(false)
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
                        <option value="pharmacist">Pharmacist</option>
                        <option value="dermatologist">Dermatologist</option>
                        <option value="pharmacyAdmin">Pharmacy Admin</option>
                        <option value="systemAdmin">System Admin</option>
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
                            user={user}
                            onClick={() => updateSelected(user)}
                            editClick={() => setShowEditModal(true)}
                            deleteClick={() => setShowDeleteModal(true)}>
                        </UserRow>
                    ))}
                </tbody>
            </Table>

        </Container>/*
      <AddUserModal show={showAddModal} onHide={() => setShowAddModal(false)} onSuccess = {reloadTable}/>
      <EditUserModal show={showEditModal} user={selected} onHide={() => setShowEditModal(false)} onSuccess = {reloadTable}/>
      <DeleteModal title={"Remove " + selected.firstName + " " + selected.lastName} show={showDeleteModal} onHide={() => setShowDeleteModal(false)} onDelete = {deleteUser}/>*/
    )
}

UserTable.propTypes = {
    userType: PropTypes.oneOf(['dermatologist', 'pharmacist', 'pharmacyAdmin', 'systemAdmin'])
}

export default UserTable
