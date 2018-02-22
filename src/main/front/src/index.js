
"use strict";
import "bootstrap/dist/css/bootstrap.css";
import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import { Provider } from "react-redux";
import { Route, Switch } from 'react-router-dom';
import { ConnectedRouter } from "react-router-redux";
import { store,history } from "./store/store";
import { Router,browserHistory } from "react-router";
import { syncHistoryWithStore, routerReducer } from "react-router-redux";
import About from "./components/About";

ReactDOM.render(
  <Provider store={store}>
    <ConnectedRouter history={history}>
      <Switch>
        <Route exact path="/" component={App} />
        <Route exact path="/chat" component={About} />
      </Switch>
    </ConnectedRouter>
  </Provider>,
  document.getElementById("root")
);
