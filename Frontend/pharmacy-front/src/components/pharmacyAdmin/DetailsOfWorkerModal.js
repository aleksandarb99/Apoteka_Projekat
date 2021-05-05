import { React } from "react";
import { Button, Modal, ListGroup } from "react-bootstrap";

function DetailsOfWorkerModal(props) {
  return (
    <Modal
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Worker pharmacies
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {props?.map[props?.workerId]?.length == 0 && (
          <h4>He does not work in any pharmacy.</h4>
        )}
        {props?.map[props?.workerId]?.length != 0 &&
          props?.map[props?.workerId]?.map((item) => (
            <ListGroup.Item>{item}</ListGroup.Item>
          ))}
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={props.handleClose}>
          Close
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default DetailsOfWorkerModal;
