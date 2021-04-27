import React from "react";
import "../styling/home_page.css";
import Login from "./users/Login";

function LoginPage() {
  return (
    <main className="home__page my__login__container">
      <Login></Login>
    </main>
  );
}

export default LoginPage;
