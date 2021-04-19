import React from 'react'
import { Button, Modal } from 'react-bootstrap'
import { PatchExclamation } from 'react-bootstrap-icons';

function WarningModal(props) {
    return (
        <>
            <style type="text/css">
                {`
                    [variant="modal-header-warning"] {
                        background: #F2CB61;
                        text-align: center;
                        margin: -5px, -5px, 0, 0;
                        padding: 10px;
                      }
                `}
            </style>
            <Modal {...props}>
                <Modal.Header variant='modal-header-warning' className="justify-content-center" closeButton>
                    <PatchExclamation color='white' size={26}></PatchExclamation>
                </Modal.Header>

                <Modal.Body className="justify-content-center">
                    <p>{props.message}</p>
                    <Button className="float-right" variant="outline-secondary" onClick={props.onHide} >Close</Button>
                </Modal.Body>
            </Modal >
        </>
    )
}

export default WarningModal
