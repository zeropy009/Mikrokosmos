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
      staffID: "",
      isOpen: false,
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

  fetchListStaff = () => {
    this.props.fetchListStaff();
  };

  handleDelete = () => {
    axios({
      method: "DELETE",
      url:
        path_variable.Staff.DELETE_STAFF +
        this.state.staffID +
        "&userName=" +
        this.props.userName,
      data: null,
    })
      .then((res) => {
        if(res.data){
          this.fetchListStaff();
          NotificationManager.success(
            "Success message",
            "Delete Staff successfully"
          );
        }else{
          NotificationManager.error("Error message", "Delete Staff failed");
        }
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Delete Staff failed");
      });
    this.setState({
      isOpen: !this.state.isOpen,
    });
  };

  render() {
    //console.log(this.state.staffID, this.props.userName);
    var { isOpen } = this.state;
    return (
      <Modal isOpen={isOpen}>
        <ModalHeader>Delete Staff</ModalHeader>
        <ModalBody>
          Are you sure you want to delete {this.state.staffID}?
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
