import React from "react";
import {
  FormGroup,
  ControlLabel,
  FormControl,
  Modal,
  Button
} from "react-bootstrap";
import DatePicker from "react-datepicker";
import moment from "moment";

import "react-datepicker/dist/react-datepicker.css";

// CSS Modules, react-datepicker-cssmodules.css
// import 'react-datepicker/dist/react-datepicker-cssmodules.css';

class Example extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      startDate: moment()
    };
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(date) {
    this.setState({
      startDate: date
    });
  }

  render() {
    return (

      <FormGroup validationState={"success"}>
      <ControlLabel>Birthdate</ControlLabel>
      <DatePicker
        readOnly={true}
        selected={this.state.startDate}
        onChange={this.handleChange}
      />
      <FormControl.Feedback />
    </FormGroup>

    );
  }
}
export default Example;
