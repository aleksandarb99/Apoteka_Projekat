export function getIdFromToken() {
  let token = JSON.parse(localStorage.getItem("user")).token;

  try {
    let parsedToken = JSON.parse(atob(token.split(".")[1]));
    let id = parsedToken.id;
    return id;
  } catch (e) {
    return null;
  }
}

export function getUserTypeFromToken() {
  if (JSON.parse(localStorage.getItem("user")) == null) return null;
  let token = JSON.parse(localStorage.getItem("user")).token;
  try {
    let parsedToken = JSON.parse(atob(token.split(".")[1]));
    let userType = parsedToken.userType;
    return userType;
  } catch (e) {
    return null;
  }
}
