import React, { useState, useEffect } from "react";
import axios from "./../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";

function PatientBenefits() {
  const [points, setPoints] = useState();
  const [category, setCategory] = useState({});

  useEffect(() => {
    async function fetchPoints() {
      const request = await axios.get(
        "http://localhost:8080/api/patients/" + getIdFromToken() + "/points"
      );
      setPoints(request.data);

      return request;
    }
    fetchPoints();
  }, {});

  useEffect(() => {
    async function fetchCategory() {
      const request = await axios.get(
        "http://localhost:8080/api/ranking-category/points/" + points
      );
      setCategory(request.data);

      return request;
    }
    fetchCategory();
  }, [points]);

  return (
    <div>
      <h3 style={{ textAlign: "center" }}>Benefits</h3>
      <p style={{ textAlign: "center" }}>Achieved points: {points} </p>
      <div style={{ display: category == "" ? "none" : "block" }}>
        <p style={{ textAlign: "center" }}>Category: {category.name} </p>
        <p style={{ textAlign: "center" }}>
          Requied points: {category.pointsRequired}{" "}
        </p>
        <p style={{ textAlign: "center" }}>Discount: {category.discount}%</p>
      </div>
      <h5
        style={{
          display: category == "" ? "block" : "none",
          fontWeight: "bold",
          textAlign: "center",
        }}
      >
        No category achieved
      </h5>
    </div>
  );
}

export default PatientBenefits;
