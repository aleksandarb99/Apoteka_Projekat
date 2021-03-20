import React from "react";
import LogIn from "./LogIn";
import Registration from "./Registration";
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
          <Route path="/registration" component={Registration} />
          <Route path="/login" component={LogIn} />
        </Switch>

        <Footer />
      </div>
    </Router>
  );
}

export default App;
