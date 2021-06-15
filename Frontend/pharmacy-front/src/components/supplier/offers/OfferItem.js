import {
  Button,
  Col,
  Container,
  FormGroup,
  FormLabel,
  Row,
} from "react-bootstrap";
import React, { useEffect, useState } from "react";
import AddEditOfferModal from "../offers/AddEditOfferModal";
import api from "../../../app/api";

const OfferItem = (props) => {
  const [showEditOfferModal, setShowEditOfferModal] = useState(false);
  const [order, setOrder] = useState({});

  useEffect(() => {
    async function fetchOrder() {
      const res = await api.get(`/api/orders/${props.offer.orderId}`).catch(() => { });
      setOrder(!!res ? res.data : {});
    }
    fetchOrder();
  }, []);

  const canEdit = () => {
    return props.offer.offerState === "PENDING" && order.deadline > Date.now();
  };

  return (
    <Container
      className="border border-primary"
      style={{
        borderRadius: "10px",
        padding: "10px",
        marginTop: "10px",
        marginBottom: "10px",
        backgroundColor: "white",
      }}
    >
      <Row className="justify-content-between">
        <Col md={3}>
          <FormGroup>
            <FormLabel>State: {props.offer.offerState}</FormLabel>
          </FormGroup>
        </Col>
        <Col md={3}>
          <FormGroup>
            <FormLabel>Price: {props.offer.price}</FormLabel>
          </FormGroup>
        </Col>
        <Col md={3}>
          <FormGroup>
            <FormLabel>
              Delivery Date:{" "}
              {new Date(props.offer.deliveryDate).toLocaleDateString("sr-sp")}
            </FormLabel>
          </FormGroup>
        </Col>
        <Col md={3}>
          <FormGroup>
            <FormLabel>
              Deadline:{" "}
              {!!order
                ? new Date(order.deadline).toLocaleDateString("sr-sp")
                : ""}
            </FormLabel>
          </FormGroup>
        </Col>
      </Row>
      <Row>
        <Col></Col>
      </Row>
      <Row>
        <Col md={{ offset: 0, span: 2 }}>
          <Button
            disabled={!canEdit()}
            onClick={() => {
              setShowEditOfferModal(true);
            }}
          >
            Edit
          </Button>
        </Col>
      </Row>
      <AddEditOfferModal
        show={showEditOfferModal}
        order={order}
        offer={props.offer}
        onHide={() => setShowEditOfferModal(false)}
        onSuccess={() => {
          props.onSuccess();
        }}
      ></AddEditOfferModal>
    </Container>
  );
};

export default OfferItem;
