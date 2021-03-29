import React, { useState, useEffect } from "react";
import "../styling/profile.css";
import { Button, Form, Alert, Container, Row, Col } from "react-bootstrap";
import axios from "axios";
import UserInfo from "./UserInfoComponent";

function UserProfile() {
  
  return (
    <main>
      <UserInfo title="Patient's information" col_width={6}></UserInfo>
    </main>
  );
}

export default UserProfile;
