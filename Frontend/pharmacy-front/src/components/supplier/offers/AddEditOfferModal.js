import React, { useState } from 'react'
import { Button, Form, Modal } from 'react-bootstrap'

const AddEditOfferModal = (props) => {
    const [validated, setValidated] = useState(false);
    const [totalPrice, setTotalPrice] = useState(false);
    const [date, setDate] = useState(false);

    const handleClose = () => {

    }

    const handleSubmit = () => {

    }

    const convertDate = (timestamp) => {
        let date = new Date(timestamp);
        let year = date.getUTCFullYear();
        let month = date.getUTCMonth() + 1;
        let day = date.getUTCDate();
        month = month < 10 ? '0' + month : month
        day = day < 10 ? '0' + day : day
        return `${year}-${month}-${day}`
    }


    return (
        <Modal {...props}>
            <Modal.Header className="justify-content-center" backdrop="static" onHide={handleClose} closeButton>
                <p>{!props.offer ? "Create offer" : "Edit offer"} </p>
            </Modal.Header>
            <Modal.Body className="justify-content-center">
                {props.order.orderItem.map((oi) => {
                    return <div key={oi.id}>
                        <p>{`${oi.medicine.code} -- ${oi.medicine.name} -- Amount: ${oi.amount}`}</p>
                    </div>
                })}
                <Form noValidate validated={validated} onSubmit={handleSubmit}>
                    <Form.Group>
                        <Form.Label>Total price</Form.Label>
                        <Form.Control
                            type="number"
                            onChange={(event) => setTotalPrice(event.target.value)}
                            defaultValue={!props.offer ? 0 : props.offer.price}
                            min={0}
                            step={100}
                            required
                        />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Due</Form.Label>

                        <Form.Control
                            type="date"
                            onChange={(event) => setDate(event.target.value)}
                            defaultValue={!props.offer ? convertDate(Date.now()) : convertDate(props.offer.deliveryDate)}
                            min={convertDate(Date.now())}
                            max={convertDate(props.order.deadline)}
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
