import React from "react";
import "../styling/footer.css";
import { Facebook, Instagram, Twitter } from "react-bootstrap-icons";
import { Button } from "react-bootstrap";

function Footer() {
  return (
    <footer className="my__footer">
      <p>Copyright &#169; 2021</p>
      <p>
        Produced by: Dejan Todorovic, Aleksandar Buljevic, Darko Tica and Jovan
        Simic
        <Button className="my__footer__btn">
          <Facebook />
        </Button>
        <Button className="my__footer__btn">
          <Instagram />
        </Button>
        <Button className="my__footer__btn">
          <Twitter />
        </Button>
      </p>
    </footer>
  );
}

export default Footer;
