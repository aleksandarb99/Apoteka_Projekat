import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

import PharmacyBasic from "./PharmacyBasic";
import MedicinesAdminView from "./MedicineAdminView";
import AppointmentView from "./AppointmentView";

import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

import Tab from "react-bootstrap/Tab";
import Nav from "react-bootstrap/Nav";

import "../styling/pharmacy.css";

function PharmacyProfile() {
  const [details, setPharmacyDetails] = useState({});

  let { id } = useParams();

  useEffect(() => {
    async function fetchPharmacy() {
      const request = await axios.get(
        `http://localhost:8080/api/pharmacy/${id}`
      );
      setPharmacyDetails(request.data);
      return request;
    }
    fetchPharmacy();
  }, [id]);

  return (
    <div>
      <Tab.Container id="left-tabs-example" defaultActiveKey="first">
        <Row className="my-panel">
          <Col sm={3} md={3} lg={2} xs={12} className="sideBar">
            <Nav variant="pills" className="flex-column">
              <Nav.Item>
                <Nav.Link eventKey="first">Basic informations</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link eventKey="second">Medicines</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link eventKey="third">Appointsments</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link eventKey="forth">
                  Pharmacists and dermatologists
                </Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link eventKey="fifth">Check availability</Nav.Link>
              </Nav.Item>
            </Nav>
          </Col>
          <Col sm={9} md={9} lg={10} xs={12}>
            <Tab.Content>
              <PharmacyBasic details={details} />
              <MedicinesAdminView
                medicines={details?.priceList?.medicineItems}
                isAdmin={true}
              />
              <AppointmentView />
            </Tab.Content>
          </Col>
        </Row>
      </Tab.Container>
    </div>
  );
}

export default PharmacyProfile;
