import React, { Component } from "react";
import TabHeader from "../tab-component/TabHeader";
import {
  Button,
  Row,
  Input,
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
import { PermissionEnum } from "../../PermissionEnum";
import Permission from "../../Permission";
import moment from "moment";
import { typeModalEnum } from "../../TypeModalEnum";

export default class Invoice extends Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      listReceipt: [],
      newReceipt: {
        receiptID: "",
        supplierName: "",
        // eslint-disable-next-line no-undef
        date: "2020-10-10",
      },
      listSupplier: [
        {
          value: "test",
          label: "abc",
        },
      ],
      typeModal: typeModalEnum.ModalAdd.value,
      userName: "",
    };
  }

  toggle = (e, row) => {
    if (row === undefined) {
      this.setState({
        newReceipt: {},
        typeModal: "New",
      });
    } else {
      this.setState({
        newReceipt: {
          ...row,
        },
        typeModal: "Edit",
      });
    }
    this.setState({
      modal: !this.state.modal,
    });
  };

  fetchListReceipt = () => {
    axios({
      method: "GET",
      url: path_variable.Receipt.GET_LIST_RECEIPT,
      data: null,
    })
      .then((res) => {
        this.setState({
          listReceipt: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Show list Receipt failed");
      });
  };

  fetchListSupplier() {
    axios({
      method: "GET",
      url: path_variable.Supplier.GET_LIST_SUPPLIER,
      data: null,
    })
      .then((res) => {
        var temp = [
          {
            supplierID: "",
            supplierName: "",
          },
        ];
        res.data.map((data) => {
          var list = {
            supplierID: data.supplierID,
            supplierName: data.supplierName,
          };
          temp.push(list);
        });
        this.setState({
          listSupplier: temp,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Show list Supplier failed");
      });
  }

  insertOrUpdateReceipt = (Receipt) => {
    if (this.state.typeModal === typeModalEnum.ModalEdit.value) {
      axios({
        method: "PUT",
        url: path_variable.Receipt.UPDATE_Receipt + this.state.userName,
        data: Receipt,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListReceipt();
            NotificationManager.success(
              "Success message",
              "Update Receipt successfully"
            );
          } else {
            NotificationManager.error("Error message", "Update Receipt failed");
          }
        })
        .catch((err) => {
          console.log(err);
          NotificationManager.error("Error message", "Update Receipt failed");
        });
    } else {
      axios({
        method: "POST",
        url: path_variable.Receipt.ADD_RECEIPT + this.state.userName,
        data: Receipt,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListReceipt();
            NotificationManager.success(
              "Success message",
              "Update Receipt successfully"
            );
          } else {
            NotificationManager.error("Error message", "Update Receipt failed");
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
      newReceipt: {
        ...this.state.newReceipt,
        [name]: value,
      },
    });
  };

  handleSubmit = (e) => {
    e.preventDefault();
    console.log(this.state.newReceipt);
    this.insertOrUpdateReceipt(this.state.newReceipt);
  };

  handleDelete = (e, row) => {
    this.refs.modalDelete.toggle(e, row.receiptID);
  };

  componentWillMount() {
    this.fetchListReceipt();
    this.fetchListSupplier();
    this.setState({
      userName: this.props.userName,
    });
  }

  handleStartChange = (date) => {
    console.log(date);
  };

  render() {
    var {
      modal,
      listReceipt,
      newReceipt,
      typeModal,
      userName,
      listSupplier,
    } = this.state;
    var { user } = this.props;
    return (
      <div>
        <Modal isOpen={modal}>
          <ModalHeader>
            {typeModal === typeModalEnum.ModalAdd.value
              ? "Add new Receipt"
              : "Edit Receipt"}
          </ModalHeader>
          <FormWithConstraints
            ref={(formWithConstraints) => (this.form = formWithConstraints)}
            onSubmit={(e) => this.handleSubmit(e)}
          >
            <ModalBody>
              <FormGroup>
                <FormControlLabel htmlFor="receiptID">
                  Receipt ID
                </FormControlLabel>
                <FormControlInput
                  name="receiptID"
                  onChange={(e) => this.onHandleChange(e)}
                  placeholder="This will be automatically created"
                  value={!!newReceipt.receiptID ? newReceipt.receiptID : ""}
                  disabled={true}
                />
              </FormGroup>
              <FormGroup>
                <FormControlLabel htmlFor="supplierID">
                  Supplier<strong style={{ color: "red" }}>*</strong>
                </FormControlLabel>
                <select
                  className="form-control"
                  name="supplierID"
                  id="supplierID"
                  value={newReceipt.supplierID}
                  onChange={(e) => this.onHandleChange(e)}
                  required
                >
                  {listSupplier.map((data) => {
                    return (
                      <option key={data.supplierID} value={data.supplierID}>
                        {data.supplierName}
                      </option>
                    );
                  })}
                </select>
                <FieldFeedbacks htmlFor="supplierID">
                  <FieldFeedback when={(value) => value === ""} warning>
                    <strong style={{ color: "red" }}>Required Field</strong>
                  </FieldFeedback>
                </FieldFeedbacks>
              </FormGroup>
              {/* <FormGroup>
                <FormControlLabel htmlFor="date">
                  Date<strong style={{ color: "red" }}>*</strong>
                </FormControlLabel>
                <Input
                  type="date"
                  value={moment(newReceipt.date).format("YYYY-MM-DD")}
                  name="date"
                  className="form-group"
                  id="datepicker"
                  onChange={(e) => {
                    this.onHandleChange(e);
                  }}
                />
                <FieldFeedbacks htmlFor="date">
                  <FieldFeedback when={(value) => value === ""} warning>
                    <strong style={{ color: "red" }}>Required Field</strong>
                  </FieldFeedback>
                </FieldFeedbacks>
              </FormGroup> */}
            </ModalBody>
            <ModalFooter>
              <Button
                type="submit"
                color="primary"
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
        <TabHeader id={3} user={user} />
        <ModalDelete
          ref="modalDelete"
          userName={userName}
          fetchListReceipt={this.fetchListReceipt}
        />
        <div className="main-container">
          <PageHeader menu={"Sale"} subMenu={"Receipt"} />
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
                        data={listReceipt}
                        showPageSizeOptions={true}
                        id="table"
                        showPagination={true}
                        columns={[
                          {
                            Header: "#",
                            id: "index",
                            accessor: (d) => d.receiptID,
                            Cell: ({ row }) => <div>{row._index + 1}</div>,
                            maxWidth: 50,
                          },
                          {
                            Header: "ID",
                            id: "receiptID",
                            accessor: (d) => d.receiptID,
                            Cell: ({ row }) => (
                              <div>{row._original.receiptID}</div>
                            ),
                            maxWidth: 200,
                          },
                          {
                            Header: "Supplier",
                            id: "supplierName",
                            accessor: (d) => d.supplierName,
                            Cell: ({ row }) => (
                              <div>{row._original.supplierName}</div>
                            ),
                            maxWidth: 400,
                          },
                          {
                            Header: "DATE",
                            id: "receiptDate",
                            accessor: (d) => d.date,
                            Cell: ({ row }) => <div>{row._original.date}</div>,
                            maxWidth: 200,
                          },
                          {
                            Header: "Action",
                            id: "receiptID",
                            style: { textAlign: "center" },
                            accessor: (d) => d.receiptID,
                            Cell: ({ row }) => (
                              <div>
                                <Link
                                  to={
                                    `${prefix2}/receipt/show/` +
                                    row._original.receiptID
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
                                        listReceipt[row._index]
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
