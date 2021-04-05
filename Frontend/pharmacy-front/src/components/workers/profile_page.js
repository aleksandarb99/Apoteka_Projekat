import React, { useState, useEffect } from "react";
import {
  Button,
  Container,
  Form,
  Row,
  Col,
  ButtonGroup,
  Card,
} from "react-bootstrap";
import axios from "axios";
import UserInfo from "../profile/UserInfoComponent";

function WorkerProfile() {
  return (
    <main>
      <Container>
        <Row className="align-items-center">
          <UserInfo title="Pharmacist's information" col_width={6}></UserInfo>
        </Row>
      </Container>
    </main>
  );
}

export default WorkerProfile;
