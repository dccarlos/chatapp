"use strict";
import "bootstrap/dist/css/bootstrap.css";
import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import { Provider } from "react-redux";
import { ConnectedRouter } from 'react-router-redux'
import { store} from "./store/store";
import { Router, Route, browserHistory } from 'react-router'
import { syncHistoryWithStore, routerReducer } from 'react-router-redux'
import About from "./components/About"




const history = syncHistoryWithStore(browserHistory, store)


//App />
ReactDOM.render(

  <Provider store={store}>
  
  </Provider>,
  document.getElementById("root")
);
