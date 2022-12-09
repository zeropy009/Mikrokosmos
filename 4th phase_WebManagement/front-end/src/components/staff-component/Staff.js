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
import ModalAddStaff from "./ModalAddStaff";

export default class Staff extends Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      listStaff: [],
      newStaff: {
        staffID: "",
        staffName: "",
        departID: "",
      },
      typeModal: "New",
      userName: "",
    };
  }

  toggle = (e, row) => {
    if (row === undefined) {
      this.setState({
        newStaff: {},
        typeModal: "New",
      });
    } else {
      this.setState({
        newStaff: {
          ...row,
        },
        typeModal: "Edit",
      });
    }
    this.setState({
      modal: !this.state.modal,
    });
  };

  fetchListStaff = () => {
    axios({
      method: "GET",
      url: path_variable.Staff.GET_LIST_STAFF,
      data: null,
    })
      .then((res) => {
        this.setState({
          listStaff: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Show list Staff failed");
      });
  };

  insertOrUpdateStaff = (Staff) => {
    if (this.state.typeModal === "Edit") {
      axios({
        method: "PUT",
        url: path_variable.Staff.UPDATE_STAFF + this.state.userName,
        data: Staff,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListStaff();
            NotificationManager.success(
              "Success message",
              "Update Staff successfully"
            );
          } else {
            NotificationManager.error("Error message", "Update Staff failed");
          }
        })
        .catch((err) => {
          console.log(err);
          NotificationManager.error("Error message", "Update Staff failed");
        });
    } else {
      axios({
        method: "POST",
        url: path_variable.Staff.ADD_STAFF + this.state.userName,
        data: Staff,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListStaff();
            NotificationManager.success(
              "Success message",
              "Update Staff successfully"
            );
          } else {
            NotificationManager.error("Error message", "Update Staff failed");
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
      newStaff: {
        ...this.state.newStaff,
        [name]: value,
      },
    });
  };

  handleSubmit = (e) => {
    e.preventDefault();
    console.log(this.state.newStaff);
    this.insertOrUpdateStaff(this.state.newStaff);
  };

  handleDelete = (e, row) => {
    this.refs.modalDelete.toggle(e, row.staffID);
  };

  componentWillMount() {
    this.fetchListStaff();
    this.setState({
      userName: this.props.userName,
    });
  }

  toggleModalAdd = ()=>{
    this.refs.modalAddStaff.toggle();
  }

  render() {
    var { modal, listStaff, newStaff, typeModal, userName } = this.state;
    var { user } = this.props;
    return (
      <div>
        <TabHeader id={5} user={user} />
        <ModalDelete
          ref="modalDelete"
          userName={userName}
          fetchListStaff={this.fetchListStaff}
        />
        <ModalAddStaff
          ref="modalAddStaff"
          userName={userName}
          fetchListStaff={this.fetchListStaff}
        />
        <div className="main-container">
          <PageHeader menu={"Sale"} subMenu={"Staff"} />
          <div className="content-wrapper">
            <div className="row gutters">
              <div className="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                <div className="card">
                  <div className="card-body">
                    <Row>
                      <Button
                        color="btn btn-outline-success btn-rounded"
                        onClick={this.toggleModalAdd}
                      >
                        Insert
                      </Button>
                    </Row>
                    <div className="table-responsive">
                      <ReactTable
                        data={listStaff}
                        showPageSizeOptions={true}
                        id="table"
                        showPagination={true}
                        columns={[
                          {
                            Header: "#",
                            id: "index",
                            accessor: (d) => d.staffID,
                            Cell: ({ row }) => <div>{row._index + 1}</div>,
                            maxWidth: 50,
                          },
                          {
                            Header: "ID",
                            id: "staffID",
                            accessor: (d) => d.staffID,
                            Cell: ({ row }) => (
                              <div>{row._original.staffID}</div>
                            ),
                            maxWidth: 200,
                          },
                          {
                            Header: "FULLNAME",
                            id: "staffName",
                            accessor: (d) => d.staffName,
                            Cell: ({ row }) => (
                              <div>{row._original.staffName}</div>
                            ),
                            maxWidth: 400,
                          },
                          {
                            Header: "Depart ID",
                            id: "departID",
                            accessor: (d) => d.departID,
                            Cell: ({ row }) => (
                              <div>{row._original.departID}</div>
                            ),
                            maxWidth: 400,
                          },
                          {
                            Header: "Action",
                            id: "staffID",
                            style: { textAlign: "center" },
                            accessor: (d) => d.staffID,
                            Cell: ({ row }) => (
                              <div>
                                <Link
                                  to={
                                    `${prefix2}/staff/show/` +
                                    row._original.staffID
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
                                        listStaff[row._index]
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
