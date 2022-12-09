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

export default class Customer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      listCustomer: [],
      newCustomer: {
        customerID: "",
        customerName: "",
        customerAddress: "",
        customerPhone: "",
        customerEmail: "",
      },
      typeModal: "New",
      userName: "",
    };
  }

  toggle = (e, row) => {
    if (row === undefined) {
      this.setState({
        newCustomer: {},
        typeModal: "New",
      });
    } else {
      this.setState({
        newCustomer: {
          ...row,
        },
        typeModal: "Edit",
      });
    }
    this.setState({
      modal: !this.state.modal,
    });
  };

  fetchListCustomer = () => {
    axios({
      method: "GET",
      url: path_variable.Customer.GET_LIST_CUSTOMER,
      data: null,
    })
      .then((res) => {
        this.setState({
          listCustomer: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Show list Customer failed");
      });
  };

  insertOrUpdateCustomer = (Customer) => {
    if (this.state.typeModal === "Edit") {
      axios({
        method: "PUT",
        url: path_variable.Customer.UPDATE_CUSTOMER + this.state.userName,
        data: Customer,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListCustomer();
            NotificationManager.success(
              "Success message",
              "Update Customer successfully"
            );
          } else {
            NotificationManager.error(
              "Error message",
              "Update Customer failed"
            );
          }
        })
        .catch((err) => {
          console.log(err);
          NotificationManager.error("Error message", "Update Customer failed");
        });
    } else {
      axios({
        method: "POST",
        url: path_variable.Customer.ADD_CUSTOMER + this.state.userName,
        data: Customer,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListCustomer();
            NotificationManager.success(
              "Success message",
              "Update Customer successfully"
            );
          } else {
            NotificationManager.error(
              "Error message",
              "Update Customer failed"
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
      newCustomer: {
        ...this.state.newCustomer,
        [name]: value,
      },
    });
  };

  handleSubmit = (e) => {
    e.preventDefault();
    console.log(this.state.newCustomer);
    this.insertOrUpdateCustomer(this.state.newCustomer);
  };

  handleDelete = (e, row) => {
    this.refs.modalDelete.toggle(e, row.customerID);
  };

  componentWillMount() {
    this.fetchListCustomer();
    this.setState({
      userName: this.props.userName,
    });
  }

  render() {
    var { modal, listCustomer, newCustomer, typeModal, userName } = this.state;
    var { user } = this.props;
    return (
      <div>
        <TabHeader id={4} user={user} />
        <ModalDelete
          ref="modalDelete"
          userName={userName}
          fetchListCustomer={this.fetchListCustomer}
        />
        <div className="main-container">
          <PageHeader menu={"Sale"} subMenu={"Customer"} />
          <div className="content-wrapper">
            <div className="row gutters">
              <div className="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                <div className="card">
                  <div className="card-body">
                    <Row>
                      <Button
                        color="btn btn-success btn-rounded"
                        onClick={this.toggle}
                      >
                        Insert
                      </Button>
                    </Row>
                    <div className="table-responsive">
                      <ReactTable
                        data={listCustomer}
                        showPageSizeOptions={true}
                        id="table"
                        showPagination={true}
                        columns={[
                          {
                            Header: "#",
                            id: "index",
                            accessor: (d) => d.customerID,
                            Cell: ({ row }) => <div>{row._index + 1}</div>,
                            maxWidth: 50,
                          },
                          {
                            Header: "ID",
                            id: "customerID",
                            accessor: (d) => d.customerID,
                            Cell: ({ row }) => (
                              <div>{row._original.customerID}</div>
                            ),
                            maxWidth: 200,
                          },
                          {
                            Header: "FULLNAME",
                            id: "customerName",
                            accessor: (d) => d.customerName,
                            Cell: ({ row }) => (
                              <div>{row._original.customerName}</div>
                            ),
                            maxWidth: 400,
                          },
                          {
                            Header: "Email",
                            id: "email",
                            accessor: (d) => d.email,
                            Cell: ({ row }) => <div>{row._original.email}</div>,
                            maxWidth: 400,
                          },
                          {
                            Header: "Action",
                            id: "customerID",
                            style: { textAlign: "center" },
                            accessor: (d) => d.customerID,
                            Cell: ({ row }) => (
                              <div>
                                <Link
                                  to={
                                    `${prefix2}/customer/show/` +
                                    row._original.customerID
                                  }
                                  className="btn btn-outline-primary btn-rounded"
                                >
                                  Detail
                                </Link>
                                <Permission
                                  user={user}
                                  hasPermission={PermissionEnum.ADMIN.value}
                                >
                                  <button
                                    className="btn btn-outline-secondary btn-rounded"
                                    onClick={(e) =>
                                      this.handleDelete(
                                        e,
                                        listCustomer[row._index]
                                      )
                                    }
                                  >
                                    Delete
                                  </button>
                                </Permission>
                              </div>
                            ),
                          },
                        ]}
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
