import React, { useState, useEffect } from "react";
import axios from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";

function PatientPenalties() {
  const [penalties, setPenalties] = useState();

  useEffect(() => {
    async function fetchPenalties() {
      const request = await axios.get(
        "/api/patients/" + getIdFromToken() + "/penalties"
      ).catch(() => { });
      setPenalties(!!request ? request.data : null);

      return request;
    }
    fetchPenalties();
  }, {});

  return (
    <div>
      <h3 style={{ textAlign: "center" }}>Penalties</h3>
      <p style={{ textAlign: "center" }}>
        Achieved points: {penalties} out of 3
      </p>
      <p style={{ textAlign: "center" }}>
        Reservation functionality is disabled once you reach 3 penalties.
      </p>
      <p style={{ textAlign: "center" }}>
        Every first day of the month the penalties are set to 0.
      </p>
    </div>
  );
}

export default PatientPenalties;
