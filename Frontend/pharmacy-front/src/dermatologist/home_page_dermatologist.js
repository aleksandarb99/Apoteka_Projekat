import React, { useState, useEffect } from "react";
import  {Container, Row, Col, Button, Navbar, Nav, NavDropdown, Form, FormControl} from "react-bootstrap";
import AppointmentDerm from "./appointment_component";
import 'bootstrap/dist/css/bootstrap.min.css';

const isToday = (someDate) => {
  const today = new Date()
  return someDate.getDate() == today.getDate() &&
    someDate.getMonth() == today.getMonth() &&
    someDate.getFullYear() == today.getFullYear()
}

function DermHomePage() {
  const [appointments, setAppointments] = useState([
    {"date":new Date('2021-05-11T15:24:00'), "price": "3000", "patient": {"firstName": "djura", "lastName": "djuric"}}, 
    {"date":new Date('2021-04-12T12:24:00'), "price": "3000", "patient": {"firstName": "djura2", "lastName": "djuric2"}},
    {"date":new Date('2021-01-09T16:24:00'), "price": "3000", "patient": {"firstName": "djura3", "lastName": "djuric3"}},
    {"date":new Date('2021-03-11T14:24:00'), "price": "3000", "patient": {"firstName": "djura4", "lastName": "djuric4"}},
    {"date":new Date('2021-02-22T03:24:00'), "price": "3000", "patient": {"firstName": "djura5", "lastName": "djuric5"}}
  ].sort((a, b) => a.date - b.date)); // sortiramo ih - proveri tamo negde kada da ih sortiras

  return (
    <div>
      <Container>
        <Navbar bg="dark" variant="dark">
          <Navbar.Brand href="#home">Dermatologist</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
          
            <Nav className="m-auto" >
                <Nav.Link href="#home">Home</Nav.Link>
                <Nav.Link href="#home">Profile</Nav.Link>
                <Nav.Link href="#link">Examined patients</Nav.Link>
                <Nav.Link href="#link">Work callendar</Nav.Link>
                <NavDropdown title="Appointments" id="basic-nav-dropdown">
                  <NavDropdown.Item href="#action/3.1">Create an appointment</NavDropdown.Item>
                  <NavDropdown.Item href="#action/3.2">Initiate the appointment</NavDropdown.Item>
                </NavDropdown>
                <Nav.Link href="#link">Request a vacation</Nav.Link>
            </Nav>
        </Navbar.Collapse>
        </Navbar>


        {appointments.length == 0 &&
          <h1>There are no upcomming appointments!</h1>
        }

        {appointments.map((value, index) => {
          if (value){
            if (isToday(value.date)){
              <p>Today</p>
            }else{
              <p>not today</p>
            }

            let currDate = value.date;
            return (<Row className="justify-content-center m-5" key={index}>
              <Col md={8}>
                {isToday(value.date) ? 
                    <h3>Today</h3> : <h3>{value.date.getDate() + "/" + (value.date.getMonth()+1) + 
                    "/" + value.date.getFullYear()}</h3> }
              <AppointmentDerm 
              time={value.date.getHours() + ":" + value.date.getMinutes()}
              pharmacy={value.pharmacy}
              price={value.price}
              patient={value.patient}
              id={index}></AppointmentDerm></Col>
              </Row>)
          }
         })}

        
        {/* <Row className="justify-content-center m-5">
          <Col md={8}  ><AppointmentDerm></AppointmentDerm></Col>
        </Row>
        <Row className="justify-content-center m-5">
          <Col md={8}  ><AppointmentDerm></AppointmentDerm></Col>
        </Row>
        <Row className="justify-content-center m-5">
          <Col md={8}  ><AppointmentDerm></AppointmentDerm></Col>
        </Row>
        <Row className="justify-content-center m-5">
          <Col md={8}  ><AppointmentDerm></AppointmentDerm></Col>
        </Row> */}
        
      </Container>
    </div>
  );
}

export default DermHomePage;