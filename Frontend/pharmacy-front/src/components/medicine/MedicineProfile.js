import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { Table, Button, Modal } from "react-bootstrap";

// import axios from "./../../app/api";
import axios from "axios";

import "../../styling/medicineProfile.css";

import { Plus } from "react-bootstrap-icons";

function MedicineProfile() {
  const [medicine, setMedicine] = useState({});
  const [pharmacies, setPharmacies] = useState([]);

  const [selectedPharmacy, setSelectedPharmacy] = useState({});
  const [showAddModal, setShowAddModal] = useState(false);

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
        <Table
          striped
          bordered
          variant="light"
          size="lg"
          style={{ display: pharmacies != null ? "block" : "none" }}
        >
          <thead>
            <tr>
              <th>Name</th>
              <th>Address</th>
              <th>Price</th>
              <th></th>
            </tr>
          </thead>
          <tbody className="my__table__body">
            {pharmacies &&
              pharmacies.map((p) => (
                <tr key={p.id}>
                  <td>{p.name}</td>
                  <td>{p.address}</td>
                  <td>{p.price}</td>
                </tr>
              ))}
          </tbody>
        </Table>
        <Button variant="primary" onClick={() => setShowAddModal(true)}>
          <Plus style={{ width: "1.5em", height: "1.5em" }} />
          Add
        </Button>
      </div>
    </div>
  );
}

export default MedicineProfile;
