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
import { Base64 } from "js-base64";

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
                name="username"
                type="username"
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
                name="password"
                type="password"
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
      if (isValidUserName(data.password) && isValidEmail(data.username))
        return true;
      return false;
    }
    retrieveDataToLogInUser() {
      var mail = this.refs.EmailTextForm.state.email;
      var nickname = this.refs.NicknameTextForm.state.nickname;

      console.log("email" + mail + "nick" + nickname);

      return {
        username: mail,
        password: nickname
      };
    }

    onClickLogInModal() {
      var url = "http://localhost:8080/";
      var data = this.retrieveDataToLogInUser();

      if (!this.validateFieldsToSave(data)) {
        window.alert("Complete los campos de manera correcta");
        return;
      }

      //const basicAuth =
      //"Basic " + Base64.encode(data.username + ":" + data.password);
      var formData = new FormData();
      formData.append("username", data.username);
      formData.append("password", data.password);

      fetch(url, {
        method: "POST",
        credentials: "include",
        headers: new Headers({
          Accept:
            "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"
        }),
        body: formData
      })
        .then(response => {
          if (!response.ok) {
            this.logInErrorManager(response.status);
            throw Error(response.statusText);
          }
          return response;
        })
        .then(res => res.text())
        .then(response => {
          this.props.history.push("/chat");
        })
        .catch(error => {
          console.error("Error:", error);
        });
      this.props.closeLogIn();
      /*

      */
    }

    logInErrorManager(errorCode) {
      switch (errorCode) {
        case 400: {
          window.alert(
            "Bad request please check that you are sending the field in the right way"
          );
          break;
        }
        case 401: {
          window.alert("Not authorized");
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
            <EmailTextForm ref="EmailTextForm" />
            <NicknameTextForm ref="NicknameTextForm" />
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

/*
<Modal.Body>
<form action="/" method="POST">
  <EmailTextForm ref="EmailTextForm" />
  <NicknameTextForm ref="NicknameTextForm" />
  <Button type="submit">Form</Button>
  </form>
</Modal.Body>
*/
