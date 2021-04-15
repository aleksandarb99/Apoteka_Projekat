import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

// import axios from "./../../app/api";
import axios from "axios";

import "../../styling/medicineProfile.css";

function MedicineProfile() {
  const [medicine, setMedicine] = useState({});

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
      </div>
    </div>
  );
}

export default MedicineProfile;
