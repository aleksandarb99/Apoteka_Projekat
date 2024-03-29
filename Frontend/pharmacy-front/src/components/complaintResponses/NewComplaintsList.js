import React, { useEffect, useState } from "react";
import ComplaintResponse from "./ComplaintResponse";
import api from "../../app/api";

const NewComplaintsList = () => {
  const [complaints, setComplaints] = useState([]);
  const [refresh, setRefresh] = useState(false);

  useEffect(() => {
    getNewComplaints();
  }, []);

  const getNewComplaints = () => {
    async function fetchComplaints() {
      api.get(`/api/complaints/new`).then((res) => {
        setComplaints(res.data);
      }).catch(() => { });
    }
    fetchComplaints();
  };

  return (
    <div>
      {complaints &&
        complaints.map((c) => {
          return (
            <ComplaintResponse
              key={c.id}
              complaint={c}
              onSuccessfulSubmit={getNewComplaints}
            ></ComplaintResponse>
          );
        })}
    </div>
  );
};

export default NewComplaintsList;
