import React, { useState, useEffect } from "react";
import {Card} from "react-bootstrap";


function AppointmentDerm({time, pharmacy, price, patient, id}) {

  return (
    <div key={"apt" + id}>
      <Card>
        <Card.Body>
            <Card.Title>{time} </Card.Title>
            <Card.Subtitle className="mb-2">Pharmacy: {pharmacy}</Card.Subtitle>
            <Card.Subtitle className="mb-2">Patient: {patient.firstName + " " + patient.lastName}</Card.Subtitle>
            <Card.Subtitle className="mb-2">Price: {price}</Card.Subtitle>
            <Card.Link href="#">Initiate appointment</Card.Link>
        </Card.Body>
        </Card>
    </div>
  );
}

export default AppointmentDerm;