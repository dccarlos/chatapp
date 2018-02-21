// store.js

import { reducers } from "../reducers/reducer";
import { createStore } from "redux";
import createHistory from 'history/createBrowserHistory'

export const history = createHistory()


export function configureStore(initialState = {}) {
  const store = createStore(reducers);
  console.log('store')
  return store;
}
export const store = configureStore();
