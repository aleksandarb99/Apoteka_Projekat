import React, { useEffect, useState } from "react";

import {
  Row,
  Col,
  Container,
  Table,
  Button,
  Form,
  Alert,
} from "react-bootstrap";
import Dropdown from "react-bootstrap/Dropdown";

import axios from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";

import moment from "moment";

import "../../styling/pharmaciesAndMedicines.css";
import "../../styling/consultation.css";

function ReservedMedicines() {
  const [reservations, setReservations] = useState([]);

  const [reload, setReload] = useState(false);
  const [showAlert, setShowAlert] = useState(false);
  const [showBadAlert, setShowBadAlert] = useState(false);

  useEffect(() => {
    async function fetchReservations() {
      const request = await axios.get(
        "http://localhost:8080/api/medicine-reservation/reserved-medicines/patient/" +
        getIdFromToken()
      );
      setReservations(request.data);

      return request;
    }
    fetchReservations();
  }, [reload]);

  const cancelReservation = (id) => {
    axios
      .put(
        "http://localhost:8080/api/medicine-reservation/cancel-reservation/" +
        id
      )
      .then((res) => {
        if (res.data == "canceled") {
          setShowAlert(true);
          setTimeout(function () {
            setShowAlert(false);
          }, 5000);
        } else {
          setShowBadAlert(true);
          setTimeout(function () {
            setShowBadAlert(false);
          }, 5000);
        }
        setReload(!reload);
      });
  };

  function differenceInMinutes(startTime) {
    let today = new Date().getTime();
    if ((startTime - today) / 60000 < 1440) return false;
    return true;
  }

  return (
    <Container fluid className="consultation__insight__container">
      <div className="consultation__insight__content">
        <Row
          className="my__flex"
          style={{ display: reservations.length === 0 ? "none" : "flex" }}
        >
          <h3>Reserved medicines</h3>
        </Row>
        <Row>
          <Table
            striped
            bordered
            variant="light"
            className="my__table__pharmacies"
            style={{ display: reservations.length === 0 ? "none" : "table" }}
          >
            <thead>
              <tr>
                <th>Id</th>
                <th>Reservation date</th>
                <th>Pickup date</th>
                <th>Medicine</th>
                <th>Pharmacy</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {reservations &&
                reservations.map((r) => (
                  <tr key={r.id}>
                    <td>{r.reservationID}</td>
                    <td>
                      {moment(r.reservationDate).format("DD-MM-YYYY HH:mm")}
                    </td>
                    <td>{moment(r.pickupDate).format("DD-MM-YYYY HH:mm")}</td>
                    <td>{r.medicineName}</td>
                    <td>{r.pharmacyName}</td>
                    <td>
                      <Button
                        variant="danger"
                        onClick={() => cancelReservation(r.id)}
                        style={{
                          display: differenceInMinutes(r.pickupDate)
                            ? "inline-block"
                            : "none",
                        }}
                      >
                        Cancel
                      </Button>
                    </td>
                  </tr>
                ))}
            </tbody>
          </Table>
        </Row>
        <Row
          className="my__flex"
          style={{ display: reservations.length === 0 ? "block" : "none" }}
        >
          <h3>You have no reservations made!</h3>
        </Row>
        <Row style={{ justifyContent: "center" }}>
          {showAlert && (
            <Alert transition={true} variant="success">
              Successfully canceled reservation!
            </Alert>
          )}
        </Row>
        <Row style={{ justifyContent: "center" }}>
          {showBadAlert && (
            <Alert transition={true} variant="danger">
              24 hours to reservation pickup! You can not cancel it!
            </Alert>
          )}
        </Row>
      </div>
    </Container>
  );
}

export default ReservedMedicines;
