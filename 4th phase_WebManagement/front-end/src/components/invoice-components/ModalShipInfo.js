import React, { Component } from "react";
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
import moment from "moment";
import { typeModalEnum } from "./../../TypeModalEnum";

class ModalShipInfo extends Component {
  constructor(props) {
    super(props);
    this.state = {
      newShipInfo: {
        fullName: "",
        shipDate: "",
        address: moment().format("YYYY-MM-DD"),
        phone: "",
      },
      typeModal: typeModalEnum.ModalAdd.value,
      isOpen: false,
    };
  }

  toggle = (e, shipInfo) => {
    console.log(shipInfo);
    if (shipInfo !== undefined) {
      this.setState({
        newShipInfo: shipInfo,
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

  handleSubmit = (e) => {
    e.preventDefault();
    if (this.form.isValid()) {
      this.setState({
        newShipInfo: {
          ...this.state.newShipInfo,
          invoiceID: this.props.invoiceID,
        },
      });
      console.log(this.state.newShipInfo);
      this.updateShipInfo(this.state.newShipInfo);
    }
  };

  onHandleChange = (e) => {
    var target = e.target;
    var name = target.name;
    var value = target.value;
    this.setState({
      newShipInfo: {
        ...this.state.newShipInfo,
        [name]: value,
      },
    });
  };

  fetchShipInforDetail = ()=>{
    this.props.fetchShipInforDetail(this.props.invoiceID);
  }

  updateShipInfo = (shipInfo) => {
    axios({
      method: "POST",
      url:
        path_variable.Invoice.INSERT_SHIP_INFO +
        this.props.userName,
      data: shipInfo,
    })
      .then((res) => {
        if (res.data) {
          this.fetchShipInforDetail();
          NotificationManager.success(
            "Success message",
            "Update Ship Information successfully"
          );
        } else {
          NotificationManager.error("Error message", "Update Ship Information failed");
        }
      })
      .catch((err) => {
        NotificationManager.error("Error message", "Update Ship Information failed");
      });
    this.setState({
      isOpen: !this.state.isOpen,
    });
  };

  render() {
    var { isOpen, typeModal, newShipInfo } = this.state;
    var { userName, invoiceID } = this.props;
    return (
      <Modal isOpen={isOpen}>
        <ModalHeader>Edit Shipping Information</ModalHeader>
        <FormWithConstraints
          ref={(formWithConstraints) => (this.form = formWithConstraints)}
          onSubmit={(e) => this.handleSubmit(e)}
        >
          <ModalBody>
            <FormGroup>
              <FormControlLabel htmlFor="fullName">
                Fullname<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <FormControlInput
                name="fullName"
                onChange={(e) => this.onHandleChange(e)}
                placeholder="Input Author Name"
                value={!!newShipInfo.fullName ? newShipInfo.fullName : ""}
                required
              />
              <FieldFeedbacks htmlFor="fullName">
                <FieldFeedback
                  when={(value) => {
                    console.log("abc");
                    return value === "";
                  }}
                >
                  <strong style={{ color: "red" }}> Required Field</strong>
                </FieldFeedback>
              </FieldFeedbacks>
            </FormGroup>
            <FormGroup>
              <FormControlLabel htmlFor="shipDate">
                Ship Date<strong style={{ color: "red" }}>*</strong>
              </FormControlLabel>
              <Input
                type="date"
                value={moment(newShipInfo.shipDate).format("YYYY-MM-DD")}
                name="shipDate"
                className="form-group"
                id="datepicker"
                onChange={(e) => {
                  this.onHandleChange(e);
                }}
              />
              <FieldFeedbacks htmlFor="shipDate">
                <FieldFeedback
                  when={(value) => {
                    console.log("abc");
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
                placeholder="Input Author Name"
                value={!!newShipInfo.address ? newShipInfo.address : ""}
                required
              />
              <FieldFeedbacks htmlFor="address">
                <FieldFeedback
                  when={(value) => {
                    console.log("abc");
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
                onChange={(e) => this.onHandleChange(e)}
                placeholder="Input Author Name"
                value={!!newShipInfo.phone ? newShipInfo.phone : ""}
                required
              />
              <FieldFeedbacks htmlFor="phone">
                <FieldFeedback
                  when={(value) => {
                    console.log("abc");
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
              {typeModal === "New" ? "Add" : "Save changes"}
            </Button>{" "}
            <Button color="secondary" onClick={this.toggle}>
              Cancel
            </Button>
          </ModalFooter>
        </FormWithConstraints>
      </Modal>
    );
  }
}

export default ModalShipInfo;
