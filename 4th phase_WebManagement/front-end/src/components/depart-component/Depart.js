import React, { Component } from "react";
import TabHeader from "./../tab-component/TabHeader";
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
import PageHeader from "./../PageHeader";
import Permission from "./../../Permission";
import { PermissionEnum } from "../../PermissionEnum";

export default class Depart extends Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      listDepart: [],
      newDepart: {
        departID: "",
        departName: "",
      },
      typeModal: "New",
      userName: "",
    };
  }

  toggle = (e, row) => {
    if (row === undefined) {
      this.setState({
        newDepart: {},
        typeModal: "New",
      });
    } else {
      this.setState({
        newDepart: {
          ...row,
        },
        typeModal: "Edit",
      });
    }
    this.setState({
      modal: !this.state.modal,
    });
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
        console.log(err);
        NotificationManager.error("Error message", "Show list Depart failed");
      });
  };

  insertOrUpdateDepart = (Depart) => {
    if (this.state.typeModal === "Edit") {
      axios({
        method: "PUT",
        url: path_variable.Depart.UPDATE_DEPART + this.state.userName,
        data: Depart,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListDepart();
            NotificationManager.success(
              "Success message",
              "Update Depart successfully"
            );
          } else {
            NotificationManager.error("Error message", "Update Depart failed");
          }
        })
        .catch((err) => {
          console.log(err);
          NotificationManager.error("Error message", "Update Depart failed");
        });
    } else {
      axios({
        method: "POST",
        url: path_variable.Depart.ADD_DEPART + this.state.userName,
        data: Depart,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListDepart();
            NotificationManager.success(
              "Success message",
              "Update Depart successfully"
            );
          } else {
            NotificationManager.error("Error message", "Add Depart failed");
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
      newDepart: {
        ...this.state.newDepart,
        [name]: value,
      },
    });
  };

  handleSubmit = (e) => {
    e.preventDefault();
    if (this.form.isValid()) {
      this.insertOrUpdateDepart(this.state.newDepart);
    }
  };

  handleDelete = (e, row) => {
    this.refs.modalDelete.toggle(e, row.departID);
  };

  componentWillMount() {
    this.fetchListDepart();
    this.setState({
      userName: this.props.userName,
    });
  }

  render() {
    var { modal, listDepart, newDepart, typeModal, userName } = this.state;
    var { user } = this.props;
    return (
      <div>
        <Modal isOpen={modal}>
          <ModalHeader>
            {typeModal === "New" ? "Add new Depart" : "Edit Depart"}
          </ModalHeader>
          <FormWithConstraints
            ref={(formWithConstraints) => (this.form = formWithConstraints)}
            onSubmit={(e) => this.handleSubmit(e)}
          >
            <ModalBody>
              <FormGroup>
                <FormControlLabel htmlFor="departID">
                  Depart ID
                </FormControlLabel>
                <FormControlInput
                  name="departID"
                  onChange={(e) => this.onHandleChange(e)}
                  placeholder="This will be automatically created"
                  value={!!newDepart.departID ? newDepart.departID : ""}
                  disabled={true}
                />
              </FormGroup>
              <FormGroup>
                <FormControlLabel htmlFor="departName">
                  Name<strong style={{ color: "red" }}>*</strong>
                </FormControlLabel>
                <FormControlInput
                  name="departName"
                  onChange={(e) => this.onHandleChange(e)}
                  placeholder="Input Depart Name"
                  value={!!newDepart.departName ? newDepart.departName : ""}
                  required
                />
                <FieldFeedbacks htmlFor="departName">
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
        <TabHeader id={5} user={user} />
        <ModalDelete
          ref="modalDelete"
          userName={userName}
          fetchListDepart={this.fetchListDepart}
        />
        <div className="main-container">
          <PageHeader menu={"Products"} subMenu={"Depart"} />
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
                        data={listDepart}
                        showPageSizeOptions={true}
                        id="table"
                        showPagination={true}
                        sortable={true}
                        columns={[
                          {
                            Header: "ID",
                            id: "departID",
                            accessor: (d) => d.departID,
                            Cell: ({ row }) => (
                              <div>{row._original.departID}</div>
                            ),
                          },
                          {
                            Header: "Depart Name",
                            id: "departName",
                            accessor: (d) => d.departName,
                            Cell: ({ row }) => (
                              <div>{row._original.departName}</div>
                            ),
                          },
                          {
                            Header: "Action",
                            id: "departID",
                            style: { textAlign: "center" },
                            accessor: (d) => d.departID,
                            Cell: ({ row }) => (
                              <Permission
                                user={user}
                                hasPermission={PermissionEnum.ADMIN.value}
                              >
                                <div>
                                  <button
                                    className="btn btn-outline-primary btn-rounded"
                                    onClick={(e) =>
                                      this.toggle(e, listDepart[row._index])
                                    }
                                  >
                                    Edit
                                  </button>
                                  <button
                                    className="btn btn-outline-secondary btn-rounded"
                                    onClick={(e) =>
                                      this.handleDelete(
                                        e,
                                        listDepart[row._index]
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

// export default Depart;