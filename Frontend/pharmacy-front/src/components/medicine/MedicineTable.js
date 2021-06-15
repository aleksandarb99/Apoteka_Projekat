import axios from "../../app/api";
import React, { useEffect, useState } from "react";
import { Button, Container, Row, Table } from "react-bootstrap";
import MedicineRow from "./MedicineRow";
import AddMedicineModal from "./AddMedicineModal";
import EditMedicineModal from "./EditMedicineModal";
import DeleteModal from "../utilComponents/modals/DeleteModal";
import { useToasts } from 'react-toast-notifications';
import { getErrorMessage } from '../../app/errorHandler';
import AddMedicineTypeModal from "./AddMedicineTypeModal";
import AddMedicineFormModal from "./AddMedicineFormModal";
import AddManufacturerModal from "./AddManufacturerModal";

function MedicineTable() {
  const [reload, setReload] = useState(false);
  const [selected, setSelected] = useState({});

  const [medicine, setMedicine] = useState([]);

  const [showAddModal, setShowAddModal] = useState(false);
  const [showAddMedicineTypeModal, setShowAddMedicineTypeModal] = useState(false);
  const [showAddMedicineFormModal, setShowAddMedicineFormModal] = useState(false);
  const [showAddManufacturerModal, setShowAddManufacturerModal] = useState(false);
  const [showEditModal, setShowEditModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const { addToast } = useToasts();

  useEffect(() => {
    async function fetchData() {
      const response = await axios.get("/api/medicine/crud").catch(() => { });
      setMedicine(response.data);
    }
    fetchData();
  }, [reload]);

  const reloadTable = () => {
    setReload(!reload);
  };

  const updateSelected = (selectedMedicine) => {
    setSelected(selectedMedicine);
  };

  const deleteMedicine = () => {
    axios
      .delete("/api/medicine/" + selected.id)
      .then(() => {
        reloadTable();
        setShowDeleteModal(false);
        addToast("Medicine deleted successfully.", { appearance: 'success' });
      })
      .catch((err) => {
        addToast(getErrorMessage(err), { appearance: 'error' })
      });
  };

  return (
    <Container style={{ marginTop: "10px" }} className="justify-content-center">
      <Row className="justify-content-md-between">
        <Button
          variant="secondary"
          style={{ float: "right", margin: "20px" }}
          onClick={() => setShowAddModal(true)}
        >
          Add new medicine
        </Button>
        <Button
          variant="secondary"
          style={{ float: "right", margin: "20px" }}
          onClick={() => setShowAddMedicineTypeModal(true)}
        >
          Add new medicine type
        </Button>
        <Button
          variant="secondary"
          style={{ float: "right", margin: "20px" }}
          onClick={() => setShowAddMedicineFormModal(true)}
        >
          Add new medicine form
        </Button>
        <Button
          variant="secondary"
          style={{ float: "right", margin: "20px" }}
          onClick={() => setShowAddManufacturerModal(true)}
        >
          Add new medicine manufacturer
        </Button>
      </Row>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Name</th>
            <th>Code</th>
            <th>Ingredients</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {medicine.map((medi) => (
            <MedicineRow
              key={medi.id}
              medicine={medi}
              onClick={() => updateSelected(medi)}
              editClick={() => setShowEditModal(true)}
              deleteClick={() => setShowDeleteModal(true)}
            ></MedicineRow>
          ))}
        </tbody>
      </Table>
      <AddMedicineModal
        show={showAddModal}
        onHide={() => setShowAddModal(false)}
        onSuccess={reloadTable}
      />
      <AddMedicineTypeModal
        show={showAddMedicineTypeModal}
        onHide={() => setShowAddMedicineTypeModal(false)}
      />
      <AddMedicineFormModal
        show={showAddMedicineFormModal}
        onHide={() => setShowAddMedicineFormModal(false)}
      />
      <AddManufacturerModal
        show={showAddManufacturerModal}
        onHide={() => setShowAddManufacturerModal(false)}
      />
      <DeleteModal
        title={"Remove " + selected.name}
        show={showDeleteModal}
        onHide={() => setShowDeleteModal(false)}
        onDelete={deleteMedicine}
      />
      <EditMedicineModal
        show={showEditModal}
        medicine={selected}
        onHide={() => setShowEditModal(false)}
        onSuccess={reloadTable}
      />
    </Container>
  );
}

export default MedicineTable;
