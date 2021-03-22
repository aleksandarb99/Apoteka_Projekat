import React from "react";
import LogIn from "./components/LogIn";
import Registration from "./components/Registration";
import HomePage from "./components/HomePage";
import NavigationBar from "./components/NavigationBar";
import Footer from "./components/Footer";
import PharmacyProfile from "./components/PharmacyProfile";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import PharmacyCrud from "./components/pharmacy/PharmacyCrud";

function App() {
  return (
    <Router>
      <div>
        <NavigationBar />

        <Switch>
          <Route path="/" exact component={HomePage} />
          <Route path="/registration" component={Registration} />
          <Route path="/login" component={LogIn} />
          <Route path="/pharmacy/:id" component={PharmacyProfile} />
          <Route path="/admin/pharmacies" component={PharmacyCrud} />
        </Switch>

        <Footer />
      </div>
    </Router>
  );
}

export default App;
