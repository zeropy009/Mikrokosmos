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
      categoryID: "",
      isOpen: false,
    };
  }

  toggle = (e, categoryID) => {
    if (categoryID !== undefined) {
      this.setState({
        categoryID: categoryID,
        isOpen: !this.state.isOpen,
      });
    } else {
      this.setState({
        isOpen: !this.state.isOpen,
      });
    }
  };

  fetchListCategory = () => {
    this.props.fetchListCategory();
  };

  handleDelete = () => {
    axios({
      method: "DELETE",
      url:
        path_variable.Category.DELETE_CATEGORY +
        this.state.categoryID +
        "&userName=" +
        this.props.userName,
      data: null,
    })
      .then((res) => {
        this.fetchListCategory();
        NotificationManager.success(
          "Success message",
          "Delete Author successfully"
        );
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Delete Author failed");
      });
    this.setState({
      isOpen: !this.state.isOpen,
    });
  };

  render() {
    var { isOpen } = this.state;
    return (
      <Modal isOpen={isOpen}>
        <ModalHeader>Delete Author</ModalHeader>
        <ModalBody>
          Are you sure you want to delete {this.state.categoryID}?
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
