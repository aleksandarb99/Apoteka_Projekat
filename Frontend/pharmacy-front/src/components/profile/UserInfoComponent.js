import React, { useState, useEffect } from "react";
import "../../styling/profile.css";
import Allergies from "./Allergies";
import { Button, Form, Alert, Container, Row, Col } from "react-bootstrap";
import axios from "axios";
import { getIdFromToken } from "../../app/jwtTokenUtils";
import PatientBenefits from "./PatientBenefits";
import BasicProfileInfo from "./BasicProfileInfo";

function UserInfo(props) {

  return (
    <Container>
      <BasicProfileInfo title={props.title}></BasicProfileInfo>
      <hr />
      <Row className="justify-content-center m-3">
        <Allergies />
      </Row>
      <hr />
      <PatientBenefits />
    </Container>
  );
}

export default UserInfo;
