import React from "react";
import { Button } from "react-bootstrap";

const AllergyRow = ({ allergy, onClick, deleteClick }) => {
  return (
    <tr onClick={onClick} key={allergy.id}>
      <td>{allergy.code}</td>
      <td>{allergy.name}</td>
      <td>{allergy.content}</td>
      <td>
        <Button variant="danger" onClick={deleteClick}>
          Delete
        </Button>
      </td>
    </tr>
  );
};

export default AllergyRow;
