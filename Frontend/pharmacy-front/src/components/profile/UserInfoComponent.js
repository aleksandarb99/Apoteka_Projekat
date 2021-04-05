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

  let handleSubmit = (event) => {
    event.preventDefault();

    setEdit(!isEdit);

    user.firstName = showUser.firstName;
    user.lastName = showUser.lastName;
    user.telephone = showUser.telephone;
    user.address.street = showUser.street;
    user.address.city = showUser.city;
    user.address.country = showUser.country;

    axios
      .put("http://localhost:8080/api/users/1", user)
      .then((res) => {
        res.data == null ? setFailed(true) : setSaved(true);
        setUser(res.data);
        setShowUser(res.data);
      })
      .catch(() => {
        alert("bla");
      });
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

            <Form.Group
              style={isSaved ? { display: "block" } : { display: "none" }}
              controlId="success"
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
              controlId="fail"
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
