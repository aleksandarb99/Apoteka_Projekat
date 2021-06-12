import React, { useEffect, useState } from "react";
import { Modal, Form, Button, Row } from "react-bootstrap";
import { useDispatch } from "react-redux";
import PasswordFormGroup from "../formGroups/PasswordFormGroup";
import api from "../../../app/api";
import { getIdFromToken } from "../../../app/jwtTokenUtils";
import ErrorModal from "../modals/ErrorModal";
import { logout } from "../../../app/slices/userSlice";

// Use when user is logging in for the first time
const SetPasswordModal = (props) => {
  const [validated, setValidated] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {});

  const [form, setForm] = useState({});
  const setField = (field, value) => {
    setForm({
      ...form,
      [field]: value,
    });
  };

  const dispatch = useDispatch();

  const handleClose = () => {
    dispatch(logout());
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    event.stopPropagation();

    const f = event.currentTarget;

    if (f.checkValidity() === true) {
      setErrorMessage("");
      setValidated(true);
      handleSet();
    }
  };

  const handleSet = () => {
    api
      .put("/api/users/set-password/" + getIdFromToken(), form)
      .then(props.onPasswordSet())
      .catch(setErrorMessage("Oops! Something went wrong, try again!"));
  };

  return (
    <Modal {...props}>
      <Modal.Header
        className="justify-content-center"
        backdrop="static"
        onHide={handleClose}
        closeButton
      >
        <p>Welcome! Please set new password!</p>
      </Modal.Header>
      <Modal.Body className="justify-content-center">
        <Form noValidate validated={validated} onSubmit={handleSubmit}>
          <PasswordFormGroup
            onChange={(event) => setField("newPassword", event.target.value)}
          ></PasswordFormGroup>
          <Button
            className="float-center"
            variant="outline-secondary"
            type="submit"
          >
            Set Password
          </Button>
        </Form>
        {errorMessage.length > 0 && (
          <Row className="justify-content-center m-3">
            <p className="text-danger">{errorMessage}</p>
          </Row>
        )}
      </Modal.Body>
    </Modal>
  );
};

SetPasswordModal.defaultProps = {
  isPasswordSet: false,
};

export default SetPasswordModal;
