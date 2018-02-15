// store.js

import { reducers } from "../reducers/reducer";
import { createStore } from "redux";

export function configureStore(initialState = {}) {
  const store = createStore(reducers);
  return store;
}
export const store = configureStore();
