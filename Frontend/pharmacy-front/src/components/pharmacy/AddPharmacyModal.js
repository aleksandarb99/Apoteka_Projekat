import axios from "axios";
import React, { useState } from "react";
import { Button, Form, Modal } from "react-bootstrap";
import ErrorModal from "../utilComponents/modals/ErrorModal";
import SuccessModal from "../utilComponents/modals/SuccessModal";
import Location from "../utilComponents/Location";

function AddPharmacyModal(props) {
  const [form, setForm] = useState({});
  const [errors, setErrors] = useState({});
  const [address, setAddress] = useState({});

  const [showErrorModal, setShowErrorModal] = useState(false);
  const [showSuccessModal, setShowSuccessModal] = useState(false);

  const setField = (field, value) => {
    setForm({
      ...form,
      [field]: value,
    });

    if (!!errors[field])
      setErrors({
        ...errors,
        [field]: null,
      });
  };

  const findFormErrors = () => {
    const { name, description } = form;
    const { city, street, country } = address;
    const newErrors = {};
    // name errors
    if (!name || name === "") newErrors.name = "Name cannot be blank!";
    else if (name.length > 40) newErrors.name = "Name is too long!";
    // Description errors
    if (!description || description === "")
      newErrors.description = "Description cannot be blank!";
    else if (description.length > 60)
      newErrors.description = "Description is too long!";
    // City errors
    if (!city || city === "") newErrors.city = "City cannot be blank!";
    else if (city.length > 40) newErrors.city = "City name is too long!";
    // Street errors
    if (!street || street === "") newErrors.street = "Street cannot be blank!";
    else if (street.length > 60) newErrors.street = "Street name is too long!";
    // Country errors
    if (!country || country === "")
      newErrors.country = "Country cannot be blank!";
    else if (country.length > 40)
      newErrors.country = "Country name is too long!";

    return newErrors;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const newErrors = findFormErrors();

    if (Object.keys(newErrors).length > 0) {
      setErrors(newErrors);
    } else {
      sendPostRequest();
    }
  };

  const sendPostRequest = () => {
    let data = convertForm();
    axios
      .post("/api/pharmacy/", data)
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

  const convertForm = () => {
    let data = {};
    data.name = form.name;
    data.description = form.description;
    data.address = address;
    return data;
  };

  return (
    <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered>
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Add new pharmacy
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group>
            <Form.Label>Name</Form.Label>
            <Form.Control
              type="text"
              placeholder="Name"
              isInvalid={!!errors.name}
              onChange={(e) => setField("name", e.target.value)}
            />
            <Form.Control.Feedback type="invalid">
              {errors.name}
            </Form.Control.Feedback>
          </Form.Group>

          <Form.Group controlId="pharmacyDescription">
            <Form.Label>Description</Form.Label>
            <Form.Control
              type="text"
              placeholder="Description"
              isInvalid={!!errors.description}
              onChange={(e) => setField("description", e.target.value)}
            />
            <Form.Control.Feedback type="invalid">
              {errors.description}
            </Form.Control.Feedback>
          </Form.Group>

          <Form.Group>
            <Form.Label>Points for appointment</Form.Label>
            <Form.Control
              type="number"
              onChange={(event) =>
                setField("pointsForAppointment", event.target.value)
              }
              defaultValue={0}
              min={0}
              max={100.0}
              step={0.01}
            />
          </Form.Group>

          <Location onChange={(address) => setAddress(address)}></Location>

          <Button variant="primary" onClick={handleSubmit}>
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
        message="Pharmacy added successfully."
      >
        {" "}
      </SuccessModal>
    </Modal>
  );
}

export default AddPharmacyModal;
