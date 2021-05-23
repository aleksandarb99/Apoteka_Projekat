import React, { useState, useEffect } from "react";
import "../../styling/profile.css";
import {  Form, Row, Col, InputGroup } from "react-bootstrap";
import { getIdFromToken } from "../../app/jwtTokenUtils";
import BasicProfileInfo from "../profile/BasicProfileInfo";
import api from "../../app/api";
import { useToasts } from "react-toast-notifications";

function DermatologistProfile() {
    const [workplaces, setWorkplaces] = useState([]);
    const [currSelect, setCurrSelect] = useState(0);
    const { addToast } = useToasts();

    useEffect(() => {
        let id = getIdFromToken();
        if (!id){
            addToast("No token, error!", { appearance: "error" });
            return;
        }
        api.get("http://localhost:8080/api/workplace/dermatologist/get_workplaces/" + id).then((resp)=>{
            setWorkplaces(resp.data);
            console.log(workplaces);
            if (workplaces.length > 0){
                setCurrSelect(0);  //selectujemo ih po indeksu
            }
        }).catch(()=>setWorkplaces([]));
    }, [])

    return (
    <div>
        <Row className="justify-content-center m-5 align-items-center">
            <BasicProfileInfo title={"Dermatologist profile"}></BasicProfileInfo>
        </Row>
        <Row className="justify-content-center m-5 align-items-center">
            <Col md={4}>
                <Row className="justify-content-center m-5 align-items-center">
                    <h3>Working hours</h3>
                </Row>
                <Row className="justify-content-center m-5 align-items-center">
                    <InputGroup>
                        <InputGroup.Prepend>
                            <InputGroup.Text>Select pharmacy:</InputGroup.Text>
                        </InputGroup.Prepend>
                        <Form.Control as="select" 
                            defaultValue={0}
                            value={currSelect} 
                            onChange={(event) => setCurrSelect(event.target.value)}>
                            {workplaces.map((value, index) => {
                                return <option value={index}>{value.pharmacy}</option>;
                            })}
                        </Form.Control>
                    </InputGroup>
                </Row>
                {(workplaces[currSelect]?.workDayList ? workplaces[currSelect].workDayList : []).map((value, index) => {
                    return (
                        <Row className="justify-content-center m-5 align-items-center" style={{backgroundColor: "#83CEC2"}}>
                            <div>{value.weekday}: {value.startTime} - {value.endTime}h</div>
                        </Row>
                        );
                })}
            </Col>
        </Row>
    </div>
    );
}

export default DermatologistProfile;
