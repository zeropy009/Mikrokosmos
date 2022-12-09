import React, { Component } from "react";
import {
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  FormGroup,
} from "reactstrap";
import {
  NotificationContainer,
  NotificationManager,
} from "react-notifications";
import axios from "axios";
import "react-notifications/lib/notifications.css";
import { path_variable } from "../../path_variable";
import { FormWithConstraints } from "react-form-with-constraints";
import {
  FormControlLabel,
  FormControlInput,
} from "react-form-with-constraints-bootstrap4";

class ModalChangePassword extends Component {
  constructor(props) {
    super(props);
    this.state = {
      staffID: "",
      isOpen: false,
      password: {
        newPassword: "",
        confirmPassword: "",
      },
    };
  }

  toggle = (e, staffID) => {
    if (staffID !== undefined) {
      this.setState({
        staffID: staffID,
        isOpen: !this.state.isOpen,
      });
    } else {
      this.setState({
        isOpen: !this.state.isOpen,
      });
    }
  };

  onHandleChange = (e) => {
    var target = e.target;
    var name = target.name;
    var value = target.value;
    this.setState({
      password: {
        ...this.state.password,
        [name]: value,
      },
    });
  };

  handleSubmitForm = (e) => {
    e.preventDefault();
    var { password } = this.state;
    if (password.newPassword !== password.confirmPassword) {
      NotificationManager.error(
        "Error message",
        "New password and Confirm password isn't same"
      );
    } else {
      this.changePassword();
    }
  };

  changePassword = () => {
    axios({
      method: "POST",
      url:
        path_variable.Staff.CHANGE_PASSWORD +
        this.state.staffID +
        "&newPass=" +
        this.state.password.newPassword,
      data: null,
    })
      .then((res) => {
        console.log(res.data);
        if (res.data) {
          NotificationManager.success(
            "Success message",
            "Update Pasword successfully"
          );
        } else {
          NotificationManager.error("Error message", "Update Pasword failed");
        }
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Update Pasword failed");
      });
    this.setState({
      isOpen: !this.state.isOpen,
    });
  };

  render() {
    var { isOpen, password } = this.state;
    return (
      <Modal isOpen={isOpen}>
        <ModalHeader>Change Password</ModalHeader>
        <FormWithConstraints
          ref={(formWithConstraints) => (this.form = formWithConstraints)}
          onSubmit={(e) => this.handleSubmitForm(e)}
        >
          <ModalBody>
            <FormGroup>
              <FormControlLabel htmlFor="departID">
                New Password
              </FormControlLabel>
              <FormControlInput
                name="newPassword"
                onChange={(e) => this.onHandleChange(e)}
                placeholder="Input new Password"
                value={!!password.newPassword ? password.newPassword : ""}
              />
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="departName">
                Confirm Password<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <FormControlInput
                name="confirmPassword"
                onChange={(e) => this.onHandleChange(e)}
                placeholder="Confirm new password"
                value={
                  !!password.confirmPassword ? password.confirmPassword : ""
                }
                required
              />
            </FormGroup>
          </ModalBody>
          <ModalFooter>
            <Button color="primary" type="submit">
              Save Password
            </Button>{" "}
            <Button color="secondary" onClick={this.toggle}>
              Cancel
            </Button>
          </ModalFooter>
        </FormWithConstraints>
        <NotificationContainer />
      </Modal>
    );
  }
}

export default ModalChangePassword;
