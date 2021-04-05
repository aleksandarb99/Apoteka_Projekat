import React, { useState, useEffect } from "react";
import "../../styling/profile.css";
import Allergies from "./Allergies";
import { Button, Form, Alert, Container, Row, Col } from "react-bootstrap";
import axios from "axios";

function UserInfo(props) {
  // States
  const [user, setUser] = useState({});
  const [showUser, setShowUser] = useState({});
  const [isEdit, setEdit] = useState(false);
  const [isMatch, setMatch] = useState(true);
  const [isSaved, setSaved] = useState(false);
  const [isFailed, setFailed] = useState(false);

  useEffect(() => {
    async function fetchUser() {
      //TODO primeniti logiku za dobavljanje korisnika

      const request = await axios.get("http://localhost:8080/api/users/1"); // Zakucan korisnik trenutno TODO izbrisi kasnije
      setUser(request.data);

      return request;
    }
    fetchUser();
  }, {});

  useEffect(() => {
    setShowUser({
      email: user?.email,
      password: user?.password,
      firstName: user?.firstName,
      lastName: user?.lastName,
      telephone: user?.telephone,
      street: user?.address?.street,
      city: user?.address?.city,
      country: user?.address?.country,
    });
  }, [user]);

  const enableEdit = () => {
    setEdit(!isEdit);
    setSaved(false);
    setFailed(false);
  };

  const cancelData = () => {
    setEdit(!isEdit);
    setMatch(true);
    document.getElementsByName("password2")[0].value = "";
    setShowUser({
      email: user?.email,
      password: user?.password,
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

  let handleSubmit = (event) => {
    event.preventDefault();

    if (validate() == false) return;
    document.getElementsByName("password2")[0].value = "";
    setEdit(!isEdit);

    user.firstName = showUser.firstName;
    user.lastName = showUser.lastName;
    user.password = showUser.password;
    user.telephone = showUser.telephone;
    user.address.street = showUser.street;
    user.address.city = showUser.city;
    user.address.country = showUser.country;

    axios.put("http://localhost:8080/api/users/1", user).then((res) => {
      res.data == null ? setFailed(true) : setSaved(true);
      setUser(res.data);
      setShowUser(res.data);
    });
  };

  const validate = () => {
    let value = document.getElementsByName("password2")[0].value;
    return value === showUser.password ? true : false;
  };

  const matchPasswords = (event) => {
    let value = event.target.value;
    value === showUser.password ? setMatch(true) : setMatch(false);
  };

  return (
    <Container>
      <Row className="justify-content-center m-3">
        <h4>{props.title}</h4>
      </Row>
      <Row className="justify-content-center m-3">
        <Col md={props.col_width}>
          <Form onSubmit={handleSubmit}>
            <Form.Label>Email</Form.Label>
            <Form.Control disabled type="email" value={showUser.email} />

            <Form.Label>Password</Form.Label>
            <Form.Control
              disabled={!isEdit}
              type="text"
              name="password"
              required
              pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^*_=+-]).{8,12}$"
              title="Should have at least 1 number, 1 uppercase, 1 lowercase, 1 symbol !@#$%^*_=+-, minimum 8, maximum 12."
              value={showUser.password}
              onChange={handleClick}
            />

            <Form.Group
              style={isEdit ? { display: "block" } : { display: "none" }}
              controlId="formBasicPasswordRetype"
            >
              <Form.Label
                style={isEdit ? { display: "block" } : { display: "none" }}
              >
                Retype Password
              </Form.Label>
              <Form.Control
                name="password2"
                type="text"
                required
                onChange={matchPasswords}
              />
            </Form.Group>

            <Form.Group
              style={!isMatch ? { display: "block" } : { display: "none" }}
              controlId="matchPassword"
            >
              <Alert
                style={!isMatch ? { display: "block" } : { display: "none" }}
                variant="warning"
              >
                Passwords have to be matched!
              </Alert>
            </Form.Group>

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

            <Form.Group
              style={isSaved ? { display: "block" } : { display: "none" }}
              controlId="matchPassword"
            >
              <Alert
                style={isSaved ? { display: "block" } : { display: "none" }}
                variant="success"
              >
                Successfuly changed!
              </Alert>
            </Form.Group>

            <Form.Group
              style={isFailed ? { display: "block" } : { display: "none" }}
              controlId="matchPassword"
            >
              <Alert
                style={isFailed ? { display: "block" } : { display: "none" }}
                variant="danger"
              >
                You have failed!
              </Alert>
            </Form.Group>

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
      <hr />
      <Row className="justify-content-center m-3">
        <Allergies />
      </Row>
    </Container>
  );
}

export default UserInfo;
