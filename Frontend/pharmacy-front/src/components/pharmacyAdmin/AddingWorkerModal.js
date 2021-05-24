import { React, useState } from "react";
import { Button, Table, Modal, Form, Row, Col } from "react-bootstrap";

import axios from "../../app/api";

import { useToasts } from "react-toast-notifications";

function AddingWorkerModal(props) {
  const { addToast } = useToasts();
  const [workerList, setWorkerList] = useState([]);
  const [selectedRowId, setSelectedRowId] = useState(-1);

  const [startHour, setStartHour] = useState(10);
  const [endHour, setEndHour] = useState(17);

  const [enable1, setEnable1] = useState(false);
  const [enable2, setEnable2] = useState(false);
  const [enable3, setEnable3] = useState(false);
  const [enable4, setEnable4] = useState(false);
  const [enable5, setEnable5] = useState(false);
  const [enable6, setEnable6] = useState(false);
  const [enable7, setEnable7] = useState(false);

  let handleClick = (medicineId) => {
    setSelectedRowId(medicineId);
  };

  async function fetchWorkers() {
    setSelectedRowId(-1);
    let dto = {
      startHour,
      endHour,
      enable1,
      enable2,
      enable3,
      enable4,
      enable5,
      enable6,
      enable7,
    };
    const request = await axios
      .post(
        `http://localhost:8080/api/workers/notexistingworkplacebypharmacyid/${props.idOfPharmacy}`,
        dto
      )
      .then((res) => {
        setWorkerList(res.data);
      })
      .catch((err) => {
        addToast(err.response.data, {
          appearance: "error",
        });
      });

    return request;
  }

  return (
    <Modal
      onExited={() => {
        setWorkerList([]);
        setSelectedRowId(-1);
        setStartHour(10);
        setEndHour(17);
        setEnable1(false);
        setEnable2(false);
        setEnable3(false);
        setEnable4(false);
        setEnable5(false);
        setEnable6(false);
        setEnable7(false);
      }}
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Adding worker
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Row>
          <Col>
            <Form.Group controlId="startPicker" di>
              <Form.Label>Shift start</Form.Label>
              <Form.Control
                type="number"
                value={startHour}
                onChange={(event) => setStartHour(event.target.value)}
              />
            </Form.Group>
            <Form.Group controlId="endPicker" di>
              <Form.Label>Shift end</Form.Label>
              <Form.Control
                type="number"
                value={endHour}
                onChange={(event) => setEndHour(event.target.value)}
              />
            </Form.Group>
          </Col>
        </Row>
        <Row>
          <Col>
            <p>Mark days</p>
            <Table bordered hover striped size="sm" variant="dark">
              <thead>
                <tr>
                  <th>#</th>
                  <th>Weekday</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  className={`${enable1 && "selectedRow"} pointer`}
                  onClick={() => setEnable1(!enable1)}
                >
                  <td>1</td>
                  <td>Monday</td>
                </tr>
                <tr
                  className={`${enable2 && "selectedRow"} pointer`}
                  onClick={() => setEnable2(!enable2)}
                >
                  <td>2</td>
                  <td>Tuesday</td>
                </tr>
                <tr
                  className={`${enable3 && "selectedRow"} pointer`}
                  onClick={() => setEnable3(!enable3)}
                >
                  <td>3</td>
                  <td>Wednesday</td>
                </tr>
                <tr
                  className={`${enable4 && "selectedRow"} pointer`}
                  onClick={() => setEnable4(!enable4)}
                >
                  <td>4</td>
                  <td>Thursday</td>
                </tr>
                <tr
                  className={`${enable5 && "selectedRow"} pointer`}
                  onClick={() => setEnable5(!enable5)}
                >
                  <td>5</td>
                  <td>Friday</td>
                </tr>
                <tr
                  className={`${enable6 && "selectedRow"} pointer`}
                  onClick={() => setEnable6(!enable6)}
                >
                  <td>6</td>
                  <td>Saturday</td>
                </tr>
                <tr
                  className={`${enable7 && "selectedRow"} pointer`}
                  onClick={() => setEnable7(!enable7)}
                >
                  <td>7</td>
                  <td>Sunday</td>
                </tr>
              </tbody>
            </Table>
          </Col>
        </Row>
        <Row>
          <Col className="center">
            <Button
              disabled={
                !enable1 &&
                !enable2 &&
                !enable3 &&
                !enable4 &&
                !enable5 &&
                !enable6 &&
                !enable7
              }
              variant="primary"
              onClick={fetchWorkers}
            >
              Check
            </Button>
          </Col>
        </Row>
        <Row>
          <Col>
            <Table bordered hover striped variant="dark">
              <thead>
                <tr>
                  <th>#</th>
                  <th>Type</th>
                  <th>Firstname</th>
                  <th>LastName</th>
                  <th>Avg. grade</th>
                </tr>
              </thead>
              <tbody>
                {workerList?.length != 0 &&
                  workerList?.map((item, index) => (
                    <tr
                      onClick={() => {
                        handleClick(item.id);
                      }}
                      className={`${
                        selectedRowId == item.id ? "selectedRow" : "pointer"
                      }`}
                    >
                      <td>{index + 1}</td>
                      <td>{item?.roleName}</td>
                      <td>{item.firstName}</td>
                      <td>{item.lastName}</td>
                      <td>{item.avgGrade}</td>
                    </tr>
                  ))}
              </tbody>
            </Table>
          </Col>
        </Row>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={props.onHide}>
          Close
        </Button>
        <Button
          disabled={selectedRowId == -1}
          variant="primary"
          onClick={() => {
            props.handleAdd(selectedRowId, {
              startHour,
              endHour,
              enable1,
              enable2,
              enable3,
              enable4,
              enable5,
              enable6,
              enable7,
            });
          }}
        >
          Save Changes
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default AddingWorkerModal;
