import { React, useState } from "react";
import {
  Button,
  Row,
  Col,
  Container,
  Modal,
  Form,
  Alert,
} from "react-bootstrap";

import moment from "moment";

function EditOrderModal(props) {
  const [startDate, setStartDate] = useState(null);
  const [showAlert, setShowAlert] = useState(false);

  let changeDate = (date) => {
    let array = date.split("-");
    let d = new Date(
      Number.parseInt(array[0]),
      Number.parseInt(array[1]) - 1,
      Number.parseInt(array[2])
    );
    d.setHours(0, 0, 0);
    let now = new Date();
    if (d.getTime() > now.getTime()) setStartDate(d);
    else {
      setStartDate(null);
      setShowAlert(true);
      setTimeout(function () {
        setShowAlert(false);
      }, 3000);
    }
  };

  return (
    <Modal
      onExited={() => {
        setStartDate(null);
      }}
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Edit deadline
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Container>
          <Row>
            <Col>
              <h4>
                Old deadline is{" "}
                {moment(props.order.deliveryDat).format("DD MMM YYYY")}
              </h4>
            </Col>
          </Row>
          <Row>
            <Col>
              <Form.Group controlId="datePicker" di>
                <Form.Label>Deadline date</Form.Label>
                <Form.Control
                  type="date"
                  onChange={(event) => changeDate(event.target.value)}
                />
              </Form.Group>
            </Col>
            <Col>
              {showAlert && (
                <Alert transition={true} variant="danger">
                  The date must be in the future.
                </Alert>
              )}
            </Col>
          </Row>
        </Container>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={props.onHide}>
          Close
        </Button>
        <Button
          disabled={startDate === null}
          variant="primary"
          onClick={() => {
            props.handleEdit(startDate);
          }}
        >
          Save Changes
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default EditOrderModal;
