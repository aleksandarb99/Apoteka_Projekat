import React, { useState, useEffect } from "react";

import { Table, Tab, Row, Col } from "react-bootstrap";

import Dropdown from "react-bootstrap/Dropdown";

import axios from "../../app/api";

import moment from "moment";

function DisplayInquiries({ idOfPharmacy, refreshInq }) {
  const [inquiries, setInquiries] = useState([]);
  const [showedInquiries, setShowedInquiries] = useState([]);
  const [dropdownLabel, setDropdownLabel] = useState("Active");

  async function fetchInquiries() {
    const request = await axios.get(
      `/api/inquiry/bypharmacyid/${idOfPharmacy}`
    );
    setInquiries(request.data);
    return request;
  }

  useEffect(() => {
    if (idOfPharmacy != undefined) {
      filterInquiries("Active");
    }
  }, [inquiries]);

  useEffect(() => {
    if (idOfPharmacy != undefined) {
      fetchInquiries();
    }
  }, [idOfPharmacy, refreshInq]);

  let filterInquiries = (filter) => {
    let result = inquiries.filter((item) => {
      if (filter == "Active") return item.active;
      else return !item.active;
    });

    setShowedInquiries(result);
  };

  return (
    <Tab.Pane eventKey="eight">
      <h1 className="content-header">Inquiries</h1>
      <hr></hr>
      <Row>
        {" "}
        <Dropdown>
          <Dropdown.Toggle variant="primary" id="dropdown-basic">
            Filter : {dropdownLabel}
          </Dropdown.Toggle>

          <Dropdown.Menu>
            <Dropdown.Item
              onClick={() => {
                filterInquiries("Active");
                setDropdownLabel("Active");
              }}
            >
              Active
            </Dropdown.Item>
            <Dropdown.Item
              onClick={() => {
                filterInquiries("End");
                setDropdownLabel("Ended");
              }}
            >
              Ended
            </Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
      </Row>
      <hr></hr>
      <Row>
        <Col>
          <Table striped bordered variant="dark">
            <thead>
              <tr>
                <th>#</th>
                <th>Medicine</th>
                <th>Date</th>
                <th>Worker</th>
                <th>Active</th>
              </tr>
            </thead>
            <tbody>
              {showedInquiries &&
                showedInquiries.map((item, index) => (
                  <tr>
                    <td>{index + 1}</td>
                    <td>{item.medicineItems.medicine.name}</td>
                    <td>{moment(item.date).format("DD MMM YYYY")}</td>
                    <td>
                      {item.worker.firstName} {item.worker.lastName}
                    </td>
                    <td>{item.active ? "True" : "False"}</td>
                  </tr>
                ))}
            </tbody>
          </Table>
        </Col>
      </Row>
    </Tab.Pane>
  );
}

export default DisplayInquiries;
