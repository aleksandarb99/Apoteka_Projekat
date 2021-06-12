import axios from "axios";
import React, { useState } from "react";
import { Button, Form, Modal } from "react-bootstrap";
import MedicineNameFormGroup from "../utilComponents/medicineFormGroups/MedicineNameFromGroup";
import MedicineCodeFormGroup from "../utilComponents/medicineFormGroups/MedicineCodeFormGroup";
import MedicineContentFormGroup from "../utilComponents/medicineFormGroups/MedicineContentFormGroup";
import SideEffectsFormGroup from "../utilComponents/medicineFormGroups/SideEffectsFormGroup";
import AdditionalNotesFormGroup from "../utilComponents/medicineFormGroups/AdditionalNotesFormGroup";
import ErrorModal from "../utilComponents/modals/ErrorModal";
import SuccessModal from "../utilComponents/modals/SuccessModal";

function EditMedicineModal(props) {
  const [form, setForm] = useState({});
  const [validated, setValidated] = useState(false);

  const [showErrorModal, setShowErrorModal] = useState(false);
  const [showSuccessModal, setShowSuccessModal] = useState(false);

  const setField = (field, value) => {
    setForm({
      ...form,
      [field]: value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    event.stopPropagation();

    const f = event.currentTarget;

    if (f.checkValidity() === true) {
      setValidated(true);
      sendPutRequest();
    }
  };

  const sendPutRequest = () => {
    axios
      .put("/api/medicine/" + props.medicine.id, form)
      .then(() => {
        setForm({});
        props.onSuccess();
        props.onHide();
        setShowSuccessModal(true);
      })
      .catch(() => {
        setShowErrorModal(true);
      });
  };

  return (
    <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered>
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Edit {props.medicine.name}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form noValidate validated={validated} onSubmit={handleSubmit}>
          <MedicineNameFormGroup
            onChange={(event) => setField("name", event.target.value)}
            defaultValue={!!props.medicine.code ? props.medicine.code : ""}
          />
          <MedicineCodeFormGroup
            onChange={(event) => setField("code", event.target.value)}
            defaultValue={!!props.medicine.name ? props.medicine.name : ""}
          />
          <MedicineContentFormGroup
            onChange={(event) => setField("content", event.target.value)}
            defaultValue={
              !!props.medicine.content ? props.medicine.content : ""
            }
          />
          <SideEffectsFormGroup
            onChange={(event) => setField("sideEffects", event.target.value)}
            defaultValue={
              !!props.medicine.sideEffects ? props.medicine.sideEffects : ""
            }
          />
          <Form.Group controlId="userTypeSelect">
            <Form.Label>Recipe Required</Form.Label>
            <Form.Control
              as="select"
              onChange={(event) =>
                setField("recipeRequired", event.target.value)
              }
              defaultValue={props.medicine.recipeRequired}
            >
              <option value="REQUIRED">Required</option>
              <option value="NOTREQUIRED">Not required</option>
            </Form.Control>
          </Form.Group>

          <Form.Group controlId="userTypeSelect">
            <Form.Label>Daily Intake</Form.Label>
            <Form.Control
              type="number"
              onChange={(event) => setField("dailyIntake", event.target.value)}
              step="0.1"
              min="0"
              max="10"
              defaultValue={props.medicine.dailyIntake}
            ></Form.Control>
          </Form.Group>

          <Form.Group>
            <Form.Label>Points</Form.Label>
            <Form.Control
              type="number"
              onChange={(event) => setField("points", event.target.value)}
              defaultValue={props.medicine.points}
              min={0}
              max={100.0}
              step={0.01}
              defaultValue={props.medicine.dailyIntake}
            />
          </Form.Group>

          <AdditionalNotesFormGroup
            onChange={(event) =>
              setField("additionalNotes", event.target.value)
            }
            defaultValue={props.medicine.additionalNotes}
          />
          <Button variant="primary" type="submit">
            Submit
          </Button>
        </Form>
      </Modal.Body>
      <Modal.Footer></Modal.Footer>
      <ErrorModal
        show={showErrorModal}
        onHide={() => setShowErrorModal(false)}
        message="Something went wrong."
      ></ErrorModal>
      <SuccessModal
        show={showSuccessModal}
        onHide={() => setShowSuccessModal(false)}
        message="Medicine updated successfully."
      >
        {" "}
      </SuccessModal>
    </Modal>
  );
}

export default EditMedicineModal;
