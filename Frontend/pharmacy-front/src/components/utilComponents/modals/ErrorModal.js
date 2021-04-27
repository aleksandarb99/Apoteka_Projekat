import React from 'react'
import { Button, Modal } from 'react-bootstrap'
import { PatchMinus } from 'react-bootstrap-icons';

function ErrorModal(props) {
    return (
        <>
            <style type="text/css">
                {`
                    [variant="modal-header-error"] {
                        background: #e85e6c;
                        text-align: center;
                        margin: -5px, -5px, 0, 0;
                        padding: 10px;
                      }
                `}
            </style>
            <Modal {...props}>
                <Modal.Header variant='modal-header-error' className="justify-content-center" closeButton>
                    <PatchMinus color='white' size={26}></PatchMinus>
                </Modal.Header>

                <Modal.Body className="justify-content-center">
                    <h4>Oops!</h4>
                    <p>{props.message}</p>
                    <Button className="float-right" variant="outline-secondary" onClick={props.onHide} >Close</Button>
                </Modal.Body>
            </Modal >
        </>
    )
}

export default ErrorModal
