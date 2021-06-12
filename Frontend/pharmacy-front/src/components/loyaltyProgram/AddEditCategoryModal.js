import React, { useState } from "react";
import { Button, Col, Container, Form, Modal, Row } from "react-bootstrap";
import api from "../../app/api";

const AddEditCategoryModal = (props) => {
  const [name, setName] = useState(!!props.category ? props.category.name : "");
  const [requiredPoints, setRequiredPoints] = useState(
    props.category ? props.category.pointsRequired : 0
  );
  const [discount, setDiscount] = useState(
    props.category ? props.category.discount : 0.0
  );

  const handleSubmit = (e) => {
    e.preventDefault();
    e.stopPropagation();

    let data = {
      id: !!props.category ? props.category.id : -1,
      name: name,
      pointsRequired: parseInt(requiredPoints),
      discount: parseFloat(discount),
    };

    api
      .post("/api/ranking-category/", data)
      .then(() => {
        props.onAddEdit();
        props.onHide();
      })
      .catch(() => {
        alert("Error");
      });
  };

  const resetData = () => {
    setName("");
    setRequiredPoints(0);
    setDiscount(0.0);
  };

  return (
    <Modal
      {...props}
      onEnter={() => {
        resetData();
      }}
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          {!!props.category
            ? `Edit ${props.category.name}`
            : "Add new category"}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={(event) => handleSubmit(event)}>
          <Form.Group>
            <Form.Label>Name</Form.Label>
            <Form.Control
              type="input"
              onChange={(event) => setName(event.target.value)}
              defaultValue={!!props.category ? props.category.name : ""}
              required
            />
          </Form.Group>
          <Form.Group>
            <Form.Label>Required points</Form.Label>
            <Form.Control
              type="number"
              onChange={(event) => setRequiredPoints(event.target.value)}
              defaultValue={
                !!props.category ? props.category.pointsRequired : 0
              }
              min={0}
              max={100000}
              step={1}
              required
            />
          </Form.Group>
          <Form.Group>
            <Form.Label>Discount</Form.Label>
            <Form.Control
              type="number"
              onChange={(event) => setDiscount(event.target.value)}
              defaultValue={!!props.category ? props.category.discount : 0.0}
              min={0}
              max={100.0}
              step={0.01}
              required
            />
          </Form.Group>
          <Container>
            <Row>
              <Col md={{ span: 4, offset: 0 }}>
                <Button variant="outline-danger" onClick={props.onHide}>
                  Cancel
                </Button>
              </Col>
              <Col md={{ span: 4, offset: 4 }}>
                <Button variant="outline-secondary" type="submit">
                  Confirm
                </Button>
              </Col>
            </Row>
          </Container>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default AddEditCategoryModal;
