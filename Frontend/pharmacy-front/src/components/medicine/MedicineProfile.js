import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { Table, Button } from "react-bootstrap";
import DatePicker from "react-datepicker";
import { FileEarmarkText } from "react-bootstrap-icons"

import {
  getIdFromToken,
  getUserTypeFromToken,
} from "./../../app/jwtTokenUtils";

import api from "./../../app/api";

import "../../styling/medicineProfile.css";
import "../../styling/allergies.css";
import { useToasts } from "react-toast-notifications";
import { getErrorMessage } from "../../app/errorHandler";

function MedicineProfile() {
  const [medicine, setMedicine] = useState({});
  const [pharmacies, setPharmacies] = useState([]);
  const [pickupDate, setPickupDate] = useState(null);
  const [selectedPharmacy, setSelectedPharmacy] = useState({});
  const { addToast } = useToasts();

  let { id, pid, priceid } = useParams();

  const [points, setPoints] = useState(0);
  const [category, setCategory] = useState({});

  useEffect(() => {
    async function fetchPoints() {
      const request = await api.get(
        "/api/patients/" + getIdFromToken() + "/points"
      ).catch(() => { });
      setPoints(!!request ? request.data : 0);
      return request;
    }
    fetchPoints();
  }, []);

  useEffect(() => {
    async function fetchCategory() {
      const request = await api.get("/api/ranking-category/points/" + points).catch(() => { });
      setCategory(!!request ? request.data : {});

      return request;
    }
    fetchCategory();
  }, [points]);

  useEffect(() => {
    async function fetchMedicine() {
      const request = await api.get(`/api/medicine/${id}`).catch(() => { });
      setMedicine(!!request ? request.data : {});
      return request;
    }
    fetchMedicine();
  }, [id]);

  useEffect(() => {
    async function fetchPharmacies() {
      const request = await api.get(`/api/pharmacy/medicine/${id}`).catch(() => { });
      setPharmacies(!!request ? request.data : []);
      return request;
    }
    if (pid == -1) {
      fetchPharmacies();
    }
  }, [id, pid]);

  const updateSelectedPharmacy = (selectedPharmacy) => {
    setSelectedPharmacy(selectedPharmacy);
  };

  const createReservation = () => {
    if (pickupDate) {
      if (pickupDate < new Date()) {
        addToast("Choose date from the future!", { appearance: "warning" });
        return;
      }
    }

    let forSend = {
      pickupDate: pickupDate.getTime(),
      medicineId: id,
      pharmacyId: pid == -1 ? selectedPharmacy.id : pid,
      userId: getIdFromToken(),
      price: priceid == -1 ? selectedPharmacy.price : priceid,
    };

    api
      .post("/api/medicine-reservation/", forSend)
      .then((res) => {
        addToast(res.data, { appearance: "success" });
      })
      .catch((err) => {
        addToast(getErrorMessage(err), { appearance: "error" });
      });

    setSelectedPharmacy({});
    setPickupDate(null);
  };

  return (
    <div className="medicine__profile__container">
      <div className="medicine__content">
        <p className="my__medicine__header">
          <h2>{medicine.name}</h2>
          <a href={`https://apotekaprojekat.herokuapp.com/api/medicine/${id}/get-pdf`} target="_blank" rel="noopener noreferrer">
            <h4><FileEarmarkText /></h4>
          </a>
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Code: </span>
          {medicine.code}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Content:</span>{" "}
          {medicine.content}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Side effects:</span>{" "}
          {medicine.sideEffects}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Daily intake: </span>
          {medicine.dailyIntake}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Recipe:</span>{" "}
          {medicine.recipeRequired === "REQUIRED" ? "Required" : "Not required"}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Additional notes: </span>
          {medicine.additionalNotes}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Average grade: </span>{" "}
          {medicine.avgGrade}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Points: </span>
          {medicine.points}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Type: </span>
          {medicine.medicineType}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Form: </span>
          {medicine.medicineForm}
        </p>
        <p className="my__medicine__paragraph">
          <span className="my__start_paragraph">Manufacturer: </span>
          {medicine.manufacturer}
        </p>
        <div>
          <Table
            striped
            bordered
            variant="light"
            style={{
              display: pharmacies.length !== 0 || pid == -1 ? "table" : "none",
            }}
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
          <div style={{ display: getUserTypeFromToken() === "PATIENT" ? "block" : "none" }}>
            <p className="my__medicine__paragraph">
              <span className="my__start_paragraph">Pickup deadline: </span>
              <DatePicker
                closeOnScroll={true}
                selected={pickupDate}
                dateFormat="dd/MM/yyyy"
                onChange={(date) => setPickupDate(date)}
                isClearable
              />
            </p>
            <p
              style={{
                textAlign: "center",
                display: category == "" ? "none" : "block",
              }}
            >
              You have a discount of {category.discount}%
            </p>
            <p
              style={{
                textAlign: "center",
                fontSize: "1.3rem",
                display:
                  pid == -1 && Object.keys(selectedPharmacy).length != 0
                    ? "block"
                    : "none",
              }}
            >
              Total price:{" "}
              <span style={{ textDecoration: "line-through" }}>
                {selectedPharmacy.price}
              </span>
              {"   ->   "}
              {(selectedPharmacy.price * (100 - category.discount)) / 100}
            </p>
            <p
              style={{
                textAlign: "center",
                fontSize: "1.3rem",
                display: pid != -1 ? "block" : "none",
              }}
            >
              Total price:{" "}
              <span style={{ textDecoration: "line-through" }}>{priceid}</span>
              {"   ->   "}
              {(priceid * (100 - category.discount)) / 100}
            </p>
            <Button
              variant="info"
              onClick={createReservation}
              disabled={
                pickupDate == null ||
                (Object.keys(selectedPharmacy).length === 0 && pid == -1)
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
    </div >
  );
}

export default MedicineProfile;
