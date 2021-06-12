import React, { useState } from "react";
import { Modal, Container, Row, Col } from "react-bootstrap";
import api from "../../app/api";
import { useToasts } from "react-toast-notifications";
import moment from "moment";

function AppointmentInfoModal(props) {
  // prosledis appointment
  const { addToast } = useToasts();
  const [apptInfo, setApptInfo] = useState(null);

  const showHandler = () => {
    if (!props.appointment.id) return;

    api
      .get("/api/appointment/get_info/" + props.appointment.id)
      .then((resp) => {
        setApptInfo(resp.data);
      })
      .catch(() =>
        addToast("Error while fetching appointment info!", {
          appearance: "error",
        })
      );
  };

  const hideHandler = () => {
    setApptInfo(null);
    props.onHideFun();
  };

  return (
    <Modal
      {...props}
      aria-labelledby="contained-modal-title-vcenter"
      centered
      onShow={showHandler}
      onHide={hideHandler}
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Appointment information
        </Modal.Title>
      </Modal.Header>
      {apptInfo && (
        <Modal.Body>
          <Container>
            <Col style={{ backgroundColor: "#83CEC2" }} className="p-2">
              <Row className="m-2">-Patient: {apptInfo.patient}</Row>
              <Row className="m-2">-Pharmacy: {apptInfo.pharmacy}</Row>
              <Row className="m-2">-Price: {apptInfo.price}</Row>
              <Row className="m-2">
                -Start time:{" "}
                {moment(apptInfo.start).format("DD. MMM YYYY. HH:mm")}
              </Row>
              <Row className="m-2">
                -End time: {moment(apptInfo.end).format("DD. MMM YYYY. HH:mm")}
              </Row>
              <Row className="m-2">-Info: {apptInfo.info}</Row>
              {apptInfo.therapyDTOList.length > 0 && (
                <div>
                  <Row className="m-2">-Therapy:</Row>
                  {apptInfo.therapyDTOList.map((value, index) => {
                    return (
                      <Row className="ml-5 mt-1 mb-1" key={index}>
                        {value.code} - {value.medicineName} :{" "}
                        {value.therapyLength} days
                      </Row>
                    );
                  })}
                </div>
              )}
            </Col>
          </Container>
        </Modal.Body>
      )}
    </Modal>
  );
}

export default AppointmentInfoModal;
