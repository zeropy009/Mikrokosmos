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
import { Redirect } from "react-router-dom";
import "react-table/react-table";
import "react-table/react-table.css";
import "../../App.css";
import { path_variable } from "../../path_variable";
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

export default class Method extends Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      listMethod: [],
      newMethod: {
        methodID: "",
        methodName: 0,
        note: "",
      },
      typeModal: "New",
      userName: "",
    };
  }

  toggle = (e, row) => {
    if (row === undefined) {
      this.setState({
        newMethod: {},
        typeModal: "New",
      });
    } else {
      this.setState({
        newMethod: {
          ...row,
        },
        typeModal: "Edit",
      });
    }
    this.setState({
      modal: !this.state.modal,
    });
  };

  fetchListMethod = () => {
    axios({
      method: "GET",
      url: path_variable.Method.GET_LIST_METHOD,
      data: null,
    })
      .then((res) => {
        this.setState({
          listMethod: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Show list Method failed");
      });
  };

  insertOrUpdateMethod = (Method) => {
    if (this.state.typeModal === "Edit") {
      axios({
        method: "PUT",
        url: path_variable.Method.UPDATE_METHOD + this.state.userName,
        data: Method,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListMethod();
            NotificationManager.success(
              "Success message",
              "Update Method successfully"
            );
          } else {
            NotificationManager.error("Error message", "Update Method failed");
          }
        })
        .catch((err) => {
          console.log(err);
          NotificationManager.error("Error message", "Update Method failed");
        });
    } else {
      axios({
        method: "POST",
        url: path_variable.Method.ADD_METHOD + this.state.userName,
        data: Method,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListMethod();
            NotificationManager.success(
              "Success message",
              "Add Method successfully"
            );
          } else {
            NotificationManager.error("Error message", "Add Method failed");
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
      newMethod: {
        ...this.state.newMethod,
        [name]: value,
      },
    });
  };

  handleSubmit = (e) => {
    e.preventDefault();
    console.log(this.state.newMethod);
    this.insertOrUpdateMethod(this.state.newMethod);
  };

  handleDelete = (e, row) => {
    this.refs.modalDelete.toggle(e, row.methodID);
  };

  componentWillMount() {
    this.fetchListMethod();
    this.setState({
      userName: this.props.userName,
    });
  }

  render() {
    var { modal, listMethod, newMethod, typeModal, userName } = this.state;
    var { user } = this.props;
    return (
      <div>
        <Modal isOpen={modal}>
          <ModalHeader>
            {typeModal === "New" ? "Add new Method" : "Edit Method"}
          </ModalHeader>
          <FormWithConstraints
            ref={(formWithConstraints) => (this.form = formWithConstraints)}
            onSubmit={(e) => this.handleSubmit(e)}
          >
            <ModalBody>
              <FormGroup>
                <FormControlLabel for="levelID">Method ID</FormControlLabel>
                <FormControlInput
                  name="levelID"
                  onChange={(e) => this.onHandleChange(e)}
                  placeholder="This will be automatically created"
                  value={!!newMethod.levelID ? newMethod.levelID : ""}
                  disabled={true}
                />
              </FormGroup>
              <FormGroup>
                <FormControlLabel for="methodName">
                  Name<strong style={{ color: "red" }}>*</strong>
                </FormControlLabel>
                <FormControlInput
                  name="methodName"
                  onChange={(e) => this.onHandleChange(e)}
                  placeholder="Input Method Name"
                  value={!!newMethod.methodName ? newMethod.methodName : ""}
                />
                <FieldFeedbacks for="methodName">
                  <FieldFeedback when={(value) => value === ""} warning>
                    <strong style={{ color: "red" }}>Required Field</strong>
                  </FieldFeedback>
                </FieldFeedbacks>
              </FormGroup>
              <FormGroup>
                <FormControlLabel for="name">
                  Note<strong style={{ color: "red" }}>*</strong>
                </FormControlLabel>
                <FormControlInput
                  name="note"
                  onChange={(e) => this.onHandleChange(e)}
                  placeholder="Input Method Note"
                  value={!!newMethod.note ? newMethod.note : ""}
                />
                <FieldFeedbacks for="name">
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
        <TabHeader id={6} user={user} />
        <ModalDelete
          ref="modalDelete"
          userName={userName}
          fetchListMethod={this.fetchListMethod}
        />
        <div className="main-container">
          <PageHeader menu={"Convention"} subMenu={"Method"} />
          <div className="content-wrapper">
            <div className="row gutters">
              <div className="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                <div className="card">
                  <div className="card-body">
                    <Row>
                      <button
                        className="btn btn-outline-success btn-rounded"
                        onClick={this.toggle}
                      >
                        Insert
                      </button>
                    </Row>
                    <div className="table-responsive">
                      <ReactTable
                        data={listMethod}
                        showPageSizeOptions={true}
                        id="table"
                        showPagination={true}
                        columns={[
                          {
                            Header: "ID",
                            id: "methodID",
                            accessor: (d) => d.methodID,
                            Cell: ({ row }) => (
                              <div>{row._original.methodID}</div>
                            ),
                            maxWidth: 50,
                          },
                          {
                            Header: "Method Point",
                            id: "methodName",
                            accessor: (d) => d.methodName,
                            Cell: ({ row }) => (
                              <div>{row._original.methodName}</div>
                            ),
                          },
                          {
                            Header: "Method Note",
                            id: "Note",
                            accessor: (d) => d.note,
                            Cell: ({ row }) => <div>{row._original.note}</div>,
                          },
                          {
                            Header: "Action",
                            id: "levelID",
                            style: { textAlign: "center" },
                            accessor: (d) => d.levelID,
                            Cell: ({ row }) => (
                              <Permission
                                user={user}
                                hasPermission={PermissionEnum.ADMIN.value}
                              >
                                <div>
                                  <button
                                    className="btn btn-outline-primary btn-rounded"
                                    onClick={(e) =>
                                      this.toggle(e, listMethod[row._index])
                                    }
                                  >
                                    Edit
                                  </button>
                                  <button
                                    className="btn btn-outline-secondary btn-rounded"
                                    onClick={(e) =>
                                      this.handleDelete(
                                        e,
                                        listMethod[row._index]
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
