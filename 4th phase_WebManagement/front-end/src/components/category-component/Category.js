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
import { PermissionEnum } from "../../PermissionEnum";
import Permission from "../../Permission";

export default class Category extends Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      listCategory: [],
      newCategory: {
        categoryID: "",
        categoryName: "",
      },
      typeModal: "New",
      userName: "",
    };
  }

  toggle = (e, row) => {
    if (row === undefined) {
      this.setState({
        newCategory: {},
        typeModal: "New",
      });
    } else {
      this.setState({
        newCategory: {
          ...row,
        },
        typeModal: "Edit",
      });
    }
    this.setState({
      modal: !this.state.modal,
    });
  };

  fetchListCategory = () => {
    axios({
      method: "GET",
      url: path_variable.Category.GET_LIST_CATEGORY,
      data: null,
    })
      .then((res) => {
        this.setState({
          listCategory: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Show list Category failed");
      });
  };

  insertOrUpdateCategory = (Category) => {
    if (this.state.typeModal === "Edit") {
      axios({
        method: "PUT",
        url: path_variable.Category.UPDATE_CATEGORY + this.state.userName,
        data: Category,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListCategory();
            NotificationManager.success(
              "Success message",
              "Update Category successfully"
            );
          } else {
            NotificationManager.error(
              "Error message",
              "Update Category failed"
            );
          }
        })
        .catch((err) => {
          console.log(err);
          NotificationManager.error("Error message", "Update Category failed");
        });
    } else {
      axios({
        method: "POST",
        url: path_variable.Category.ADD_CATEGORY + this.state.userName,
        data: Category,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListCategory();
            NotificationManager.success(
              "Success message",
              "Update Category successfully"
            );
          } else {
            NotificationManager.error(
              "Error message",
              "Update Category failed"
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
      newCategory: {
        ...this.state.newCategory,
        [name]: value,
      },
    });
  };

  handleSubmit = (e) => {
    e.preventDefault();
    this.insertOrUpdateCategory(this.state.newCategory);
  };

  handleDelete = (e, row) => {
    this.refs.modalDelete.toggle(e, row.categoryID);
  };

  componentWillMount() {
    this.fetchListCategory();
    this.setState({
      userName: this.props.userName,
    });
  }

  render() {
    var { modal, listCategory, newCategory, typeModal, userName } = this.state;
    var { user } = this.props;
    return (
      <div>
        <Modal isOpen={modal}>
          <ModalHeader>
            {typeModal === "New" ? "Add new Category" : "Edit Category"}
          </ModalHeader>
          <FormWithConstraints
            ref={(formWithConstraints) => (this.form = formWithConstraints)}
            onSubmit={(e) => this.handleSubmit(e)}
          >
            <ModalBody>
              <FormGroup>
                <FormControlLabel htmlFor="categoryID">
                  Category ID
                </FormControlLabel>
                <FormControlInput
                  name="categoryID"
                  onChange={(e) => this.onHandleChange(e)}
                  placeholder="This will be automatically created"
                  value={!!newCategory.categoryID ? newCategory.categoryID : ""}
                  disabled={true}
                />
              </FormGroup>
              <FormGroup>
                <FormControlLabel htmlFor="categoryName">
                  Name<strong style={{ color: "red" }}>*</strong>
                </FormControlLabel>
                <FormControlInput
                  name="categoryName"
                  onChange={(e) => this.onHandleChange(e)}
                  placeholder="Input category Name"
                  value={
                    !!newCategory.categoryName ? newCategory.categoryName : ""
                  }
                />
                <FieldFeedbacks htmlFor="categoryName">
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
        <TabHeader user={user} id={2} />
        <ModalDelete
          ref="modalDelete"
          userName={userName}
          fetchListCategory={this.fetchListCategory}
        />
        <div className="main-container">
          <PageHeader menu={"Products"} subMenu={"Category"} />
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
                        data={listCategory}
                        showPageSizeOptions={true}
                        id="table"
                        showPagination={true}
                        columns={[
                          {
                            Header: "ID",
                            id: "categoryID",
                            accessor: (d) => d.categoryID,
                            Cell: ({ row }) => (
                              <div>{row._original.categoryID}</div>
                            ),
                          },
                          {
                            Header: "Category Name",
                            id: "categoryName",
                            accessor: (d) => d.categoryName,
                            Cell: ({ row }) => (
                              <div>{row._original.categoryName}</div>
                            ),
                          },
                          {
                            Header: "Action",
                            id: "categoryID",
                            style: { textAlign: "center" },
                            accessor: (d) => d.categoryID,
                            Cell: ({ row }) => (
                              <Permission
                                user={user}
                                hasPermission={PermissionEnum.ADMIN.value}
                              >
                                <div>
                                  <button
                                    className="btn btn-outline-primary btn-rounded"
                                    onClick={(e) =>
                                      this.toggle(e, listCategory[row._index])
                                    }
                                  >
                                    Edit
                                  </button>
                                  <button
                                    className="btn btn-outline-secondary btn-rounded"
                                    onClick={(e) =>
                                      this.handleDelete(
                                        e,
                                        listCategory[row._index]
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
