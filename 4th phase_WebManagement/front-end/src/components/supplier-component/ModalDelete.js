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
      supplierID: "",
      isOpen: false,
    };
  }

  toggle = (e, supplierID) => {
    if (supplierID !== undefined) {
      this.setState({
        supplierID: supplierID,
        isOpen: !this.state.isOpen,
      });
    } else {
      this.setState({
        isOpen: !this.state.isOpen,
      });
    }
  };

  fetchListSupplier = () => {
    this.props.fetchListSupplier();
  };

  handleDelete = () => {
    axios({
      method: "DELETE",
      url:
        path_variable.Supplier.DELETE_SUPPLIER +
        this.state.supplierID +
        "&userName=" +
        this.props.userName,
      data: null,
    })
      .then((res) => {
        this.fetchListSupplier();
        NotificationManager.success(
          "Success message",
          "Delete Supplier successfully"
        );
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Delete Supplier failed");
      });
    this.setState({
      isOpen: !this.state.isOpen,
    });
  };

  render() {
    //console.log(this.state.authorID, this.props.userName);
    var { isOpen } = this.state;
    return (
      <Modal isOpen={isOpen}>
        <ModalHeader>Delete Supplier</ModalHeader>
        <ModalBody>
          Are you sure you want to delete {this.state.authorID}?
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
