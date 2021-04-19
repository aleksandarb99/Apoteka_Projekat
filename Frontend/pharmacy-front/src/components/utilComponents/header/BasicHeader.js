import React from 'react'
import { Button, Nav, Navbar } from 'react-bootstrap';
import { Link } from "react-router-dom";
import { House } from 'react-bootstrap-icons';
import { logout } from "../../../app/slices/userSlice";
import { useDispatch } from 'react-redux';

const BasicHeader = () => {

    const dispatch = useDispatch()

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
                    <Nav.Link as={Link} style={{ color: "white" }} to="/profile">
                        Profile
              </Nav.Link>
                </Nav>
                <Nav>
                    <Nav.Link as={Link} style={{ color: "white" }} to="/registration">
                        Register
              </Nav.Link>
                    <Nav.Link as={Link} style={{ color: "white" }} to="/login">
                        Log In
              </Nav.Link>
                    <Button
                        as={Link}
                        style={{ color: "white" }}
                        onClick={() => {
                            dispatch(logout());
                        }}
                    >
                        Log Out
              </Button>
                </Nav>
            </Navbar.Collapse>
        </Navbar>
    )
}

export default BasicHeader
