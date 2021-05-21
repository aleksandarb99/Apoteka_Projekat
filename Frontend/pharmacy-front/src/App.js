import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";

import LoginPage from "./components/LoginPage";
import Registration from "./components/Registration";
import HomePage from "./components/utilComponents/HomePage";
import PharmacyAdminHomePage from "./components/pharmacyAdmin/PharmacyAdminHomePage";
import UserProfile from "./components/profile/UserProfile";
import Footer from "./components/Footer";
import PharmacyProfile from "./components/pharmacyProfile/PharmacyProfile";
import MedicineProfile from "./components/medicine/MedicineProfile";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import DermHomePage from "./components/workers/dermatologist/home_page_dermatologist";
import PharmHomePage from "./components/workers/pharmacist/home_page_pharmacist";
import PharmacyCrud from "./components/pharmacy/PharmacyCrud";
import MedicineCrud from "./components/medicine/MedicineCrud";
import SearchPatPage from "./components/workers/search_patients";
import SearchExaminedPatPage from "./components/workers/list_examined";
import CommonHeader from "./components/utilComponents/header/CommonHeader";
import BasicProfileInfo from "./components/profile/BasicProfileInfo";

import WorkCalendar from "./components/workers/work_calendar";
import AppointmentReport from "./components/workers/appointment_report";
import VacationRequest from "./components/workers/vacation_request";
import DermatologistProfile from "./components/workers/dermatologist_profile";
import PharmacistProfile from "./components/workers/pharmacist_profile";
import IssueMedicine from "./components/workers/issue_medicine";

import UserCrud from "./components/users/UserCrud";
import PharmaciesWithFreePharmacists from "./components/unregisteredAndPatient/PharmaciesWithFreePharmacists";
import ConsultationsInsight from "./components/unregisteredAndPatient/ConsultationsInsight";
import CheckupsInsight from "./components/unregisteredAndPatient/CheckupsInsight";
import SubscribedPharmacies from "./components/unregisteredAndPatient/SubscribedPharmacies";
import ReservedMedicines from "./components/unregisteredAndPatient/ReservedMedicines";
import Rating from "./components/unregisteredAndPatient/Rating";
import ComplaintsPage from "./components/complaints/ComplaintsPage";
import ComplaintResponsesPage from "./components/complaintResponses/ComplaintResponsesPage";
import EPrescriptionPage from "./components/ePrescription/EPrescriptionPage";
import CategotyList from "./components/loyaltyProgram/CategoryList";

import "./styling/navbar.css";

function App() {
  return (
    <Router>
      <div style={{ minHeight: "100vh" }}>
        <CommonHeader />
        <Switch>
          <Route path="/" exact component={HomePage} />
          <Route path="/profile" exact component={UserProfile} />
          <Route path="/infoProfile" exact component={BasicProfileInfo} />
          <Route
            path="/reserve-consultation/pharmacies"
            component={PharmaciesWithFreePharmacists}
          />
          <Route
            path="/consultations-insight"
            component={ConsultationsInsight}
          />
          <Route
            path="/subscribed-pharmacies"
            component={SubscribedPharmacies}
          />
          <Route path="/rating" component={Rating} />
          <Route path="/reserved-medicines" component={ReservedMedicines} />
          <Route path="/checkups-insight" component={CheckupsInsight} />
          <Route path="/registration" component={Registration} />
          <Route path="/login" component={LoginPage} />
          <Route path="/pharmacy/:id" component={PharmacyProfile} />
          <Route
            path="/medicine/:id/pharmacy/:pid/price/:priceid"
            component={MedicineProfile}
          />
          <Route path="/dermatologist" component={DermHomePage} />
          <Route path="/pharmacist" component={PharmHomePage} />
          <Route path="/pharmacyAdmin" component={PharmacyAdminHomePage} />
          <Route path="/admin/pharmacies" component={PharmacyCrud} />
          <Route path="/admin/medicine" component={MedicineCrud} />
          <Route path="/worker/search-patients" component={SearchPatPage} />
          <Route
            path="/worker/dermatologist_profile"
            component={DermatologistProfile}
          />
          <Route
            path="/worker/pharmacist_profile"
            component={PharmacistProfile}
          />
          <Route path="/admin/users" component={UserCrud} />
          <Route path="/worker/examined" component={SearchExaminedPatPage} />
          <Route path="/worker/calendar" component={WorkCalendar} />
          <Route
            path="/worker/appointment_report"
            component={AppointmentReport}
          />
          <Route path="/worker/vacation" component={VacationRequest} />
          <Route path="/worker/issue_medicine" component={IssueMedicine} />
          <Route path="/user/complaints" component={ComplaintsPage} />
          <Route
            path="/admin/complaint-responses"
            component={ComplaintResponsesPage}
          />
          <Route path="/e-prescription" component={EPrescriptionPage} />
          <Route path="/admin/loyalty" component={CategotyList} />
        </Switch>
      </div>
      <Footer />
    </Router>
  );
}

export default App;
