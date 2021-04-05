import React, { useState } from "react";
import { Button, Form } from "react-bootstrap";
import "../styling/unregistered.css";
import FirstNameFormGroup from "./utilComponents/formGroups/FirstNameFormGroup"
import LastNameFormGroup from "./utilComponents/formGroups/LastNameFormGroup"
import EmailFormGroup from "./utilComponents/formGroups/EmailFormGroup"
import PasswordFormGroup from "./utilComponents/formGroups/PasswordFormGroup"
import PhoneNumberFormGroup from "./utilComponents/formGroups/PhoneNumberFormGroup"
import CityFormGroup from "./utilComponents/formGroups/CityFormGroup"
import CountryFormGroup from "./utilComponents/formGroups/CountryFormGroup"
import StreetFormGroup from "./utilComponents/formGroups/StreetFormGroup"

import axios from "axios";
import { useSelector } from "react-redux";
import { Redirect } from "react-router";

function Registration() {
  const [form, setForm] = useState({})
  const [validated, setValidated] = useState(false)
  const user = useSelector(state => state.user)

  const setField = (field, value) => {
    setForm({
      ...form,
      [field]: value
    })
  }

  const handleSubmit = (event) => {
    event.preventDefault()
    event.stopPropagation()

    const f = event.currentTarget;

    if (f.checkValidity() === true) {
      setValidated(true)
      sendPostRequest()
    }
  }

  const sendPostRequest = () => {
    const newForm = convertForm(form);
    console.log(newForm)
    axios
      .post('http://localhost:8080/api/users/', newForm)
      .then(() => {
        setForm({})
        alert('User added successfully')
      })
      .catch(() => {
        alert('Server error')
      })
  }

  const convertForm = () => {
    let address = {
      'city': form['city'],
      'street': form['street'],
      'country': form['country'],
    }

    let newForm = {
      ...form,
      'address': address,
      'userType': "PATIENT"
    }

    delete newForm['city']
    delete newForm['street']
    delete newForm['country']

    return newForm
  }

  if (user.user) {
    return <Redirect to='/' />
  }

  return (
    <main>
      <Form noValidate validated={validated} onSubmit={handleSubmit}>
        <FirstNameFormGroup onChange={(event) => setField('firstName', event.target.value)} />
        <LastNameFormGroup onChange={(event) => setField('lastName', event.target.value)} />
        <EmailFormGroup onChange={(event) => setField('email', event.target.value)}></EmailFormGroup>
        <PasswordFormGroup onChange={(event) => setField('password', event.target.value)}></PasswordFormGroup>
        <PhoneNumberFormGroup onChange={(event) => setField('telephone', event.target.value)}></PhoneNumberFormGroup>
        <CityFormGroup onChange={(event) => setField('city', event.target.value)}></CityFormGroup>
        <StreetFormGroup onChange={(event) => setField('street', event.target.value)}></StreetFormGroup>
        <CountryFormGroup onChange={(event) => setField('country', event.target.value)}></CountryFormGroup>
        <Button variant="primary" type="submit">Submit</Button>
      </Form>
    </main>
  )

}

export default Registration;
