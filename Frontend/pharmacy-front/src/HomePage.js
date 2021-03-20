import React, { useState, useEffect } from "react";
import MedicineComponent from "./MedicineComponent";
import "./styling/unregistered.css";
import axios from "axios";

function HomePage() {
  const [medicines, setMedicines] = useState([]);

  useEffect(() => {
    async function fetchMedicines() {
      const request = await axios.get("http://localhost:8080/api/medicine/");
      setMedicines(request.data);

      return request;
    }
    fetchMedicines();
  }, []);

  return (
    <main>
      <div className="row">
        <p>Pharmacies</p>
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
