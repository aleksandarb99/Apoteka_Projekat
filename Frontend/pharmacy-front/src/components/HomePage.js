import React, { useState, useEffect } from "react";
import MedicineComponent from "./MedicineComponent";
import PharmacyComponent from "./PharmacyComponent";
import "./styling/unregistered.css";
import axios from "axios";

function HomePage() {
  const [medicines, setMedicines] = useState([]);
  const [pharmacies, setPharmacies] = useState([]);

  useEffect(() => {
    async function fetchMedicines() {
      const request = await axios.get("http://localhost:8080/api/medicine/");
      setMedicines(request.data);

      return request;
    }
    fetchMedicines();
  }, []);

  useEffect(() => {
    async function fetchPharmacies() {
      const request = await axios.get("http://localhost:8080/api/pharmacy/");
      setPharmacies(request.data);

      return request;
    }
    fetchPharmacies();
  }, []);

  return (
    <main>
      <div className="row">
        <p>Pharmacies</p>
        <div className="card_row">
          {pharmacies.map((pharmacy) => (
            <PharmacyComponent
              key={pharmacy.id}
              name={pharmacy.name}
              location={pharmacy.location}
              description={pharmacy.description}
              avgGrade={pharmacy.avgGrade}
            />
          ))}
        </div>
      </div>
      <div className="row">
        <p>Medicines</p>
        <div className="card_row">
          {medicines.map((medicine) => (
            <MedicineComponent
              key={medicine.id}
              name={medicine.name}
              code={medicine.code}
              content={medicine.content}
              avgGrade={medicine.avgGrade}
            />
          ))}
        </div>
      </div>
    </main>
  );
}

export default HomePage;
