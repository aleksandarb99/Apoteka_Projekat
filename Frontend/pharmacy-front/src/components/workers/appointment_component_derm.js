import React from "react";
import {Card} from "react-bootstrap";
import moment from "moment";
import { Link } from "react-router-dom";


function AppointmentDerm({time, pharmacy, price, patient, id}) {

  const is_initiable = (start_time) => {
    if (moment(Date.now()).subtract(15, 'minutes') < time && moment(Date.now()).add(15, 'minutes') > time){
      return true;
    }
    return false;
  }

  const on_link_click = (start_time) => {
    if (moment(Date.now()).subtract(15, 'minutes') < time && moment(Date.now()).add(15, 'minutes') > time){
      return true;
    }
    return false;
  }

  return (
    <div key={"apt" + id}>
      <Card>
        <Card.Body>
            <Card.Title>{moment(time).format("DD MMM YYYY hh:mm a")} </Card.Title>
            <Card.Subtitle className="mb-2">Pharmacy: {pharmacy}</Card.Subtitle>
            <Card.Subtitle className="mb-2">Patient: {patient}</Card.Subtitle>
            <Card.Subtitle className="mb-2">Price: {price}</Card.Subtitle>
            <Card.Link as={Link} to='#'>Initiate appointment</Card.Link>
        </Card.Body>
        </Card>
    </div>
  );
}

export default AppointmentDerm;