import axios from 'axios';
import React, { useState } from 'react'
import { Button, Col, Container, Form, Row } from 'react-bootstrap'
import api from '../../app/api';
import PharmacyWithMedicineList from './PharmacyWithMedicineList';

const ERecipeSearch = () => {
    const [errorMessage, setErrorMessage] = useState("");
    const [QRImage, setQRImage] = useState({});

    const handleFileChange = (e) => {
        let files = e.target.files;
        setQRImage(e.target.files[0])
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        e.stopPropagation();

        let config = {
            headers: {
                'content-type': 'multipart/form-data'
            }
        }

        let formData = new FormData();
        formData.append('file', QRImage);

        api({
            method: 'post',
            url: 'http://localhost:8080/api/e-recipes/upload-qr',
            data: formData,
            headers: { 'Content-Type': 'multipart/form-data' }
        })
            .then(() => { alert("Success") })
            .catch(() => { alert("DEBIL") })
    }

    return (
        <Form onSubmit={(e) => { handleSubmit(e) }}>
            <Container style={{ marginBottom: "15px" }}>
                <Row>
                    <Col md={{ span: 4, offset: 2 }}>
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
                <PharmacyWithMedicineList> </PharmacyWithMedicineList>
            </Container>
        </Form>
    )
}

export default ERecipeSearch
