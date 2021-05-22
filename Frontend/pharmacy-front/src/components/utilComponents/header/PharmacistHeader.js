import React from "react";
import { Nav, Navbar } from "react-bootstrap";
import { Link, Redirect } from "react-router-dom";
import { House } from "react-bootstrap-icons";
import { logout } from "../../../app/slices/userSlice";
import { useDispatch } from "react-redux";

const PharmacistHeader = () => {
  const dispatch = useDispatch();

  const handleLogout = () => {
    dispatch(logout());
  };

  return (
    <Navbar className="my__navbar" sticky="top" expand="lg">
      <Navbar.Brand
        as={Link}
        style={{ color: "white" }}
        className="my__navbar__house"
        to="/pharmacist"
      >
        <House />
        Home
      </Navbar.Brand>

      <Navbar.Toggle aria-controls="responsive-navbar-nav" />

      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="mr-auto">
          <Nav.Link
            as={Link}
            style={{ color: "white" }}
            to="/worker/search-patients"
          >
            Search patients
          </Nav.Link>
          <Nav.Link
            as={Link}
            style={{ color: "white" }}
            to="/worker/issue_medicine"
          >
            Issue medicine
          </Nav.Link>
          <Nav.Link as={Link} style={{ color: "white" }} to="/worker/calendar">
            Work Calendar
          </Nav.Link>
          <Nav.Link as={Link} style={{ color: "white" }} to="/worker/examined">
            Examined patients
          </Nav.Link>
          <Nav.Link as={Link} style={{ color: "white" }} to="/worker/vacation">
            Vacation
          </Nav.Link>
        </Nav>
        <Nav>
          <Nav.Link
            as={Link}
            style={{ color: "white" }}
            to="/worker/pharmacist_profile"
          >
            Profile
          </Nav.Link>
          <Nav.Link
            as={Link}
            style={{ color: "white" }}
            to="/"
            onClick={() => handleLogout()}
          >
            Log out
          </Nav.Link>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
};

export default PharmacistHeader;
