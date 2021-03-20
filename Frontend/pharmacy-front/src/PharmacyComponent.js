import React from "react";
import "./styling/pharmacyComponent.css";
import GradeIcon from "@material-ui/icons/Grade";

function PharmacyComponent({ name, location, description, avgGrade }) {
  return (
    <div className="card">
      <p>{name}</p>
      <p>Description: {description}</p>
      <div>
        {[...Array(avgGrade)].map(() => (
          <GradeIcon />
        ))}
      </div>
    </div>
  );
}

export default PharmacyComponent;
