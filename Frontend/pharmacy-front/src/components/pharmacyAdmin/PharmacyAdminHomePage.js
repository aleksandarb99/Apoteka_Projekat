import React, { useState, useEffect } from "react";

import axios from "axios";

import Tab from "react-bootstrap/Tab";

import Nav from "react-bootstrap/Nav";

import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

import "../../styling/pharmacyHomePage.css";
import EditBasicInfo from "./EditBasicInfo";
import DisplayPurchaseOrders from "./DisplayPurchaseOrders";
import AddAppointment from "./AddAppointment";

function PharmacyAdminHomePage() {
  const [pharmacyDetails, setPharmacyDetails] = useState({});

  async function fetchPharmacy() {
    // TODO vidi koji je user pa uzmi apoteku koja ti treba
    const request = await axios.get("http://localhost:8080/api/pharmacy/1");
    setPharmacyDetails(request.data);
    return request;
  }

  let changedPharmacy = () => {
    fetchPharmacy();
  };

  useEffect(() => {
    fetchPharmacy();
  }, []);

  return (
    <div style={{ height: "100vh" }}>
      <Tab.Container id="left-tabs-example" defaultActiveKey="first">
        <Row className="my-panel">
          <Col sm={3} md={3} lg={2} xs={12} className="sideBar">
            <Nav variant="pills" className="flex-column">
              <Nav.Item>
                <Nav.Link eventKey="first">Basic informations</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link eventKey="second">
                  Pharmacists and dermatologists
                </Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link eventKey="third">Medicine</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link eventKey="fourth">Pricelist</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link eventKey="fifth">Add appointment</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link eventKey="sixth">Inquiries</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link eventKey="seventh">Reports</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link eventKey="eight">Purchase orders</Nav.Link>
              </Nav.Item>
            </Nav>
          </Col>
          <Col sm={9} md={9} lg={10} xs={12}>
            <Tab.Content>
              <EditBasicInfo
                pharmacyDetails={pharmacyDetails}
                changedPharmacy={changedPharmacy}
              />
              <AddAppointment idOfPharmacy={pharmacyDetails?.id} />
              <DisplayPurchaseOrders idOfPharmacy={pharmacyDetails?.id} />
            </Tab.Content>
          </Col>
        </Row>
      </Tab.Container>
    </div>
  );
}

export default PharmacyAdminHomePage;
