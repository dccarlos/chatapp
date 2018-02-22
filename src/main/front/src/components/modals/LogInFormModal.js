"use strict";

import React, { isValidElement } from "react";
import { connect } from "react-redux";
import {
  FormGroup,
  ControlLabel,
  FormControl,
  Modal,
  Button
} from "react-bootstrap";
import {
  isValidUserName,
  isValidPassword,
  isValidEmail
} from "../../util/AppUtils";
import { showLogIn, closeLogIn } from "../../actions/actions";

let LogInFormModal = (function() {
  let EmailTextForm = (function() {
    return class EmailTextForm extends React.Component {
      constructor(props) {
        super(props);
        this.state = { email: "" };
        this.handleEmailChange = this.handleEmailChange.bind(this);
      }
      getLoginEmailValidationState() {
        if (isValidEmail(this.state.email)) return "success";
        else return "error";
      }
      handleEmailChange(e) {
        this.setState({
          email: e.target.value
        });
      }
      render() {
        return (
          <div>
            <FormGroup validationState={this.getLoginEmailValidationState()}>
              <ControlLabel>Email</ControlLabel>
              <FormControl
                type="email"
                value={this.state.email}
                placeholder="Enter email"
                onChange={this.handleEmailChange}
              />
              <FormControl.Feedback />
            </FormGroup>
          </div>
        );
      }
    };
  })();
  let NicknameTextForm = (function() {
    return class NicknameTextForm extends React.Component {
      constructor(props) {
        super(props);
        this.state = { nickname: "" };
        this.handleNicknameChange = this.handleNicknameChange.bind(this);
      }
      getLoginNicknameValidationState() {
        if (isValidUserName(this.state.nickname)) return "success";
        else return "error";
      }
      handleNicknameChange(e) {
        this.setState({
          nickname: e.target.value
        });
      }
      render() {
        return (
          <div>
            <FormGroup validationState={this.getLoginNicknameValidationState()}>
              <ControlLabel> Nickname</ControlLabel>
              <FormControl
                type="text"
                value={this.state.nickname}
                placeholder="Enter nickname"
                onChange={this.handleNicknameChange}
              />
              <FormControl.Feedback />
            </FormGroup>
          </div>
        );
      }
    };
  })();

  return class AppLogInModal extends React.Component {
    constructor(props) {
      super(props);
      this.onClickLogInModal = this.onClickLogInModal.bind(this);
      this.onClickCloseLogInModal = this.onClickCloseLogInModal.bind(this);
    }
    validateFieldsToSave(data) {
      if (
        isValidUserName(data.nickname) &&
        isValidEmail(data.mail)
      )
        return true;
      return false;
    }
    retrieveDataToLogInUser() {

      var mail = this.refs.EmailTextForm.state.email;
      var nickname = this.refs.NicknameTextForm.state.nickname;
      return {

        username: mail,
        password: nickname
      };
    }

    onClickLogInModal() {
      var url = "http://localhost:8080/user/resources/insert";
      var data = this.retrieveDataToLogInUser();

      if (!this.validateFieldsToSave(data)) {
        window.alert("Complete los campos de manera correcta");
        return;
      }

      fetch(url, {
        method: "POST",
        body: JSON.stringify(data),
        headers: new Headers({
          "Content-Type": "application/json"
        })
      })
        .then(response => {
          if (!response.ok) {
            this.logInErrorManager(response.status);
            throw Error(response.statusText);
          }
          return response;
        })
        .then(res => res.json())
        .then(response => {
          console.log("response:", response);
        })
        .catch(error => {
          console.error("Error:", error);
        });
      this.props.closeSignIn();
    }

    logInErrorManager(errorCode) {
      switch (errorCode) {
        case 400: {
          window.alert(
            "Bad request please check that you are sending the field in the right way"
          );
          break;
        }
        case 409: {
          window.alert("You send and already existing email");
          break;
        }
        case 500: {
          window.alert("Server error please try again or send us a message");
          break;
        }
        default: {
          window.alert("Unespected error occured");
        }
      }
    }

    onClickCloseLogInModal() {
      this.props.closeLogIn();
    }

    //show={this.props.showLogInModel.showLogInState}
    render() {
      console.log("render");
      return (
        <Modal show={this.props.showLogInModel.showLogInState}>
          <Modal.Header>
            <Modal.Title>LogIn Modal</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <form>
              <EmailTextForm ref="EmailTextForm" />
              <NicknameTextForm ref="NicknameTextForm" />
            </form>
          </Modal.Body>
          <Modal.Footer>
            <Button onClick={this.onClickCloseLogInModal}>Cancel</Button>
            <Button onClick={this.onClickLogInModal}>LogIn</Button>
          </Modal.Footer>
        </Modal>
      );
    }
  };
})();

const mapStateToProps = (state, ownProps) => ({
  showLogInModel: state.showLogInModel
});

const mapDispatchToProps = {
  showLogIn,
  closeLogIn
};

const AppContainer = connect(mapStateToProps, mapDispatchToProps)(
  LogInFormModal
);

export default AppContainer;
