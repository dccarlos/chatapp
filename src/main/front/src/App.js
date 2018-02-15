'use strict'
import React, { Component } from "react";
import { connect } from "react-redux";

import { activateGeod, closeGeod, showSignIn } from "./actions/actions";
import SingInFormModal from "./components/modals/SingInFormModal";
import { Panel, Button, ListGroup, ListGroupItem } from "react-bootstrap";

import "./App.css";

// App.js
export class App extends Component {
  render() {
    return (
      <div>
        <header>
          <Panel>
            <Button onClick={this.props.showSignIn}>
              SingIn
            </Button>
            <Button>LogIn</Button>
          </Panel>
        </header>
        <SingInFormModal/>

        <h1>{this.props.geod.title || "Hello World!"}</h1>
        {this.props.geod.title ? (
          <button onClick={this.props.closeGeod}>Exit Geod</button>
        ) : (
          <button
            onClick={() =>
              this.props.activateGeod({ title: "I am a geo dude!" })
            }
          >
            Click Me!
          </button>
        )}
      </div>
    );
  }
}

// AppContainer.js
const mapStateToProps = (state, ownProps) => ({
  geod: state.geod
});

const mapDispatchToProps = {
  activateGeod,
  closeGeod,
  showSignIn
};

const AppContainer = connect(mapStateToProps, mapDispatchToProps)(App);

export default AppContainer;
