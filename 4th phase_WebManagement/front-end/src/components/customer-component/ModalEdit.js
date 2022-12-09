import React, { Component } from "react";
import TabHeader from "../tab-component/TabHeader";
import {
  Button,
  Row,
  Modal,
  ModalHeader,
  ModalFooter,
  ModalBody,
} from "reactstrap";
import {
  FormWithConstraints,
  FieldFeedback,
} from "react-form-with-constraints";
import {
  FormGroup,
  FieldFeedbacks,
  FormControlInput,
  FormControlLabel,
} from "react-form-with-constraints-bootstrap4";
import ReactTable from "react-table";
import { Link } from "react-router-dom";
import "react-table/react-table";
import "react-table/react-table.css";
import "../../App.css";
import { path_variable, prefix2 } from "../../path_variable";
import {
  NotificationContainer,
  NotificationManager,
} from "react-notifications";
import axios from "axios";
import "react-notifications/lib/notifications.css";
import ModalDelete from "./ModalDelete";
import PageHeader from "../PageHeader";
import Permission from "../../Permission";
import { PermissionEnum } from "../../PermissionEnum";
import NumberFormat from "react-number-format";

class ModalEdit extends Component {
  constructor(props) {
    super(props);
    this.state = {
      newCustomer: {},
      isOpen: false,
    };
  }

  toggle = (e, customer) => {
    if (customer !== undefined) {
      this.setState({
        newCustomer: customer,
        isOpen: !this.state.isOpen,
      });
    } else {
      this.setState({
        isOpen: !this.state.isOpen,
      });
    }
  };

  onHandleChange = (e) => {
    var target = e.target;
    var name = target.name;
    var value = target.value;
    this.setState({
      newCustomer: {
        ...this.state.newCustomer,
        [name]: value,
      },
    });
  };

  fetchCustomerDetail = () => {
    this.props.fetchCustomerDetail(this.props.param);
    this.props.fetchListPoint(this.props.param);
  };

  handleSubmit = (e) => {
    e.preventDefault();
    if (this.form.isValid()) {
      this.updateCustomer(this.state.newCustomer);
    }
  };

  updateCustomer = (Customer) => {
    axios({
      method: "PUT",
      url: path_variable.Customer.UPDATE_CUSTOMER + this.props.userName,
      data: Customer,
    })
      .then((res) => {
        if (res.data) {
          this.fetchCustomerDetail();
          NotificationManager.success(
            "Success message",
            "Update Customer successfully"
          );
        } else {
          NotificationManager.error("Error message", "Update Customer failed");
        }
      })
      .catch((err) => {
        NotificationManager.error("Error message", "Update Customer failed");
      });
    this.setState({
      isOpen: !this.state.isOpen,
    });
  };

  render() {
    var { isOpen, newCustomer } = this.state;
    console.log(newCustomer);
    return (
      <Modal isOpen={isOpen}>
        <ModalHeader>Edit</ModalHeader>
        <FormWithConstraints
          ref={(formWithConstraints) => (this.form = formWithConstraints)}
          onSubmit={(e) => this.handleSubmit(e)}
        >
          <ModalBody>
            <FormGroup>
              <FormControlLabel htmlFor="customerID">
                Customer ID
              </FormControlLabel>
              <FormControlInput
                name="customerID"
                onChange={(e) => this.onHandleChange(e)}
                placeholder="This will be automatically created"
                value={!!newCustomer.customerID ? newCustomer.customerID : ""}
                disabled={true}
              />
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="customerName">
                Name<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <FormControlInput
                name="customerName"
                onChange={(e) => this.onHandleChange(e)}
                placeholder="Input Customer Name"
                value={
                  !!newCustomer.customerName ? newCustomer.customerName : ""
                }
                required
              />
              <FieldFeedbacks htmlFor="customerName">
                <FieldFeedback
                  when={(value) => {
                    return value === "";
                  }}
                >
                  <strong style={{ color: "red" }}> Required Field</strong>
                </FieldFeedback>
              </FieldFeedbacks>
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="address">
                Address<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <FormControlInput
                name="address"
                onChange={(e) => this.onHandleChange(e)}
                placeholder="Input author address"
                value={!!newCustomer.address ? newCustomer.address : ""}
                required
              />
              <FieldFeedbacks htmlFor="address">
                <FieldFeedback
                  when={(value) => {
                    return value === "";
                  }}
                >
                  <strong style={{ color: "red" }}> Required Field</strong>
                </FieldFeedback>
              </FieldFeedbacks>
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="email">
                Email<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <FormControlInput
                type="email"
                name="email"
                onChange={(e) => this.onHandleChange(e)}
                placeholder="Input Author Email"
                value={!!newCustomer.email ? newCustomer.email : ""}
                required
              />
              <FieldFeedbacks htmlFor="email">
                <FieldFeedback
                  when={(value) => {
                    return value === "";
                  }}
                >
                  <strong style={{ color: "red" }}> Required Field</strong>
                </FieldFeedback>
              </FieldFeedbacks>
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="customerPoint">
                Point<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <NumberFormat
                name="customerPoint"
                id="customerPoint"
                value={
                  !!newCustomer.customerPoint ? newCustomer.customerPoint : 0
                }
                customInput={FormControlInput}
                thousandSeparator={true}
                decimalScale={2}
                allowNegative={false}
                onValueChange={(values) => {
                  const { floatValue } = values;
                  this.setState({
                    newCustomer: {
                      ...this.state.newCustomer,
                      ["customerPoint"]: floatValue,
                    },
                  });
                }}
                required
              />
              <FieldFeedbacks htmlFor="customerPoint">
                <FieldFeedback
                  when={(value) => {
                    return value === "";
                  }}
                >
                  <strong style={{ color: "red" }}> Required Field</strong>
                </FieldFeedback>
              </FieldFeedbacks>
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="phone">
                Phone<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <FormControlInput
                name="phone"
                maxLength={10}
                onChange={(e) => this.onHandleChange(e)}
                placeholder="Input Author phone"
                value={!!newCustomer.phone ? newCustomer.phone : ""}
                required
              />
              <FieldFeedbacks htmlFor="phone">
                <FieldFeedback
                  when={(value) => {
                    return value === "";
                  }}
                >
                  <strong style={{ color: "red" }}> Required Field</strong>
                </FieldFeedback>
              </FieldFeedbacks>
            </FormGroup>
          </ModalBody>
          <ModalFooter>
            <Button
              type="submit"
              color="success"
              onClick={(e) => {
                if (!!this.form) {
                  this.form.validateFields();
                  // eslint-disable-next-line no-undef
                  customValidateFields(this.form);
                }
              }}
            >
              Save changes
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

export default ModalEdit;
