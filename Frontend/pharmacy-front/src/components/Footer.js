import React from "react";
import "../styling/footer.css";
import { Facebook, Instagram, Twitter } from "react-bootstrap-icons";
import { Button } from "react-bootstrap";

function Footer() {
  return (
    <footer className="my-footer">
      <p>Copyright &#169; 2021</p>
      <p>
        Produced by: Dejan Todorovic, Aleksandar Buljevic, Darko Tica and Jovan
        Simic
        <Button variant="dark">
          <Facebook />
        </Button>
        <Button variant="dark">
          <Instagram />
        </Button>
        <Button variant="dark">
          <Twitter />
        </Button>
      </p>
    </footer>
  );
}

export default Footer;
