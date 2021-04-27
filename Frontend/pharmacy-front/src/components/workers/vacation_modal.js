import React, { useState, useEffect } from 'react'
import { Button, Form, Modal, Card, Row, Col, Container, InputGroup } from 'react-bootstrap'
import PropTypes from 'prop-types'
import api from '../../app/api';
import moment from "moment";
import DatePicker from "react-datepicker";
import { getIdFromToken } from '../../app/jwtTokenUtils';

function VacationModal(props) {
    const [fromDate, setFromDate] = useState(null);
    const [toDate, setToDate] = useState(null);
    const [typeVac, setTypeVac] = useState('none');
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        setToDate(null);
        setFromDate(null);
        setTypeVac('none');
    }, [props.show])

    const onStart = () => {
        if (!fromDate){
            setErrorMessage('Starting date is empty!');
            return;
        }else if (!toDate){
            setErrorMessage('Ending date is empty!');
            return;
        }else if (typeVac === 'none'){
            setErrorMessage('Vacation type is none!');
            return;
        }

        if (fromDate.getTime() >= toDate.getTime()){
            setErrorMessage('Invalid start/end date!');
            return;
        }

        let userID = getIdFromToken();
        if (!userID){
            alert("No user id in token! error");
            return;
        }
        
        let startDate = Math.floor(fromDate.getTime());
        let endDate = Math.floor(toDate.getTime());

        if (moment(Date.now()) > moment(startDate)){
            setErrorMessage('Start date of vacation has to be in future!');
            return;
        }

        let search_params = new URLSearchParams();
        search_params.append('id', userID);
        search_params.append('start', startDate);
        search_params.append('end', endDate);
        search_params.append('type', typeVac);

        api.get("http://localhost:8080/api/vacation/request_vacation", 
                { params: search_params}).then(()=>props.onCreateMethod()).catch((error) =>setErrorMessage(error.response.data));
    }
    
    return (
        <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered>
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Create a request for a vacation
                </Modal.Title>
            </Modal.Header>
            <Modal.Body as={Row} className="justify-content-center m-3">
                <Col>
                    <Row className="justify-content-center m-3">
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text>Start date:</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control 
                                as={DatePicker} 
                                closeOnScroll={true}
                                selected={fromDate} 
                                dateFormat="dd/MM/yyyy" 
                                onChange={date => setFromDate(date)}
                                isClearable/>
                        </InputGroup>
                    </Row>

                    <Row className="justify-content-center m-3">
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text>End date:</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control 
                                as={DatePicker} 
                                closeOnScroll={true}
                                selected={toDate} 
                                dateFormat="dd/MM/yyyy" 
                                onChange={date => setToDate(date)}
                                isClearable/>
                        </InputGroup>
                    </Row>

                    <Row className="justify-content-center m-3">
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text>Type:</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control as="select" 
                                    value={typeVac} 
                                    onChange={(event)=> setTypeVac(event.target.value)}
                                    name="typeVac">
                                    <option value="none">none</option>
                                    <option value="vacation">vacation</option>
                                    <option value="leave">leave</option>
                            </Form.Control>
                        </InputGroup>
                    </Row>

                    
                    {errorMessage.length > 0 &&
                        <Row className="justify-content-center m-3">
                            <p className="text-danger">{errorMessage}</p>
                        </Row>
                    }
                </Col>
            </Modal.Body>
            <Modal.Footer>
                <Button onClick={onStart}>Create request</Button>
            </Modal.Footer>
        </Modal>
    )
}

export default VacationModal
