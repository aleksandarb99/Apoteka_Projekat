import { React } from "react";
import "../../styling/home_page.css";
import { Nav, Tab, Row, Col } from "react-bootstrap";

import DisplayHolidayRequests from "../pharmacyAdmin/DisplayHolidayRequests";

function AdminHomePage() {
  return (
    <main className="home__page">
      <Tab.Container id="left-tabs-example" defaultActiveKey="first">
        <Row className="my-panel">
          <Col sm={3} md={3} lg={2} xs={12} className="side__bar">
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
              <DisplayHolidayRequests idOfPharmacy={-1} />
            </Tab.Content>
          </Col>
        </Row>
      </Tab.Container>
    </main>
  );
}

export default AdminHomePage;
