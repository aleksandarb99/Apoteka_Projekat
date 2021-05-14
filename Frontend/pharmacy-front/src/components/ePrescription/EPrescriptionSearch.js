import axios from 'axios';
import { data } from 'jquery';
import React, { useState } from 'react'
import { Button, Col, Container, Form, Row } from 'react-bootstrap'
import api from '../../app/api';
import PharmacyWithMedicineList from './PharmacyWithMedicineList';

const ERecipeSearch = () => {
    const [errorMessage, setErrorMessage] = useState("");
    const [QRImage, setQRImage] = useState({});
    const [pharmacies, setPharmacies] = useState([]);
    const [showPharmaciesPanel, setShowPharmaciesPanel] = useState(false);

    const handleFileChange = (e) => {
        setQRImage(e.target.files[0])
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        e.stopPropagation();

        let formData = new FormData();
        formData.append('file', QRImage);

        api({
            method: 'post',
            url: 'http://localhost:8080/api/e-recipes/upload-qr',
            data: formData,
            headers: { 'Content-Type': 'multipart/form-data' }
        })
            .then((res) => {
                processResponse(res.data)
            })
            .catch(() => { setErrorMessage("Invalid QR code!") })
    }

    const processResponse = (data) => {
        if (data.state == "REJECTED") {
            setPharmacies([])
            setErrorMessage("Invalid QR code!")
            setShowPharmaciesPanel(false)
            return;
        } else if (data.state == "PROCESSED") {
            setPharmacies([])
            setErrorMessage("This code has already been processed!")
            setShowPharmaciesPanel(false)
            return;
        }

        api({
            method: 'post',
            url: `http://localhost:8080/api/pharmacy/e-recipe`,
            data: data
        })
            .then((res) => {
                setErrorMessage("")
                setPharmacies(res.data)
                setShowPharmaciesPanel(true)
            })
            .catch(() => {
                setPharmacies([])
                setErrorMessage("Invalid QR code!")
                setShowPharmaciesPanel(false)
            })
    }

    return (
        <Form onSubmit={(e) => { handleSubmit(e) }}>
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
                <PharmacyWithMedicineList hidden={!showPharmaciesPanel} pharmacies={pharmacies}> </PharmacyWithMedicineList>
            </Container>
        </Form>
    )
}

export default ERecipeSearch
