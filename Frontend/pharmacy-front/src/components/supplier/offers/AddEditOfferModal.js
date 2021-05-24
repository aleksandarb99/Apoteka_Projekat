import React, { useState } from 'react'
import { Button, Form, Modal } from 'react-bootstrap'
import api from '../../../app/api';
import { getIdFromToken } from '../../../app/jwtTokenUtils';
import { useToasts } from "react-toast-notifications";
import { getErrorMessage } from '../../../app/errorHandler';

const AddEditOfferModal = (props) => {
    const [totalPrice, setTotalPrice] = useState(!props.offer ? 0 : props.offer.price);
    const [selectedDate, setSelectedDate] = useState(!props.offer ? Date.now() : new Date(props.offer.deliveryDate).getTime());
    const { addToast } = useToasts();

    const setDateTimestamp = (dateString) => {
        setSelectedDate(new Date(dateString).getTime())
    }

    const handleClose = () => {

    }

    const handleSubmit = (e) => {
        e.preventDefault()
        e.stopPropagation()

        let data = {}
        data.price = totalPrice;
        data.deliveryDate = selectedDate;
        data.offerState = props.offer ? props.offer.offerState : "PENDING";
        data.orderId = props.order.id;
        props.offer ? editOffer(data) : addOffer(data)
    }

    const addOffer = (data) => {
        api.post(`http://localhost:8080/api/suppliers/offers/${getIdFromToken()}`, data)
            .then(() => {
                props.onSuccess()
                props.onHide()
                addToast("Successfully added", { appearance: "success" })
            })
            .catch((err) => {
                addToast(getErrorMessage(err), { appearance: "error" });
            })
    }

    const editOffer = (data) => {
        data.id = props.offer.id;
        api.put(`http://localhost:8080/api/suppliers/offers/${getIdFromToken()}`, data)
            .then(() => {
                addToast("Successfully updated", { appearance: "success" })
                props.onSuccess()
                props.onHide()
            })
            .catch((err) => {
                addToast(err.response.data.message, { appearance: "error" });
            })
    }

    const convertDate = (timestamp) => {
        let date = new Date(timestamp);
        let year = date.getFullYear();
        let month = date.getMonth() + 1;
        let day = date.getDate();
        month = month < 10 ? '0' + month : month
        day = day < 10 ? '0' + day : day
        return `${year}-${month}-${day}`
    }

    let itemsToRender;
    if (!!props.order && !!props.order.orderItem) {
        itemsToRender = props.order.orderItem.map((oi) => {
            return <div key={oi.id}>
                <p>{`${oi.medicine.code} -- ${oi.medicine.name} -- Amount: ${oi.amount}`}</p>
            </div>
        });
    }

    return (
        <Modal {...props}>
            <Modal.Header className="justify-content-center" backdrop="static" onHide={handleClose} closeButton>
                <p>{!props.offer ? "Create offer" : "Edit offer"} </p>
            </Modal.Header>
            <Modal.Body className="justify-content-center">
                {itemsToRender}
                <Form onSubmit={handleSubmit}>
                    <Form.Group>
                        <Form.Label>Total price</Form.Label>
                        <Form.Control
                            type="number"
                            onChange={(event) => setTotalPrice(event.target.value)}
                            defaultValue={!props.offer ? 0 : props.offer.price}
                            min={0}
                            step={1}
                            required
                        />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Due</Form.Label>

                        <Form.Control
                            type="date"
                            onChange={(event) => setDateTimestamp(event.target.value)}
                            defaultValue={!props.offer ? convertDate(Date.now()) : convertDate(props.offer.deliveryDate)}
                            min={convertDate(Date.now())}
                            max={!!props.order ? convertDate(props.order.deadline) : ""}
                            required
                        />
                    </Form.Group>
                    <Button className="float-center" variant="outline-secondary" type="submit" >Save</Button>
                </Form>
            </Modal.Body>
        </Modal >
    )
}

export default AddEditOfferModal
