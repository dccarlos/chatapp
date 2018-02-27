import React from "react";
import {
  FormGroup,
  ControlLabel,
  FormControl,
  Modal,
  Button
} from "react-bootstrap";

const About = () => (
  <div>
    <h1>Chat window</h1>
    <form action="/logout" method="GET">
      <Button type="submit">LogOut</Button>
    </form>
  </div>
);

export default About;
