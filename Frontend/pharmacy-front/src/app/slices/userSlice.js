import { createSlice } from "@reduxjs/toolkit";
import { Redirect } from "react-router";
import axios from "../api";

const initialUser = localStorage.getItem("user")
  ? JSON.parse(localStorage.getItem("user"))
  : null;

const slice = createSlice({
  name: "user",
  initialState: {
    user: initialUser,
  },
  reducers: {
    loginSuccess: (state, action) => {
      state.user = action.payload;
      localStorage.setItem("user", JSON.stringify(action.payload));
    },
    logoutSuccess: (state, action) => {
      state.user = null;
      localStorage.removeItem("user");
    },
  },
});

export default slice.reducer;

const { loginSuccess, logoutSuccess } = slice.actions;

export const login = ({ email, password }) => async (dispatch) => {
  try {
    const res = await axios.post("/api/auth/login/", {
      email,
      password,
    });
    dispatch(loginSuccess(res.data));
  } catch (e) {
    alert("User not found");
    return console.error(e.message);
  }
};

export const logout = () => async (dispatch) => {
  try {
    return dispatch(logoutSuccess());
  } catch (e) {
    return console.error(e.message);
  }
};
