import React, { useEffect, useState } from 'react'
import { Button, Form, Modal, Row, Col, InputGroup } from 'react-bootstrap';
import api from '../../app/api';
import { getIdFromToken } from '../../app/jwtTokenUtils';
import DatePicker from "react-datepicker";
import moment from "moment";
import { getUserTypeFromToken } from '../../app/jwtTokenUtils';
import { useToasts } from "react-toast-notifications";

const ScheduleAnotherApp = (props) => {
    const [selectedDate, setSelectedDate] = useState(null);
    const [workersWorktime, setWorkersWorktime] = useState(null);
    const [showWorkTime, setShowWorkTime] = useState(false);
    const [apptsOnSelectedDate, setApptsOnSelectedDate] = useState([]);
    
    const [startHours, setStartHours] = useState('');
    const [startMinutes, setStartMinutes] = useState('');
    const [duration, setDuration] = useState('');

    const { addToast } = useToasts();

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
                .catch(() => addToast("Couldn't get worktime!", { appearance: "error" }));
        }else{
            if (props.appt){
                bodyFormData.append('pharmacyID', props.appt.pharmacyID);
                api.post("http://localhost:8080/api/workers/getWorkTimeForReportDerm", bodyFormData)
                    .then(
                        (resp) => {
                            setWorkersWorktime(resp.data);
                        })
                    .catch(() => addToast("Couldn't get worktime!", { appearance: "error" }));
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
            .catch(() => addToast("Couldn't get appts on selected date!", { appearance: "error" }));

    }, [selectedDate])

    const scheduleAndFinish = () => {
        if (!selectedDate){
            addToast("Invalid date", { appearance: "error" });
            return;
        }
        if (isNaN(startHours)){
            addToast('Invalid start time!', { appearance: "error" });
            return;
        }else if (isNaN(startMinutes)){
            addToast('Invalid start time!', { appearance: "error" });
            return;
        }else if (isNaN(duration)){
            addToast('Invalid duration!', { appearance: "error" });
            return;
        }
        let hours = parseInt(startHours);
        let minutes = parseInt(startMinutes);
        let durationI = parseInt(duration);
        if (hours < 0 || hours > 24 || minutes < 0 || minutes > 60){
            addToast('Invalid start time!', { appearance: "error" });
            return;
        }
        if (durationI < 0 || durationI > 180){
            addToast('Invalid duration!', { appearance: "error" });
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
        bodyFormData.append('date', moment(selectedDate).valueOf() + totalMillisHourMin);
        bodyFormData.append('duration', durationI);
        api.post("http://localhost:8080/api/appointment/scheduleAppointment", bodyFormData)
            .then(
                () => {
                    addToast("Appointment scheduled!", { appearance: "success" });
                    props.onSchedule();
                })
            .catch((resp) => {addToast(resp.response.data, { appearance: "error" })});
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

                <Row className="justify-content-center m-3 align-items-center">
                    <Button onClick={() => scheduleAndFinish()}>Schedule</Button>
                </Row>

            </Modal.Body>
        </Modal>
    )
}

export default ScheduleAnotherApp
