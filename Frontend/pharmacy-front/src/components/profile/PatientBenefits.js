import React, { useState, useEffect } from "react";
import api from "./../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";

function PatientBenefits() {
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
  }, {});

  useEffect(() => {
    if (!!points) {
      async function fetchCategory() {
        const request = await api.get("/api/ranking-category/points/" + points).catch(() => { });
        setCategory(!!request ? request.data : {});

        return request;
      }
      fetchCategory();
    }
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
