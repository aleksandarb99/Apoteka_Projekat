import React, { useRef, useState } from "react";
import { Button, Col, Form, Modal, Row } from "react-bootstrap";
import PropTypes from "prop-types";
import FirstNameFormGroup from "../utilComponents/formGroups/FirstNameFormGroup";
import LastNameFormGroup from "../utilComponents/formGroups/LastNameFormGroup";
import EmailFormGroup from "../utilComponents/formGroups/EmailFormGroup";
import PhoneNumberFormGroup from "../utilComponents/formGroups/PhoneNumberFormGroup";
import PasswordFormGroup from "../utilComponents/formGroups/PasswordFormGroup";
import CityFormGroup from "../utilComponents/formGroups/CityFormGroup";
import StreetFormGroup from "../utilComponents/formGroups/StreetFormGroup";
import CountryFormGroup from "../utilComponents/formGroups/CountryFormGroup";
import { useToasts } from 'react-toast-notifications';
import { getErrorMessage } from '../../app/errorHandler';

import axios from "../../app/api";
import Validator from "../../app/validator";

function AddUserModal(props) {
  const [form, setForm] = useState(
    {
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      repeatPassword: '',
      telephone: '',
      city: '',
      street: '',
      country: ''
    }
  );
  const { addToast } = useToasts();


  const setField = (field, value) => {
    setForm({
      ...form,
      [field]: value,
    });
  };

  const validateForm = () => {
    return Validator['firstName'](form['firstName'])
      && Validator['lastName'](form['lastName'])
      && Validator['email'](form['email'])
      && Validator['password'](form['password'])
      && Validator['password'](form['repeatPassword'])
      && Validator['telephone'](form['telephone'])
      && Validator['city'](form['city'])
      && Validator['street'](form['street'])
      && Validator['country'](form['country'])
  }

  const handleSubmit = (event) => {
    event.preventDefault();
    event.stopPropagation();

    if (validateForm()) {
      if (form['password'] !== form['repeatPassword']) {
        addToast("Passwords do not match", { appearance: 'warning' })
        return;
      }
      sendPostRequest();
    }
  };

  const sendPostRequest = () => {
    const newForm = convertForm(form);
    axios
      .post("/api/users/", newForm)
      .then(() => {
        setForm({});
        props.onSuccess();
        props.onHide();
        addToast("User added successfully.", { appearance: 'success' });
      })
      .catch((err) => {
        addToast(getErrorMessage(err), { appearance: 'error' });
      });
  };

  const convertForm = () => {
    let address = {
      city: form["city"],
      street: form["street"],
      country: form["country"],
    };

    let newForm = {
      ...form,
      address: address,
      roleName: props.usertype,
    };

    delete newForm["city"];
    delete newForm["street"];
    delete newForm["country"];

    return newForm;
  };

  return (
    <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered>
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Add new {props.usertype}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form noValidate onSubmit={handleSubmit}>
          <Row>
            <Col md={6}>
              <FirstNameFormGroup
                onChange={(event) => setField("firstName", event.target.value)}
              />
            </Col>
            <Col md={6}>
              <LastNameFormGroup
                onChange={(event) => setField("lastName", event.target.value)}
              />
            </Col>
          </Row>
          <Row>
            <Col md={6}>
              <EmailFormGroup
                onChange={(event) => setField("email", event.target.value)}
              ></EmailFormGroup>
            </Col>
            <Col md={6}>
              <PhoneNumberFormGroup
                onChange={(event) => setField("telephone", event.target.value)}
              ></PhoneNumberFormGroup>
            </Col>
          </Row>
          <Row>
            <Col md={6}>
              <PasswordFormGroup
                onChange={(event) => setField("password", event.target.value)}
              ></PasswordFormGroup>
            </Col>
            <Col md={6}>
              <PasswordFormGroup
                name="Repeat password" onChange={(event) => setField("repeatPassword", event.target.value)}
              ></PasswordFormGroup>
            </Col>
          </Row>
          <CityFormGroup
            onChange={(event) => setField("city", event.target.value)}
          ></CityFormGroup>
          <StreetFormGroup
            onChange={(event) => setField("street", event.target.value)}
          ></StreetFormGroup>
          <CountryFormGroup
            onChange={(event) => setField("country", event.target.value)}
          ></CountryFormGroup>
          <Button variant="primary" type="submit">
            Submit
          </Button>
        </Form>
      </Modal.Body>
      <Modal.Footer />
    </Modal >
  );
}

AddUserModal.propTypes = {
  usertype: PropTypes.string.isRequired,
};
export default AddUserModal;
