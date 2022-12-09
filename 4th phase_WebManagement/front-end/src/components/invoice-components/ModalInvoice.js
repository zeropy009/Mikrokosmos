import React, { Component } from 'react';
import {
  Button,
  Modal,
  ModalHeader,
  ModalFooter,
  ModalBody,
  Input,
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
import { path_variable, prefix2 } from "../../path_variable";
import {
  NotificationContainer,
  NotificationManager,
} from "react-notifications";
import axios from "axios";
import "react-notifications/lib/notifications.css";
import { PermissionEnum } from "../../PermissionEnum";
import NumberFormat from "react-number-format";
import { typeModalEnum } from "./../../TypeModalEnum";

class ModalInvoice extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isOpen: false,
      listStaff:[],
      typeModal: typeModalEnum.ModalEdit.value,
      newInvoice: {
        invoiceID: "",
        solDate: "",
        customerID: "",
        staffID: "",
        discount: 0,
        shipStatus: "",
        payStatus: "",
        methodID: 0,
      },
    };
  }

  toggle = (e, invoice) => {
    if (invoice !== undefined) {
      this.setState({
        newInvoice: invoice,
        isOpen: !this.state.isOpen,
        typeModal: typeModalEnum.ModalEdit.value,
      });
    } else {
      this.setState({
        isOpen: !this.state.isOpen,
        typeModal: typeModalEnum.ModalAdd.value,
      });
    }
  };

  onHandleChange = (e) => {
    var target = e.target;
    var name = target.name;
    var value = target.value;
    this.setState({
      newInvoice: {
        ...this.state.newInvoice,
        [name]: value,
      },
    });
  };

  fetchListStaff = () => {
    axios({
      method: "GET",
      url: path_variable.Staff.GET_LIST_STAFF,
      data: null,
    })
      .then((res) => {
        var temp = [
          {
            staffID: "",
            staffName: "",
          },
        ];
        res.data.map((data) => {
          var list = {
            staffID: data.staffID,
            staffName: data.staffName,
          };
          temp.push(list);
        });
        this.setState({
          listStaff: temp,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Show list Staff failed");
      });
  };

  componentDidMount(){
    this.fetchListStaff();
  }

  handleSubmit = e=>{
    e.preventDefault();
    if(this.form.isValid()){
      this.updateInvoice();
    }
  }

  fetchInvoiceDetail = ()=>{
    this.props.fetchInvoiceDetail(this.state.newInvoice.invoiceID);
  }

  updateInvoice= ()=>{
    axios({
      method: "PUT",
      url: path_variable.Invoice.UPDATE_INVOICE+this.props.userName,
      data: this.state.newInvoice,
    })
      .then((res) => {
        if (res.data) {
          this.fetchInvoiceDetail();
          NotificationManager.success(
            "Success message",
            "Update Invoice successfully"
          );
        } else {
          NotificationManager.error("Error message", "Update Invoice failed");
        }
        this.setState({
          isOpen:false
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Show list Staff failed");
      });
  }


  render() {
    var { isOpen, typeModal, newInvoice,listStaff } = this.state;
    var { listCustomer } = this.props;
    return (
      <Modal isOpen={isOpen}>
        <ModalHeader>
          {typeModal === typeModalEnum.ModalAdd.value
            ? "Add Invoice"
            : "Edit Invoice"}
        </ModalHeader>
        <FormWithConstraints
          ref={(formWithConstraints) => (this.form = formWithConstraints)}
          onSubmit={(e) => this.handleSubmit(e)}
        >
          <ModalBody>
            <FormGroup>
              <FormControlLabel htmlFor="invoiceID">
                Invoice ID
              </FormControlLabel>
              <FormControlInput
                name="invoiceID"
                onChange={(e) => this.onHandleChange(e)}
                placeholder="This will be automatically created"
                value={!!newInvoice.invoiceID ? newInvoice.invoiceID : ""}
                disabled={true}
              />
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="staffID">
                Staff<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <select
                className="form-control"
                name="staffID"
                id="staffID"
                value={newInvoice.staffID}
                onChange={(e) => this.onHandleChange(e)}
                required
              >
                {listStaff.map((data) => {
                  return (
                    <option key={data.staffID} value={data.staffID}>
                      {data.staffName}
                    </option>
                  );
                })}
              </select>
              <FieldFeedbacks htmlFor="staffID">
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
              <FormControlLabel htmlFor="customerID">
                Customer<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <select
                className="form-control"
                name="customerID"
                id="customerID"
                value={newInvoice.customerID}
                onChange={(e) => this.onHandleChange(e)}
                required
              >
                {listCustomer.map((data) => {
                  return (
                    <option key={data.customerID} value={data.customerID}>
                      {data.customerName}
                    </option>
                  );
                })}
              </select>
              <FieldFeedbacks htmlFor="customerID">
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
              <FormControlLabel htmlFor="discount">
                Discount<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <NumberFormat
                name="discount"
                id="discount"
                value={!!newInvoice.discount ? newInvoice.discount : ""}
                customInput={FormControlInput}
                placeholder="Input Book Amount"
                thousandSeparator={true}
                decimalScale={2}
                allowNegative={false}
                onValueChange={(values) => {
                  const { floatValue } = values;
                  this.setState({
                    newInvoice: {
                      ...this.state.newInvoice,
                      ["discount"]: floatValue,
                    },
                  });
                }}
              />
              <FieldFeedbacks htmlFor="discount">
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
              <FormControlLabel htmlFor="solDate">Sold Date</FormControlLabel>
              <FormControlInput
                name="solDate"
                onChange={(e) => this.onHandleChange(e)}
                // placeholder="This will be automatically created"
                value={!!newInvoice.solDate ? newInvoice.solDate : ""}
                disabled={true}
              />
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="methodID">
                Payment Method<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <select className="form-control" value={newInvoice.methodID} onChange={(e) => this.onHandleChange(e)}>
                <option value="1">Tiền mặt</option>
                <option value="2">Chuyển khoản</option>
              </select>
              <FieldFeedbacks htmlFor="methodID">
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
              <FormControlLabel htmlFor="payStatus">
                Payment Method<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <select className="form-control" value={newInvoice.payStatus} onChange={(e) => this.onHandleChange(e)} >
                <option value="Chưa thanh toán">Chưa thanh toán</option>
                <option value="Đã thanh toán">Đã thanh toán</option>
              </select>
              <FieldFeedbacks htmlFor="payStatus">
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
              {typeModal === typeModalEnum.ModalAdd.value
                ? "Add"
                : "Save changes"}
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

export default ModalInvoice;