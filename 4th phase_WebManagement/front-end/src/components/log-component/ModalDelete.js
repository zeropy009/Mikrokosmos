import axios from "axios";
import React, { Component } from "react";
import {
  NotificationContainer,
  NotificationManager
} from "react-notifications";
import "react-notifications/lib/notifications.css";
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from "reactstrap";
import { path_variable } from "../../path_variable";

class ModalDelete extends Component {
  constructor(props) {
    super(props);
    this.state = {
      logID: "",
      isOpen: false,
    };
  }

  toggle = (e, logID) => {
    if (logID !== undefined) {
      this.setState({
        logID: logID,
        isOpen: !this.state.isOpen,
      });
    } else {
      this.setState({
        isOpen: !this.state.isOpen,
      });
    }
  };

  fetchListLog = () => {
    this.props.fetchListLog();
  };

  handleDelete = () => {
    axios({
      method: "DELETE",
      url:
        path_variable.Log.DELETE_LOG +
        this.state.logID +
        "&userName=" +
        this.props.userName,
      data: null,
    })
      .then((res) => {
        this.fetchListLog();
        NotificationManager.success(
          "Success message",
          "Delete Log successfully"
        );
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Delete Log failed");
      });
    this.setState({
      isOpen: !this.state.isOpen,
    });
  };

  render() {
    //console.log(this.state.logID, this.props.userName);
    var { isOpen } = this.state;
    return (
      <Modal isOpen={isOpen}>
        <ModalHeader>Delete Log</ModalHeader>
        <ModalBody>
          Are you sure you want to delete {this.state.logID}?
        </ModalBody>
        <ModalFooter>
          <Button color="primary" onClick={this.handleDelete}>
            Delete
          </Button>{" "}
          <Button color="secondary" onClick={this.toggle}>
            Cancel
          </Button>
        </ModalFooter>
        <NotificationContainer />
      </Modal>
    );
  }
}

export default ModalDelete;
