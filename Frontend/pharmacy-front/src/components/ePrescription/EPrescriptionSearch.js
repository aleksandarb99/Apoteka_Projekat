import axios from "axios";
import { data } from "jquery";
import React, { useEffect, useState } from "react";
import { Button, Col, Container, Form, Row } from "react-bootstrap";
import api from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";
import PharmacyWithMedicineList from "./PharmacyWithMedicineList";
import RequiredMedicineList from "./RequiredMedicineList";

const ERecipeSearch = (pharmacyId) => {
  const [errorMessage, setErrorMessage] = useState("");
  const [QRImage, setQRImage] = useState({});
  const [pharmacies, setPharmacies] = useState([]);
  const [showSearchPanel, setShowSearchPanel] = useState(false);
  const [sortBy, setSortBy] = useState("totalPrice");
  const [sortOrder, setSortOrder] = useState("ASC");
  const [parsedData, setParsedData] = useState({});

  const handleFileChange = (e) => {
    setQRImage(e.target.files[0]);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    e.stopPropagation();

    let formData = new FormData();
    formData.append("file", QRImage);

    api({
      method: "post",
      url: `http://localhost:8080/api/e-recipes/upload-qr/${getIdFromToken()}`,
      data: formData,
      headers: { "Content-Type": "multipart/form-data" },
    })
      .then((res) => {
        setParsedData(res.data);
      })
      .catch(() => {
        setErrorMessage("Invalid QR code!");
      });
  };

  useEffect(() => {
    let data = parsedData;
    if (data.state == "REJECTED") {
      setPharmacies([]);
      setErrorMessage("Invalid QR code!");
      setShowSearchPanel(false);
      return;
    } else if (data.state == "PROCESSED") {
      setPharmacies([]);
      setErrorMessage("This code has already been processed!");
      setShowSearchPanel(false);
      return;
    }

    api({
      method: "post",
      url: `http://localhost:8080/api/pharmacy/e-recipe?sort=${sortBy}&order=${sortOrder}`,
      data: data,
    })
      .then((res) => {
        setErrorMessage("");
        setPharmacies(res.data);
        setShowSearchPanel(true);
      })
      .catch(() => {
        setPharmacies([]);
        setErrorMessage("Invalid QR code!");
        setShowSearchPanel(false);
      });
  }, [parsedData, sortBy, sortOrder]);

  const doBuy = (pharmacy) => {
    let data = {
      ...parsedData,
      pharmacyId: pharmacy.id,
      totalPrice: pharmacy.totalPrice,
    };
    api
      .post(
        `http://localhost:8080/api/e-recipes/dispense-medicine/${getIdFromToken()}`,
        data
      )
      .then(() => {
        alert("Success");
        setParsedData([]);
      })
      .catch(() => {
        alert("Oops");
      });
  };

  return (
    <div className="consultation__insight__content">
      <Container>
        <Form
          onSubmit={(e) => {
            handleSubmit(e);
          }}
        >
          <Container style={{ marginBottom: "15px" }}>
            <Row>
              <Col className="my-auto" md={{ span: 4, offset: 2 }}>
                <Form.File>
                  <Form.File.Label>Upload QR Code</Form.File.Label>
                  <Form.File.Input onChange={(e) => handleFileChange(e)} />
                </Form.File>
              </Col>

              <Col className="my-auto" md={{ span: 4, offset: 2 }}>
                <Button type="submit">Submit</Button>
              </Col>
            </Row>
            <p>{errorMessage}</p>
          </Container>
        </Form>
        <Form hidden={!showSearchPanel}>
          <p>Required medicine: </p>
          <RequiredMedicineList
            medicines={!!parsedData.eRecipeItems ? parsedData.eRecipeItems : []}
          ></RequiredMedicineList>
          <Container>
            <Row className="align-content-between">
              <Col md={{ span: 3, offset: 2 }}>
                <Form.Group>
                  <Form.Label>Sort by</Form.Label>
                  <Form.Control
                    as="select"
                    onChange={(e) => {
                      setSortBy(e.target.value);
                    }}
                  >
                    <option value="totalPrice" selected>
                      Total price
                    </option>
                    <option value="name">Pharmacy name</option>
                    <option value="avgGrade">Pharmacy grade</option>
                    <option value="addressCity">City</option>
                    <option value="street">Street</option>
                  </Form.Control>
                </Form.Group>
              </Col>
              <Col md={{ span: 3, offset: 2 }}>
                <Form.Group>
                  <Form.Label>Order</Form.Label>
                  <Form.Control
                    as="select"
                    onChange={(e) => {
                      setSortOrder(e.target.value);
                    }}
                  >
                    <option value="ASC" selected>
                      Ascending
                    </option>
                    <option value="DESC">Descending</option>
                  </Form.Control>
                </Form.Group>
              </Col>
            </Row>
          </Container>
        </Form>
        <PharmacyWithMedicineList
          pharmacies={pharmacies}
          doBuy={(pharmacy) => {
            doBuy(pharmacy);
          }}
        >
          {" "}
        </PharmacyWithMedicineList>
      </Container>
    </div>
  );
};

export default ERecipeSearch;
