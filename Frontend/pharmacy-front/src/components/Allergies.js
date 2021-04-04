import React, { useEffect, useState } from "react";
import axios from "axios";
import { Table, Button, Modal } from "react-bootstrap";
import AllergyRow from "./AllergyRow";
import DeleteModal from "./utilComponents/DeleteModal";
import { Plus } from "react-bootstrap-icons";
import "./../styling/allergies.css";

function Allergies() {
  const [reload, setReload] = useState(false);
  const [allergies, setAllergies] = useState([]);
  const [medicines, setMedicines] = useState([]);
  const [selectedAllergy, setSelectedAllergy] = useState({});
  const [selectedMedicine, setSelectedMedicine] = useState({});
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [showAddModal, setShowAddModal] = useState(false);

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

  useEffect(() => {
    async function fetchData() {
      const response = await axios.get("http://localhost:8080/api/medicine/");
      setMedicines(response.data);
      if (response.data == "") setMedicines(null);
    }
    fetchData();
  }, [reload]);

  const updateSelectedAllergy = (selectedAllergy) => {
    setSelectedAllergy(selectedAllergy);
  };

  const updateSelectedMedicine = (selectedMedicine) => {
    setSelectedMedicine(selectedMedicine);
  };

  const deleteAllergy = () => {
    axios
      .delete(
        "http://localhost:8080/api/patients/allergies/2/" + selectedAllergy.id //TODO primeniti logiku za dobaljanje konkretnog pacijenta
      )
      .then((res) => {
        if (res.data === "") {
          alert("Deleting allergy has failed!");
          return;
        }
        reloadTable();
        alert("Allergy deleted successfully");
        setShowDeleteModal(false);
      });
  };

  const reloadTable = () => {
    setReload(!reload);
  };

  const addAllergy = () => {
    axios
      .post(
        "http://localhost:8080/api/patients/allergies/2/" + selectedMedicine.id //TODO primeniti logiku za dobaljanje konkretnog pacijenta
      )
      .then((res) => {
        if (res.data === "") {
          alert("Adding allergy has failed!");
          return;
        }
        reloadTable();
        alert("Allergy added successfully");
        setShowAddModal(false);
        setSelectedMedicine({});
      });
  };

  return (
    <div>
      <h3 className="my__allergy__header">Allergies</h3>
      <Table
        striped
        bordered
        variant="light"
        size="lg"
        style={{ display: allergies != null ? "block" : "none" }}
      >
        <thead>
          <tr>
            <th>Code</th>
            <th>Name</th>
            <th>Content</th>
            <th></th>
          </tr>
        </thead>
        <tbody className="my__table__body">
          {allergies &&
            allergies.map((a) => (
              <AllergyRow
                key={a.id}
                allergy={a}
                onClick={() => updateSelectedAllergy(a)}
                deleteClick={() => setShowDeleteModal(true)}
              ></AllergyRow>
            ))}
        </tbody>
      </Table>
      <Button variant="primary" onClick={() => setShowAddModal(true)}>
        <Plus style={{ width: "1.5em", height: "1.5em" }} />
        Add
      </Button>
      <DeleteModal
        title={"Remove " + selectedAllergy.name}
        show={showDeleteModal}
        onHide={() => setShowDeleteModal(false)}
        onDelete={deleteAllergy}
      />
      <Modal
        aria-labelledby="contained-modal-title-vcenter"
        centered
        onHide={() => {
          setShowAddModal(false);
          setSelectedMedicine({});
        }}
        show={showAddModal}
      >
        <Modal.Header closeButton>
          <Modal.Title id="contained-modal-title-vcenter">
            Add new allergy
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Table
            striped
            bordered
            variant="light"
            size="md"
            style={{ display: medicines != null ? "block" : "none" }}
          >
            <thead>
              <tr>
                <th>Code</th>
                <th>Name</th>
                <th>Content</th>
              </tr>
            </thead>
            <tbody className="my__table__body">
              {medicines &&
                medicines.map((m) => (
                  <tr
                    onClick={() => updateSelectedMedicine(m)}
                    key={m.id}
                    className={
                      selectedMedicine.id === m.id
                        ? "my__row__selected my__table__row"
                        : "my__table__row"
                    }
                  >
                    <td>{m.name}</td>
                    <td>{m.code}</td>
                    <td>{m.content}</td>
                  </tr>
                ))}
            </tbody>
          </Table>
          <Button
            variant="info"
            onClick={addAllergy}
            disabled={Object.keys(selectedMedicine).length === 0 ? true : false}
          >
            Add
          </Button>
        </Modal.Body>
        <Modal.Footer></Modal.Footer>
      </Modal>
    </div>
  );
}

export default Allergies;
