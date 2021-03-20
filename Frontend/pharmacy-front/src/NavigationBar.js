import React from "react";
import { Link } from "react-router-dom";
import Button from "@material-ui/core/Button";
import HomeIcon from "@material-ui/icons/Home";
import ButtonGroup from "@material-ui/core/ButtonGroup";
import "./styling/NavigationBar.css";

function NavigationBar() {
  return (
    <nav>
      <Button variant="contained" color="primary">
        <HomeIcon />
        <Link style={{ textDecoration: "none" }} exact to="/">
          <span>Home</span>
        </Link>
      </Button>
      <ButtonGroup
        variant="contained"
        color="primary"
        aria-label="outlined primary button group"
      >
        <Button>
          <Link style={{ textDecoration: "none" }} to="/registration">
            <span>Register</span>
          </Link>
        </Button>
        <Button>
          <Link style={{ textDecoration: "none" }} to="/login">
            <span>Log In</span>
          </Link>
        </Button>
      </ButtonGroup>
    </nav>
  );
}

export default NavigationBar;
