import { combineReducers, configureStore } from '@reduxjs/toolkit';
import user from './slices/userSlice'

const reducer = combineReducers({
  user,
})

const store = configureStore({
  reducer,
});

export default store