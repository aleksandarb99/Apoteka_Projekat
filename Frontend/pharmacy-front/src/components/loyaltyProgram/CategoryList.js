import React, { useEffect, useState } from 'react'
import { Col, Container, Row } from 'react-bootstrap';
import api from '../../app/api';
import CategoryItem from './CategoryItem';

const CategoryList = () => {
    const [components, setComponents] = useState([]);
    const [refresh, setRefresh] = useState(false);

    useEffect(() => {
        api.get(`http://localhost:8080/api/ranking-category/`)
            .then((res) => {
                setComponents(res.data)
            })
    }, [refresh])

    return (
        <Container fluid>
            <Row>
                <Col md={{ span: 4, offset: 4 }}>
                    <div>
                        {components.map((c) => {
                            return <CategoryItem key={c.id} category={c} onItemChanged={() => { setRefresh(!refresh) }}></CategoryItem>
                        })}
                    </div>
                </Col>
            </Row>

        </Container >
    )
}

export default CategoryList
