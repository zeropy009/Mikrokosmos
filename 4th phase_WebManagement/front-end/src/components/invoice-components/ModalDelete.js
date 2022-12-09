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
      invoiceID: "",
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

  toggleInvoice = (e, invoiceID) => {
    console.log(invoiceID);
    if (invoiceID !== undefined) {
      this.setState({
        invoiceID: invoiceID,
        isOpen: !this.state.isOpen,
      });
    } else {
      this.setState({
        isOpen: !this.state.isOpen,
      });
    }
  };

  fetchListBookInvoice = (invoiceID) => {
    this.props.fetchListBookInvoice(invoiceID);
  };

  fetchListInvoice = ()=>{
    this.props.fetchListInvoice();
  }

  handleDelete = () => {
    const { invoiceID, userName, type } = this.props;
    console.log(invoiceID, userName, type);
    if (type === "Book") {
      axios({
        method: "DELETE",
        url:
          path_variable.Invoice.DELETE_BOOK_INVOICE +
          invoiceID +
          "&bookID=" +
          this.state.bookID +
          "&userName=" +
          userName,
        data: null,
      })
        .then((res) => {
          this.fetchListBookInvoice(invoiceID);
          NotificationManager.success(
            "Success message",
            "Delete Book successfully"
          );
        })
        .catch((err) => {
          console.log(err);
          NotificationManager.error("Error message", "Delete Book failed");
        });
    } else {
      axios({
        method: "DELETE",
        url:
          path_variable.Invoice.DELETE_INVOICE +
          this.state.invoiceID +
          "&userName=" +
          userName,
        data: null,
      })
        .then((res) => {
          this.fetchListInvoice();
          NotificationManager.success(
            "Success message",
            "Delete Invoice successfully"
          );
        })
        .catch((err) => {
          console.log(err);
          NotificationManager.error("Error message", "Delete Invoice failed");
        });
    }
    this.setState({
      isOpen: !this.state.isOpen,
    });
  };

  render() {
    var { isOpen, bookID, invoiceID } = this.state;
    var { type } = this.props;
    return (
      <Modal isOpen={isOpen}>
        <ModalHeader>
          {type === "Invoice" ? "Delete Invoice" : "Delete Book"}?
        </ModalHeader>
        <ModalBody>
          Are you sure you want to delete{" "}
          {type === "Invoice" ? invoiceID : bookID}?
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
