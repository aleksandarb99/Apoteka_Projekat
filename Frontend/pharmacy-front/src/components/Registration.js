import React, { useRef, useState } from "react";
import { Button, Form } from "react-bootstrap";
import "../styling/home_page.css";
import FirstNameFormGroup from "./utilComponents/formGroups/FirstNameFormGroup";
import LastNameFormGroup from "./utilComponents/formGroups/LastNameFormGroup";
import EmailFormGroup from "./utilComponents/formGroups/EmailFormGroup";
import PasswordFormGroup from "./utilComponents/formGroups/PasswordFormGroup";
import PhoneNumberFormGroup from "./utilComponents/formGroups/PhoneNumberFormGroup";
import CityFormGroup from "./utilComponents/formGroups/CityFormGroup";
import CountryFormGroup from "./utilComponents/formGroups/CountryFormGroup";
import StreetFormGroup from "./utilComponents/formGroups/StreetFormGroup";

import api from "../app/api";
import { Redirect } from "react-router";
import { useToasts } from 'react-toast-notifications'
import { getErrorMessage } from "../app/errorHandler";
import Validator from "../app/validator";

function Registration() {
  const [form, setForm] = useState(
    {
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      repeatPassword: '',
      telephone: '',
      city: '',
      street: '',
      country: ''
    }
  );

  const resetForm = () => {
    setForm(
      {
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        repeatPassword: '',
        telephone: '',
        city: '',
        street: '',
        country: ''
      }
    )
    formRef.current.reset();
  }

  const { addToast } = useToasts();
  const formRef = useRef(null);

  const setField = (field, value) => {
    setForm({
      ...form,
      [field]: value,
    });
  };

  const validateForm = () => {
    return Validator['firstName'](form['firstName'])
      && Validator['lastName'](form['lastName'])
      && Validator['email'](form['email'])
      && Validator['password'](form['password'])
      && Validator['password'](form['repeatPassword'])
      && Validator['telephone'](form['telephone'])
      && Validator['city'](form['city'])
      && Validator['street'](form['street'])
      && Validator['country'](form['country'])
  }

  const handleSubmit = (event) => {
    event.preventDefault();
    event.stopPropagation();

    if (validateForm()) {
      if (form['password'] !== form['repeatPassword']) {
        addToast("Passwords do not match", { appearance: 'warning' })
        return;
      }
      sendPostRequest();
    }
  };

  const sendPostRequest = () => {
    const newForm = convertForm(form);
    api
      .post("http://localhost:8080/api/users/", newForm)
      .then(() => {
        resetForm();
        addToast("Successfully registred. Please confirm your email.", { appearance: 'success' });
        return <Redirect
          to={{
            pathname: "/login",
          }}
        />
      })
      .catch((err) => {
        addToast(getErrorMessage(err), { appearance: 'error' })
      });
  };

  const convertForm = () => {
    let address = {
      city: form["city"],
      street: form["street"],
      country: form["country"],
    };

    let newForm = {
      ...form,
      address: address,
      roleName: "PATIENT",
    };

    delete newForm["city"];
    delete newForm["street"];
    delete newForm["country"];

    return newForm;
  };

  return (
    <main className="home__page my__login__container">
      <Form
        ref={formRef}
        noValidate
        onSubmit={handleSubmit}
        className="my__login__form"
      >
        <FirstNameFormGroup
          value={form['firstName']}
          onChange={(event) => setField("firstName", event.target.value)}
        />
        <LastNameFormGroup
          onChange={(event) => setField("lastName", event.target.value)}
        />
        <EmailFormGroup
          onChange={(event) => setField("email", event.target.value)}
        ></EmailFormGroup>
        <PasswordFormGroup
          onChange={(event) => setField("password", event.target.value)}
        ></PasswordFormGroup>
        <PasswordFormGroup
          name="Repeat password" onChange={(event) => setField("repeatPassword", event.target.value)}
        ></PasswordFormGroup>
        <PhoneNumberFormGroup
          onChange={(event) => setField("telephone", event.target.value)}
        ></PhoneNumberFormGroup>
        <CityFormGroup
          onChange={(event) => setField("city", event.target.value)}
        ></CityFormGroup>
        <StreetFormGroup
          onChange={(event) => setField("street", event.target.value)}
        ></StreetFormGroup>
        <CountryFormGroup
          onChange={(event) => setField("country", event.target.value)}
        ></CountryFormGroup>
        <Button variant="primary" type="submit">
          Submit
        </Button>
      </Form>
      {" "}
    </main >
  );
}

export default Registration;
