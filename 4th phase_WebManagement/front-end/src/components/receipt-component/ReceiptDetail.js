/* eslint-disable no-undef */
import React, { Component } from "react";
import TabHeader from "../tab-component/TabHeader";
import { Button, Modal, ModalHeader, ModalFooter, ModalBody } from "reactstrap";
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
import { Link, Redirect } from "react-router-dom";
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
import ModalDelete from "./ModalDeleteDetail";
import PageHeader from "../PageHeader";
import Permission from "../../Permission";
import { PermissionEnum } from "../../PermissionEnum";
import Select from "react-select";
import { typeModalEnum, TypeModalEnum } from "./../../TypeModalEnum";
import NumberFormat from "react-number-format";

class InvoiceDetail extends Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      listBook: [],
      newBook: {
        bookid: "",
        bookTitle: "",
        amount: 0,
        price: 0,
      },
      listBookInsert: [],
      supplierName: "",
      date: "",
      receiptID: "",
      typeModal: typeModalEnum.ModalAdd.value,
    };
  }

  fetchReceiptDetail = () => {
    axios({
      method: "GET",
      url:
        path_variable.Receipt.GET_RECEIPT_DETAI +
        this.props.match.match.params.receiptid,
      data: null,
    })
      .then((res) => {
        this.setState({
          supplierName: !!res.data[0].supplierName
            ? res.data[0].supplierName
            : "",
          date: !!res.data[0].date ? res.data[0].date : new Date(),
        });
      })
      .catch((err) => {
        NotificationManager.error(
          "Error message",
          "Show Receipt Detail failed"
        );
      });
  };

  fetchListBookInsert = () => {
    axios({
      method: "GET",
      url: path_variable.Book.GET_LIST_BOOK,
      data: null,
    })
      .then((res) => {
        var temp = [
          {
            bookid: "",
            bookTitle: "",
          },
        ];
        res.data.map((data) => {
          var list = {
            bookid: data.bookID,
            bookTitle: data.bookTitle,
          };
          temp.push(list);
        });
        this.setState({
          listBookInsert: temp,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error(
          "Error message",
          "Show Receipt Detail failed"
        );
      });
  };

  onHandleChange = (e) => {
    var target = e.target;
    var name = target.name;
    var value = target.value;
    this.setState({
      newBook: {
        ...this.state.newBook,
        [name]: value,
      },
    });
  };

  componentDidMount() {
    const param = this.props.match.match.params.receiptid;
    this.setState({
      receiptID: param,
      userName: this.props.userName,
    });
    this.fetchReceiptDetail();
    this.fetchListBookInsert();
    this.fetchListBookDetail(param);
  }

  insertOrReceiptDetail = (Book) => {
    if (this.state.typeModal === typeModalEnum.ModalEdit.value) {
      axios({
        method: "PUT",
        url:
          path_variable.Receipt.UPDATE_RECEIPT_DETAIL +
          this.state.receiptID +
          "&userName=" +
          this.state.userName,
        //   path_variable.Receipt.UPDATE_AUTHOR + this.state.userName,
        data: Book,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListBookDetail(this.state.receiptID);
            NotificationManager.success(
              "Success message",
              "Update Receipt Detail successfully"
            );
          } else {
            NotificationManager.error(
              "Error message",
              "Update Receipt Detail failed"
            );
          }
        })
        .catch((err) => {
          console.log(err);
          NotificationManager.error(
            "Error message",
            "Update Receipt Detail failed"
          );
        });
    } else {
      axios({
        method: "POST",
        url:
          path_variable.Receipt.ADD_RECEIPT_DETAIL +
          this.state.receiptID +
          "&userName=" +
          this.state.userName,
        // url: path_variable.Receipt.ADD_RECEIPT_DETAI + this.state.userName,
        data: Book,
      })
        .then((res) => {
          if (res.data) {
            this.fetchListBookDetail(this.state.receiptID);
            NotificationManager.success(
              "Success message",
              "Add Receipt Detail successfully"
            );
          } else {
            NotificationManager.error(
              "Error message",
              "Add Receipt Detail failed"
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

  toggle = (e, row) => {
    if (row === undefined) {
      this.setState({
        newBook: {},
        typeModal: typeModalEnum.ModalAdd.value,
      });
    } else {
      this.setState({
        newBook: {
          ...row,
        },
        typeModal: typeModalEnum.ModalEdit.value,
      });
    }
    this.setState({
      modal: !this.state.modal,
    });
  };

  handleDelete = (e, row) => {
    this.refs.modalDelete.toggle(e, row.bookid);
  };

  handleSubmit = (e) => {
    e.preventDefault();
    if (this.form.isValid()) {
      this.insertOrReceiptDetail(this.state.newBook);
    }
  };

  fetchListBookDetail = (receiptID) => {
    axios({
      method: "GET",
      url: path_variable.Receipt.GET_LIST_BOOK_RECEIPT_DETAIL + receiptID,
      data: null,
    })
      .then((res) => {
        this.setState({
          listBook: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error(
          "Error message",
          "Show Receipt Detail failed"
        );
      });
  };

  render() {
    var {
      listBook,
      supplierName,
      date,
      typeModal,
      modal,
      listBookInsert,
      newBook,
    } = this.state;
    var { user, userName } = this.props;
    const param = this.props.match.match.params.receiptid;
    return (
      <div>
        <ModalDelete
          receiptID={param}
          ref="modalDelete"
          userName={userName}
          fetchReceiptDetail={this.fetchReceiptDetail}
        />
        <TabHeader user={user} id={3} />
        <div className="main-container">
          <PageHeader menu={"Sale"} subMenu={param} />
          <div className="content-wrapper">
            <Link
              to={`${prefix2}/receipt/show/`}
              style={{
                margin: "0px 0 10px 10px",
              }}
              className="btn btn-outline-secondary btn-rounded"
            >
              Go back
            </Link>
            <h3 className="text-center">{param}</h3>
            <div className="row gutters">
              <div className="col-xl-12 col-lg-12 col-md-8 col-sm-12">
                <div className="card">
                  <div className="card-header">
                    <div className="card-title">Receipt Detail</div>
                  </div>
                  <div className="card-body">
                    <div className="row gutters">
                      <div className="col-xl-1 col-lg-1 col-md-1 col-sm-1 col-12"></div>
                      <div className="col-xl-5 col-lg-5 col-md-5 col-sm-5 col-12">
                        <div className="form-group">
                          <label htmlFor="inputEmail">Supplier</label>
                          <input
                            type="text"
                            className="form-control"
                            value={supplierName}
                            disabled={true}
                          />
                        </div>
                      </div>
                      <div className="col-xl-5 col-lg-5 col-md-5 col-sm-5 col-12">
                        <div className="form-group">
                          <label htmlFor="lastName">Date</label>
                          <input
                            type="text"
                            className="form-control"
                            value={date}
                            disabled={true}
                          />
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="col-xl-12 col-lg-12 col-md-8 col-sm-12">
                <div className="card">
                  <div className="card-header">
                    <div className="card-title">List books</div>
                  </div>
                  <div className="card-body">
                    <div className="row gutters">
                      <button
                        className="btn btn-outline-primary btn-rounded"
                        onClick={this.toggle}
                      >
                        Insert
                      </button>
                      <div className="table-responsive">
                        {
                          <ReactTable
                            data={listBook}
                            showPageSizeOptions={true}
                            id="table"
                            showPagination={true}
                            columns={[
                              {
                                Header: "#",
                                id: "index",
                                accessor: (d) => d.bookID,
                                Cell: ({ row }) => <div>{row._index + 1}</div>,
                                maxWidth: 50,
                              },
                              {
                                Header: "ID",
                                id: "bookID",
                                accessor: (d) => d.bookid,
                                Cell: ({ row }) => (
                                  <div>{row._original.bookid}</div>
                                ),
                                maxWidth: 210,
                              },
                              {
                                Header: "Amount",
                                id: "amount",
                                accessor: (d) => d.amount,
                                Cell: ({ row }) => (
                                  <div>{row._original.amount}</div>
                                ),
                                maxWidth: 150,
                              },
                              {
                                Header: "Price",
                                id: "price",
                                accessor: (d) => d.price,
                                Cell: ({ row }) => (
                                  <div>{row._original.price}</div>
                                ),
                                maxWidth: 150,
                              },
                              {
                                Header: "Action",
                                id: "bookid",
                                style: { textAlign: "center" },
                                accessor: (d) => d.bookid,
                                Cell: ({ row }) => (
                                  <Permission
                                    user={user}
                                    hasPermission={PermissionEnum.ADMIN.value}
                                  >
                                    <div>
                                      <button
                                        className="btn btn-outline-primary btn-rounded"
                                        onClick={(e) =>
                                          this.toggle(e, listBook[row._index])
                                        }
                                      >
                                        Edit
                                      </button>
                                      <button
                                        className="btn btn-outline-secondary btn-rounded"
                                        onClick={(e) =>
                                          this.handleDelete(
                                            e,
                                            listBook[row._index]
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
                        }
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="w-100"></div>
            </div>
          </div>
        </div>
        <Modal isOpen={modal}>
          <ModalHeader>
            {typeModal === typeModalEnum.ModalAdd.value
              ? "Add new Book"
              : "Edit Book"}
          </ModalHeader>
          <FormWithConstraints
            ref={(formWithConstraints) => (this.form = formWithConstraints)}
            onSubmit={(e) => this.handleSubmit(e)}
          >
            <ModalBody>
              <FormGroup>
                <FormControlLabel htmlFor="bookid">Book Name</FormControlLabel>
                {typeModal === typeModalEnum.ModalAdd.value ? (
                  <select
                    className="form-control"
                    name="bookid"
                    id="bookid"
                    value={newBook.bookid}
                    onChange={(e) => this.onHandleChange(e)}
                    required
                  >
                    {listBookInsert.map((data) => {
                      return (
                        <option key={data.bookid} value={data.bookid}>
                          {data.bookTitle}
                        </option>
                      );
                    })}
                  </select>
                ) : (
                  <FormControlInput value={newBook.bookid} disabled={true} />
                )}
                <FieldFeedbacks htmlFor="bookid">
                  <FieldFeedback when={(value) => value === ""} warning>
                    <strong style={{ color: "red" }}> Required Field</strong>
                  </FieldFeedback>
                </FieldFeedbacks>
              </FormGroup>
              <FormGroup>
                <FormControlLabel htmlFor="amount">
                  Amount<strong style={{ color: "red" }}>*</strong>
                </FormControlLabel>
                <NumberFormat
                  name="amount"
                  id="amount"
                  value={!!newBook.amount ? newBook.amount : ""}
                  customInput={FormControlInput}
                  thousandSeparator={true}
                  decimalScale={2}
                  allowNegative={false}
                  onValueChange={(values) => {
                    const { floatValue } = values;
                    this.setState({
                      newBook: {
                        ...this.state.newBook,
                        ["amount"]: floatValue,
                      },
                    });
                  }}
                  required
                />
                <FieldFeedbacks htmlFor="amount">
                  <FieldFeedback when={(value) => value === 0} warning>
                    <strong style={{ color: "red" }}> Required Field</strong>
                  </FieldFeedback>
                </FieldFeedbacks>
              </FormGroup>
              <FormGroup>
                <FormControlLabel htmlFor="price">
                  Price<strong style={{ color: "red" }}>*</strong>
                </FormControlLabel>
                <NumberFormat
                  name="price"
                  id="price"
                  value={
                    !!this.state.newBook.price ? this.state.newBook.price : ""
                  }
                  customInput={FormControlInput}
                  thousandSeparator={true}
                  decimalScale={2}
                  allowNegative={false}
                  onValueChange={(values) => {
                    const { floatValue } = values;
                    this.setState({
                      newBook: {
                        ...this.state.newBook,
                        ["price"]: floatValue,
                      },
                    });
                  }}
                  required
                />
                <FieldFeedbacks htmlFor="price">
                  <FieldFeedback when={(value) => value === 0} warning>
                    <strong style={{ color: "red" }}> Required Field</strong>
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
        <NotificationContainer />
      </div>
    );
  }
}

export default InvoiceDetail;
