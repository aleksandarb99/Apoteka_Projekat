import React from "react";
import "./styling/footer.css";
import InstagramIcon from "@material-ui/icons/Instagram";
import FacebookIcon from "@material-ui/icons/Facebook";
import TwitterIcon from "@material-ui/icons/Twitter";
import { IconButton } from "@material-ui/core";

function Footer() {
  return (
    <footer>
      <p>Copyright &#169; 2021</p>
      <p>
        Produced by: Dejan Todorovic, Aleksandar Buljevic, Darko Tica and Jovan
        Simic
        <IconButton>
          <InstagramIcon />
        </IconButton>
        <IconButton>
          <FacebookIcon />
        </IconButton>
        <IconButton>
          <TwitterIcon />
        </IconButton>
      </p>
    </footer>
  );
}

export default Footer;
