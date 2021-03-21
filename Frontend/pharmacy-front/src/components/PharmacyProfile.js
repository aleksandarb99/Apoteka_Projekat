import React from "react";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "bootstrap/dist/css/bootstrap.min.css";

function PharmacyProfile() {
  const [getPharmacyDetails, setPharmacyDetails] = useState({});

  let { id } = useParams();

  useEffect(() => {
    async function fetchPharmacy() {
      const request = await axios.get(
        `http://localhost:8080/api/pharmacy/${id}`
      );
      setPharmacyDetails(request.data);

      return request;
    }
    fetchPharmacy();
  }, []);

  return (
    <main>
      <p>Ovo je profil apoteke</p>
    </main>
  );
}

export default PharmacyProfile;
