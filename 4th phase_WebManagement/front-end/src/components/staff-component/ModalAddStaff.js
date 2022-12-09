import React, { Component } from "react";
import {
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  FormGroup,
  Input,
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
  FormControlInput,
  FormControlLabel,
} from "react-form-with-constraints-bootstrap4";
import moment from "moment";
import NumberFormat from "react-number-format";

class ModalAddStaff extends Component {
  constructor(props) {
    super(props);
    this.state = {
      staffID: "",
      isOpen: false,
      newStaff: {
        staffName: "",
        birthDay: "",
        phone: "",
        email: "",
        address: "",
        username: "",
        password: "",
        departID: "",
        role: "",
      },
      listDepart: [],
      listUsername: [],
    };
  }

  onHandleChange = (e) => {
    var target = e.target;
    var name = target.name;
    var value = target.value;
    if (name == "role") {
      value = value === "true" ? true : false;
    }
    this.setState({
      newStaff: {
        ...this.state.newStaff,
        [name]: value,
      },
    });
  };

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

  insertStaff = () => {
    axios({
      method: "POST",
      url: path_variable.Staff.ADD_STAFF + this.props.userName,
      data: this.state.newStaff,
    })
      .then((res) => {
        if (res.data) {
          this.fetchListStaff();
          NotificationManager.success(
            "Success message",
            "Insert Staff successfully"
          );
        } else {
          NotificationManager.error("Error message", "Insert Staff failed");
        }
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Insert Staff failed");
      });
    // this.setState({
    //   isOpen: !this.state.isOpen,
    // });
  };

  fetchListDepart = () => {
    axios({
      method: "GET",
      url: path_variable.Depart.GET_LIST_DEPART,
      data: null,
    })
      .then((res) => {
        this.setState({
          listDepart: res.data,
        });
      })
      .catch((err) => {
        NotificationManager.error("Error message", "Show list Depart failed");
      });
  };

  fetchListUsername = () => {
    axios({
      method: "GET",
      url: path_variable.Staff.GET_LIST_USERNAME,
      data: null,
    })
      .then((res) => {
        this.setState({
          listUsername: res.data,
        });
      })
      .catch((err) => {
        NotificationManager.error("Error message", "Show list Depart failed");
      });
  };

  checkUsernameIsExist = (username) => {
    const { listUsername } = this.state;
    for (var i = 0; i < listUsername.length; i++) {
      if (listUsername[i] == username) {
        return true;
      }
    }
    return false;
  };

  componentDidMount() {
    this.fetchListDepart();
    this.fetchListUsername();
  }

  handleSubmitForm = (e) => {
    e.preventDefault();
    if(this.checkUsernameIsExist(this.state.newStaff.username)){
      NotificationManager.error("Error message", "Username is existed");
    }else{
      this.insertStaff();
    }
  };

  render() {
    var { isOpen, newStaff, listDepart } = this.state;
    return (
      <Modal isOpen={isOpen}>
        <ModalHeader>Add Staff</ModalHeader>
        <FormWithConstraints
          ref={(formWithConstraints) => (this.form = formWithConstraints)}
          onSubmit={(e) => this.handleSubmitForm(e)}
        >
          <ModalBody>
            <FormGroup>
              <FormControlLabel htmlFor="authorID">Fullname</FormControlLabel>
              <FormControlInput
                name="staffName"
                onChange={(e) => this.onHandleChange(e)}
                placeholder="Please input Fullname"
                value={!!newStaff.staffName ? newStaff.staffName : ""}
                required
              />
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="authorID">Birthday</FormControlLabel>
              <Input
                type="date"
                value={moment(newStaff.birthDay).format("YYYY-MM-DD")}
                name="birthDay"
                onChange={(e) => this.onHandleChange(e)}
                placeholder="Please select birthday"
                required
              />
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="authorID">Phone</FormControlLabel>
              <FormControlInput
                name="phone"
                onChange={(e) => this.onHandleChange(e)}
                placeholder="Please input phone"
                value={!!newStaff.phone ? newStaff.phone : ""}
                required
              />
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="authorID">Email</FormControlLabel>
              <FormControlInput
                type="email"
                name="email"
                onChange={(e) => this.onHandleChange(e)}
                placeholder="Please input username"
                value={!!newStaff.email ? newStaff.email : ""}
                required
              />
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="authorID">Username</FormControlLabel>
              <FormControlInput
                name="username"
                onChange={(e) => this.onHandleChange(e)}
                placeholder="This will be automatically created"
                value={!!newStaff.username ? newStaff.username : ""}
                required
              />
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="authorID">Password</FormControlLabel>
              <FormControlInput
                type="password"
                name="password"
                onChange={(e) => this.onHandleChange(e)}
                placeholder="Please input password"
                value={!!newStaff.password ? newStaff.password : ""}
                required
              />
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="authorID">Salary</FormControlLabel>
              <NumberFormat
                name="salary"
                id="salary"
                value={!!newStaff.salary ? newStaff.salary : ""}
                customInput={FormControlInput}
                placeholder="Input Book Price"
                thousandSeparator={true}
                decimalScale={2}
                allowNegative={false}
                onValueChange={(values) => {
                  const { floatValue } = values;
                  this.setState({
                    newStaff: {
                      ...this.state.newStaff,
                      ["salary"]: floatValue,
                    },
                  });
                }}
                required
              />
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="authorID">Depart</FormControlLabel>
              <select
                className="form-control"
                value={newStaff.departID}
                name="departID"
                onChange={(e) => this.onHandleChange(e)}
                required
              >
                {!!listDepart
                  ? listDepart.map((depart, index) => {
                      return (
                        <option key={index} value={depart.departID}>
                          {depart.departName}
                        </option>
                      );
                    })
                  : ""}
              </select>
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="authorID">Position</FormControlLabel>
              <select
                className="form-control"
                value={newStaff.role}
                name="role"
                onChange={(e) => this.onHandleChange(e)}
                required
              >
                <option value={""}>Select Role</option>
                <option value={false}>Manager</option>
                <option value={true}>Employee</option>
              </select>
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="authorID">Address</FormControlLabel>
              <Input
                type="textarea"
                value={!!newStaff.address ? newStaff.address : ""}
                name="address"
                onChange={(e) => this.onHandleChange(e)}
                placeholder="Please input Address"
                required
              />
            </FormGroup>
          </ModalBody>
          <ModalFooter>
            <Button color="primary" type="submit">
              Add
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

export default ModalAddStaff;
