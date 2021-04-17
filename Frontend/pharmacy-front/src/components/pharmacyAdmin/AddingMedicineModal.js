import { React, useState, useEffect } from "react";
import { Button, Table, Modal } from "react-bootstrap";

import axios from "axios";

function AddingMedicineModal(props) {
  const [medicineList, setMedicineList] = useState([]);
  const [selectedRowId, setSelectedRowId] = useState(-1);

  let handleClick = (medicineId) => {
    setSelectedRowId(medicineId);
  };

  async function fetchMedicine() {
    const request = await axios.get(
      `http://localhost:8080/api/medicine/notexistingmedicinebypharmacyid/${props.idOfPharmacy}`
    );
    setMedicineList(request.data);
    return request;
  }

  useEffect(() => {
    if (props.idOfPharmacy != undefined) {
      fetchMedicine();
    }
  }, [props.idOfPharmacy, props.medicineItemsLength]);

  return (
    <Modal
      onExited={() => {
        setSelectedRowId(-1);
      }}
      {...props}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Choose medicine
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Table bordered variant="light">
          <thead>
            <tr>
              <th>#</th>
              <th>Code</th>
              <th>Name</th>
              <th>Content</th>
              <th>Avg. grade</th>
            </tr>
          </thead>
          <tbody>
            {medicineList?.length != 0 &&
              medicineList?.map((item, index) => (
                <tr
                  onClick={() => {
                    handleClick(item.id);
                  }}
                  className={`${
                    selectedRowId == item.id && "selectedRow"
                  } pointer`}
                >
                  <td>{index + 1}</td>
                  <td>{item.code}</td>
                  <td>{item.name}</td>
                  <td>{item.content}</td>
                  <td>{item.avgGrade}</td>
                </tr>
              ))}
          </tbody>
        </Table>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={props.onHide}>
          Close
        </Button>
        <Button
          disabled={selectedRowId == -1}
          variant="primary"
          onClick={() => {
            props.handleAdd(selectedRowId);
          }}
        >
          Save Changes
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default AddingMedicineModal;
