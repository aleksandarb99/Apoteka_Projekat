import React, { useRef, useState } from "react";
import { Button, Col, Form, Modal, Row } from "react-bootstrap";
import PropTypes from "prop-types";
import FirstNameFormGroup from "../utilComponents/formGroups/FirstNameFormGroup";
import LastNameFormGroup from "../utilComponents/formGroups/LastNameFormGroup";
import EmailFormGroup from "../utilComponents/formGroups/EmailFormGroup";
import PhoneNumberFormGroup from "../utilComponents/formGroups/PhoneNumberFormGroup";
import CityFormGroup from "../utilComponents/formGroups/CityFormGroup";
import StreetFormGroup from "../utilComponents/formGroups/StreetFormGroup";
import CountryFormGroup from "../utilComponents/formGroups/CountryFormGroup";
import { useToasts } from 'react-toast-notifications';
import { getErrorMessage } from '../../app/errorHandler';
import api from "../../app/api";
import Validator from "../../app/validator";

function EditUserModal(props) {
  const [form, setForm] = useState({});
  const formRef = useRef(null);
  const { addToast } = useToasts();

  const resetForm = () => {
    setForm(
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
    )
    formRef.current.reset();
  }


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
      sendPutRequest();
    }
  };

  const showHandler = () => {
    let newForm = {
      ...props.user,
      city: props.user.address.city,
      street: props.user.address.street,
      country: props.user.address.country,
    };
    setForm(newForm);
  };

  const sendPutRequest = () => {
    const newForm = convertForm(form);
    console.log(newForm);
    api
      .put("/api/users/" + props.user.id, newForm)
      .then(() => {
        setForm({});
        props.onSuccess();
        props.onHide();
        addToast("User updated successfully.", { appearance: 'success' });
      })
      .catch((err) => {
        addToast(getErrorMessage(err), { appearance: 'error' });
      });
  };

  const convertForm = () => {
    let address = {
      ...props.user.address,
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
    <Modal
      {...props}
      aria-labelledby="contained-modal-title-vcenter"
      centered
      onShow={showHandler}
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Edit {props.user.firstName + " " + props.user.lastName}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form noValidate onSubmit={handleSubmit}>
          <Row>
            <Col md={6}>
              <FirstNameFormGroup
                onChange={(event) => setField("firstName", event.target.value)}
                defaultValue={!!props.user.firstName ? props.user.firstName : ""}
              />
            </Col>
            <Col md={6}>
              <LastNameFormGroup
                onChange={(event) => setField("lastName", event.target.value)}
                defaultValue={!!props.user.lastName ? props.user.lastName : ""}
              />
            </Col>
          </Row>
          <Row>
            <Col md={6}>
              <EmailFormGroup
                onChange={(event) => setField("email", event.target.value)}
                defaultValue={!!props.user.email ? props.user.email : ""}
                disabled={true}
              />
            </Col>
            <Col md={6}>
              <PhoneNumberFormGroup
                onChange={(event) => setField("telephone", event.target.value)}
                defaultValue={!!props.user.telephone ? props.user.telephone : ""}
              />
            </Col>
          </Row>
          <CityFormGroup
            onChange={(event) => setField("city", event.target.value)}
            defaultValue={!!props.user.address ? props.user.address.city : ""}
          />
          <StreetFormGroup
            onChange={(event) => setField("street", event.target.value)}
            defaultValue={!!props.user.address ? props.user.address.street : ""}
          />
          <CountryFormGroup
            onChange={(event) => setField("country", event.target.value)}
            defaultValue={
              !!props.user.address ? props.user.address.country : ""
            }
          />
          <Button variant="primary" type="submit">
            Submit
          </Button>
        </Form>
      </Modal.Body>
      <Modal.Footer></Modal.Footer>
    </Modal>
  );
}

EditUserModal.propTypes = {
  usertype: PropTypes.string.isRequired,
  user: PropTypes.object.isRequired,
};
export default EditUserModal;
