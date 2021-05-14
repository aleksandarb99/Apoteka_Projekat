import React, { useEffect, useState } from 'react'
import { Col, Container, Row } from 'react-bootstrap';
import api from '../../app/api';
import CategoryItem from './CategoryItem';

const CategoryList = () => {
    const [components, setComponents] = useState([]);

    useEffect(() => {
        api.get(`http://localhost:8080/api/ranking-category/`)
            .then((res) => {
                setComponents(res.data)
            })
    }, [])

    return (
        <Container fluid>
            <Row>
                <Col md={{ span: 4, offset: 4 }}>
                    <div>
                        {components.map((c) => {
                            return <CategoryItem category={c}></CategoryItem>
                        })}
                    </div>
                </Col>
            </Row>

        </Container>
    )
}

export default CategoryList
