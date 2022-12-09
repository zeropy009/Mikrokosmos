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
      bookID: "",
      isOpen: false,
    };
  }

  toggle = (e, bookID) => {
    if (bookID !== undefined) {
      this.setState({
        bookID: bookID,
        isOpen: !this.state.isOpen,
      });
    } else {
      this.setState({
        isOpen: !this.state.isOpen,
      });
    }
  };

  fetchReceiptDetail = () => {
    this.props.fetchReceiptDetail();
  };

  handleDelete = () => {
    axios({
      method: "DELETE",
      url:
        path_variable.Receipt.DELETE_RECEIPT_DETAIL +
        this.props.receiptID+
        "&bookID=" +
        this.state.bookID +
        "&userName=" +
        this.props.userName,
      data: null,
    })
      .then((res) => {
        this.fetchReceiptDetail();
        NotificationManager.success(
          "Success message",
          "Delete Book successfully"
        );
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Delete Book failed");
      });
    this.setState({
      isOpen: !this.state.isOpen,
    });
  };

  render() {
    //console.log(this.state.bookID, this.props.userName);
    var { isOpen } = this.state;
    return (
      <Modal isOpen={isOpen}>
        <ModalHeader>Delete Author</ModalHeader>
        <ModalBody>
          Are you sure you want to delete {this.state.bookID}?
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
