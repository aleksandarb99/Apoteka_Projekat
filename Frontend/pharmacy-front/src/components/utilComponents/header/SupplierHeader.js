import React from "react";
import { Nav, Navbar } from "react-bootstrap";
import { Link, Redirect } from "react-router-dom";
import { House } from "react-bootstrap-icons";
import { logout } from "../../../app/slices/userSlice";
import { useDispatch } from "react-redux";

const SupplierHeader = () => {
  const dispatch = useDispatch();

  const handleLogout = () => {
    dispatch(logout());
  };

  return (
    <Navbar
      className="my__navbar justify-content-between"
      sticky="top"
      expand="lg"
    >
      <Navbar.Brand
        as={Link}
        style={{ color: "white" }}
        className="my__navbar__house"
        to="/"
      >
        <House />
        Home
      </Navbar.Brand>
      <Nav>
        <Nav.Link as={Link} style={{ color: "white" }} to="/infoProfile">
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
    </Navbar>
  );
};

export default SupplierHeader;
