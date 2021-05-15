import React from 'react'
import { Button, Nav, Navbar } from 'react-bootstrap';
import { Link, Redirect } from "react-router-dom";
import { House } from 'react-bootstrap-icons';
import { logout } from "../../../app/slices/userSlice";
import { useDispatch } from 'react-redux';

const AdminHeader = ({ userChanged }) => {

    const dispatch = useDispatch()

    const handleLogout = () => {
        dispatch(logout());
    }

    return (
        <Navbar className="my__navbar" sticky="top" expand="lg">
            <Navbar.Brand
                as={Link}
                style={{ color: "white" }}
                className="my__navbar__house"
                to="/"
            >
                <House />Home
            </Navbar.Brand>

            <Navbar.Toggle aria-controls="responsive-navbar-nav" />

            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="mr-auto">
                    <Nav.Link as={Link} style={{ color: "white" }} to="/admin/users">
                        Users
                    </Nav.Link>
                    <Nav.Link as={Link} style={{ color: "white" }} to="/admin/pharmacies">
                        Pharmacies
                    </Nav.Link>
                    <Nav.Link as={Link} style={{ color: "white" }} to="/admin/medicine">
                        Medicine
                    </Nav.Link>
                    <Nav.Link as={Link} style={{ color: "white" }} to="/admin/complaint-responses">
                        Complaints
                    </Nav.Link>
                    <Nav.Link as={Link} style={{ color: "white" }} to="/admin/loyalty">
                        Loyalty Program
                    </Nav.Link>
                </Nav>
                <Nav>
                    <Nav.Link as={Link} style={{ color: "white" }} to="/profile">
                        Profile
                    </Nav.Link>
                    <Nav.Link as={Link} style={{ color: "white" }} to="/" onClick={() => handleLogout()}>
                        Log out
                    </Nav.Link>
                </Nav>
            </Navbar.Collapse>
        </Navbar>
    )
}

export default AdminHeader
