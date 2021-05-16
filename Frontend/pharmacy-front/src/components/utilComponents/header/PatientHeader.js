import React, { useState, useEffect } from "react";
import { Button, Nav, Navbar } from "react-bootstrap";
import { Link, Redirect } from "react-router-dom";
import { House } from "react-bootstrap-icons";
import { logout } from "../../../app/slices/userSlice";
import { useDispatch } from "react-redux";
import axios from "../../../app/api";
import { getIdFromToken } from "../../../app/jwtTokenUtils";

const PatientHeader = () => {
  const [penalties, setPenalties] = useState();

  useEffect(() => {
    async function fetchPenalties() {
      const request = await axios.get(
        "http://localhost:8080/api/patients/" + getIdFromToken() + "/penalties"
      );
      setPenalties(request.data);

      return request;
    }
    fetchPenalties();
  }, {});

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
        to="/"
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
            to="/reserve-consultation/pharmacies"
          >
            Reserve consultation
          </Nav.Link>
          <Nav.Link
            as={Link}
            style={{ color: "white" }}
            to="/consultations-insight"
          >
            Consultations
          </Nav.Link>
          <Nav.Link as={Link} style={{ color: "white" }} to="/checkups-insight">
            Checkups
          </Nav.Link>
          <Nav.Link as={Link} style={{ color: "white" }} to="/user/complaints">
            Complaints
          </Nav.Link>
          <Nav.Link
            as={Link}
            style={{ color: "white" }}
            to="/subscribed-pharmacies"
          >
            Subscribed pharmacies
          </Nav.Link>
          <Nav.Link
            as={Link}
            style={{ color: "white" }}
            to="/reserved-medicines"
          >
            Reserved medicines
          </Nav.Link>
          <Nav.Link as={Link} style={{ color: "white" }} to="/rating">
            Rating
          </Nav.Link>
          <Nav.Link
            as={Link}
            style={{
              display: penalties === 3 ? "none" : "block",
              color: "white",
            }}
            to="/e-prescription"
          >
            E-Prescription
          </Nav.Link>
        </Nav>
        <Nav>
          <Nav.Link as={Link} style={{ color: "white" }} to="/profile">
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

export default PatientHeader;
