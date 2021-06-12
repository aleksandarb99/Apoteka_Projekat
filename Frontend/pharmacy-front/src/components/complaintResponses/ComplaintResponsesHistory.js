import React, { useEffect, useState } from "react";
import api from "../../app/api";
import { getIdFromToken } from "../../app/jwtTokenUtils";
import ComplaintResponseDetails from "./ComplaintResponseDetails";

const ComplaintResponsesHistory = () => {
  const [complaintResponses, setComplaintResponses] = useState([]);

  useEffect(() => {
    async function fetchComplaints() {
      api
        .get(`/api/complaint-responses/admin/${getIdFromToken()}`)
        .then((res) => {
          setComplaintResponses(res.data);
        });
    }
    fetchComplaints();
  }, []);

  return (
    <div>
      {complaintResponses &&
        complaintResponses.map((cr) => {
          return (
            <ComplaintResponseDetails
              key={cr.id}
              complaintResponse={cr}
            ></ComplaintResponseDetails>
          );
        })}
    </div>
  );
};

export default ComplaintResponsesHistory;
