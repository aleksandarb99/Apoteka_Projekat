import React, { useState } from "react";
import { Button, Card, Col, Container, Row } from "react-bootstrap";
import DeleteModal from "../utilComponents/modals/DeleteModal";
import api from "../../app/api";
import AddEditCategoryModal from "./AddEditCategoryModal";

const CategoryItem = ({ category, onItemChanged }) => {
  const [showAddEditModal, setShowAddEditModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);

  const deleteCategory = () => {
    api.delete(`/api/ranking-category/${category.id}`).then(() => {
      onItemChanged();
    });
    setShowDeleteModal(false);
  };

  const editCategory = () => {
    onItemChanged();
    setShowDeleteModal(false);
  };

  return (
    <Card style={{ marginBottom: "20px" }}>
      <Card.Header>
        <Container>
          <Row>
            <Col md={{ span: 3, offset: 0 }}>{`${category.name}`}</Col>
            <Col md={{ span: 4, offset: 5 }}>
              <div style={{ float: "right" }}>{`${category.discount}%`}</div>
            </Col>
          </Row>
        </Container>
      </Card.Header>
      <Card.Body>
        <Container>{`Points required: ${category.pointsRequired}`}</Container>
      </Card.Body>
      <Card.Footer>
        <Container>
          <Row>
            <Col md={{ span: 4, offset: 0 }}>
              <Button
                variant="outline-success"
                style={{ width: "100%", marginLeft: "0" }}
                onClick={() => {
                  setShowAddEditModal(true);
                }}
              >
                Edit
              </Button>
            </Col>
            <Col md={{ span: 4, offset: 4 }}>
              <Button
                variant="outline-danger"
                style={{ width: "100%" }}
                onClick={() => {
                  setShowDeleteModal(true);
                }}
              >
                Remove
              </Button>
            </Col>
          </Row>
        </Container>
      </Card.Footer>
      <DeleteModal
        title={"Remove " + category.name}
        show={showDeleteModal}
        onHide={() => setShowDeleteModal(false)}
        onDelete={deleteCategory}
      />
      <AddEditCategoryModal
        show={showAddEditModal}
        category={category}
        onHide={() => setShowAddEditModal(false)}
        onAddEdit={editCategory}
      />
    </Card>
  );
};

export default CategoryItem;
