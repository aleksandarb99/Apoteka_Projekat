export function getIdFromToken() {
  try {
    let token = JSON.parse(localStorage.getItem("user")).token;
    let parsedToken = JSON.parse(atob(token.split(".")[1]));
    let id = parsedToken.id;
    return id;
  } catch (e) {
    return null;
  }
}

export function getUserTypeFromToken() {
  try {
    let token = JSON.parse(localStorage.getItem("user")).token;
    let parsedToken = JSON.parse(atob(token.split(".")[1]));
    let userType = parsedToken.userType;
    return userType;
  } catch (e) {
    return null;
  }
}
