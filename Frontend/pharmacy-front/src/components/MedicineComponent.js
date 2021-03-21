import React from "react";
import "../styling/medicineComponent.css";
import GradeIcon from "@material-ui/icons/Grade";

function MedicineComponent({ name, code, content, avgGrade }) {
  return (
    <div className="card">
      <div className="card-title">
        <p>{name}</p>
        <p>#{code}</p>
      </div>
      <p>Content: {content}</p>
      <div>
        {[...Array(avgGrade)].map(() => (
          <GradeIcon />
        ))}
      </div>
    </div>
  );
}

export default MedicineComponent;
