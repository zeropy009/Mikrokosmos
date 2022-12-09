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
import ModalDelete from "./ModalDelete";
import PageHeader from "../PageHeader";
import Permission from "../../Permission";
import { PermissionEnum } from "../../PermissionEnum";
import ModalInvoice from "./ModalInvoice";
import ModalBookInvoice from "./ModalBookInvoice";
import ModalShipInfo from "./ModalShipInfo";

class InvoiceDetail extends Component {
  constructor(props) {
    super(props);
    this.state = {
      listBook: [],
      listCustomer: [],
      newShipInfo: {
        fullName: "",
        shipDate: "",
        address: "",
        phone: "",
      },
      total: 0,
      newInvoice: {
        invoiceID: "",
        solDate: "",
        customerID: "",
        staffID: "",
        discount: 0,
        shipStatus: "",
        payStatus: "",
        methodID: 0,
      },
    };
  }

  fetchListBookInvoice = (invoiceID) => {
    axios({
      method: "GET",
      url: path_variable.Invoice.GET_BOOK_INVOICE + invoiceID,
      data: null,
    })
      .then((res) => {
        this.setState({
          listBook: res.data,
        });
        res.data.map((data) => {
          this.setState({
            total: this.state.total + data.total,
          });
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Show list Book failed");
      });
  };

  fetchShipInforDetail = (invoiceID) => {
    axios({
      method: "GET",
      url: path_variable.Invoice.GET_SHIP_INFOR_INVOICE + invoiceID,
      data: null,
    })
      .then((res) => {
        this.setState({
          newShipInfo: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error(
          "Error message",
          "Get Shipping information failed"
        );
      });
  };

  fetchInvoiceDetail = (invoiceID) => {
    axios({
      method: "GET",
      url: path_variable.Invoice.GET_INVOICE_DETAIL + invoiceID,
      data: null,
    })
      .then((res) => {
        this.setState({
          newInvoice: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error(
          "Error message",
          "Get Shipping information failed"
        );
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

  componentDidMount() {
    const param = this.props.match.match.params.invoiceid;
    this.fetchListBookInvoice(param);
    this.fetchInvoiceDetail(param);
    this.fetchShipInforDetail(param);
    this.fetchListCustomer();
  }

  handleEditInvoice = (e) => {
    this.refs.modalInvoice.toggle(e, this.state.newInvoice);
  };

  handleEditShipInfor = (e) => {
    this.refs.modalShipInfo.toggle(e, this.state.newShipInfo);
  };

  toggleModalBook = (e, row) => {
    var temp = {
      ...row,
      discount: !!this.state.newInvoice.discount ? this.state.newInvoice.discount : 0 ,
    };
    this.refs.modalBookInvoice.toggle(e, temp);
  };

  handleDelete = (e,row )=>{
    this.refs.modalDelete.toggle(e,row.bookID);
  }

  render() {
    var {
      listCustomer,
      listBook,
      newShipInfo,
      newInvoice,
      total,
    } = this.state;
    var { user, userName } = this.props;
    const param = this.props.match.match.params.invoiceid;
    return (
      <div>
        <ModalShipInfo
          ref="modalShipInfo"
          invoiceID={param}
          fetchShipInforDetail={this.fetchShipInforDetail}
          //fetchInvoiceDetail={this.fetchInvoiceDetail}
          userName={userName}
        />
        <ModalDelete
          ref="modalDelete"
          userName={userName}
          invoiceID={param}
          fetchListBookInvoice={this.fetchListBookInvoice}
          type={`Book`}
        />
        <ModalBookInvoice
          ref="modalBookInvoice"
          invoiceID={param}
          fetchListBookInvoice={this.fetchListBookInvoice}
          fetchInvoiceDetail={this.fetchInvoiceDetail}
          userName={userName}
        />
        <ModalInvoice
          ref="modalInvoice"
          listCustomer={listCustomer}
          fetchInvoiceDetail={this.fetchInvoiceDetail}
          userName={userName}
        />
        <TabHeader id={4} user={user} />
        <div className="main-container">
          <PageHeader menu={"Sale"} subMenu={param} />
          <div className="content-wrapper">
            <Link
              to={`${prefix2}/invoice/show/`}
              style={{
                margin: "0px 0 10px 10px",
              }}
              className="btn btn-outline-secondary btn-rounded"
            >
              Go back
            </Link>
            <h3 className="text-center">{param}</h3>
            <div className="row gutters">
              <div className="col-xl-8 col-lg-8 col-md-8 col-sm-12">
                <div className="card">
                  <div className="card-header">
                    <div className="card-title">Invoice Detail</div>
                  </div>
                  <div className="card-body">
                    <div className="row gutters">
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                        <div className="form-group">
                          <label htmlFor="lastName">Customer</label>
                          <select
                            value={newInvoice.customerID}
                            className="form-control"
                            disabled
                          >
                            {listCustomer.map((data, index) => {
                              return (
                                <option key={index} value={data.customerID}>
                                  {data.customerName} - {data.customerID}
                                </option>
                              );
                            })}
                          </select>
                        </div>
                      </div>
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                        <div className="form-group">
                          <label htmlFor="inputEmail">Staff</label>
                          <input
                            type="text"
                            className="form-control"
                            placeholder="Total bill"
                            value={newInvoice.staffID}
                            disabled
                          />
                        </div>
                      </div>
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                        <div className="form-group">
                          <label htmlFor="lastName">Discount</label>
                          <input
                            type="text"
                            className="form-control"
                            placeholder="Discount"
                            value={newInvoice.discount}
                            disabled
                          />
                        </div>
                      </div>
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                        <div className="form-group">
                          <label htmlFor="lastName">Total</label>
                          <input
                            type="text"
                            className="form-control"
                            placeholder="Total bill"
                            value={total}
                            disabled
                          />
                        </div>
                      </div>
                      <div className="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-12">
                        <div className="form-group">
                          <label htmlFor="lastName">Sold Date</label>
                          <input
                            type="text"
                            className="form-control"
                            placeholder="Sold date"
                            value={newInvoice.solDate}
                            disabled
                          />
                        </div>
                      </div>
                      <div className="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-12">
                        <div className="form-group">
                          <label htmlFor="inputEmail">Payment Method</label>
                          <select
                            className="form-control"
                            value={newInvoice.methodID}
                            disabled
                          >
                            <option value="1">Tiền mặt</option>
                            <option value="2">Chuyển khoản</option>
                          </select>
                        </div>
                      </div>
                      <div className="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-12">
                        <div className="form-group">
                          <label htmlFor="inputEmail">Payment Status</label>
                          <select
                            className="form-control"
                            value={newInvoice.payStatus}
                            disabled
                          >
                            <option value="Chưa thanh toán">
                              Chưa thanh toán
                            </option>
                            <option value="Đã thanh toán">Đã thanh toán</option>
                          </select>
                        </div>
                      </div>
                    </div>
                    <Permission
                      user={user}
                      hasPermission={PermissionEnum.ADMIN.value}
                    >
                      <button
                        style={{ marginRight: "10px" }}
                        className="btn btn-outline-primary btn-rounded"
                        onClick={(e) => this.handleEditInvoice(e)}
                      >
                        Edit
                      </button>
                    </Permission>
                  </div>
                </div>
              </div>
              <div className="col-xl-4 col-lg-4 col-md- 8 col-sm-12">
                <div className="card">
                  <div className="card-header">
                    <div className="card-title">Ship Information</div>
                  </div>
                  <div className="card-body">
                    <div className="row gutters">
                      <div className="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                        <div className="form-group">
                          <label htmlFor="lastName">Fullname</label>
                          <input
                            type="text"
                            className="form-control"
                            placeholder="Fullname"
                            value={newShipInfo.fullName}
                            disabled
                          />
                        </div>
                      </div>
                      <div className="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                        <div className="form-group">
                          <label htmlFor="lastName">Address</label>
                          <input
                            type="text"
                            className="form-control"
                            placeholder="Address"
                            value={newShipInfo.address}
                            disabled
                          />
                        </div>
                      </div>
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                        <div className="form-group">
                          <label htmlFor="lastName">Phone</label>
                          <input
                            type="text"
                            className="form-control"
                            placeholder="Phone number"
                            value={newShipInfo.phone}
                            disabled
                          />
                        </div>
                      </div>
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                        <div className="form-group">
                          <label htmlFor="lastName">Ship date</label>
                          <input
                            type="text"
                            className="form-control"
                            placeholder="Ship date"
                            value={newShipInfo.shipDate}
                            disabled
                          />
                        </div>
                      </div>
                    </div>
                    <Permission
                      user={user}
                      hasPermission={PermissionEnum.ADMIN.value}
                    >
                      <button
                        //style={{ marginRight: "10px" }}
                        className="btn btn-outline-primary btn-rounded"
                        onClick={(e) => this.handleEditShipInfor(e)}
                      >
                        Edit
                      </button>
                    </Permission>
                    {/* <button className="btn btn-outline-primary btn-rounded">
                      Edit
                    </button> */}
                  </div>
                </div>
              </div>
              <div className="col-xl-12 col-lg-12 col-md-8 col-sm-12">
                <div className="card">
                  <div className="card-header">
                    <div className="card-title">List products</div>
                  </div>
                  <div className="card-body">
                    <button
                      style={{ marginBottom: "10px" }}
                      className="btn btn-outline-success btn-rounded"
                      onClick={(e) => this.toggleModalBook(e)}
                    >
                      Insert
                    </button>
                    <div className="row gutters">
                      <div className="table-responsive">
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
                              accessor: (d) => d.bookID,
                              Cell: ({ row }) => (
                                <div>{row._original.bookID}</div>
                              ),
                              maxWidth: 150,
                            },
                            {
                              Header: "Name",
                              id: "bookTitle",
                              accessor: (d) => d.bookTitle,
                              Cell: ({ row }) => (
                                <div>{row._original.bookTitle}</div>
                              ),
                              maxWidth: 400,
                            },
                            {
                              Header: "Quantiy",
                              id: "quantity",
                              accessor: (d) => d.quantity,
                              Cell: ({ row }) => (
                                <div>{row._original.quantity}</div>
                              ),
                              maxWidth: 150,
                            },
                            {
                              Header: "Total",
                              id: "total",
                              accessor: (d) => d.total,
                              Cell: ({ row }) => (
                                <div>{row._original.total}</div>
                              ),
                              maxWidth: 150,
                            },
                            {
                              Header: "Action",
                              id: "productID",
                              style: { textAlign: "center" },
                              accessor: (d) => d.productID,
                              Cell: ({ row }) => (
                                <Permission
                                  user={user}
                                  hasPermission={PermissionEnum.ADMIN.value}
                                >
                                  <div>
                                    <button
                                      style={{ marginRight: "10px" }}
                                      className="btn btn-outline-primary btn-rounded"
                                      onClick={(e) =>
                                        this.toggleModalBook(
                                          e,
                                          listBook[row._index]
                                        )
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
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="w-100"></div>
            </div>
          </div>
        </div>
        <NotificationContainer />
      </div>
    );
  }
}

export default InvoiceDetail;
