import React, { useEffect, useState } from "react";
import { Button, Col, Container, Form, Row, Modal } from "react-bootstrap";
import api from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";
import { Eye } from "react-bootstrap-icons";
import { useToasts } from "react-toast-notifications";

const BasicProfileInfo = (props) => {
  const [user, setUser] = useState({});
  const [showUser, setShowUser] = useState({});
  const [passwordDTO, setPasswordDTO] = useState({
    oldPassword: "",
    newPassword: "",
    repeatPassword: "",
  });
  const [isEdit, setEdit] = useState(false);
  const [showPasswordModal, setShowPasswordModal] = useState(false);
  const [inputType, setInputType] = useState("password");
  const { addToast } = useToasts();

  useEffect(() => {
    async function fetchUser() {
      const request = await api.get(
        "http://localhost:8080/api/users/" + getIdFromToken()
      );
      setUser(request.data);
      setShowUser({
        email: request.data?.email,
        firstName: request.data?.firstName,
        lastName: request.data?.lastName,
        telephone: request.data?.telephone,
        street: request.data?.address?.street,
        city: request.data?.address?.city,
        country: request.data?.address?.country,
      });
      return request;
    }
    fetchUser();
  }, {});

  const enableEdit = () => {
    setEdit(!isEdit);
  };

  const cancelData = () => {
    setEdit(!isEdit);
    setShowUser({
      email: user?.email,
      firstName: user?.firstName,
      lastName: user?.lastName,
      telephone: user?.telephone,
      street: user?.address?.street,
      city: user?.address?.city,
      country: user?.address?.country,
    });
  };

  let handleClick = (event) => {
    let key = event.target.name;
    let value = event.target.value;
    setShowUser({ ...showUser, [key]: value });
  };

  let handleClick2 = (event) => {
    let key = event.target.name;
    let value = event.target.value;
    setPasswordDTO({ ...passwordDTO, [key]: value });
  };

  let handleSubmit = (event) => {
    event.preventDefault();
    setEdit(!isEdit);

    if (
      user.firstName === showUser.firstName &&
      user.lastName === showUser.lastName &&
      user.telephone === showUser.telephone &&
      user.address.street === showUser.street &&
      user.address.city === showUser.city &&
      user.address.country === showUser.country
    )
      return;

    user.firstName = showUser.firstName;
    user.lastName = showUser.lastName;
    user.telephone = showUser.telephone;
    user.address.street = showUser.street;
    user.address.city = showUser.city;
    user.address.country = showUser.country;

    api
      .put("http://localhost:8080/api/users/" + getIdFromToken(), user)
      .then((res) => {
        addToast("User changed successfully!", { appearance: "success" });
        setUser(res.data);
      })
      .catch((err) => {
        addToast(err.response.data, { appearance: "error" });
      });
  };

  const changePassword = (event) => {
    event.preventDefault();

    if (passwordDTO.newPassword !== passwordDTO.repeatPassword) {
      addToast("Passwords not matches!", { appearance: "warning" });
      return;
    }

    let forSend = { ...passwordDTO };
    delete forSend.repeatPassword;
    api
      .put(
        "http://localhost:8080/api/users/change-password/" + getIdFromToken(),
        forSend
      )
      .then(() => {
        addToast("Successfully changed password!", { appearance: "success" });
        passwordDTO.oldPassword = "";
        passwordDTO.newPassword = "";
        passwordDTO.repeatPassword = "";
        setShowPasswordModal(false);
      })
      .catch((err) => {
        addToast(err.response.data, { appearance: "error" });
      });
  };

  return (
    <Container>
      <Row>
        <Modal
          show={showPasswordModal}
          onHide={() => setShowPasswordModal(false)}
        >
          <Modal.Header closeButton>
            <Modal.Title>Change password</Modal.Title>
          </Modal.Header>
          <Form onSubmit={changePassword}>
            <Modal.Body>
              <Form.Label>Old password</Form.Label>
              <Form.Control
                type={inputType}
                name="oldPassword"
                required
                value={passwordDTO.oldPassword}
                onChange={handleClick2}
                maxLength={30}
              />

              <Form.Label>New password</Form.Label>
              <Form.Control
                type={inputType}
                name="newPassword"
                required
                value={passwordDTO.newPassword}
                onChange={handleClick2}
                maxLength={30}
              />

              <Form.Label>Repeat password</Form.Label>
              <Form.Control
                type={inputType}
                name="repeatPassword"
                required
                value={passwordDTO.repeatPassword}
                onChange={handleClick2}
                maxLength={30}
              />
            </Modal.Body>
            <Modal.Footer>
              <Button
                variant="secondary"
                style={{
                  borderRadius: "50%",
                  position: "absolute",
                  left: "30px",
                }}
                onClick={() =>
                  setInputType(inputType === "text" ? "password" : "text")
                }
              >
                <Eye />
              </Button>
              <Button
                variant="secondary"
                onClick={() => setShowPasswordModal(false)}
              >
                Close
              </Button>
              <Button type="submit" variant="primary">
                Submit
              </Button>
            </Modal.Footer>
          </Form>
        </Modal>
      </Row>
      <Row className="justify-content-center m-3">
        <h4>{props.title}</h4>
      </Row>
      <Row className="justify-content-center m-3">
        <Col md={6}>
          <Form onSubmit={handleSubmit}>
            <Form.Label>Email</Form.Label>
            <Form.Control disabled type="email" value={showUser.email} />

            <Form.Label>First Name</Form.Label>
            <Form.Control
              disabled={!isEdit}
              type="text"
              name="firstName"
              required
              value={showUser.firstName}
              onChange={handleClick}
            />

            <Form.Label>Last Name</Form.Label>
            <Form.Control
              disabled={!isEdit}
              type="text"
              name="lastName"
              required
              value={showUser.lastName}
              onChange={handleClick}
            />

            <Form.Label>Telephone</Form.Label>
            <Form.Control
              disabled={!isEdit}
              type="number"
              name="telephone"
              pattern="[0-9]{10}"
              required
              value={showUser.telephone}
              onChange={handleClick}
            />

            <Form.Label>Street</Form.Label>
            <Form.Control
              disabled={!isEdit}
              type="text"
              name="street"
              required
              value={showUser?.street}
              onChange={handleClick}
            />

            <Form.Label>City</Form.Label>
            <Form.Control
              disabled={!isEdit}
              type="text"
              name="city"
              required
              value={showUser?.city}
              onChange={handleClick}
            />

            <Form.Label>Country</Form.Label>
            <Form.Control
              disabled={!isEdit}
              type="text"
              name="country"
              required
              value={showUser?.country}
              onChange={handleClick}
            />

            <Row style={{ marginTop: "10px", position: "relative" }}>
              <Button
                disabled={!isEdit}
                variant="info"
                type="button"
                onClick={() => setShowPasswordModal(true)}
                style={{ position: "absolute", right: "0px" }}
              >
                Change password
              </Button>
            </Row>

            <Form.Group className="form__buttons mt-5" controlId="buttons">
              <Button disabled={!isEdit} variant="secondary" type="submit">
                Save
              </Button>
              <Button
                onClick={enableEdit}
                disabled={isEdit}
                variant="secondary"
                type="button"
              >
                Edit
              </Button>
              <Button
                onClick={cancelData}
                disabled={!isEdit}
                variant="secondary"
                type="button"
              >
                Cancel
              </Button>
            </Form.Group>
          </Form>
        </Col>
      </Row>
    </Container>
  );
};

export default BasicProfileInfo;
