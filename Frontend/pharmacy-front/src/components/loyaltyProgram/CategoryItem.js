import React from 'react'
import { Button, Card, Col, Container, Row } from 'react-bootstrap'

const CategoryItem = ({ category }) => {
    return (
        <Card style={{ marginBottom: '20px' }}>
            <Card.Header>
                <Container>
                    <Row>
                        <Col md={{ span: 3, offset: 0 }}>
                            {`${category.name}`}
                        </Col>
                        <Col md={{ span: 4, offset: 5 }}>
                            <div style={{ float: 'right' }}>
                                {`${category.discount}%`}
                            </div>
                        </Col>
                    </Row>
                </Container>
            </Card.Header>
            <Card.Body>
                <Container>
                    {`Points required: ${category.pointsRequired}`}
                </Container>
            </Card.Body>
            <Card.Footer>
                <Container>
                    <Row>
                        <Col md={{ span: 4, offset: 0 }}>
                            <Button variant="outline-success" style={{ width: '100%', marginLeft: '0' }} onClick={() => { }}>Edit</Button>
                        </Col>
                        <Col md={{ span: 4, offset: 4 }}>
                            <Button variant="outline-danger" style={{ width: '100%' }} onClick={() => { }}>Remove</Button>
                        </Col>
                    </Row>
                </Container>
            </Card.Footer>
        </Card>
    )
}

export default CategoryItem
