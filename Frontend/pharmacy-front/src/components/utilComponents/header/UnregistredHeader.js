import React from 'react'
import { Button, Nav, Navbar } from 'react-bootstrap';
import { Link } from "react-router-dom";
import { House } from 'react-bootstrap-icons';
import { logout } from "../../../app/slices/userSlice";
import { useDispatch } from 'react-redux';

const UnregistredHeader = () => {

    const dispatch = useDispatch()

    return (
        <Navbar className="my__navbar justify-content-between" sticky="top" expand="lg">
            <Navbar.Brand
                as={Link}
                style={{ color: "white" }}
                className="my__navbar__house"
                to="/"
            >
                <House />Home
            </Navbar.Brand>
            <Nav className="justify-content-end">
                <Nav.Link as={Link} style={{ color: "white" }} to="/registration">
                    Register
                    </Nav.Link>
                <Nav.Link as={Link} style={{ color: "white" }} to="/login">
                    Log In
                    </Nav.Link>
            </Nav>
        </Navbar>
    )
}

export default UnregistredHeader
