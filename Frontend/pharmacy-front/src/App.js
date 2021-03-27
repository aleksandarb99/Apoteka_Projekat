import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";

import LogIn from "./components/LogIn";
import Registration from "./components/Registration";
import HomePage from "./components/HomePage";
import PharmacyAdminHomePage from "./components/PharmacyAdminHomePage";
import UserProfile from "./components/UserProfile";
import Footer from "./components/Footer";
import { Navbar, Nav } from "react-bootstrap";
import PharmacyProfile from "./components/PharmacyProfile";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import DermHomePage from "./workers/dermatologist/home_page_dermatologist";
import PharmHomePage from "./workers/pharmacist/home_page_pharmacist";
import PharmacyCrud from "./components/pharmacy/PharmacyCrud";

import "./styling/navbar.css";
import { House } from "react-bootstrap-icons";

function App() {
  return (
    <Router>
      <div>
        <Navbar className="my__navbar" sticky="top" expand="lg">
          <Navbar.Brand
            style={{ color: "white" }}
            className="my__navbar__house"
            href="/"
          >
            <House /> Home
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="mr-auto">
              <Nav.Link style={{ color: "white" }} href="/profile">
                Profile
              </Nav.Link>
              <Nav.Link style={{ color: "white" }} href="/">
                Another link here
              </Nav.Link>
            </Nav>
            <Nav>
              <Nav.Link style={{ color: "white" }} href="/registration">
                Register
              </Nav.Link>
              <Nav.Link style={{ color: "white" }} href="/login">
                Log In
              </Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Navbar>

        <Switch>
          <Route path="/" exact component={HomePage} />
          <Route path="/profile" exact component={UserProfile} />
          <Route path="/registration" component={Registration} />
          <Route path="/login" component={LogIn} />
          <Route path="/pharmacy/:id" component={PharmacyProfile} />
          <Route path="/dermatologist" component={DermHomePage} />
          <Route path="/pharmacist" component={PharmHomePage} />
          <Route path="/pharmacyAdmin" component={PharmacyAdminHomePage} />
          <Route path="/admin/pharmacies" component={PharmacyCrud} />
        </Switch>

        <Footer />
      </div>
    </Router>
  );
}

export default App;
