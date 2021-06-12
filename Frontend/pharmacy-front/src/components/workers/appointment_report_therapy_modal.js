import React, { useEffect, useState } from "react";
import { Button, Form, Modal, Row, Col } from "react-bootstrap";
import api from "../../app/api";
import { Typeahead } from "react-bootstrap-typeahead";
import { useToasts } from "react-toast-notifications";
import { getIdFromToken } from "../../app/jwtTokenUtils";

const TherapyMedicineModal = (props) => {
  const [singleSelection, setSingleSelection] = useState([]);
  const [primaryOptions, setPrimaryOptions] = useState([]);
  const [showHidePrimary, setShowHidePrimary] = useState(false);

  const [alternativeSelection, setAlternativeSelection] = useState([]);
  const [alternativeOptions, setAlternativeOptions] = useState([]);
  const [showHideSecondary, setShowHideSecondary] = useState(false);

  const [selectedMedicine, setSelectedMedicine] = useState(null);

  const [alternativeError, setAlternativeError] = useState(false); //todo

  const [amount, setAmount] = useState("");

  const { addToast } = useToasts();

  const reset = () => {
    setSingleSelection([]);
    setPrimaryOptions([]);
    setShowHidePrimary(false);
    setAlternativeSelection([]);
    setAlternativeOptions([]);
    setShowHideSecondary(false);
    setSelectedMedicine(null);
    setAlternativeError(false);
    setAmount("");
  };

  useEffect(() => {
    async function fetchMedicine() {
      if (!props.appt) {
        return;
      }
      let pharm_id = props.appt.pharmacyID;
      let pat_id = props.appt.patientID;
      const response = await api
        .get(
          "/api/pharmacy/getMedicineFromPharmWithoutAllergies?pharm_id=" +
            pharm_id +
            "&patient_id=" +
            pat_id
        )
        .catch(() => {
          addToast("No medicine to add to therapy!", { appearance: "error" });
          exitModal();
        });
      if (response.data.length === 0) {
        addToast("No medicine to add to therapy!", { appearance: "error" });
        exitModal();
      }
      setPrimaryOptions(response.data);
    }
    fetchMedicine();
  }, [props.clickedShow]);

  const setAlternatives = (med_item_id, med_id) => {
    if (!props.appt) {
      return;
    }
    let worker_id = getIdFromToken();
    if (!worker_id) {
      return;
    }
    let pharm_id = props.appt.pharmacyID;
    let pat_id = props.appt.patientID;
    api
      .get(
        "/api/pharmacy/getAlternativeFromPharmacy?worker_id=" +
          worker_id +
          "&pharm_id=" +
          pharm_id +
          "&patient_id=" +
          pat_id +
          "&medicine_item_id=" +
          med_item_id +
          "&medicine_id=" +
          med_id
      )
      .then((resp) => {
        if (resp.data.length == 0) {
          setAlternativeError(true);
        } else {
          setAlternativeOptions(resp.data);
        }
      })
      .catch(() => setAlternativeError(true));
  };

  const exitModal = () => {
    reset();
    props.onHideModal();
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    e.stopPropagation();
    if (alternativeError) {
      return;
    }
    if (!selectedMedicine) {
      addToast("No medicine is selected!", { appearance: "error" });
      return;
    }
    if (!amount) {
      addToast("Therapy length not defined!", { appearance: "error" });
      return;
    }
    if (isNaN(amount)) {
      addToast("Invalid therapy duration!", { appearance: "error" });
      return;
    }
    let th = "" + amount;
    let therapyLen = parseInt(th);
    if (therapyLen <= 0) {
      addToast("Invalid therapy duration!", { appearance: "error" });
      return;
    }
    selectedMedicine.duration = therapyLen;
    props.onAddMedicine(selectedMedicine);
    reset();
  };

  return (
    <Modal
      {...props}
      aria-labelledby="contained-modal-title-vcenter"
      centered
      scrollable
      onHide={exitModal}
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Add medicine for therapy
        </Modal.Title>
      </Modal.Header>

      <Modal.Body>
        <Form onSubmit={handleSubmit}>
          <Form.Group>
            <Form.Label>Medicine</Form.Label>
            <Typeahead
              id="1"
              labelKey={(option) => `${option.code} -- ${option.name}`}
              onChange={(data) => {
                setAlternativeError(false);
                setSingleSelection(data);
                setAlternativeOptions([]);
                setAlternativeSelection([]);
                if (data.length > 0) {
                  if (data[0].amount <= 0) {
                    setAlternatives(data[0].medicineItemID, data[0].medicineID);
                  } else {
                    setSelectedMedicine(data[0]);
                  }
                } else {
                  setSelectedMedicine(null);
                }
              }}
              options={primaryOptions}
              placeholder="Select a medicine..."
              selected={singleSelection}
            />
          </Form.Group>
          <Row className="justify-content-center mb-4">
            <Button
              variant="primary"
              onClick={() => setShowHidePrimary(!showHidePrimary)}
            >
              Show/Hide info
            </Button>
          </Row>

          {singleSelection.length > 0 && showHidePrimary && (
            <Col style={{ backgroundColor: "#83CEC2" }}>
              <Row className="justify-content-center mt-3">
                Medicine information
              </Row>
              <Row className="m-2">-Code: {singleSelection[0].code}</Row>
              <Row className="m-2">-Name: {singleSelection[0].name}</Row>
              <Row className="m-2">-Price: {singleSelection[0].price}</Row>
              <Row className="m-2">-Content: {singleSelection[0].content}</Row>
              <Row className="m-2">
                -Side effects: {singleSelection[0].sideEffects}
              </Row>
              <Row className="m-2">
                -Daily intake: {singleSelection[0].dailyIntake}
              </Row>
              <Row className="m-2">
                -Additional notes: {singleSelection[0].additionalNotes}
              </Row>
              <Row className="m-2">
                -Type: {singleSelection[0].medicineType}
              </Row>
              <Row className="m-2">
                -Form: {singleSelection[0].medicineForm}
              </Row>
              <Row className="m-2">
                -Manufacturer: {singleSelection[0].manufacturer}
              </Row>
            </Col>
          )}

          {singleSelection.length > 0 && singleSelection[0].amount <= 0 && (
            <div>
              {alternativeError ? (
                <div style={{ color: "red" }}>
                  Selected medicine '{singleSelection[0].name}' is currently not
                  in stock and has no alternative medicine available. Please
                  chose another.
                </div>
              ) : (
                <div>
                  <Form.Group>
                    <Form.Label>
                      Selected medicine '{singleSelection[0].name}' is currently
                      not in stock. Please select an alternative medicine:
                    </Form.Label>
                    <Typeahead
                      id="2"
                      labelKey={(option) => `${option.code} -- ${option.name}`}
                      onChange={(data) => {
                        setAlternativeSelection(data);
                        if (data.length > 0) {
                          setSelectedMedicine(data[0]);
                        } else {
                          setSelectedMedicine(null);
                        }
                      }}
                      options={alternativeOptions}
                      placeholder="Select a medicine..."
                      selected={alternativeSelection}
                    />
                  </Form.Group>
                  <Row className="justify-content-center mb-4">
                    <Button
                      variant="primary"
                      onClick={() => setShowHideSecondary(!showHideSecondary)}
                    >
                      Show/Hide alternative info
                    </Button>
                  </Row>
                </div>
              )}
            </div>
          )}
          {alternativeSelection.length > 0 && showHideSecondary && (
            <Col style={{ backgroundColor: "#83CEC2" }}>
              <Row className="justify-content-center mt-3">
                Alternative medicine information
              </Row>
              <Row className="m-2">-Code: {alternativeSelection[0].code}</Row>
              <Row className="m-2">-Name: {alternativeSelection[0].name}</Row>
              <Row className="m-2">-Price: {alternativeSelection[0].price}</Row>
              <Row className="m-2">
                -Content: {alternativeSelection[0].content}
              </Row>
              <Row className="m-2">
                -Side effects: {alternativeSelection[0].sideEffects}
              </Row>
              <Row className="m-2">
                -Daily intake: {alternativeSelection[0].dailyIntake}
              </Row>
              <Row className="m-2">
                -Additional notes: {alternativeSelection[0].additionalNotes}
              </Row>
              <Row className="m-2">
                -Type: {alternativeSelection[0].medicineType}
              </Row>
              <Row className="m-2">
                -Form: {alternativeSelection[0].medicineForm}
              </Row>
              <Row className="m-2">
                -Manufacturer: {alternativeSelection[0].manufacturer}
              </Row>
            </Col>
          )}
          <Row className="mb-2 mt-5 ml-2">
            Selected medicine:{" "}
            {selectedMedicine ? selectedMedicine.name : "- none selected -"}
          </Row>

          <Form.Group as={Row} className="mb-4 ml-2">
            <Form.Label>Prescribed days of therapy: </Form.Label>
            <Col md={3}>
              <Form.Control
                type="text"
                onChange={(e) => setAmount(e.target.value)}
                value={amount}
              />
            </Col>
          </Form.Group>

          <Button variant="primary" onClick={handleSubmit}>
            Confirm
          </Button>
          <Button variant="secondary" onClick={exitModal}>
            Cancel
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default TherapyMedicineModal;
