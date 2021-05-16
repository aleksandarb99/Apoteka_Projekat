import React, { useEffect, useState } from 'react'
import { Button, Form, Modal, Row, Col, InputGroup } from 'react-bootstrap';
import api from '../../app/api';
import { getIdFromToken } from '../../app/jwtTokenUtils';
import DatePicker from "react-datepicker";
import moment from "moment";
import { getUserTypeFromToken } from '../../app/jwtTokenUtils';

const ScheduleAnotherApp = (props) => {
    const [selectedDate, setSelectedDate] = useState(null);
    const [workersWorktime, setWorkersWorktime] = useState(null);
    const [showWorkTime, setShowWorkTime] = useState(false);
    const [apptsOnSelectedDate, setApptsOnSelectedDate] = useState([]);
    
    const [startHours, setStartHours] = useState('');
    const [startMinutes, setStartMinutes] = useState('');
    const [duration, setDuration] = useState('');
    const [price, setPrice] = useState('');

    useEffect(() => {
        let bodyFormData = new FormData();
        let workerID = getIdFromToken();
        if (workerID === null){
            return;
        }
        bodyFormData.append('workerID', workerID);

        if (getUserTypeFromToken().trim() === 'PHARMACIST'){
            api.post("http://localhost:8080/api/workers/getWorkTimeForReport", bodyFormData)
                .then(
                    (resp) => {
                        setWorkersWorktime(resp.data);
                    })
                .catch(() => alert("Couldn't get worktime!"));
        }else{
            console.log("trebalo bi");
            if (props.appt){
                console.log("trebalo bi 2");
                bodyFormData.append('pharmacyID', props.appt.pharmacyID);
                api.post("http://localhost:8080/api/workers/getWorkTimeForReportDerm", bodyFormData)
                    .then(
                        (resp) => {
                            setWorkersWorktime(resp.data);
                        })
                    .catch(() => alert("Couldn't get worktime!"));
            }
        }
    }, [props.appt])

    useEffect(() => {
        if (!selectedDate){ setApptsOnSelectedDate([]); return; }
        let bodyFormData = new FormData();
        let workerID = getIdFromToken();
        if (workerID === null){
            return;
        }
        bodyFormData.append('workerID', workerID);
        bodyFormData.append('patientID', props.appt.patientID);
        bodyFormData.append('date', moment(selectedDate).valueOf());

        api.post("http://localhost:8080/api/appointment/appointmentsOnThatDate", bodyFormData)
            .then(
                (resp) => {
                    setApptsOnSelectedDate(resp.data);
                })
            .catch(() => alert("Couldn't get appts on selected date!"));

    }, [selectedDate])

    const scheduleAndFinish = () => {
        if (!selectedDate){
            alert("invalid date");
            return;
        }
        if (isNaN(startHours)){
            alert('Invalid start time!');
            return;
        }else if (isNaN(startMinutes)){
            alert('Invalid start time!');
            return;
        }else if (isNaN(price)){
            alert('Invalid price!');
            return;
        }else if (isNaN(duration)){
            alert('Invalid duration!');
            return;
        }
        let hours = parseInt(startHours);
        let minutes = parseInt(startMinutes);
        let durationI = parseInt(duration);
        let priceF = parseFloat(price);
        if (hours < 0 || hours > 24 || minutes < 0 || minutes > 60){
            alert('Invalid start time!');
            return;
        }
        if (durationI < 0 || durationI > 180){
            alert('Invalid duration!');
            return;
        }
        if (priceF < 0 || priceF > 10000){
            alert('Invalid price!');
            return;
        }
        let totalMillisHourMin = hours * 60 * 60 *1000 + minutes*60*1000;

        let bodyFormData = new FormData();
        let workerID = getIdFromToken();
        if (workerID === null){
            return;
        }
        bodyFormData.append('workerID', workerID);
        bodyFormData.append('patientID', props.appt.patientID);
        bodyFormData.append('pharmacyID', props.appt.pharmacyID);
        bodyFormData.append('price', priceF);
        bodyFormData.append('date', moment(selectedDate).valueOf() + totalMillisHourMin);
        bodyFormData.append('duration', durationI);
        api.post("http://localhost:8080/api/appointment/scheduleAppointment", bodyFormData)
            .then(
                (resp) => {
                    alert("Appointment scheduled!");
                    props.onSchedule();
                })
            .catch((resp) => {console.log(resp); alert(resp.response.data)});
    }

    return (
        <Modal {...props} aria-labelledby="contained-modal-title-vcenter" centered scrollable>
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Schedule another appointment
                </Modal.Title>
            </Modal.Header>

            <Modal.Body>
                <Row className="ml-3 mb-2">
                    <InputGroup>
                        <InputGroup.Prepend>
                            <InputGroup.Text>Worker's schedule</InputGroup.Text>
                        </InputGroup.Prepend>
                        <Button onClick = {()=> setShowWorkTime(!showWorkTime)} >Show/hide</Button> 
                    </InputGroup>    
                </Row>
                {showWorkTime &&
                    <div>
                    <Row className="ml-5 mb-2">
                        Holidays:
                    </Row>
                    {(workersWorktime ? workersWorktime.holidays : []).map((value, index) => {
                        return (
                            <Row className="ml-5 mb-2" key={index+100}>
                            {index+1}) On holiday from {moment(value.startTime).format("DD MMM YYYY")} to {moment(value.endTime).format("DD MMM YYYY")}
                            </Row>
                            );
                    })}
                    <Row className="ml-5 mb-2">
                        Worktime:
                    </Row>
                    {(workersWorktime ? workersWorktime.workDayList : []).map((value, index) => {
                        return (
                            <Row className="ml-5 mb-2" key={index}>
                            {value.weekday}: {value.startTime} - {value.endTime}
                            </Row>
                            );
                    })}
                    </div>
                }
                <Form>
                    <Form.Group as={Row} className='ml-3 mb-2 mt-4'>
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text>Date for appointment:</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control
                                as={DatePicker} 
                                closeOnScroll={true}
                                selected={selectedDate} 
                                minDate={Date.now()}
                                dateFormat="dd/MM/yyyy" 
                                onChange={date => setSelectedDate(date)}
                                isClearable
                            />
                        </InputGroup>
                        
                    </Form.Group>
                </Form>

                {apptsOnSelectedDate.length > 0 &&
                    <div>
                    <Row className="ml-5 mb-2">
                        Appointments on selected date:
                    </Row>
                    {(apptsOnSelectedDate).map((value, index) => {
                        return (
                            <Row className="ml-5 mb-2" key={index+100}>
                            {index+1}) From {moment(value.startTime).format("HH:mm")} to {moment(value.endTime).format("HH:mm")}
                            </Row>
                            );
                    })}
                    </div>
                }
                <Row className="ml-1 mt-4 mb-2">
                    <Col md={8}>
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text>Start time:</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control type="text" placeholder="hours" value={startHours} onChange={(e)=>setStartHours(e.target.value)}/>
                            <Form.Control type="text" placeholder="minutes" value={startMinutes} onChange={(e)=>setStartMinutes(e.target.value)}/>
                        </InputGroup>
                    </Col>
                </Row>
                <Row className="ml-1 mt-4 mb-2">
                    <Col md={8}>
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text>Duration(min):</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control type="text" value={duration} onChange={(e)=>setDuration(e.target.value)}/>
                        </InputGroup>
                    </Col>
                </Row>
                <Row className="ml-1 mt-4 mb-5">
                    <Col md={8}>
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text>Price:</InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control type="text" value={price} onChange={(e)=>setPrice(e.target.value)}/>
                        </InputGroup>
                    </Col>
                </Row>

                <Row className="justify-content-center m-3 align-items-center">
                    <Button onClick={() => scheduleAndFinish()}>Schedule</Button>
                </Row>

            </Modal.Body>
        </Modal>
    )
}

export default ScheduleAnotherApp
