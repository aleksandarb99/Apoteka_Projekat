function getIdFromToken() {
  let token = JSON.parse(localStorage.getItem("user")).token;

  try {
    let parsedToken = JSON.parse(atob(token.split(".")[1]));
    let id = parsedToken.id;
    return id;
  } catch (e) {
    return null;
  }
}

export default getIdFromToken;
