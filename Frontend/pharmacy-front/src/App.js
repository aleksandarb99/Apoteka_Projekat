import React from "react";
import LogIn from "./LogIn";
import Registration from "./Registration";
import Pharmacies from "./Pharmacies";
import Medicines from "./Medicines";
import HomePage from "./HomePage";
import NavigationBar from "./NavigationBar";
import Footer from "./Footer";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

function App() {
  return (
    <Router>
      <div>
        <NavigationBar />

        <Switch>
          <Route path="/" exact component={HomePage} />
          <Route path="/pharmacies" component={Pharmacies} />
          <Route path="/medicines" component={Medicines} />
          <Route path="/registration" component={Registration} />
          <Route path="/login" component={LogIn} />
        </Switch>

        <Footer />
      </div>
    </Router>
  );
}

export default App;
