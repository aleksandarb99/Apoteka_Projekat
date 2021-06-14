import axios from "axios";

const api = axios.create({
  baseURL: "https://apotekaprojekat.herokuapp.com",
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use(
  (config) => {
    let token = null;
    try {
      token = JSON.parse(localStorage.getItem("user")).token;
    } catch (e) {}
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default api;
