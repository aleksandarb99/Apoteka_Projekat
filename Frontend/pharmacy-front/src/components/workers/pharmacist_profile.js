import React, { useState, useEffect } from "react";
import "../../styling/profile.css";
import { Row, Col} from "react-bootstrap";
import { getIdFromToken } from "../../app/jwtTokenUtils";
import BasicProfileInfo from "../profile/BasicProfileInfo";
import api from "../../app/api";
import { useToasts } from "react-toast-notifications";

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
    <div>
      <Row className="justify-content-center m-5 align-items-center">
        <BasicProfileInfo title={"Pharmacist profile"}></BasicProfileInfo>
      </Row>

      <Row className="justify-content-center m-5 align-items-center">
            <Col md={4}>
                <Row className="justify-content-center m-5 align-items-center">
                    <h3>Working hours: {workplace ? workplace.pharmacy : ""}</h3>
                </Row>
                {(workplace?.workDayList ? workplace.workDayList : []).map((value, index) => {
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

export default PharmacistProfile;
