import React from 'react'
import { Button, Modal } from 'react-bootstrap'

function DeleteModal(props) {
    return (
        <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    {props.title}
                </Modal.Title>
            </Modal.Header>

            <Modal.Body>
                <p class="text-danger">{props.bodyText ? props.bodyText : 'Delete this item?'}</p>
            </Modal.Body>

            <Modal.Footer>
                <Button variant="danger" onClick={props.onDelete}>Confirm</Button>
                <Button variant="secondary" onClick={props.onHide}>Cancel</Button>
            </Modal.Footer>
        </Modal>
    )
}

export default DeleteModal
