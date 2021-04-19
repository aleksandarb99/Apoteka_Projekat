import React from 'react'
import { Button, Modal } from 'react-bootstrap'
import { PatchCheck } from 'react-bootstrap-icons';

function SuccessModal(props) {
    return (
        <>
            <style type="text/css">
                {`
                    [variant="modal-header-success"] {
                        background: #B9E883;
                        text-align: center;
                        margin: -5px, -5px, 0, 0;
                        padding: 10px;
                      }
                `}
            </style>
            <Modal {...props}>
                <Modal.Header variant='modal-header-success' className="justify-content-center" closeButton>
                    <PatchCheck color='white' size={26}></PatchCheck>
                </Modal.Header>
                <Modal.Body className="justify-content-center">
                    <p>{props.message}</p>
                    <Button className="float-right" variant="outline-secondary" onClick={props.onHide} >Close</Button>
                </Modal.Body>
            </Modal >
        </>
    )
}

export default SuccessModal
