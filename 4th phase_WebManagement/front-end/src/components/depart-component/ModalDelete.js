import React, { Component } from "react";
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from "reactstrap";
import {
  NotificationContainer,
  NotificationManager,
} from "react-notifications";
import axios from "axios";
import "react-notifications/lib/notifications.css";
import { path_variable } from "../../path_variable";

export default class ModalDelete extends Component {
  constructor(props) {
    super(props);
    this.state = {
      departID: "",
      isOpen: false,
    };
  }

  toggle = (e, departID) => {
    if (departID !== undefined) {
      this.setState({
        departID: departID,
        isOpen: !this.state.isOpen,
      });
    } else {
      this.setState({
        isOpen: !this.state.isOpen,
      });
    }
  };

  fetchListDepart = () => {
    this.props.fetchListDepart();
  };

  handleDelete = () => {
    axios({
      method: "DELETE",
      url:
        path_variable.Depart.DELETE_DEPART +
        this.state.departID +
        "&userName=" +
        this.props.userName,
      data: null,
    })
      .then((res) => {
        this.fetchListDepart();
        NotificationManager.success(
          "Success message",
          "Delete Depart successfully"
        );
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Delete Depart failed");
      });
    this.setState({
      isOpen: !this.state.isOpen,
    });
  };

  render() {
    //console.log(this.state.departID, this.props.userName);
    var { isOpen } = this.state;
    return (
      <Modal isOpen={isOpen}>
        <ModalHeader>Delete Depart</ModalHeader>
        <ModalBody>
          Are you sure you want to delete {this.state.departID}?
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


