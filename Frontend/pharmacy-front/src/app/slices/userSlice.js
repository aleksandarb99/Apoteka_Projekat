import { createSlice } from "@reduxjs/toolkit";
import axios from "../api";
import { useToasts } from 'react-toast-notifications'
import { getErrorMessage } from '../errorHandler'

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
    const res = await axios.post("http://localhost:8080/api/auth/login/", {
      email,
      password,
    });
    dispatch(loginSuccess(res.data));
  } catch (err) {
    return err;
  }
};

export const logout = () => async (dispatch) => {
  try {
    return dispatch(logoutSuccess());
  } catch (e) {
    return console.error(e.message);
  }
};
