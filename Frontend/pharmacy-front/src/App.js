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
import './styling/modal.css'

import { ToastProvider } from "react-toast-notifications";

import GuardedRoute from "./components/utilComponents/GuardedRoute";

function App() {
  return (
    <ToastProvider
      autoDismiss="true"
      autoDismissTimeout="4000"
      placement="top-center"
    >
      <Router>
        <div style={{ minHeight: "100vh" }}>
          <CommonHeader />
          <Switch>
            <Route path="/" exact component={HomePage} />

            {/* null znaci da moze i neregistrovani korisnik */}
            <GuardedRoute
              path="/registration"
              component={Registration}
              userType={[null]}
            />

            <GuardedRoute
              path="/login"
              component={LoginPage}
              userType={[null]}
            />
            <GuardedRoute
              path="/pharmacy/:id"
              component={PharmacyProfile}
              userType={[null, "PATIENT", "PHARMACY_ADMIN", "ADMIN", "DERMATOLOGIST", "PHARMACIST"]}
            />
            <GuardedRoute
              path="/medicine/:id/pharmacy/:pid/price/:priceid"
              component={MedicineProfile}
              userType={[null, "PATIENT", "PHARMACY_ADMIN", "ADMIN", "DERMATOLOGIST", "PHARMACIST"]}
            />

            <GuardedRoute
              path="/profile"
              exact
              component={UserProfile}
              userType={["PATIENT"]}
            />

            <GuardedRoute
              path="/infoProfile"
              exact
              component={BasicProfileInfo}
              userType={[
                "ADMIN",
                "PHARMACIST",
                "DERMATOLOGIST",
                "SUPPLIER",
                "PHARMACY_ADMIN",
              ]}
            />

            <GuardedRoute
              path="/reserve-consultation/pharmacies"
              component={PharmaciesWithFreePharmacists}
              userType={["PATIENT"]}
            />

            <GuardedRoute
              path="/consultations-insight"
              component={ConsultationsInsight}
              userType={["PATIENT"]}
            />

            <GuardedRoute
              path="/subscribed-pharmacies"
              component={SubscribedPharmacies}
              userType={["PATIENT"]}
            />

            <GuardedRoute
              path="/rating"
              component={Rating}
              userType={["PATIENT"]}
            />

            <GuardedRoute
              path="/reserved-medicines"
              component={ReservedMedicines}
              userType={["PATIENT"]}
            />

            <GuardedRoute
              path="/checkups-insight"
              component={CheckupsInsight}
              userType={["PATIENT"]}
            />

            {/* Ne treba nam ovo jer gore imamo home page koji koristi sve ove */}
            {/* <Route path="/dermatologist" component={DermHomePage} />
            <Route path="/pharmacist" component={PharmHomePage} />
            <Route path="/pharmacyAdmin" component={PharmacyAdminHomePage} /> */}

            <GuardedRoute
              path="/admin/pharmacies"
              component={PharmacyCrud}
              userType={["ADMIN"]}
            />
            <GuardedRoute
              path="/admin/medicine"
              component={MedicineCrud}
              userType={["ADMIN"]}
            />

            <GuardedRoute
              path="/worker/search-patients"
              component={SearchPatPage}
              userType={["DERMATOLOGIST", "PHARMACIST"]}
            />
            <GuardedRoute
              path="/worker/dermatologist_profile"
              component={DermatologistProfile}
              userType={["DERMATOLOGIST"]}
            />
            <GuardedRoute
              path="/worker/pharmacist_profile"
              component={PharmacistProfile}
              userType={["PHARMACIST"]}
            />
            <GuardedRoute
              path="/admin/users"
              component={UserCrud}
              userType={["ADMIN"]}
            />
            <GuardedRoute
              path="/worker/examined"
              component={SearchExaminedPatPage}
              userType={["DERMATOLOGIST", "PHARMACIST"]}
            />
            <GuardedRoute
              path="/worker/calendar"
              component={WorkCalendar}
              userType={["DERMATOLOGIST", "PHARMACIST"]}
            />
            <GuardedRoute
              path="/worker/appointment_report"
              component={AppointmentReport}
              userType={["DERMATOLOGIST", "PHARMACIST"]}
            />
            <GuardedRoute
              path="/worker/vacation"
              component={VacationRequest}
              userType={["DERMATOLOGIST", "PHARMACIST"]}
            />
            <GuardedRoute
              path="/worker/issue_medicine"
              component={IssueMedicine}
              userType={["DERMATOLOGIST", "PHARMACIST"]}
            />
            <GuardedRoute
              path="/user/complaints"
              component={ComplaintsPage}
              userType={["PATIENT"]}
            />
            <GuardedRoute
              path="/admin/complaint-responses"
              component={ComplaintResponsesPage}
              userType={["ADMIN"]}
            />
            <GuardedRoute
              path="/e-prescription"
              component={EPrescriptionPage}
              userType={["PATIENT"]}
            />
            <GuardedRoute
              path="/admin/loyalty"
              component={CategotyList}
              userType={["ADMIN"]}
            />
          </Switch>
        </div>
        <Footer />
      </Router>
    </ToastProvider>
  );
}

export default App;
