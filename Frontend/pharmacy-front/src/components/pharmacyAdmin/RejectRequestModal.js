import { React, useState } from "react";
import { Button, Modal, Form } from "react-bootstrap";

function RejectRequestModal(props) {
  const [reason, setReason] = useState("");
  return (
    <Modal
      onExited={() => {
        setReason("");
      }}
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Reason for rejecting request
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form.Group controlId="exampleForm.ControlTextarea1">
          <Form.Label>Write a reason for rejecting request</Form.Label>
          <Form.Control
            onChange={(event) => setReason(event.target.value)}
            as="textarea"
            rows={3}
          />
        </Form.Group>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={props.onHide}>
          Close
        </Button>
        <Button
          disabled={reason === ""}
          variant="primary"
          onClick={() => {
            props.rejectRequest(reason);
          }}
        >
          Save Changes
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default RejectRequestModal;
