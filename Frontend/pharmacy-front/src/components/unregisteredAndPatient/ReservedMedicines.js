import React, { useEffect, useState } from "react";

import { Row, Container, Table, Button } from "react-bootstrap";

import axios from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";

import moment from "moment";

import "../../styling/pharmaciesAndMedicines.css";
import "../../styling/consultation.css";
import { useToasts } from "react-toast-notifications";
import { getErrorMessage } from "../../app/errorHandler";

function ReservedMedicines() {
  const [reservations, setReservations] = useState([]);
  const [reload, setReload] = useState(false);
  const { addToast } = useToasts();

  useEffect(() => {
    async function fetchReservations() {
      const request = await axios.get(
        "/api/medicine-reservation/reserved-medicines/patient/" +
        getIdFromToken()
      ).catch(() => { });;
      setReservations(!!request ? request.data : []);

      return request;
    }
    fetchReservations();
  }, [reload]);

  const cancelReservation = (id) => {
    axios
      .put("/api/medicine-reservation/cancel-reservation/" + id)
      .then((res) => {
        addToast(res.data, { appearance: "success" });
        setReload(!reload);
      })
      .catch((err) => {
        addToast(getErrorMessage(err), { appearance: "error" });
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
      </div>
    </Container>
  );
}

export default ReservedMedicines;
