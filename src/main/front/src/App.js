"use strict";
import React, { Component } from "react";
import { Link } from "react-router-dom";
import { connect } from "react-redux";

import { showSignIn } from "./actions/actions";
import SingInFormModal from "./components/modals/SingInFormModal";
import { Panel, Button, ListGroup, ListGroupItem } from "react-bootstrap";
import About from "./components/About"
import { Routes,Route} from 'react-router'

import "./App.css";

export class App extends Component {
  render() {
    return (
      <div>
        <header>
          <Panel>
            <Link to="/">Home</Link>
            <Link to="/about">About</Link>
            <Button onClick={this.props.showSignIn}>SingIn</Button>
            <Button>LogIn</Button>
          </Panel>
        </header>
        <Route exact path="/" component={About} />
        <Route exact path="/about" component={About} />
        <SingInFormModal />
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => ({
  shoModal: state.showModal
});

const mapDispatchToProps = {
  showSignIn
};

const AppContainer = connect(mapStateToProps, mapDispatchToProps)(App);
export default AppContainer;
