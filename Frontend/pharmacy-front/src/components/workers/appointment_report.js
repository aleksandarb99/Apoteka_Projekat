import React, { useState, useEffect } from "react";
import { Form, Row, Col, Button } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import TherapyMedicineModal from "./appointment_report_therapy_modal";
import ScheduleAnotherApp from "./appointment_report_schedule_modal";
import api from "../../app/api";
import { useLocation, useHistory } from "react-router-dom";
import moment from "moment";
import { getUserTypeFromToken } from "../../app/jwtTokenUtils";
import { useToasts } from "react-toast-notifications";

function AppointmentReport() {
  const [selectedMedicine, setSelectedMedicine] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [showScheduleAnother, setShowScheduleAnother] = useState(false);
  const [showClicked, setShowClicked] = useState(false);
  const [currAppt, setCurrAppt] = useState(null);
  const [apptInfo, setApptInfo] = useState("");
  const [apptType, setApptType] = useState("");

  const location = useLocation();
  const history = useHistory();

  const { addToast } = useToasts();

  useEffect(() => {
    if (getUserTypeFromToken().trim() === "DERMATOLOGIST") {
      setApptType("checkup");
    } else if (getUserTypeFromToken().trim() === "PHARMACIST") {
      setApptType("consultation");
    } else {
      setApptType("appointment");
    }
    let bodyFormData = new FormData();
    if (!location.state.appointmentID) {
      addToast("No appointment id! Couldn't start appointment!", {
        appearance: "error",
      });
      history.push("/");
      return;
    }
    let appt_id = location.state.appointmentID;
    bodyFormData.append("id", appt_id);

    api
      .post("/api/appointment/getApptForReport", bodyFormData)
      .then((resp) => {
        setCurrAppt(resp.data);
      })
      .catch(() => {
        addToast("Couldn't get ids!", { appearance: "error" });
        history.push("/");
      }); //todo sta koji djavo ovo znaci
  }, []);

  const onAdd = (medItem) => {
    for (let i = 0; i < selectedMedicine.length; i++) {
      if (selectedMedicine[i].medicineID === medItem.medicineID) {
        addToast("That medicine is already added to therapy!", {
          appearance: "error",
        });
        return;
      }
    }
    selectedMedicine.push(medItem);
    setShowModal(false);
  };

  const hideModal = () => {
    setShowModal(false);
  };

  const finishAppt = () => {
    let appointment_id = currAppt.id;
    api
      .post("/api/appointment/finalizeAppointment", {
        apptId: appointment_id,
        medicineList: selectedMedicine,
        info: apptInfo,
      })
      .then(() => {
        addToast("Appointment finished!", { appearance: "success" });
        setShowScheduleAnother(false);
        history.push("/");
      })
      .catch((resp) => {
        setSelectedMedicine([]);
        addToast(resp.response.data, { appearance: "error" });
      });
  };

  return (
    <div>
      <div style={{ backgroundColor: "#395E82" }}>
        <Row className="justify-content-center pt-5 mb-2 align-items-center">
          <h2 style={{ color: "#83CEC2" }}>
            Appointment report for: {currAppt?.patient}
          </h2>
        </Row>
        <Row className="justify-content-center mb-5 align-items-center">
          <h3 style={{ color: "#83CEC2" }}>{currAppt?.pharmacy}</h3>
        </Row>

        <Row className="justify-content-center pb-3  align-items-center">
          <Col md={8}>
            <Row
              className="justify-content-center align-items-center pt-3 rounded"
              style={{ backgroundColor: "#83CEC2" }}
            >
              <Col>
                <p className="text-center">
                  Date: {moment(currAppt?.start).format("DD MMM YYYY")}
                </p>
              </Col>
              <Col>
                <p className="text-center">
                  Time:
                  {moment(currAppt?.start).format("HH:mm a")}-
                  {moment(currAppt?.end).format("HH:mm a")}
                </p>
              </Col>
              <Col>
                <p className="text-center">Price: {currAppt?.price}</p>
              </Col>
            </Row>
          </Col>
        </Row>

        <Row className="justify-content-center mb-5 align-items-center">
          <Col md={8}>
            <Form>
              <Form.Group
                as={Row}
                className="justify-content-center align-items-center  mt-3"
              >
                <Form.Label>
                  <h4 style={{ color: "#83CEC2" }}>
                    Input information about the {apptType}
                  </h4>
                </Form.Label>
                <Form.Control
                  as="textarea"
                  rows="8"
                  name="address"
                  value={apptInfo}
                  onChange={(e) => setApptInfo(e.target.value)}
                />
              </Form.Group>
            </Form>
          </Col>
        </Row>
        <Row className="justify-content-center pb-5 align-items-center">
          <Col md={8}>
            <Row className="justify-content-center align-items-center">
              <div style={{ color: "white" }}>Therapy medicine</div>
              <Button
                size="sm"
                onClick={() => {
                  setShowModal(true);
                  setShowClicked(!showClicked);
                }}
              >
                Add new
              </Button>
            </Row>
            {(selectedMedicine ? selectedMedicine : []).map((value, index) => {
              return (
                <Row
                  className="justify-content-center align-items-center mt-3 ml=5"
                  key={value.code}
                >
                  <div style={{ color: "white" }}>
                    Medicine: {value.code} - {value.name}. Duration of
                    therapy(in days): {value.duration}
                  </div>
                  <Button
                    onClick={() => {
                      let newArray = [];
                      for (let i = 0; i < selectedMedicine.length; i++) {
                        if (i != index) newArray.push(selectedMedicine[i]);
                      }
                      setSelectedMedicine(newArray);
                    }}
                  >
                    Remove
                  </Button>
                </Row>
              );
            })}

            <Row className="justify-content-center mt-5 align-items-center">
              <div style={{ color: "#83CEC2" }}>Finalize appointment</div>
            </Row>
            <Row className="justify-content-center mt-3 mb-5 align-items-center">
              <Button
                onClick={() => {
                  setShowScheduleAnother(true);
                }}
              >
                Schedule another and finish appointment
              </Button>
              <Button onClick={() => finishAppt()}>
                {" "}
                Finish appointment without scheduling
              </Button>
            </Row>
          </Col>
        </Row>
      </div>

      <TherapyMedicineModal
        show={showModal}
        appt={currAppt}
        onHideModal={hideModal}
        onAddMedicine={onAdd}
        clickedShow={showClicked}
      ></TherapyMedicineModal>
      <ScheduleAnotherApp
        show={showScheduleAnother}
        appt={currAppt}
        onHide={() => setShowScheduleAnother(false)}
        onSchedule={() => {
          setShowScheduleAnother(false);
          finishAppt();
        }}
      ></ScheduleAnotherApp>
    </div>
  );
}

export default AppointmentReport;
