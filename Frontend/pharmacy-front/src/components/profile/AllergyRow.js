import React from "react";
import { Button } from "react-bootstrap";

const AllergyRow = ({ allergy, deleteClick }) => {
  return (
    <tr key={allergy.id}>
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
