import React, { useState, useEffect } from "react";

import axios from "axios";

import Tab from "react-bootstrap/Tab";
import TabContainer from "react-bootstrap/TabContainer";
import TabContent from "react-bootstrap/TabContent";
import TabPane from "react-bootstrap/TabPane";

import Nav from "react-bootstrap/Nav";

import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

import Map from "ol/Map";
import OSM from "ol/source/OSM";
import TileLayer from "ol/layer/Tile";

import View from "ol/View";

import { fromLonLat, toLonLat } from "ol/proj";

import "../styling/pharmacyHomePage.css";

function PharmacyAdminHomePage() {
  const [editMode, setEditMode] = useState(false);
  const [pharmacyDetails, setPharmacyDetails] = useState({});

  const [text, setText] = useState({
    name: "",
    description: "",
    country: "",
    city: "",
    street: "",
    longitude: 0,
    latitude: 0,
  });

  let handleChange = (event) => {
    let key = event.target.name;
    let value = event.target.value;
    setText({ ...text, [key]: value });
  };

  let handleClickOnMap = function (longitude, latitude) {
    console.log("u hendleru");
    setText({ ...text, latitude: latitude, longitude: longitude });
  };

  useEffect(() => {
    setText({
      name: pharmacyDetails?.name,
      description: pharmacyDetails?.description,
      country: pharmacyDetails?.address?.country,
      city: pharmacyDetails?.address?.city,
      street: pharmacyDetails?.address?.street,
      longitude: pharmacyDetails?.address?.location?.longitude,
      latitude: pharmacyDetails?.address?.location?.latitude,
    });
  }, [pharmacyDetails]);

  useEffect(() => {
    async function fetchPharmacy() {
      // TODO vidi koji je user pa uzmi apoteku koja ti treba
      const request = await axios.get("http://localhost:8080/api/pharmacy/1");
      setPharmacyDetails(request.data);
      return request;
    }
    fetchPharmacy();
  }, []);

  useEffect(() => {
    document.getElementById("divForMap").innerHTML = "";
    let map = new Map({
      target: "divForMap",
      layers: [
        new TileLayer({
          source: new OSM(),
        }),
      ],

      view: new View({
        center: fromLonLat([
          pharmacyDetails?.address?.location?.longitude,
          pharmacyDetails?.address?.location?.latitude,
        ]),
        zoom: 10,
        minZoom: 5,
        maxZoom: 12,
      }),
    });

    if (editMode) {
      map.on("click", (evt) => {
        let coords = toLonLat(evt.coordinate);
        let lat = coords[1];
        let lon = coords[0];
        handleClickOnMap(lon, lat);
      });
    } else {
      map.removeEventListener("click");
    }

    return map;
  }, [pharmacyDetails, editMode]);

  let editClickHandler = () => {
    setEditMode(true);
  };

  let saveClickHandler = () => {
    setEditMode(false);
  };

  let cancelClickHandler = () => {
    setEditMode(false);
    setText({
      name: pharmacyDetails?.name,
      description: pharmacyDetails?.description,
      country: pharmacyDetails?.address?.country,
      city: pharmacyDetails?.address?.city,
      street: pharmacyDetails?.address?.street,
      longitude: pharmacyDetails?.address?.location?.longitude,
      latitude: pharmacyDetails?.address?.location?.latitude,
    });
  };

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
                <Nav.Link eventKey="fifth">Appointsments</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link eventKey="sixth">Inquiries</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link eventKey="seventh">Reports</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link eventKey="eighth">Purchase orders</Nav.Link>
              </Nav.Item>
            </Nav>
          </Col>
          <Col sm={9} md={9} lg={10}>
            <Tab.Content>
              <Tab.Pane eventKey="first">
                <h1 className="content-header">Basic informations</h1>
                <hr></hr>
                <Row className="row-content">
                  <Col lg={6} md={12} sm={12}>
                    <Form>
                      <Form.Group as={Row} controlId="formHorizontalName">
                        <Form.Label column sm={2}>
                          Name
                        </Form.Label>
                        <Col sm={10}>
                          <Form.Control
                            type="name"
                            name="name"
                            placeholder="Name"
                            disabled={!editMode}
                            value={text?.name}
                            onChange={handleChange}
                          />
                        </Col>
                      </Form.Group>

                      <Form.Group
                        as={Row}
                        controlId="formHorizontalDescription"
                      >
                        <Form.Label column sm={2}>
                          Description
                        </Form.Label>
                        <Col sm={10}>
                          <Form.Control
                            type="description"
                            name="description"
                            placeholder="Description"
                            disabled={!editMode}
                            value={text?.description}
                            onChange={handleChange}
                          />
                        </Col>
                      </Form.Group>

                      <Form.Group as={Row} controlId="formHorizontalCountry">
                        <Form.Label column sm={2}>
                          Country
                        </Form.Label>
                        <Col sm={10}>
                          <Form.Control
                            type="country"
                            name="country"
                            placeholder="Country"
                            disabled={!editMode}
                            value={text?.country}
                            onChange={handleChange}
                          />
                        </Col>
                      </Form.Group>

                      <Form.Group as={Row} controlId="formHorizontalCity">
                        <Form.Label column sm={2}>
                          City
                        </Form.Label>
                        <Col sm={10}>
                          <Form.Control
                            type="city"
                            name="city"
                            placeholder="City"
                            disabled={!editMode}
                            value={text?.city}
                            onChange={handleChange}
                          />
                        </Col>
                      </Form.Group>

                      <Form.Group as={Row} controlId="formHorizontalStreet">
                        <Form.Label column sm={2}>
                          Street
                        </Form.Label>
                        <Col sm={10}>
                          <Form.Control
                            type="street"
                            name="street"
                            placeholder="Street"
                            disabled={!editMode}
                            value={text?.street}
                            onChange={handleChange}
                          />
                        </Col>
                      </Form.Group>

                      <Form.Group as={Row} controlId="formHorizontalLongitude">
                        <Form.Label column sm={2}>
                          Longitude
                        </Form.Label>
                        <Col sm={10}>
                          <Form.Control
                            type="longitude"
                            name="longitude"
                            placeholder="Longitude"
                            disabled={!editMode}
                            value={text?.longitude}
                            onChange={handleChange}
                          />
                        </Col>
                      </Form.Group>

                      <Form.Group as={Row} controlId="formHorizontalLatitude">
                        <Form.Label column sm={2}>
                          Latitude
                        </Form.Label>
                        <Col sm={10}>
                          <Form.Control
                            type="latitude"
                            name="latitude"
                            placeholder="Latitude"
                            disabled={!editMode}
                            value={text?.latitude}
                            onChange={handleChange}
                          />
                        </Col>
                      </Form.Group>
                    </Form>
                  </Col>
                  <Col lg={6} md={12} sm={12}>
                    <div id="divForMap"></div>
                  </Col>
                </Row>
                <hr></hr>
                <Row>
                  <Col className="center">
                    <Button
                      variant="primary"
                      size="lg"
                      disabled={!editMode}
                      onClick={saveClickHandler}
                    >
                      Save
                    </Button>{" "}
                    <Button
                      variant="secondary"
                      size="lg"
                      disabled={editMode}
                      onClick={editClickHandler}
                    >
                      Edit
                    </Button>
                    <Button
                      variant="secondary"
                      size="lg"
                      disabled={!editMode}
                      onClick={cancelClickHandler}
                    >
                      Cancel
                    </Button>
                  </Col>
                </Row>
              </Tab.Pane>
              <Tab.Pane eventKey="second">
                <div></div>
              </Tab.Pane>
            </Tab.Content>
          </Col>
        </Row>
      </Tab.Container>
    </div>
  );
}

export default PharmacyAdminHomePage;
