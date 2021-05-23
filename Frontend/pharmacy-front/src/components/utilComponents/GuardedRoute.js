import { React, useEffect } from "react";
import { Route, Redirect } from "react-router-dom";
import { getUserTypeFromToken } from "../../app/jwtTokenUtils.js";

import { useToasts } from "react-toast-notifications";

const GuardedRoute = ({ component: Component, userType, ...rest }) => {
  const { addToast } = useToasts();
  useEffect(() => {
    if (userType != undefined) {
      if (!userType.includes(getUserTypeFromToken())) {
        addToast("Unauthorized!", {
          appearance: "error",
        });
      }
    }
  }, [userType]);

  return (
    <Route
      {...rest}
      render={(props) =>
        userType.includes(getUserTypeFromToken()) ? (
          <Component {...props} />
        ) : (
          <Redirect to="/" />
        )
      }
    />
  );
};

export default GuardedRoute;
