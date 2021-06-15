import { React, useEffect, useState } from "react";
import "../../styling/home_page.css";
import { Nav, Tab, Row, Col } from "react-bootstrap";

import DisplayHolidayRequests from "../pharmacyAdmin/DisplayHolidayRequests";
import SetPasswordModal from '../utilComponents/modals/SetPasswordModal'
import { getIdFromToken } from "../../app/jwtTokenUtils";
import api from "../../app/api";
import PharmaciesView from "../unregisteredAndPatient/PharmaciesView";
import MedicinesView from "../unregisteredAndPatient/MedicinesView";

function AdminHomePage() {
  const [showModalPWChange, setShowModalPWChange] = useState(false);

  useEffect(() => {
    let id = getIdFromToken();
    api.get("/api/users/" + id)
      .then((res) => {
        setShowModalPWChange(!res.data.passwordChanged)
      }).catch(() => { })
  }, [])

  return (
    <div>
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
                  <Nav.Link className="my__nav__link" eventKey="third">
                    Medicines
                  </Nav.Link>
                </Nav.Item>
              </Nav>
              <Nav variant="pills" className="flex-column">
                <Nav.Item>
                  <Nav.Link className="my__nav__link" eventKey="fifth">
                    Holiday requests
                  </Nav.Link>
                </Nav.Item>
              </Nav>
            </Col>
            <Col className="my__container" sm={9} md={9} lg={10} xs={12}>
              <Tab.Content>
                <PharmaciesView />
                <MedicinesView />
                <DisplayHolidayRequests idOfPharmacy={-1} />
              </Tab.Content>
            </Col>
          </Row>
        </Tab.Container>
      </main>
      <SetPasswordModal show={showModalPWChange} onPasswordSet={() => setShowModalPWChange(false)}></SetPasswordModal>
    </div>
  );
}

export default AdminHomePage;
