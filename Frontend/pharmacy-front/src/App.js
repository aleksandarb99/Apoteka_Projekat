import React from "react";
import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

import LoginPage from "./components/LoginPage";
import Registration from "./components/Registration";
import HomePage from "./components/unregisteredAndPatient/HomePage";
import PharmacyAdminHomePage from "./components/PharmacyAdminHomePage";
import UserProfile from "./components/profile/UserProfile";
import Footer from "./components/Footer";
import { Navbar, Nav } from "react-bootstrap";
import PharmacyProfile from "./components/PharmacyProfile";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import DermHomePage from "./components/workers/dermatologist/home_page_dermatologist";
import PharmHomePage from "./components/workers/pharmacist/home_page_pharmacist";
import PharmacyCrud from "./components/pharmacy/PharmacyCrud";
import MedicineCrud from "./components/medicine/MedicineCrud";
import SearchPatPage from "./components/workers/search_patients";
import SearchExaminedPatPage from "./components/workers/list_examined";

import WorkCalendar from "./components/workers/work_calendar";

import WorkerProfile from "./components/workers/profile_page";
import UserCrud from "./components/users/UserCrud";

import "./styling/navbar.css";
import { House } from "react-bootstrap-icons";

function App() {
  return (
    <Router>
      <div style={{ minHeight: "100vh" }}>
        <Navbar className="my__navbar" sticky="top" expand="lg">
          <Navbar.Brand
            as={Link}
            style={{ color: "white" }}
            className="my__navbar__house"
            to="/"
          >
            <House /> Home
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
            </Nav>
          </Navbar.Collapse>
        </Navbar>

        <Switch>
          <Route path="/" exact component={HomePage} />
          <Route path="/profile" exact component={UserProfile} />
          <Route path="/registration" component={Registration} />
          <Route path="/login" component={LoginPage} />
          <Route path="/pharmacy/:id" component={PharmacyProfile} />
          <Route path="/dermatologist" component={DermHomePage} />
          <Route path="/pharmacist" component={PharmHomePage} />
          <Route path="/pharmacyAdmin" component={PharmacyAdminHomePage} />
          <Route path="/admin/pharmacies" component={PharmacyCrud} />
          <Route path="/admin/medicine" component={MedicineCrud} />
          <Route path="/worker/search-patients" component={SearchPatPage} />
          <Route path="/wp" component={WorkerProfile} />
          <Route path="/admin/users" component={UserCrud} />
          <Route path="/worker/examined" component={SearchExaminedPatPage} />
          <Route path="/wc" component={WorkCalendar} />
        </Switch>
      </div>
      <Footer />
    </Router>
  );
}

export default App;
