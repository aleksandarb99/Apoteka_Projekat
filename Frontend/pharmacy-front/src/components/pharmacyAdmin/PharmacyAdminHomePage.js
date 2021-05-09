import React, { useState, useEffect } from "react";

import axios from "../../app/api";

import { Tab, Nav, Row, Col } from "react-bootstrap";

import "../../styling/pharmacyHomePage.css";
import "../../styling/pharmacy.css";
import "../../styling/home_page.css";

import EditBasicInfo from "./EditBasicInfo";
import DisplayPurchaseOrders from "./DisplayPurchaseOrders";
import AddAppointment from "./AddAppointment";
import DisplayMedicine from "./DisplayMedicine";
import DisplayHolidayRequests from "./DisplayHolidayRequests";
import DisplayWorkers from "./DisplayWorkers";
import DisplayInquiries from "./DisplayInquiries";

import { getIdFromToken } from "../../app/jwtTokenUtils";

function PharmacyAdminHomePage() {
  const [pharmacyDetails, setPharmacyDetails] = useState({});
  const [refreshPriceList, setRefreshPriceList] = useState(false);

  const [pharmacyId, setPharmacyId] = useState(null);

  const [refreshInq, setRefreshInq] = useState(false);

  async function fetchPharmacyid() {
    const request = await axios.get(
      `http://localhost:8080/api/pharmacy/getpharmacyidbyadmin/${getIdFromToken()}`
    );
    setPharmacyId(request.data);
    return request;
  }

  async function fetchPharmacy() {
    const request = await axios.get(
      `http://localhost:8080/api/pharmacy/${pharmacyId}`
    );
    setPharmacyDetails(request.data);
    return request;
  }

  let changedPharmacy = () => {
    fetchPharmacy();
  };

  useEffect(() => {
    if (pharmacyId != null) fetchPharmacy();
  }, [pharmacyId]);

  useEffect(() => {
    fetchPharmacyid();
  }, []);

  return (
    <div style={{ height: "100vh" }}>
      <Tab.Container id="left-tabs-example" defaultActiveKey="first">
        <Row className="my-panel">
          <Col sm={3} md={3} lg={2} xs={12} className="side__bar">
            <Nav variant="pills" className="flex-column">
              <Nav.Item>
                <Nav.Link className="my__nav__link" eventKey="first">
                  Basic informations
                </Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link className="my__nav__link" eventKey="second">
                  Pharmacists and dermatologists
                </Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link className="my__nav__link" eventKey="third">
                  Medicine
                </Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link className="my__nav__link" eventKey="fourth">
                  Add appointment
                </Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link className="my__nav__link" eventKey="fifth">
                  Holiday requests
                </Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link className="my__nav__link" eventKey="sixth">
                  Reports
                </Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link className="my__nav__link" eventKey="seventh">
                  Purchase orders
                </Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link className="my__nav__link" eventKey="eight">
                  Inquiries
                </Nav.Link>
              </Nav.Item>
            </Nav>
          </Col>
          <Col className="my__container" sm={9} md={9} lg={10} xs={12}>
            <Tab.Content>
              <EditBasicInfo
                pharmacyDetails={pharmacyDetails}
                changedPharmacy={changedPharmacy}
              />
              <DisplayWorkers idOfPharmacy={pharmacyDetails?.id} />
              <DisplayHolidayRequests idOfPharmacy={pharmacyDetails?.id} />
              <AddAppointment idOfPharmacy={pharmacyDetails?.id} />
              <DisplayMedicine
                setRefreshPriceList={setRefreshPriceList}
                refreshPriceList={refreshPriceList}
                idOfPharmacy={pharmacyDetails?.id}
                priceListId={pharmacyDetails?.priceListId}
              />
              <DisplayPurchaseOrders
                refreshInq={refreshInq}
                setRefreshInq={setRefreshInq}
                refresh={refreshPriceList}
                idOfPharmacy={pharmacyDetails?.id}
                priceListId={pharmacyDetails?.priceListId}
              />
              <DisplayInquiries
                refreshInq={refreshInq}
                idOfPharmacy={pharmacyDetails?.id}
              />
            </Tab.Content>
          </Col>
        </Row>
      </Tab.Container>
    </div>
  );
}

export default PharmacyAdminHomePage;
