import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { Table, Button, Alert } from "react-bootstrap";
import DatePicker from "react-datepicker";

import {
  getIdFromToken,
  getUserTypeFromToken,
} from "./../../app/jwtTokenUtils";

import axios from "axios";
import axios2 from "./../../app/api";

import "../../styling/medicineProfile.css";
import "../../styling/allergies.css";

function MedicineProfile() {
  const [medicine, setMedicine] = useState({});
  const [pharmacies, setPharmacies] = useState([]);
  const [pickupDate, setPickupDate] = useState(null);
  const [showAlert, setShowAlert] = useState(false);
  const [successAlert, setSuccessAlert] = useState(false);
  const [selectedPharmacy, setSelectedPharmacy] = useState({});

  let { id } = useParams();

  useEffect(() => {
    async function fetchMedicine() {
      const request = await axios.get(
        `http://localhost:8080/api/medicine/${id}`
      );
      setMedicine(request.data);
      return request;
    }
    fetchMedicine();
  }, [id]);

  useEffect(() => {
    async function fetchPharmacies() {
      const request = await axios.get(
        `http://localhost:8080/api/pharmacy/medicine/${id}`
      );
      setPharmacies(request.data);
      return request;
    }
    fetchPharmacies();
  }, [id]);

  const updateSelectedPharmacy = (selectedPharmacy) => {
    setSelectedPharmacy(selectedPharmacy);
  };

  const createReservation = () => {
    if (pickupDate) {
      if (pickupDate > new Date()) {
        setShowAlert(false);
      } else {
        setSuccessAlert(false);
        setShowAlert(true);
        return;
      }
    }

    let forSend = {
      pickupDate: pickupDate.getTime(),
      medicineId: id,
      pharmacyId: selectedPharmacy.id,
      userId: getIdFromToken(),
    };

    axios2
      .post("http://localhost:8080/api/medicine-reservation/", forSend)
      .then(() => {
        setSuccessAlert(true);
        setShowAlert(false);
      })
      .catch(() => {
        setSuccessAlert(false);
        setShowAlert(true);
      });

    setSelectedPharmacy({});
    setPickupDate(null);
  };

  return (
    <div className="medicine__profile__container">
      <div className="medicine__content">
        <p className="my__medicine__header">{medicine.name}</p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Kod: </span>
          {medicine.code}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Sadrzaj:</span>{" "}
          {medicine.content}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Nezeljeni efekti:</span>{" "}
          {medicine.sideEffects}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Dnevni unos: </span>
          {medicine.dailyIntake}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Recept:</span>{" "}
          {medicine.recipeRequired === "REQUIRED" ? "Treba" : "Ne treba"}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Dodatne informacije: </span>
          {medicine.additionalNotes}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Ocena: </span>{" "}
          {medicine.avgGrade}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">
            Poeni osvojeni pri kupovini:{" "}
          </span>
          {medicine.points}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Tip: </span>
          {medicine.medicineType}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Oblik: </span>
          {medicine.medicineForm}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Proizvodjac: </span>
          {medicine.manufacturer}
        </p>
        <div
          style={{
            display: getUserTypeFromToken() === "PATIENT" ? "block" : "none",
          }}
        >
          <Table
            striped
            bordered
            variant="light"
            style={{ display: pharmacies != null ? "table" : "none" }}
            className="my__table__pharmacies"
          >
            <thead>
              <tr>
                <th>Name</th>
                <th>Address</th>
                <th>Price</th>
              </tr>
            </thead>
            <tbody>
              {pharmacies &&
                pharmacies.map((p) => (
                  <tr
                    key={p.id}
                    onClick={() => updateSelectedPharmacy(p)}
                    className={
                      selectedPharmacy.id === p.id
                        ? "my__row__selected my__table__row"
                        : "my__table__row"
                    }
                  >
                    <td>{p.name}</td>
                    <td>{p.address}</td>
                    <td>{p.price}</td>
                  </tr>
                ))}
            </tbody>
          </Table>
          <p className="my__medicine__paragraph">
            <span className="my__start_paragraph">Rok preuzimanja leka: </span>
            <DatePicker
              closeOnScroll={true}
              selected={pickupDate}
              dateFormat="dd/MM/yyyy"
              onChange={(date) => setPickupDate(date)}
              isClearable
            />
          </p>
          <Alert
            style={{
              display: showAlert ? "block" : "none",
              width: "80%",
              margin: "35px auto",
            }}
            variant="danger"
          >
            Izaberite dan iz buducnosti!
          </Alert>
          <Alert
            style={{
              display: successAlert ? "block" : "none",
              width: "80%",
              margin: "35px auto",
            }}
            variant="success"
          >
            Uspesno rezervisan lek!
          </Alert>
          <Button
            variant="info"
            onClick={createReservation}
            disabled={
              pickupDate == null || Object.keys(selectedPharmacy).length === 0
            }
            style={{
              marginBottom: "25px",
            }}
          >
            Reserve
          </Button>
        </div>
      </div>
    </div>
  );
}

export default MedicineProfile;
