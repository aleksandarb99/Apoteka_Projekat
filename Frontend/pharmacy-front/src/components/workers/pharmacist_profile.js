import React, { useState, useEffect } from "react";
import "../../styling/profile.css";
import { Row, Col} from "react-bootstrap";
import { getIdFromToken } from "../../app/jwtTokenUtils";
import BasicProfileInfo from "../profile/BasicProfileInfo";
import api from "../../app/api";
import { useToasts } from "react-toast-notifications";
import "../../styling/worker.css";

function PharmacistProfile() {
  const [workplace, setWorkplace] = useState({});
  const { addToast } = useToasts();

  useEffect(() => {
      let id = getIdFromToken();
      if (!id){
          addToast("Token error!", { appearance: "error" });
          return;
      }
      api.get("http://localhost:8080/api/workplace/pharmacist/get_workplace/" + id)
        .then((resp)=>{
              setWorkplace(resp.data);})
        .catch(()=>setWorkplace({}));
  }, [])

  return (
    <div className="my__container" style={{minHeight: "100vh"}}>
      <Row className="justify-content-center pt-5 align-items-center">
        <BasicProfileInfo title={"Pharmacist profile"}></BasicProfileInfo>
      </Row>

      <Row className="justify-content-center mt-5 ml-5 mr-5 pb-5 align-items-center">
            <Col md={4} className="card_appt_home" style={{backgroundColor: 'white'}}>
                <Row className="justify-content-center m-5 align-items-center">
                    <h3 className="my_content_header" style={{textAlign: 'center'}}>Working hours: {workplace ? workplace.pharmacy : ""}</h3>
                </Row>
                {(workplace?.workDayList ? workplace.workDayList : []).map((value, index) => {
                    return (
                        <Row className="justify-content-center m-2 align-items-center p-2" style={{backgroundColor: "#83CEC2"}}>
                            <div>{value.weekday}: {value.startTime} - {value.endTime}h</div>
                        </Row>
                        );
                })}
            </Col>
        </Row>
    </div>
  );
}

export default PharmacistProfile;
