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
      levelID: "",
      isOpen: false,
    };
  }

  toggle = (e, levelID) => {
    if (levelID !== undefined) {
      this.setState({
        levelID: levelID,
        isOpen: !this.state.isOpen,
      });
    } else {
      this.setState({
        isOpen: !this.state.isOpen,
      });
    }
  };

  fetchListLevel = () => {
    this.props.fetchListLevel();
  };

  handleDelete = () => {
    axios({
      method: "DELETE",
      url:
        path_variable.Level.DELETE_LEVEL +
        this.state.levelID +
        "&userName=" +
        this.props.userName,
      data: null,
    })
      .then((res) => {
        this.fetchListLevel();
        NotificationManager.success(
          "Success message",
          "Delete Author successfully"
        );
      })
      .catch((error) => {
        console.log(error);
        NotificationManager.error("Error message", "Delete Author failed");
      });
    this.setState({
      isOpen: !this.state.isOpen,
    });
  };

  render() {
    //console.log(this.state.levelID, this.props.userName);
    var { isOpen } = this.state;
    return (
      <Modal isOpen={isOpen}>
        <ModalHeader>Delete Author</ModalHeader>
        <ModalBody>
          Are you sure you want to delete {this.state.levelID}?
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
