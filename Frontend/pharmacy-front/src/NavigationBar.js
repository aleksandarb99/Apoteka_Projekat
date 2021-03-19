import React from "react";
import { Link } from "react-router-dom";

import "./styling/NavigationBar.css";

function NavigationBar() {
  return (
    <nav>
      <div>
        <Link style={{ textDecoration: "none" }} to="/">
          <span>Home</span>
        </Link>
        <Link style={{ textDecoration: "none" }} to="/pharmacies">
          <span>Pharmacies</span>
        </Link>
        <Link style={{ textDecoration: "none" }} to="/medicines">
          <span>Medicines</span>
        </Link>
      </div>
      <div>
        <Link style={{ textDecoration: "none" }} to="/registration">
          <span>Register</span>
        </Link>
        <Link style={{ textDecoration: "none" }} to="/login">
          <span>Log In</span>
        </Link>
      </div>
    </nav>
  );
}

export default NavigationBar;
