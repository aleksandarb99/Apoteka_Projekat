import { React, useState } from "react";
import { Button, Modal, Form } from "react-bootstrap";

function ChangePriceModal(props) {
  const [price, setPrice] = useState(0);

  return (
    <Modal
      onExited={() => {
        setPrice(0);
      }}
      onEntered={() => {
        setPrice(props.oldPrice);
      }}
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Change price
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form.Group controlId="pricePicker" di>
          <Form.Label>Price</Form.Label>
          <Form.Control
            type="number"
            value={price}
            onChange={(event) => setPrice(Number.parseInt(event.target.value))}
            min="0"
          />
        </Form.Group>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={props.onHide}>
          Close
        </Button>
        <Button
          variant="primary"
          onClick={() => {
            props.handleChange(price);
          }}
        >
          Save Changes
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default ChangePriceModal;
