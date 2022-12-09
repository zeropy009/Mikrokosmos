import axios from "axios";
import React, { Component } from "react";
import {
  FieldFeedback, FormWithConstraints
} from "react-form-with-constraints";
import {
  FieldFeedbacks,
  FormControlInput,
  FormControlLabel, FormGroup
} from "react-form-with-constraints-bootstrap4";
import {
  NotificationContainer,
  NotificationManager
} from "react-notifications";
import "react-notifications/lib/notifications.css";
import ReactTable from "react-table";
import "react-table/react-table";
import "react-table/react-table.css";
import {
  Button,
  Modal,
  ModalBody, ModalFooter, ModalHeader, Row
} from "reactstrap";
import "../../App.css";
import { path_variable } from "../../path_variable";
import Permission from "../../Permission";
import { PermissionEnum } from "../../PermissionEnum";
import PageHeader from "../PageHeader";
import TabHeader from "../tab-component/TabHeader";
import ModalDelete from "./ModalDelete";

export default class Supplier extends Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      listSupplier: [],
      newSupplier: {
        supplierID: "",
        supplierName: "",
        address: "",
        phone: "",
        email: "",
      },
      typeModal: "New",
      userName: "",
    };
  }

  toggle = (e, row) => {
    if (row === undefined) {
      this.setState({
        newSupplier: {},
        typeModal: "New",
      });
    } else {
      this.setState({
        newSupplier: {
          ...row,
        },
        typeModal: "Edit",
      });
    }
    this.setState({
      modal: !this.state.modal,
    });
  };

  fetchListSupplier = () => {
    axios({
      method: "GET",
      url: path_variable.Supplier.GET_LIST_SUPPLIER,
      data: null,
    })
      .then((res) => {
        this.setState({
          listSupplier: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Show list Supplier failed");
      });
  };

  insertOrUpdateSupplier = (Supplier) => {
    if (this.state.typeModal === "Edit") {
      axios({
        method: "PUT",
        url: path_variable.Supplier.UPDATE_SUPPLIER + this.state.userName,
        data: Supplier,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListSupplier();
            NotificationManager.success(
              "Success message",
              "Update Supplier successfully"
            );
          } else {
            NotificationManager.error(
              "Error message",
              "Update Supplier failed"
            );
          }
        })
        .catch((err) => {
          console.log(err);
          NotificationManager.error("Error message", "Update Supplier failed");
        });
    } else {
      axios({
        method: "POST",
        url: path_variable.Supplier.ADD_SUPPLIER + this.state.userName,
        data: Supplier,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListSupplier();
            NotificationManager.success(
              "Success message",
              "Add Supplier successfully"
            );
          } else {
            NotificationManager.error(
              "Error message",
              "Update Supplier failed"
            );
          }
        })
        .catch((err) => {
          console.log(err);
        });
    }
    this.setState({
      modal: !this.state.modal,
    });
  };

  onHandleChange = (e) => {
    var target = e.target;
    var name = target.name;
    var value = target.value;
    this.setState({
      newSupplier: {
        ...this.state.newSupplier,
        [name]: value,
      },
    });
  };

  handleSubmit = (e) => {
    e.preventDefault();
    console.log(this.state.newSupplier);
    this.insertOrUpdateSupplier(this.state.newSupplier);
  };

  handleDelete = (e, row) => {
    this.refs.modalDelete.toggle(e, row.supplierID);
  };

  componentWillMount() {
    this.fetchListSupplier();
    this.setState({
      userName: this.props.userName,
    });
  }

  render() {
    var { modal, listSupplier, newSupplier, typeModal, userName } = this.state;
    var { user } = this.props;
    return (
      <div>
        <Modal isOpen={modal}>
          <ModalHeader>
            {typeModal === "New" ? "Add new Supplier" : "Edit Supplier"}
          </ModalHeader>
          <FormWithConstraints
            ref={(formWithConstraints) => (this.form = formWithConstraints)}
            onSubmit={(e) => this.handleSubmit(e)}
          >
            <ModalBody>
              <FormGroup>
                <FormControlLabel htmlFor="supplierID">
                  Supplier ID
                </FormControlLabel>
                <FormControlInput
                  name="supplierID"
                  onChange={(e) => this.onHandleChange(e)}
                  placeholder="This will be automatically created"
                  value={!!newSupplier.supplierID ? newSupplier.supplierID : ""}
                  disabled={true}
                />
              </FormGroup>
              <FormGroup>
                <FormControlLabel htmlFor="supplierName">
                  Name<strong style={{ color: "red" }}>*</strong>
                </FormControlLabel>
                <FormControlInput
                  name="supplierName"
                  onChange={(e) => this.onHandleChange(e)}
                  placeholder="Input Supplier Name"
                  value={
                    !!newSupplier.supplierName ? newSupplier.supplierName : ""
                  }
                />
                <FieldFeedbacks for="supplierName">
                  <FieldFeedback when={(value) => value === ""} warning>
                    <strong style={{ color: "red" }}>Required Field</strong>
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
                  placeholder="Input Supplier Address"
                  value={!!newSupplier.address ? newSupplier.address : ""}
                />
                <FieldFeedbacks for="address">
                  <FieldFeedback when={(value) => value === ""} warning>
                    <strong style={{ color: "red" }}>Required Field</strong>
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
                  placeholder="Input Supplier Phone"
                  value={!!newSupplier.phone ? newSupplier.phone : ""}
                />
                <FieldFeedbacks for="phone">
                  <FieldFeedback when={(value) => value === ""} warning>
                    <strong style={{ color: "red" }}>Required Field</strong>
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
                  placeholder="Input Supplier Email"
                  value={!!newSupplier.email ? newSupplier.email : ""}
                />
                <FieldFeedbacks for="email">
                  <FieldFeedback when={(value) => value === ""} warning>
                    <strong style={{ color: "red" }}>Required Field</strong>
                  </FieldFeedback>
                </FieldFeedbacks>
              </FormGroup>
            </ModalBody>
            <ModalFooter>
              <Button
                type="submit"
                color="primary"
                onClick={(e) => {
                  if (!!this.form) {
                    this.form.validateFields();
                    // customValidateFields(this.form);
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
        <TabHeader user={user} id={3} />
        <ModalDelete
          ref="modalDelete"
          userName={userName}
          fetchListSupplier={this.fetchListSupplier}
        />
        <div className="main-container">
          <PageHeader menu={"Storage"} subMenu={"Supplier"} />
          <div className="content-wrapper">
            <div className="row gutters">
              <div className="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                <div className="card">
                  <div className="card-body">
                    <Row>
                      <Button
                        color="btn btn-outline-success btn-rounded"
                        onClick={this.toggle}
                      >
                        Insert
                      </Button>
                    </Row>
                    <div className="table-responsive">
                      <ReactTable
                        data={listSupplier}
                        showPageSizeOptions={true}
                        id="table"
                        showPagination={true}
                        columns={[
                          {
                            Header: "ID",
                            id: "supplierID",
                            accessor: (d) => d.supplierID,
                            Cell: ({ row }) => (
                              <div>{row._original.supplierID}</div>
                            ),
                          },
                          {
                            Header: "Supplier Name",
                            id: "supplierName",
                            accessor: (d) => d.supplierName,
                            Cell: ({ row }) => (
                              <div>{row._original.supplierName}</div>
                            ),
                          },
                          {
                            Header: "Supplier Address",
                            id: "address",
                            accessor: (d) => d.address,
                            Cell: ({ row }) => (
                              <div>{row._original.address}</div>
                            ),
                          },
                          {
                            Header: "Supplier Phone",
                            id: "phone",
                            accessor: (d) => d.phone,
                            Cell: ({ row }) => <div>{row._original.phone}</div>,
                          },
                          {
                            Header: "Supplier Email",
                            id: "supplierEmail",
                            accessor: (d) => d.email,
                            Cell: ({ row }) => <div>{row._original.email}</div>,
                          },
                          {
                            Header: "Action",
                            id: "SupplierID",
                            minWidth: 120,
                            style: { textAlign: "center" },
                            accessor: (d) => d.SupplierID,
                            Cell: ({ row }) => (
                              <Permission
                                user={user}
                                hasPermission={PermissionEnum.ADMIN.value}
                              >
                                <div>
                                  <button
                                    className="btn btn-outline-primary btn-rounded"
                                    onClick={(e) =>
                                      this.toggle(e, listSupplier[row._index])
                                    }
                                  >
                                    Edit
                                  </button>
                                  <button
                                    className="btn btn-outline-secondary btn-rounded"
                                    onClick={(e) =>
                                      this.handleDelete(
                                        e,
                                        listSupplier[row._index]
                                      )
                                    }
                                  >
                                    Delete
                                  </button>
                                </div>
                              </Permission>
                            ),
                          },
                        ]}
                        defaultPageSize={5}
                        minRows
                        className="table -striped -highlight"
                      />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <NotificationContainer />
      </div>
    );
  }
}
