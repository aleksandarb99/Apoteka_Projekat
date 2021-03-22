import React, { useState, useEffect } from "react";
import  {Container, Row, Col, Button, Navbar, Nav, NavDropdown, Form, FormControl} from "react-bootstrap";
import AppointmentDerm from "./appointment_component";
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from "axios";

// const isToday = (someDate) => {
//   const today = new Date()
//   return someDate.getDate() == today.getDate() &&
//     someDate.getMonth() == today.getMonth() &&
//     someDate.getFullYear() == today.getFullYear()
// }

function DermHomePage() {
  const [appointments, setAppointments] = useState([]); 
  // sortiramo ih - proveri tamo negde kada da ih sortiras
  // TODO fixovati hardkodovano dermatologist id, kao i time

  useEffect(() => {
    async function fetchAppointments() {
      const request = await axios.get("http://localhost:8080/api/dermatologist/upcomming/1");
      setAppointments(request.data.sort((a, b) => a.date - b.date));

      return request;
    }
    fetchAppointments();
  }, []);

  return (
    <div>
      
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

        <Row className="justify-content-center m-3 align-items-center"><h2>Upcomming appointments</h2></Row>
        
        {appointments.length == 0 &&
          <h1>There are no upcomming appointments!</h1>
        }

        {appointments.map((value, index) => {
          if (value){
            // if (isToday(value.date)){
            //   <p>Today</p>
            // }else{
            //   <p>not today</p>
            // }

            // <Row className="justify-content-center m-3 align-items-center" key={index}>
            return (<Row className="justify-content-center m-5 align-items-center" key={index}>
              <Col md={8}>
                <h3>{value.startTime}</h3>
                {/* {isToday(value.date) ?  */}
                    {/*  : <h3>{value.date.getDate() + "/" + (value.date.getMonth()+1) + 
                    "/" + value.date.getFullYear()}</h3> } */}
              <AppointmentDerm 
              // time={value.date.getHours() + ":" + value.date.getMinutes()}
              time="12:00"
              pharmacy={value.pharmacy.name}
              price={value.price}
              patient={value.patient ? value.patient.firstName + " " + value.patient.lastName : 'EMPTY'}
              id={index}></AppointmentDerm></Col>
              </Row>)
          }
         })}
    </div>
  );
}

export default DermHomePage;