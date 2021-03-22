import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { Button, Col, Container, Row, Table } from 'react-bootstrap'

function PharmacyTable(props) {
    const [pharmacies, setPharmacies] = useState([]);

    useEffect(async () => {
            const request = await axios.get("http://localhost:8080/api/pharmacy/all");
            setPharmacies(request.data);
    }, []);

    return (
        <Container style={{ marginTop: '100px' }}>
            <Button variant="secondary" style={{ float: 'right', margin: '20px' }}>Dodaj apoteku</Button>
            <Table striped bordered hover>
                <thead>
                    <tr>
                    <th>Naziv apoteke</th>
                    <th>Opis</th>
                    <th>Opcije</th>
                    </tr>
                </thead>
                <tbody>
                    {pharmacies.map((pharmacy) => (
                        <tr>
                        <td>{pharmacy.name}</td>
                        <td>{pharmacy.description}</td>
                        <td><Button>Azuriraj</Button> <Button variant="info">Detalji</Button> <Button variant="danger">Obrisi</Button> </td>
                        </tr>
                    ))}
                </tbody>
            </Table>
      </Container>
    )
}

export default PharmacyTable
