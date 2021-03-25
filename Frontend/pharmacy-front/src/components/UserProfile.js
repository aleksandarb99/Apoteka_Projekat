import React, { useState, useEffect } from "react";
import "../styling/profile.css";
import { Button, Form } from "react-bootstrap";
import axios from "axios";

function UserProfile() {
  const [user, setUser] = useState({});
  const [isEdit, setEdit] = useState(false);

  useEffect(() => {
    async function fetchUser() {
      //TODO primeniti logiku za dobavljanje korisnika

      const request = await axios.get("http://localhost:8080/api/users/7"); // Zakucan korisnik trenutno TODO izbrisi kasnije
      setUser(request.data);

      return request;
    }
    fetchUser();
  }, {});

  const changeEdit = () => {
    setEdit(!isEdit);
  };

  return (
    <main>
      <div className="my__profile__container">
        <Form>
          <Form.Group controlId="formBasicEmail">
            <Form.Label>Email</Form.Label>
            <Form.Control disabled={!isEdit} type="email" value={user.email} />
          </Form.Group>

          <Form.Group controlId="formBasicPassword">
            <Form.Label>Password</Form.Label>
            <Form.Control
              disabled={!isEdit}
              type="text"
              value={user.password}
            />
          </Form.Group>

          <Form.Group controlId="firstName">
            <Form.Label>First Name</Form.Label>
            <Form.Control
              disabled={!isEdit}
              type="text"
              value={user.firstName}
            />
          </Form.Group>

          <Form.Group controlId="lastName">
            <Form.Label>Last Name</Form.Label>
            <Form.Control
              disabled={!isEdit}
              type="text"
              value={user.lastName}
            />
          </Form.Group>

          <Form.Group controlId="telephone">
            <Form.Label>Telephone</Form.Label>
            <Form.Control
              disabled={!isEdit}
              type="text"
              value={user.telephone}
            />
          </Form.Group>

          <Form.Group controlId="street">
            <Form.Label>Street</Form.Label>
            <Form.Control
              disabled={!isEdit}
              type="text"
              value={user?.address?.street}
            />
          </Form.Group>

          <Form.Group controlId="city">
            <Form.Label>City</Form.Label>
            <Form.Control
              disabled={!isEdit}
              type="text"
              value={user?.address?.city}
            />
          </Form.Group>

          <Form.Group controlId="country">
            <Form.Label>Country</Form.Label>
            <Form.Control
              disabled={!isEdit}
              type="text"
              value={user?.address?.country}
            />
          </Form.Group>
          <Form.Group className="form__buttons" controlId="buttons">
            <Button
              onClick={changeEdit}
              disabled={!isEdit}
              variant="secondary"
              type="submit"
            >
              Save
            </Button>
            <Button
              onClick={changeEdit}
              disabled={isEdit}
              variant="secondary"
              type="submit"
            >
              Edit
            </Button>
          </Form.Group>
        </Form>
      </div>
    </main>
  );
}

export default UserProfile;
