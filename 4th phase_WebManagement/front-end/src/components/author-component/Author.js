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
import "./css/author.css";
import PageHeader from "./../PageHeader";
import Permission from "./../../Permission";
import { PermissionEnum } from "../../PermissionEnum";

class Author extends Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      listAuthor: [],
      newAuthor: {
        authorID: "",
        authorName: "",
      },
      typeModal: "New",
      userName: "",
    };
  }

  toggle = (e, row) => {
    if (row === undefined) {
      this.setState({
        newAuthor: {},
        typeModal: "New",
      });
    } else {
      this.setState({
        newAuthor: {
          ...row,
        },
        typeModal: "Edit",
      });
    }
    this.setState({
      modal: !this.state.modal,
    });
  };

  fetchListAuthor = () => {
    axios({
      method: "GET",
      url: path_variable.Author.GET_LIST_AUTHOR,
      data: null,
    })
      .then((res) => {
        this.setState({
          listAuthor: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Show list Author failed");
      });
  };

  insertOrUpdateAuthor = (Author) => {
    if (this.state.typeModal === "Edit") {
      axios({
        method: "PUT",
        url: path_variable.Author.UPDATE_AUTHOR + this.state.userName,
        data: Author,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListAuthor();
            NotificationManager.success(
              "Success message",
              "Update Author successfully"
            );
          } else {
            NotificationManager.error("Error message", "Update Author failed");
          }
        })
        .catch((err) => {
          console.log(err);
          NotificationManager.error("Error message", "Update Author failed");
        });
    } else {
      axios({
        method: "POST",
        url: path_variable.Author.ADD_AUTHOR + this.state.userName,
        data: Author,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListAuthor();
            NotificationManager.success(
              "Success message",
              "Update Author successfully"
            );
          } else {
            NotificationManager.error("Error message", "Add Author failed");
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
      newAuthor: {
        ...this.state.newAuthor,
        [name]: value,
      },
    });
  };

  handleSubmit = (e) => {
    e.preventDefault();
    if (this.form.isValid()) {
      this.insertOrUpdateAuthor(this.state.newAuthor);
    }
  };

  handleDelete = (e, row) => {
    this.refs.modalDelete.toggle(e, row.authorID);
  };

  componentWillMount() {
    this.fetchListAuthor();
    this.setState({
      userName: this.props.userName,
    });
  }

  render() {
    var { modal, listAuthor, newAuthor, typeModal, userName } = this.state;
    var { user } = this.props;
    return (
      <div>
        <Modal isOpen={modal}>
          <ModalHeader>
            {typeModal === "New" ? "Add new Author" : "Edit Author"}
          </ModalHeader>
          <FormWithConstraints
            ref={(formWithConstraints) => (this.form = formWithConstraints)}
            onSubmit={(e) => this.handleSubmit(e)}
          >
            <ModalBody>
              <FormGroup>
                <FormControlLabel htmlFor="authorID">
                  Author ID
                </FormControlLabel>
                <FormControlInput
                  name="authorID"
                  onChange={(e) => this.onHandleChange(e)}
                  placeholder="This will be automatically created"
                  value={!!newAuthor.authorID ? newAuthor.authorID : ""}
                  disabled={true}
                />
              </FormGroup>
              <FormGroup>
                <FormControlLabel htmlFor="authorName">
                  Name<strong style={{ color: "red" }}>*</strong>
                </FormControlLabel>
                <FormControlInput
                  name="authorName"
                  onChange={(e) => this.onHandleChange(e)}
                  placeholder="Input Author Name"
                  value={!!newAuthor.authorName ? newAuthor.authorName : ""}
                  required
                />
                <FieldFeedbacks htmlFor="authorName">
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
        <TabHeader id={2} user={user} />
        <ModalDelete
          ref="modalDelete"
          userName={userName}
          fetchListAuthor={this.fetchListAuthor}
        />
        <div className="main-container">
          <PageHeader menu={"Products"} subMenu={"Author"} />
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
                        data={listAuthor}
                        showPageSizeOptions={true}
                        id="table"
                        showPagination={true}
                        sortable={true}
                        columns={[
                          {
                            Header: "ID",
                            id: "authorID",
                            accessor: (d) => d.authorID,
                            Cell: ({ row }) => (
                              <div>{row._original.authorID}</div>
                            ),
                          },
                          {
                            Header: "Author Name",
                            id: "authorName",
                            accessor: (d) => d.authorName,
                            Cell: ({ row }) => (
                              <div>{row._original.authorName}</div>
                            ),
                          },
                          {
                            Header: "Action",
                            id: "authorID",
                            style: { textAlign: "center" },
                            accessor: (d) => d.authorID,
                            Cell: ({ row }) => (
                              <Permission
                                user={user}
                                hasPermission={PermissionEnum.ADMIN.value}
                              >
                                <div>
                                  <button
                                    className="btn btn-outline-primary btn-rounded"
                                    onClick={(e) =>
                                      this.toggle(e, listAuthor[row._index])
                                    }
                                  >
                                    Edit
                                  </button>
                                  <button
                                    className="btn btn-outline-secondary btn-rounded"
                                    onClick={(e) =>
                                      this.handleDelete(
                                        e,
                                        listAuthor[row._index]
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

export default Author;
