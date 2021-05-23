import React, { useEffect, useState } from "react";
import axios from "./../../app/api";
import { Table, Button, Modal } from "react-bootstrap";
import AllergyRow from "./AllergyRow";
import { Plus } from "react-bootstrap-icons";
import "../../styling/allergies.css";
import { getIdFromToken } from "../../app/jwtTokenUtils";
import { useToasts } from "react-toast-notifications";

function Allergies() {
  const [reload, setReload] = useState(false);
  const [allergies, setAllergies] = useState([]);
  const [medicines, setMedicines] = useState([]);
  const [selectedMedicine, setSelectedMedicine] = useState({});
  const [showAddModal, setShowAddModal] = useState(false);
  const { addToast } = useToasts();

  useEffect(() => {
    async function fetchData() {
      const response = await axios.get(
        "http://localhost:8080/api/patients/allergies/all/" + getIdFromToken()
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

  const updateSelectedMedicine = (selectedMedicine) => {
    setSelectedMedicine(selectedMedicine);
  };

  const deleteAllergy = (id) => {
    axios
      .delete(
        "http://localhost:8080/api/patients/allergies/" +
          getIdFromToken() +
          "/" +
          id
      )
      .then((res) => {
        reloadTable();
        addToast(res.data, { appearance: "success" });
      })
      .catch((err) => {
        addToast(err.response.data, { appearance: "error" });
      });
  };

  const reloadTable = () => {
    setReload(!reload);
  };

  const addAllergy = () => {
    axios
      .post(
        "http://localhost:8080/api/patients/allergies/" +
          getIdFromToken() +
          "/" +
          selectedMedicine.id
      )
      .then((res) => {
        addToast(res.data, { appearance: "success" });
        reloadTable();
        setShowAddModal(false);
        setSelectedMedicine({});
      })
      .catch((err) => {
        addToast(err.response.data, { appearance: "error" });
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
                deleteClick={() => deleteAllergy(a.id)}
              ></AllergyRow>
            ))}
        </tbody>
      </Table>
      <Button variant="primary" onClick={() => setShowAddModal(true)}>
        <Plus style={{ width: "1.5em", height: "1.5em" }} />
        Add
      </Button>
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
      </Modal>
    </div>
  );
}

export default Allergies;
