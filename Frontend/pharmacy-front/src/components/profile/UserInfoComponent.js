import React from "react";
import "../../styling/profile.css";
import Allergies from "./Allergies";
import { Container, Row } from "react-bootstrap";
import PatientBenefits from "./PatientBenefits";
import PatientPenalties from "./PatientPenalties";
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
      <hr />
      <PatientPenalties />
    </Container>
  );
}

export default UserInfo;
