import React, { useState, useEffect } from "react";
import { Button, Form, Modal, Row, Col, InputGroup } from "react-bootstrap";
import api from "../../app/api";
import moment from "moment";
import DatePicker from "react-datepicker";
import { getIdFromToken } from "../../app/jwtTokenUtils";
import { useToasts } from "react-toast-notifications";

function VacationModal(props) {
  const [fromDate, setFromDate] = useState(null);
  const [toDate, setToDate] = useState(null);
  const [typeVac, setTypeVac] = useState("none");
  const { addToast } = useToasts();

  useEffect(() => {
    setToDate(null);
    setFromDate(null);
    setTypeVac("none");
  }, [props.show]);

  const onStart = () => {
    if (!fromDate) {
      addToast("Starting date is empty!", { appearance: "error" });
      return;
    } else if (!toDate) {
      addToast("Ending date is empty!", { appearance: "error" });
      return;
    } else if (typeVac === "none") {
      addToast("Vacation type is none!", { appearance: "error" });
      return;
    }

    if (fromDate.getTime() >= toDate.getTime()) {
      addToast("Invalid start/end date!", { appearance: "error" });
      return;
    }

    let userID = getIdFromToken();
    if (!userID) {
      addToast("No user id in token! error", { appearance: "error" });
      return;
    }

    let startDate = Math.floor(fromDate.getTime());
    let endDate = Math.floor(toDate.getTime());

    if (moment(Date.now()) > moment(startDate)) {
      addToast("Start date of vacation has to be in future!", {
        appearance: "error",
      });
      return;
    }

    let search_params = new URLSearchParams();
    search_params.append("id", userID);
    search_params.append("start", startDate);
    search_params.append("end", endDate);
    search_params.append("type", typeVac);

    api
      .get("/api/vacation/request_vacation", { params: search_params })
      .then(() => props.onCreateMethod())
      .catch((error) => addToast(error.response.data, { appearance: "error" }));
  };

  return (
    <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered>
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Create a request for a vacation
        </Modal.Title>
      </Modal.Header>
      <Modal.Body as={Row} className="justify-content-center m-3">
        <Col>
          <Row className="justify-content-center m-3">
            <InputGroup>
              <InputGroup.Prepend>
                <InputGroup.Text>Start date:</InputGroup.Text>
              </InputGroup.Prepend>
              <Form.Control
                as={DatePicker}
                closeOnScroll={true}
                selected={fromDate}
                dateFormat="dd/MM/yyyy"
                onChange={(date) => setFromDate(date)}
                isClearable
              />
            </InputGroup>
          </Row>

          <Row className="justify-content-center m-3">
            <InputGroup>
              <InputGroup.Prepend>
                <InputGroup.Text>End date:</InputGroup.Text>
              </InputGroup.Prepend>
              <Form.Control
                as={DatePicker}
                closeOnScroll={true}
                selected={toDate}
                dateFormat="dd/MM/yyyy"
                onChange={(date) => setToDate(date)}
                isClearable
              />
            </InputGroup>
          </Row>

          <Row className="justify-content-center m-3">
            <InputGroup>
              <InputGroup.Prepend>
                <InputGroup.Text>Type:</InputGroup.Text>
              </InputGroup.Prepend>
              <Form.Control
                as="select"
                value={typeVac}
                onChange={(event) => setTypeVac(event.target.value)}
                name="typeVac"
              >
                <option value="none">none</option>
                <option value="vacation">vacation</option>
                <option value="leave">leave</option>
              </Form.Control>
            </InputGroup>
          </Row>
        </Col>
      </Modal.Body>
      <Modal.Footer>
        <Button onClick={onStart}>Create request</Button>
      </Modal.Footer>
    </Modal>
  );
}

export default VacationModal;
