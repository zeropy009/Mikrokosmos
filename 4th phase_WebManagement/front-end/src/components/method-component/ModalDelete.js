import React, { Component } from "react";
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from "reactstrap";
import {
  NotificationContainer,
  NotificationManager,
} from "react-notifications";
import axios from "axios";
import "react-notifications/lib/notifications.css";
import { path_variable } from "../../path_variable";

class ModalDelete extends Component {
  constructor(props) {
    super(props);
    this.state = {
      methodID: "",
      isOpen: false,
    };
  }

  toggle = (e, methodID) => {
    if (methodID !== undefined) {
      this.setState({
        methodID: methodID,
        isOpen: !this.state.isOpen,
      });
    } else {
      this.setState({
        isOpen: !this.state.isOpen,
      });
    }
  };

  fetchListMethod = () => {
    this.props.fetchListMethod();
  };

  handleDelete = () => {
    axios({
      method: "DELETE",
      url:
        path_variable.Method.DELETE_METHOD +
        this.state.methodID +
        "&userName=" +
        this.props.userName,
      data: null,
    })
      .then((res) => {
        this.fetchListMethod();
        NotificationManager.success(
          "Success message",
          "Delete Author successfully"
        );
      })
      .catch((error) => {
        NotificationManager.error("Error message", "Delete Author failed");
      });
    this.setState({
      isOpen: !this.state.isOpen,
    });
  };

  render() {
    //console.log(this.state.methodID, this.props.userName);
    var { isOpen } = this.state;
    return (
      <Modal isOpen={isOpen}>
        <ModalHeader>Delete Author</ModalHeader>
        <ModalBody>
          Are you sure you want to delete {this.state.methodID}?
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
