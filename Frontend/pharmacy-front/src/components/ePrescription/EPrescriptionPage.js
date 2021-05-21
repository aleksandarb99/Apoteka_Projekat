import React from "react";
import { Col, Nav, Row, Tab, TabPane } from "react-bootstrap";
import EPrescriptionSearch from "./EPrescriptionSearch";
import EPrescriptionReview from "./EPrescriptionReview";

function EPrescriptionPage() {
  return (
    <main className="home__page">
      <Tab.Container
        id="left-tabs-example"
        defaultActiveKey="first"
        unmountOnExit
      >
        <Row className="my-panel">
          <Col sm={3} md={3} lg={2} xs={12} className="side__bar">
            <Nav variant="pills" className="flex-column">
              <Nav.Item>
                <Nav.Link className="my__nav__link" eventKey="fifth">
                  Upload
                </Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link className="my__nav__link" eventKey="second">
                  Review
                </Nav.Link>
              </Nav.Item>
            </Nav>
          </Col>
          <Col
            className="my__container"
            sm={9}
            md={9}
            lg={10}
            xs={12}
            style={{ backgroundColor: "rgba(228, 245, 245, 0.897)" }}
          >
            <Tab.Content>
              <TabPane eventKey="fifth">
                <EPrescriptionSearch pharmacyId={-1} />
              </TabPane>
              <TabPane eventKey="second">
                <EPrescriptionReview />
              </TabPane>
            </Tab.Content>
          </Col>
        </Row>
      </Tab.Container>
    </main>
  );
}

export default EPrescriptionPage;
