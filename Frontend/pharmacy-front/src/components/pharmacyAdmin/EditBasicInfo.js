import React, { useState, useEffect } from "react";

import axios from "../../app/api";

import Tab from "react-bootstrap/Tab";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

import Map from "ol/Map";
import OSM from "ol/source/OSM";
import TileLayer from "ol/layer/Tile";
import View from "ol/View";
import { fromLonLat, toLonLat } from "ol/proj";

import "../../styling/pharmacyHomePage.css";
import { useToasts } from "react-toast-notifications";

function EditBasicInfo({ pharmacyDetails, changedPharmacy }) {
  const { addToast } = useToasts();
  const [fixing, setFixing] = useState(null);
  const [editMode, setEditMode] = useState(false);
  const [valid, setValid] = useState(true);
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
    validateData(key, value);
    setText({ ...text, [key]: value });
  };

  let editClickHandler = () => {
    setEditMode(true);
  };

  let saveClickHandler = () => {
    if (valid) {
      setEditMode(false);
    }
    let dto = {
      id: pharmacyDetails.id,
      name: text.name,
      description: text.description,
      address: {
        id: pharmacyDetails.address.id,
        country: text.country,
        city: text.city,
        street: text.street,
        location: {
          id: pharmacyDetails.address.location.id,
          longitude: text.longitude,
          latitude: text.latitude,
        },
      },
    };

    axios
      .put(`http://localhost:8080/api/pharmacy/${pharmacyDetails.id}`, dto)
      .then((res) => {
        changedPharmacy();
        addToast(res.data, {
          appearance: "success",
        });
      })
      .catch((err) => {
        addToast(err.response.data, {
          appearance: "error",
        });
      });
  };

  let cancelClickHandler = () => {
    setValid(true);
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

  let handleClickOnMap = function (longitude, latitude) {
    setText({ ...text, latitude: latitude, longitude: longitude });
    if (fixing === "longitude" || fixing === "latitude") {
      setFixing(null);
      setValid(true);
    }
    // Try finding city and country
    axios
      .get(
        `https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=${latitude}&longitude=${longitude}&localityLanguage=en`
      )
      .then((res) => {
        setText({
          ...text,
          country: res.data.countryName,
          latitude: latitude,
          longitude: longitude,
          street: res.data.locality,
        });
      })
      .catch((err) => {
        addToast(err.response.data, {
          appearance: "error",
        });
      });
  };

  let validateData = (key, value) => {
    let isValid = true;
    let message = "";
    if (value == "") {
      isValid = false;
      message = `Value '${key}' is required.`;
    }
    if (key === "longitude" || key === "latitude") {
      let list = value.split(/\./);
      if (list.length !== 2) {
        isValid = false;
      } else {
        if (
          Number.parseInt(list[0]).toString() === list[0] &&
          Number.parseInt(list[1]).toString() === list[1]
        ) {
          isValid = true;
        } else {
          isValid = false;
        }
      }

      if (!isValid) {
        message = `Value '${key}' is not valid. Example of valid value is '19.833549'`;
      }
    }

    if (!isValid) {
      setFixing(key);
    } else {
      setFixing(null);
    }

    setValid(isValid);

    if (message != "")
      addToast(message, {
        appearance: "warning",
      });
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
  }, [pharmacyDetails, editMode, handleClickOnMap]);

  return (
    <Tab.Pane eventKey="first">
      <h1 className="content-header">Basic informations</h1>
      <hr></hr>
      <Form>
        <Row className="row-content">
          <Col lg={6} md={12} sm={12}>
            <Form.Group as={Row} controlId="formHorizontalName">
              <Form.Label column sm={2}>
                Name
              </Form.Label>
              <Col sm={10}>
                <Form.Control
                  type="text"
                  name="name"
                  placeholder="Name"
                  disabled={!editMode || (fixing !== null && fixing !== "name")}
                  value={text?.name}
                  onChange={handleChange}
                />
              </Col>
            </Form.Group>

            <Form.Group as={Row} controlId="formHorizontalDescription">
              <Form.Label column sm={2}>
                Description
              </Form.Label>
              <Col sm={10}>
                <Form.Control
                  type="text"
                  name="description"
                  placeholder="Description"
                  disabled={
                    !editMode || (fixing !== null && fixing !== "description")
                  }
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
                  type="text"
                  name="country"
                  placeholder="Country"
                  disabled={
                    !editMode || (fixing !== null && fixing !== "country")
                  }
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
                  type="text"
                  name="city"
                  placeholder="City"
                  disabled={!editMode || (fixing !== null && fixing !== "city")}
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
                  type="text"
                  name="street"
                  placeholder="Street"
                  disabled={
                    !editMode || (fixing !== null && fixing !== "street")
                  }
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
                  type="text"
                  name="longitude"
                  placeholder="Longitude"
                  disabled={
                    !editMode || (fixing !== null && fixing !== "longitude")
                  }
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
                  type="text"
                  name="latitude"
                  placeholder="Latitude"
                  disabled={
                    !editMode || (fixing !== null && fixing !== "latitude")
                  }
                  value={text?.latitude}
                  onChange={handleChange}
                />
              </Col>
            </Form.Group>
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
              disabled={!editMode || !valid}
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
      </Form>
    </Tab.Pane>
  );
}

export default EditBasicInfo;
