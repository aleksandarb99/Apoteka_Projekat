import React from 'react'
import { Button, Card, Col, Container, Row } from 'react-bootstrap'

const PharmacyWithMedicineItem = ({ pharmacy }) => {

    const doBuy = () => {

    }

    return (
        <Card style={{ marginBottom: '20px' }}>
            <Card.Header>
                <Container fluid>
                    <Row>
                        <Col md={{ span: 3, offset: 0 }}>
                            {`${pharmacy.name}`}
                        </Col>
                        <Col md={{ span: 4, offset: 5 }}>
                            <div style={{ float: 'right' }}>
                                {`${pharmacy.addressStreet}, ${pharmacy.addressCity}`}
                            </div>
                        </Col>
                    </Row>
                </Container>
            </Card.Header>
            <Card.Body>
                <Container>
                    {`Grade: ${pharmacy.avgGrade}`}
                </Container>
            </Card.Body>
            <Card.Footer className="align-content-end" >
                <Container fluid>
                    <Row>
                        <Col md={{ span: 3, offset: 0 }}>
                            <i>{`Total price: ${pharmacy.totalPrice}`}</i>
                        </Col>
                        <Col md={{ span: 2, offset: 7 }}>
                            <Button style={{ width: '100%' }} onClick={doBuy}>Buy</Button>
                        </Col>
                    </Row>
                </Container>
            </Card.Footer>
        </Card >
    )
}

export default PharmacyWithMedicineItem
