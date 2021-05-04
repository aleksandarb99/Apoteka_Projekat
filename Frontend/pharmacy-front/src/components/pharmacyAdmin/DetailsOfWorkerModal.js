import { React, useState, useEffect } from "react";
import { Button, Modal, ListGroup } from "react-bootstrap";

import axios from "../../app/api";

function DetailsOfWorkerModal(props) {
  const [pharmacies, setPharmacies] = useState([]);

  async function fetchPharmacies() {
    const request = await axios.get(
      `http://localhost:8080/api/workplace/pharmacies/byworkerid/${props.workerId}`
    );
    setPharmacies(request.data);
    return request;
  }

  useEffect(() => {
    if (props.workerId != -1) {
      fetchPharmacies();
    }
  }, [props.workerId]);

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
        {pharmacies?.length == 0 && <h4>He does not work in any pharmacy.</h4>}
        {pharmacies?.length != 0 &&
          pharmacies?.map((item) => <ListGroup.Item>{item}</ListGroup.Item>)}
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
