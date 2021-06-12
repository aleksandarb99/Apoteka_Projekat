import React, { useEffect, useState } from "react";
import { Button, Col, Container, Form, Row } from "react-bootstrap";
import api from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";
import { useToasts } from "react-toast-notifications";

const ERecipeSearchInPharmacy = ({ pharmacyId }) => {
  const { addToast } = useToasts();
  const [QRImage, setQRImage] = useState({});
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
      url: `/api/e-recipes/upload-qr/${getIdFromToken()}`,
      data: formData,
      headers: { "Content-Type": "multipart/form-data" },
    })
      .then((res) => {
        setParsedData(res.data);
      })
      .catch(() => {
        addToast("Invalid QR code", {
          appearance: "error",
        });
      });
  };

  useEffect(() => {
    let data = parsedData;
    if (Object.keys(data) == 0) return;

    api({
      method: "post",
      url: `/api/pharmacy/e-recipe/${pharmacyId}`,
      data: data,
    })
      .then((res) => {
        addToast(res.data, {
          appearance: "success",
        });
      })
      .catch(() => {
        addToast("E-Recipe is not in the pharmacy!", {
          appearance: "error",
        });
      });
  }, [parsedData]);

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
          </Container>
        </Form>
      </Container>
    </div>
  );
};

export default ERecipeSearchInPharmacy;
