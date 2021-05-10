import React, { useEffect, useState } from "react";
import api from '../../app/api'
import { getIdFromToken, getUserTypeFromToken } from '../../app/jwtTokenUtils'

import { StarFill } from "react-bootstrap-icons";

import Map from "ol/Map";
import OSM from "ol/source/OSM";
import TileLayer from "ol/layer/Tile";
import View from "ol/View";
import { fromLonLat } from "ol/proj";

import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import Tab from "react-bootstrap/Tab";

function PharmacyBasic({ details }) {
  const [isUserSubscribed, setIsUserSubscribed] = useState(false)

  useEffect(() => {
    checkIfUserIsSubscribed();
    document.getElementById("mapCol").innerHTML = "";
    return new Map({
      target: "mapCol",
      layers: [
        new TileLayer({
          source: new OSM(),
        }),
      ],

      view: new View({
        center: fromLonLat([
          details?.address?.location?.longitude,
          details?.address?.location?.latitude,
        ]),
        zoom: 10,
        minZoom: 5,
        maxZoom: 12,
      }),
    });
  }, [details]);

  const subscribe = () => {
    if (isUserSubscribed) {
      api.post(`http://localhost:8080/api/pharmacy/${details.id}/unsubscribe/${getIdFromToken()}`)
        .then(() => {
          alert("Success")
          checkIfUserIsSubscribed()
        })
        .catch(() => { alert("Error") })
    } else {
      api.post(`http://localhost:8080/api/pharmacy/${details.id}/subscribe/${getIdFromToken()}`)
        .then(() => {
          alert("Success")
          checkIfUserIsSubscribed()
        })
        .catch(() => { alert("Error") })
    }
  }

  const checkIfUserIsSubscribed = () => {
    api.get(`http://localhost:8080/api/pharmacy/${details.id}/subscribe/${getIdFromToken()}`)
      .then((res) => {
        console.log(res.data)
        setIsUserSubscribed(res.data)
      })
  }

  return (
    <Tab.Pane eventKey="first">
      <Container fluid>
        <Row className="row-content">
          <Col lg={6} md={6} sm={12} className="my-vertical-col center">
            <h1>{details?.name}</h1>
            <div className="center">
              {details.avgGrade &&
                [...Array(Math.ceil(details.avgGrade))].map(() => (
                  <StarFill className="my__star" />
                ))}
            </div>
            <h5>- {details?.description} -</h5>
            <h4>
              {details?.address?.street}, {details?.address?.city},{" "}
              {details?.address?.country}
            </h4>
            <Button
              variant="primary"
              hidden={getUserTypeFromToken() !== "PATIENT"}
              onClick={subscribe}>{isUserSubscribed ? "Unsubscribe" : "Subscribe"}
            </Button>
          </Col>
          <Col lg={6} md={6} sm={12} id="mapCol" className="center"></Col>
        </Row>
        <hr></hr>
      </Container>
    </Tab.Pane>
  );
}

export default PharmacyBasic;
