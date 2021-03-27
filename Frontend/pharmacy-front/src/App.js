import React from "react";
import LogIn from "./components/LogIn";
import Registration from "./components/Registration";
import HomePage from "./components/HomePage";
import Footer from "./components/Footer";
import { Navbar, Nav } from "react-bootstrap";
import PharmacyProfile from "./components/PharmacyProfile";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import DermHomePage from "./components/workers/dermatologist/home_page_dermatologist";
import PharmHomePage from "./components/workers/pharmacist/home_page_pharmacist";
import "bootstrap/dist/css/bootstrap.min.css";
import PharmacyCrud from "./components/pharmacy/PharmacyCrud";
import SearchPatPage from "./components/workers/search_patients";

function App() {
  return (
    <Router>
      <div>
        <Navbar bg="dark" variant="dark">
          <Navbar.Brand href="/">Home</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="m-auto">
              <Nav.Link href="/registration">Register</Nav.Link>
              <Nav.Link href="/login">Log In</Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Navbar>

        <Switch>
          <Route path="/" exact component={HomePage} />
          <Route path="/registration" component={Registration} />
          <Route path="/login" component={LogIn} />
          <Route path="/pharmacy/:id" component={PharmacyProfile} />
          <Route path="/dermatologist" component={DermHomePage} />
          <Route path="/pharmacist" component={PharmHomePage} />
          <Route path="/admin/pharmacies" component={PharmacyCrud} />

          <Route path="/worker/search-patients" component={SearchPatPage} />
        </Switch>

        <Footer />
      </div>
    </Router>
  );
}

export default App;
