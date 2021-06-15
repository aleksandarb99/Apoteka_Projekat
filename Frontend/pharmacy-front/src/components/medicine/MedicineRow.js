import React from "react";
import { Button } from "react-bootstrap";
import { FileEarmarkText } from "react-bootstrap-icons";

const MedicineRow = ({
  medicine,
  onClick,
  editClick,
  detailsClick,
  deleteClick,
}) => {
  return (
    <tr onClick={onClick} key={medicine.id}>
      <td>{medicine.name}</td>
      <td>{medicine.code}</td>
      <td>{medicine.content}</td>
      <td>
        <span>
          <Button onClick={editClick}>Edit</Button>
          <Button variant="danger" onClick={deleteClick}>
            Delete
          </Button>
          <Button
            variant="info"
            onClick={() =>
              window.open(`https://apotekaprojekat.herokuapp.com/api/medicine/${medicine.id}/get-pdf`, "_blank")
            }
          >
            {" "}
            Details
          </Button>
        </span>
      </td>
    </tr>
  );
};

export default MedicineRow;
