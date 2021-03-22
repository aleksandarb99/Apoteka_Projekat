import React from "react";
import "../styling/pharmacyComponent.css";
import { StarFill } from "react-bootstrap-icons";

function PharmacyComponent({ name, description, avgGrade }) {
  return (
    <div className="card2">
      <p>{name}</p>
      <p>Description: {description}</p>
      <div>
        {[...Array(avgGrade)].map(() => (
          <StarFill />
        ))}
      </div>
    </div>
  );
}

export default PharmacyComponent;
