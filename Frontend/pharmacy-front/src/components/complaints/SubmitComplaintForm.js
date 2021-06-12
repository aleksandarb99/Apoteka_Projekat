import axios from "axios";
import React, { useEffect, useState } from "react";
import {
  Button,
  Col,
  Container,
  Form,
  FormGroup,
  FormLabel,
  Row,
} from "react-bootstrap";
import { Typeahead } from "react-bootstrap-typeahead";
import api from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";

const SubmitComplaintForm = () => {
  const [complaintType, setComplaintType] = useState("PHARMACIST");
  const [singleSelection, setSingleSelection] = useState([]);
  const [options, setOptions] = useState([]);
  const [textAreaText, setTextAreaText] = useState("");

  useEffect(() => {
    let url = `/api/patients/${getIdFromToken()}/`;
    switch (complaintType) {
      case "PHARMACIST":
        url = url + "my-pharmacists";
        break;
      case "DERMATOLOGIST":
        url = url + "my-dermatologists";
        break;
      case "PHARMACY":
        url = url + "my-pharmacies";
        break;
      default:
        return;
    }
    api.get(url).then((res) => {
      setOptions(res.data);
      setSingleSelection([]);
    });
  }, [complaintType]);

  const getLabelKey = (option) => {
    try {
      switch (complaintType) {
        case "PHARMACIST":
          return `${option.name}`;
        case "DERMATOLOGIST":
          return `${option.name}`;
        case "PHARMACY":
          return `${option.name} -- ${option.address.street}`;
        default:
          return "";
      }
    } catch {
      return "";
    }
  };

  const updateComplaintType = (e) => {
    setComplaintType(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    e.stopPropagation();
    if (!singleSelection[0]) {
      alert(`Please select the ${complaintType.toLocaleLowerCase()}`);
      return;
    }
    if (!textAreaText) {
      alert(`Please fill out complaint text`);
      return;
    }
    let data = {
      content: textAreaText,
      complaintOn: getLabelKey(singleSelection[0]),
      complaintOnId: singleSelection[0].id,
      type: complaintType,
      date: Date.now(),
      patientId: getIdFromToken(),
    };

    let url = "/api/complaints/";
    api
      .post(url, JSON.stringify(data))
      .then(() => {
        alert("Successfully submitted");
      })
      .catch((err) => {
        console.log(err);
        alert("Error. Complaint not submitted");
      });
  };

  return (
    <Form onSubmit={handleSubmit}>
      <Container>
        <Row className="justify-content-between">
          <Col md={3}>
            <FormGroup>
              <FormLabel>Select type</FormLabel>
              <Form.Control
                as="select"
                onChange={updateComplaintType.bind(this)}
              >
                <option value="PHARMACIST" defaultValue>
                  Pharmacist
                </option>
                <option value="DERMATOLOGIST">Dermatologist</option>
                <option value="PHARMACY">Pharmacy</option>
              </Form.Control>
            </FormGroup>
          </Col>
          <Col md={7}>
            <FormGroup>
              <FormLabel>Select name</FormLabel>
              <Typeahead
                id="complaintTypeahead"
                labelKey={(option) => getLabelKey(option)}
                onChange={setSingleSelection}
                options={options}
                placeholder={"Select..."}
                selected={singleSelection}
              />
            </FormGroup>
          </Col>
        </Row>
        <Form.Group>
          <Form.Label>Complaint text</Form.Label>
          <Form.Control
            as="textarea"
            rows={5}
            onChange={(event) => setTextAreaText(event.target.value)}
            required
          />
        </Form.Group>
        <Button type="submit">Submit</Button>
      </Container>
    </Form>
  );
};

export default SubmitComplaintForm;
