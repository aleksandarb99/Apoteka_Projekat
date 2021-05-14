import React, { useEffect, useState } from 'react'
import { Button, Col, Container, Row } from 'react-bootstrap';
import api from '../../app/api';
import AddEditCategoryModal from './AddEditCategoryModal';
import CategoryItem from './CategoryItem';

const CategoryList = () => {
    const [components, setComponents] = useState([]);
    const [refresh, setRefresh] = useState(false);
    const [showAddEditModal, setShowAddEditModal] = useState(false);

    useEffect(() => {
        api.get(`http://localhost:8080/api/ranking-category/`)
            .then((res) => {
                setComponents(res.data)
            })
    }, [refresh])

    const addCategory = () => {
        setRefresh(!refresh)
    }

    return (
        <Container style={{ marginBottom: '30px', marginTop: '30px' }} fluid>
            <Row>
                <Col md={{ span: 4, offset: 4 }}>
                    <div>
                        {components.map((c) => {
                            return <CategoryItem key={c.id} category={c} onItemChanged={() => { setRefresh(!refresh) }}></CategoryItem>
                        })}
                    </div>
                </Col>
            </Row>
            <Row>
                <Col md={{ span: 2, offset: 5 }}>
                    <Button style={{ width: '100%' }} variant="outline-secondary" onClick={() => { setShowAddEditModal(true) }}>Add new category</Button>
                </Col>
            </Row>
            <AddEditCategoryModal
                show={showAddEditModal}
                onHide={() => setShowAddEditModal(false)}
                onAddEdit={addCategory}
            />
        </Container >
    )
}

export default CategoryList
