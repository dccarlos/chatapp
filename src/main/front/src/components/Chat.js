import React from "react";
import {
  FormGroup,
  ControlLabel,
  FormControl,
  Modal,
  Button
} from "react-bootstrap";
import { Panel, Table, Grid, Row, Col } from "react-bootstrap";
import SimpleTextFormControl from "./SimpleTextFormControl";
import Stomp from "stompjs";
import SockJS from "sockjs-client";

const LogoOut = () => (
  <div>
    <h1>Chat window</h1>
    <form action="/logout" method="GET">
      <Button type="submit">LogOut</Button>
    </form>
  </div>
);

//export default LogOut;

let AppPanel = (function() {
  var serverUrl = "http://localhost:8080/socket";
  var title = "WebSockets chat";
  var stompClient;

  let MessageForm = (function() {
    return class NameTextForm extends React.Component {
      constructor(props) {
        super(props);

        this.state = { name: "" };
        this.handleNameChange = this.handleNameChange.bind(this);
      }
      getLoginNameValidationState() {
        return "success";
      }
      handleNameChange(e) {
        this.setState({ name: e.target.value });
      }
      render() {
        return (
          <div>
            <FormGroup validationState={this.getLoginNameValidationState()}>
              <ControlLabel>Name</ControlLabel>
              <FormControl
                type="text"
                value={this.state.name}
                placeholder="Enter name"
                onChange={this.handleNameChange}
              />
              <FormControl.Feedback />
            </FormGroup>
          </div>
        );
      }
    };
  })();

  return class AppPanel extends React.Component {
    constructor(props) {
      console.log("chatpanelconstructor");
      super(props);
      this.serverUrl = "http://localhost:8080/socket";
      this.title = "WebSockets chat";
      this.stompClient;

      this.initializeWebSocketConnection = this.initializeWebSocketConnection.bind(this);
      this.sendMessage = this.sendMessage.bind(this);
      this.printInConsole = this.printInConsole.bind(this);
      this.connect = this.connect.bind(this);
      this.append = this.append.bind(this);
      this.onResponse=this.onResponse.bind(this);
      this.initializeWebSocketConnection(this.onConnection, this.onResponse);

      this.state = {
        response: "",
        request: "",
        messages: [],
        isConnected: false
      };
    }

    connect() {
      this.initializeWebSocketConnection();
    }

    initializeWebSocketConnection(onConnectionCallback, onResponseCallback) {
      console.log(this.serverUrl);
      let ws = new SockJS(this.serverUrl);
      this.stompClient = Stomp.over(ws);
      let that = this;
      this.stompClient.connect({}, function(frame) {
        that.stompClient.subscribe("/chat", message => {
          if (message.body) {
            onResponseCallback(message);
            console.log("MessageReceived" + message.body);
          }
        });
      });
    }

    sendMessage(message) {
      this.stompClient.send("/app/send/message", {}, message);
    }
    printInConsole() {
      var message = this.refs.MessageForm.state.name;
      this.stompClient.send("/app/send/message", {}, message);
      console.log(this.refs.MessageForm.state.name);
    }
    onConnection() {}

    onResponse(message) {
      console.log("On response callback");
      console.log(message);
      var msg = { name:JSON.parse(message.body).name,text:JSON.parse(message.body).message , id: message.headers['message-id'] };
      console.log(msg)
      console.dir(msg)
      console.log("append");
      console.log(this)
      let messages = this.state.messages;
      this.setState({
        messages: messages.concat(msg)
      });
      console.log(this.state);
    }
    append() {
      //var msg = {text: JSON.parse(message.body).content, id: message.headers["message-id"]};
      var msg = { text: "text", id: 1 };
      console.log("append");
      let messages = this.state.messages;
      this.setState({
        messages: messages.concat(msg)
      });
      console.log(this.state);
    }

    render() {
      let messages = this.state.messages.map(message => {
        return (
          <tr key={message.id}>
          
            <td>{message.name}-{message.text}</td>
          </tr>
        );
      });

      let messageList = (
        <Panel>
          <div style={{ overflowY: "scroll", height: "500px" }}>
            <Table
              bordered
              condensed
              hover
              responsive
              striped
              style={{ wordWrap: "break-word", tableLayout: "fixed" }}
            >
              <tbody>
                <tr>
                  <th style={{ width: "100%" }}>Messages</th>
                </tr>
                {messages}
              </tbody>
            </Table>
          </div>
        </Panel>
      );

      return (
        <Grid fluid={true}>
          <LogoOut />
          <Row className="show-grid">
            <Col md={4} xs={9}>
              <Panel>
                <Button onClick={this.connect} disabled={false}>
                  Connect
                </Button>
                <Button onClick={this.append} disabled={false}>
                  Disconnect
                </Button>
                <SimpleTextFormControl readOnly={false} />
                <MessageForm ref="MessageForm" />
                <Button onClick={this.printInConsole} disabled={false}>
                  Send message
                </Button>
              </Panel>
            </Col>
            <Col md={8} xs={9}>
              {messageList}
            </Col>
          </Row>
        </Grid>
      );
    }
  };
})();

export default AppPanel;

/*
'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import {Panel, Button, Table, Grid, Row, Col} from 'react-bootstrap';
import SockJS from 'sockjs-client';

import {Stomp} from '../../../util/stomp';

import {SimpleTextFormControl} from '../util/ViewsUtils.react';

var stompClient = null;
var isConnected = false;

function initStompClient(onConnectionCallback, onMessageCallback) {
    if (stompClient == null || !isConnected) {
        console.log('Starting STOMP over SocksJS...');

        var socket = new SockJS('/sample-chat');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            console.log('STOMP Connection: ' + frame);

            stompClient.subscribe('/topic/chat', function (greeting) {
                onMessageCallback(greeting);
            });

            onConnectionCallback(true, onConnectionCallback);
        }, function (frame) {
            console.log('STOMP Disconnection error: ' + frame);
            onConnectionCallback(false, onConnectionCallback);
        });
    }

    return stompClient;
}

function disconnectStompClient(onConnectionCallback) {
    if (stompClient != null) {
        stompClient.disconnect(function () {
            console.log('STOMP Disconnection');

            onConnectionChange(false, onConnectionCallback);
        });
    }
}

function onConnectionChange(connected, callback) {
    isConnected = connected;
    callback(connected);
}

function sendMessage(message) {
    stompClient.send("/app/chat", {}, JSON.stringify({'content': message}));
}

let AppPanel = (function () {


    return class AppPanel extends React.Component {
        constructor(props) {
            super(props);

            this.state = {
                response: '',
                request: '',
                messages: [],
                isConnected: false
            };

            this.connectChat = this.connectChat.bind(this);
            this.disconnectChat = this.disconnectChat.bind(this);
            this.onChangeRequestMessage = this.onChangeRequestMessage.bind(this);
            this.handleEnterKeyPress = this.handleEnterKeyPress.bind(this);
            this.canExecuteRequest = this.canExecuteRequest.bind(this);
            this.executeRequest = this.executeRequest.bind(this);
            this.onResponseReceived = this.onResponseReceived.bind(this);
            this.setConnected = this.setConnected.bind(this);
        }

        connectChat() {
            initStompClient(
                this.setConnected,
                this.onResponseReceived
            );
        }

        disconnectChat() {
            disconnectStompClient((isConnected) => {
                this.setState({
                    isConnected: isConnected
                });
            });
        }

        setConnected(isConnected) {
            this.setState({
                isConnected: isConnected
            });
        }

        onChangeRequestMessage(e) {
            this.setState({
                request: e.target.value
            });
        }

        onResponseReceived(message) {
            var msg = {text: JSON.parse(message.body).content, id: message.headers["message-id"]};
            let messages = this.state.messages;

            this.setState({
                messages: messages.concat(msg)
            });
        }

        handleEnterKeyPress(e) {
            if (e.key === 'Enter') {
                this.executeRequest();
            }
        }

        canExecuteRequest() {
            return (this.state.isConnected && this.state.request && this.state.request.trim().length > 0);
        }

        executeRequest() {
            if (this.canExecuteRequest()) {
                sendMessage(this.state.request.trim());
                this.setState({
                    request: ''
                });
            }
        }

        componentWillMount() {
            this.connectChat();
        }

        render() {

            let messages = this.state.messages.map(message => {
                return (
                    <tr key={message.id}>
                        <td>{message.text}</td>
                    </tr>
                );
            });

            let messageList = (
                <Panel>
                    <div style={{overflowY:"scroll", height: "500px"}}>
                        <Table bordered condensed hover responsive striped
                               style={{wordWrap: "break-word", tableLayout: "fixed"}}>
                            <tbody>
                            <tr>
                                <th style={{width: "100%"}}>Messages</th>
                            </tr>
                            {messages}
                            </tbody>
                        </Table>
                    </div>
                </Panel>
            );

            return (
                <Grid fluid={true}>
                    <Row className="show-grid">
                        <Col md={4} xs={9}>
                            <Panel>
                                <Button onClick={this.connectChat} disabled={this.state.isConnected}>
                                    Connect
                                </Button>
                                <Button onClick={this.disconnectChat} disabled={!this.state.isConnected}>
                                    Disconnect
                                </Button>
                                <SimpleTextFormControl
                                    readOnly={!this.state.isConnected}
                                    field={this.state.request}
                                    placeholder="Send message"
                                    handleFieldChange={this.onChangeRequestMessage}
                                    onKeyPress={this.handleEnterKeyPress}
                                />
                                <Button onClick={this.executeRequest} disabled={!this.canExecuteRequest()}>
                                    Send message
                                </Button>
                            </Panel>
                        </Col>
                        <Col md={8} xs={9}>
                            {messageList}
                        </Col>
                    </Row>
                </Grid>
            );
        }
    }
}());

export default AppPanel;
*/
