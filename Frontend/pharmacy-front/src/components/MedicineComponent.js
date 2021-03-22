import React from "react";
import "../styling/medicineComponent.css";
import { StarFill } from "react-bootstrap-icons";

function MedicineComponent({ name, code, content, avgGrade }) {
  return (
    <div className="card2">
      <div className="card2-title">
        <p>{name}</p>
        <p>#{code}</p>
      </div>
      <p>Content: {content}</p>
      <div>
        {[...Array(avgGrade)].map(() => (
          <StarFill />
        ))}
      </div>
    </div>
  );
}

export default MedicineComponent;
