import React, { useEffect, useState } from "react";
import { XCircle } from "react-bootstrap-icons";

import { Row, Container, Table } from "react-bootstrap";

import axios from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";

import "../../styling/pharmaciesAndMedicines.css";
import "../../styling/consultation.css";
import api from "../../app/api";
import DeleteModal from "../utilComponents/modals/DeleteModal";

// CHECK Deki ovde ima
function SubscribedPharmacies() {
  const [pharmacies, setPharmacies] = useState([]);
  const [selectedPharmacy, setSelectedPharmacy] = useState([]);
  const [showDeleteModal, setShowDeleteModal] = useState(false);

  useEffect(() => {
    loadPharmacies();
  }, []);

  const loadPharmacies = () => {
    async function fetchPharmacies() {
      const request = await axios.get(
        "/api/pharmacy/subscribed/patient/" + getIdFromToken()
      );
      setPharmacies(request.data);

      return request;
    }
    fetchPharmacies();
  };

  const unsubscribe = () => {
    api
      .post(
        `/api/pharmacy/${selectedPharmacy.id}/unsubscribe/${getIdFromToken()}`
      )
      .then(() => {
        loadPharmacies();
        setShowDeleteModal(false);
      });
  };

  return (
    <Container fluid className="consultation__insight__container">
      <div className="consultation__insight__content">
        <Row className="my__flex">
          <h3>Pharmacies to which you are subscribed</h3>
        </Row>
        <Row>
          <Table
            striped
            bordered
            variant="light"
            className="my__table__pharmacies"
          >
            <thead>
              <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Average grade</th>
                <th>Address</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {pharmacies &&
                pharmacies.map((p) => (
                  <tr
                    key={p.id}
                    onClick={() => {
                      setSelectedPharmacy(p);
                    }}
                  >
                    <td>{p.name}</td>
                    <td>
                      {p.description.length > 30
                        ? p.description.substr(0, 29) + "..."
                        : p.description}
                    </td>
                    <td>{p.avgGrade}</td>
                    <td>{p.address}</td>
                    <td>
                      <XCircle
                        style={{ color: "red" }}
                        onClick={() => {
                          setShowDeleteModal(true);
                        }}
                      />
                    </td>
                  </tr>
                ))}
            </tbody>
          </Table>
        </Row>
      </div>
      <DeleteModal
        show={showDeleteModal}
        title={`Unsubscribe from ${selectedPharmacy.name}`}
        bodyText="Press 'confirm' to unsubscribe"
        onDelete={unsubscribe}
        onHide={() => {
          setShowDeleteModal(false);
        }}
      >
        {" "}
      </DeleteModal>
    </Container>
  );
}

export default SubscribedPharmacies;
