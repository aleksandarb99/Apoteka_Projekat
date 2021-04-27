import React from "react";
import "../../styling/home_page.css";
import { Nav, Tab, Row, Col } from "react-bootstrap";
import PharmaciesView from "./PharmaciesView";
import MedicinesView from "./MedicinesView";

function UnregistredAndPatientHomePage() {
  return (
    <main className="home__page">
      <Tab.Container id="left-tabs-example" defaultActiveKey="first">
        <Row className="my-panel">
          <Col sm={3} md={3} lg={2} xs={12} className="side__bar">
            <Nav variant="pills" className="flex-column">
              <Nav.Item>
                <Nav.Link className="my__nav__link" eventKey="first">
                  Pharmacies
                </Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link className="my__nav__link" eventKey="second">
                  Medicines
                </Nav.Link>
              </Nav.Item>
            </Nav>
          </Col>
          <Col className="my__container" sm={9} md={9} lg={10} xs={12}>
            <Tab.Content>
              <PharmaciesView />
              <MedicinesView />
            </Tab.Content>
          </Col>
        </Row>
      </Tab.Container>
    </main>
  );
}

export default UnregistredAndPatientHomePage;
