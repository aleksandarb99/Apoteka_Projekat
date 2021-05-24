import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import { getUserTypeFromToken } from "../../app/jwtTokenUtils";
import PharmacyAdminHomePage from "../pharmacyAdmin/PharmacyAdminHomePage";
import PharmHomePage from "../workers/pharmacist/home_page_pharmacist";
import DermHomePage from "../workers/dermatologist/home_page_dermatologist";
import UnregistredAndPatientHomePage from "../unregisteredAndPatient/UnregistredAndPatientHomePage";
import SupplierHomePage from "../supplier/SupplierHomePage";
import AdminHomePage from "./AdminHomePage";

const HomePage = () => {
  const user = useSelector((state) => state.user);
  let userType = getUserTypeFromToken();
  useEffect(() => {
    userType = getUserTypeFromToken();
  }, [user]);

  let homePage;
  switch (userType) {
    case "ADMIN":
      homePage = <AdminHomePage></AdminHomePage>;
      break;
    case "PHARMACIST":
      homePage = <PharmHomePage></PharmHomePage>;
      break;
    case "DERMATOLOGIST":
      homePage = <DermHomePage></DermHomePage>;
      break;
    case "SUPPLIER":
      homePage = <SupplierHomePage></SupplierHomePage>;
      break;
    case "PATIENT":
      homePage = (
        <UnregistredAndPatientHomePage></UnregistredAndPatientHomePage>
      );
      break;
    case "PHARMACY_ADMIN":
      homePage = <PharmacyAdminHomePage></PharmacyAdminHomePage>;
      break;
    default:
      homePage = (
        <UnregistredAndPatientHomePage></UnregistredAndPatientHomePage>
      );
      break;
  }
  return <div>{homePage}</div>;
};

export default HomePage;
