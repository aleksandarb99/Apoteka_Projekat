import React from "react";
import UserInfo from "./UserInfoComponent";

import "../../styling/profile.css";

function UserProfile() {
  return (
    <main>
      <UserInfo title="Patient's information" col_width={6}></UserInfo>
    </main>
  );
}

export default UserProfile;
