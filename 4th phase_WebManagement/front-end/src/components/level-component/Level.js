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

export default class Level extends Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      listLevel: [],
      newLevel: {
        levelID: "",
        point: 0,
        name: "",
      },
      typeModal: "New",
      userName: "",
    };
  }

  toggle = (e, row) => {
    if (row === undefined) {
      this.setState({
        newLevel: {},
        typeModal: "New",
      });
    } else {
      this.setState({
        newLevel: {
          ...row,
        },
        typeModal: "Edit",
      });
    }
    this.setState({
      modal: !this.state.modal,
    });
  };

  fetchListLevel = () => {
    axios({
      method: "GET",
      url: path_variable.Level.GET_LIST_LEVEL,
      data: null,
    })
      .then((res) => {
        this.setState({
          listLevel: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Show list Level failed");
      });
  };

  insertOrUpdateLevel = (Level) => {
    if (this.state.typeModal === "Edit") {
      axios({
        method: "PUT",
        url: path_variable.Level.UPDATE_LEVEL + this.state.userName,
        data: Level,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListLevel();
            NotificationManager.success(
              "Success message",
              "Update Level successfully"
            );
          } else {
            NotificationManager.error("Error message", "Update Level failed");
          }
        })
        .catch((err) => {
          console.log(err);
          NotificationManager.error("Error message", "Update Level failed");
        });
    } else {
      axios({
        method: "POST",
        url: path_variable.Level.ADD_LEVEL + this.state.userName,
        data: Level,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListLevel();
            NotificationManager.success(
              "Success message",
              "Add Level successfully"
            );
          } else {
            NotificationManager.error("Error message", "Add Level failed");
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
      newLevel: {
        ...this.state.newLevel,
        [name]: value,
      },
    });
  };

  handleSubmit = (e) => {
    e.preventDefault();
    console.log(this.state.newLevel);
    this.insertOrUpdateLevel(this.state.newLevel);
  };

  handleDelete = (e, row) => {
    this.refs.modalDelete.toggle(e, row.levelID);
  };

  componentWillMount() {
    this.fetchListLevel();
    this.setState({
      userName: this.props.userName,
    });
  }

  render() {
    var { modal, listLevel, newLevel, typeModal, userName } = this.state;
    var { user } = this.props;
    return (
      <div>
        <Modal isOpen={modal}>
          <ModalHeader>
            {typeModal === "New" ? "Add new Level" : "Edit Level"}
          </ModalHeader>
          <FormWithConstraints
            ref={(formWithConstraints) => (this.form = formWithConstraints)}
            onSubmit={(e) => this.handleSubmit(e)}
          >
            <ModalBody>
              <FormGroup>
                <FormControlLabel for="levelID">Level ID</FormControlLabel>
                <FormControlInput
                  name="levelID"
                  onChange={(e) => this.onHandleChange(e)}
                  placeholder="This will be automatically created"
                  value={!!newLevel.levelID ? newLevel.levelID : ""}
                  disabled={true}
                />
              </FormGroup>
              <FormGroup>
                <FormControlLabel for="name">
                  Name<strong style={{ color: "red" }}>*</strong>
                </FormControlLabel>
                <FormControlInput
                  name="name"
                  onChange={(e) => this.onHandleChange(e)}
                  placeholder="Input Level Name"
                  value={!!newLevel.name ? newLevel.name : ""}
                />
                <FieldFeedbacks for="name">
                  <FieldFeedback when={(value) => value === ""} warning>
                    <strong style={{ color: "red" }}>Required Field</strong>
                  </FieldFeedback>
                </FieldFeedbacks>
              </FormGroup>
              <FormGroup>
                <FormControlLabel for="point">
                  Point<strong style={{ color: "red" }}>*</strong>
                </FormControlLabel>
                <FormControlInput
                  name="point"
                  onChange={(e) => this.onHandleChange(e)}
                  placeholder="Input Level Point"
                  value={!!newLevel.point ? newLevel.point : ""}
                />
                <FieldFeedbacks for="point">
                  <FieldFeedback when={(value) => value === ""} warning>
                    <strong style={{ color: "red" }}>Required Field</strong>
                  </FieldFeedback>
                </FieldFeedbacks>
              </FormGroup>
            </ModalBody>
            <ModalFooter>
              <Button
                color="primary"
                // onClick={(e) => {
                //   if (!!this.form) {
                //     this.form.validateFields();
                //     // customValidateFields(this.form);
                //   }
                // }}
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
          fetchListLevel={this.fetchListLevel}
        />
        <div className="main-container">
          <PageHeader menu={"Convention"} subMenu={"Level"} />
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
                        data={listLevel}
                        showPageSizeOptions={true}
                        id="table"
                        showPagination={true}
                        columns={[
                          {
                            Header: "ID",
                            id: "levelID",
                            accessor: (d) => d.levelID,
                            Cell: ({ row }) => (
                              <div>{row._original.levelID}</div>
                            ),
                          },
                          {
                            Header: "Level Name",
                            id: "name",
                            accessor: (d) => d.name,
                            Cell: ({ row }) => <div>{row._original.name}</div>,
                          },
                          {
                            Header: "Level Point",
                            id: "point",
                            accessor: (d) => d.point,
                            Cell: ({ row }) => <div>{row._original.point}</div>,
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
                                <button //disabled={isCheckEditing}//disabled={!isPDClerk || isPoClosed || editingByAnother}
                                  className="btn btn-outline-primary btn-rounded"
                                  onClick={(e) =>
                                    this.toggle(e, listLevel[row._index])
                                  }
                                >
                                  Edit
                                </button>
                                <button //disabled={isCheckEditing}//disabled={!isPDClerk || isPoClosed || editingByAnother}
                                  className="btn btn-outline-secondary btn-rounded"
                                  onClick={(e) =>
                                    this.handleDelete(e, listLevel[row._index])
                                  }
                                >
                                  Delete
                                </button>
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
