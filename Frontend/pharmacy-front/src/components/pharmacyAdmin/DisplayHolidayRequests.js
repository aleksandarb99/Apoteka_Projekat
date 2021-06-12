import React, { useState, useEffect } from "react";

import { Tab, Row, Col, Button, Table, Modal, Alert } from "react-bootstrap";

import moment from "moment";

import axios from "../../app/api";

import "../../styling/pharmacy.css";
import RejectRequestModal from "./RejectRequestModal";

import { useToasts } from "react-toast-notifications";
function DisplayHolidayRequests({ idOfPharmacy }) {
  const { addToast } = useToasts();
  const [requests, setRequests] = useState([]);
  const [selectedRowId, setSelectedRowId] = useState(-1);
  const [showAlert, setShowAlert] = useState(false);

  async function fetchRequests() {
    const request = await axios.get(
      `/api/vacation/getunresolvedrequestsbypharmacyid/${idOfPharmacy}`
    );
    setRequests(request.data);

    return request;
  }

  async function rejectRequest(reason) {
    const request = await axios
      .post(`/api/vacation/rejectrequest/${selectedRowId}`, reason)
      .then((res) => {
        fetchRequests();
        addToast(res.data, {
          appearance: "success",
        });
      })
      .catch((err) => {
        addToast(err.response.data, {
          appearance: "error",
        });
      });
    return request;
  }

  async function acceptRequest() {
    const request = await axios
      .post(`/api/vacation/acceptrequest/${selectedRowId}`)
      .then((res) => {
        fetchRequests();
        addToast(res.data, {
          appearance: "success",
        });
      })
      .catch((err) => {
        addToast(err.response.data, {
          appearance: "error",
        });
      });
    return request;
  }

  useEffect(() => {
    if (idOfPharmacy != undefined) {
      fetchRequests();
    }
  }, [idOfPharmacy]);

  let handleClick = (requestId) => {
    setSelectedRowId(requestId);
  };

  let handleReject = (reason) => {
    setShowAlert(false);
    rejectRequest(reason);
    setSelectedRowId(-1);
  };

  let handleAccept = () => {
    acceptRequest();
    setSelectedRowId(-1);
  };

  return (
    <Tab.Pane eventKey="fifth">
      <h1 className="content-header">Requests for vacations and absences</h1>
      <Row>
        <Col>
          <Table bordered striped hover variant="dark">
            <thead>
              <tr>
                <th>#</th>
                <th>Type</th>
                <th>State</th>
                <th>Start time</th>
                <th>End time</th>
                <th>Worker details</th>
              </tr>
            </thead>
            <tbody>
              {" "}
              {requests?.length != 0 &&
                requests?.map((item, index) => (
                  <tr
                    onClick={() => {
                      handleClick(item.id);
                    }}
                    className={`${
                      selectedRowId == item.id ? "selectedRow" : "pointer"
                    } `}
                  >
                    <td>{index + 1}</td>
                    <td>{item.absenceType}</td>
                    <td>{item.requestState}</td>
                    <td>{moment(item.start).format("DD-MM-YYYY")}</td>
                    <td>{moment(item.end).format("DD-MM-YYYY")}</td>
                    <td>{item.workerDetails}</td>
                  </tr>
                ))}
            </tbody>
          </Table>

          <RejectRequestModal
            show={showAlert}
            onHide={() => setShowAlert(false)}
            rejectRequest={handleReject}
          />

          <div className="center">
            <Button
              disabled={selectedRowId == -1}
              onClick={handleAccept}
              variant="primary"
            >
              Accept request
            </Button>
            <Button
              disabled={selectedRowId == -1}
              onClick={() => setShowAlert(true)}
              variant="secondary"
            >
              Reject request
            </Button>
          </div>
        </Col>
      </Row>
    </Tab.Pane>
  );
}

export default DisplayHolidayRequests;
