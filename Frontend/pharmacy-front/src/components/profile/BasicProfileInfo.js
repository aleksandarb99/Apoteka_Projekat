import React, { useEffect, useState } from 'react'
import { Alert, Button, Col, Container, Form, Row } from 'react-bootstrap';
import api from '../../app/api';
import { getIdFromToken } from '../../app/jwtTokenUtils';

const BasicProfileInfo = (props) => {
    const [user, setUser] = useState({});
    const [showUser, setShowUser] = useState({});
    const [isEdit, setEdit] = useState(false);
    const [isSaved, setSaved] = useState(false);
    const [isFailed, setFailed] = useState(false);

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

        api
            .put("http://localhost:8080/api/users/" + getIdFromToken(), user)
            .then((res) => {
                res.data === null ? setFailed(true) : setSaved(true);
                setUser(res.data);
                // ovde format nije dobar i on postavi user-u adresu sa undefined poljima
                // setShowUser(res.data);
            })
            .catch(() => {
                alert("Editing has failed!");
            });
    };

    return (
        <Container>
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

                        <Form.Group
                            style={isSaved ? { display: "block" } : { display: "none" }}
                            controlId="success"
                        >
                            <Alert
                                style={isSaved ? { display: "block" } : { display: "none" }}
                                variant="success"
                            >
                                User changed successfully!
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
                                User not changed!
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
        </Container>

    )
}

export default BasicProfileInfo