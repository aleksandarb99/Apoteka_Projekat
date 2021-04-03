import React, { useEffect, useState } from "react";
import axios from "axios";
import { Table, Button } from "react-bootstrap";
import AllergyRow from "./AllergyRow";
import DeleteModal from "./utilComponents/DeleteModal";
import { Plus } from "react-bootstrap-icons";

function Allergies() {
  const [reload, setReload] = useState(false);
  const [allergies, setAllergies] = useState([]);
  const [selected, setSelected] = useState({});
  const [showDeleteModal, setShowDeleteModal] = useState(false);

  useEffect(() => {
    async function fetchData() {
      const response = await axios.get(
        "http://localhost:8080/api/patients/allergies/all/2" //TODO primeniti logiku za dobaljanje konkretnog pacijenta
      );
      setAllergies(response.data);
      if (response.data == "") setAllergies(null);
    }
    fetchData();
  }, [reload]);

  const updateSelected = (selectedAllergy) => {
    setSelected(selectedAllergy);
  };

  const deleteAllergy = () => {
    //TODO primeniti logiku za dobaljanje konkretnog pacijenta
    axios
      .delete("http://localhost:8080/api/patients/allergies/2/" + selected.id)
      .then(() => {
        reloadTable();
        alert("Allergy deleted successfully");
        setShowDeleteModal(false);
      });
  };

  const reloadTable = () => {
    setReload(!reload);
  };

  const addAllergy = () => {};

  return (
    <div>
      <h3 style={{ textAlign: "center", margin: "20px auto" }}>Allergies</h3>
      <Table
        striped
        bordered
        variant="light"
        size="sm"
        style={{ display: allergies != null ? "block" : "none" }}
      >
        <thead>
          <tr>
            <th>Code</th>
            <th>Name</th>
            <th>Content</th>
            <th>#Delete</th>
          </tr>
        </thead>
        <tbody>
          {allergies &&
            allergies.map((a) => (
              <AllergyRow
                key={a.id}
                allergy={a}
                onClick={() => updateSelected(a)}
                deleteClick={() => setShowDeleteModal(true)}
              ></AllergyRow>
            ))}
        </tbody>
      </Table>
      <Button variant="primary">
        <Plus style={{ width: "1.5em", height: "1.5em" }} />
        Add
      </Button>
      <DeleteModal
        title={"Remove " + selected.name}
        show={showDeleteModal}
        onHide={() => setShowDeleteModal(false)}
        onDelete={deleteAllergy}
      />
    </div>
  );
}

export default Allergies;
