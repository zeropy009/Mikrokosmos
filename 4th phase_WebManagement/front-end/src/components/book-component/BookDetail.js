import axios from "axios";
import React, { Component } from "react";
import {
  FormWithConstraints
} from "react-form-with-constraints";
import {
  NotificationContainer,
  NotificationManager
} from "react-notifications";
import "react-notifications/lib/notifications.css";
import NumberFormat from "react-number-format";
import { Link } from "react-router-dom";
import ReactTable from "react-table";
import "react-table/react-table";
import "react-table/react-table.css";
import {
  Button,
  Modal,
  ModalBody, ModalFooter, ModalHeader
} from "reactstrap";
import "../../App.css";
import { path_variable, prefix2 } from "../../path_variable";
import Permission from "../../Permission";
import { PermissionEnum } from "../../PermissionEnum";
import Image from "../image-component/Image";
import PageHeader from "../PageHeader";
import TabHeader from "../tab-component/TabHeader";
import ModalDelete from "./ModalDelete";
import ModalForm from "./ModalForm";

class BookDetail extends Component {
  constructor(props) {
    super(props);
    this.state = {
      listAuthor: [],
      listCategory: [],
      newBook: {
        bookID: "",
        bookTitle: "",
        authorID: "",
        categoryID: "",
        description: "",
        amount: 0,
        price: 0,
      },
      listPrice: [],
      typeModal: "New",
      modal: false,
      userName: "",
    };
  }


  handleSubmit = (e) => {
    e.preventDefault();
    if (this.form.isValid()) {
      this.updateCustomer(this.state.newCustomer);
    }
  };

  openFileUpload = (e) => {
    this.setState({
      modal: !this.state.modal,
    });
  };

  fetchBookDetail = (bookID) => {
    axios({
      method: "GET",
      url: path_variable.Book.GET_BOOK_DETAIL + bookID,
      data: null,
    })
      .then((res) => {
        this.setState({
          newBook: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Show list Author failed");
      });
  };

  handleSubmitImage = (e) => {
    e.preventDefault();
    var self = this;
    const param = this.props.match.match.params.bookid;
    this.refs.attachment.uploadAllFileSameTime(param, function () {
      self.fetchBookDetail(param);
    });
    window.location.reload();
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

  fetchListHistoryPrice = (bookID) => {
    axios({
      method: "GET",
      url: path_variable.Book.GET_HISTORY_PRICE_BOOK + bookID,
      data: null,
    })
      .then((res) => {
        this.setState({
          listPrice: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Show list Author failed");
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

  handleEditBook = (e) => {
    this.refs.modalForm.toggle(e, this.state.newBook);
  };

  handleSubmit = (e) => {
    e.preventDefault();
    const param = this.props.match.match.params.bookid;
    this.refs.attachment.uploadAllFileSameTime(param, function () {
      // InvoiceStore.getTotalPayablePaymentOfInvoice(invoiceId);
      //self.fetchListCreditNote();
    });
  };

  componentWillMount() {
    const param = this.props.match.match.params.bookid;
    this.fetchBookDetail(param);
    this.fetchListAuthor();
    this.fetchListCategory();
    this.fetchListHistoryPrice(param);
  }

  render() {
    var {
      modal,
      typeModal,
      newBook,
      listAuthor,
      listCategory,
      listPrice,
    } = this.state;
    var { user, userName } = this.props;
    const param = this.props.match.match.params.bookid;
    return (
      <div>
        <ModalForm
          ref="modalForm"
          userName={userName}
          param={param}
          fetchBookDetail={this.fetchBookDetail}
          fetchListPrice={this.fetchListHistoryPrice}
          listAuthor={listAuthor}
          listCategory={listCategory}
        />
        <TabHeader user={user} id={2} />
        <Modal isOpen={modal}>
          <ModalHeader>Upload Image</ModalHeader>
          <FormWithConstraints onSubmit={(e) => this.handleSubmitImage(e)}>
            <ModalBody>
              <Image ref="attachment" refID={param} />
            </ModalBody>
            <ModalFooter>
              <Button
                type="submit"
                color="primary"
                // onClick={(e) => {
                //   if (!!this.form) {
                //     this.form.validateFields();
                //     // eslint-disable-next-line no-undef
                //     customValidateFields(this.form);
                //   }
                // }}
              >
                {typeModal === "New" ? "Add" : "Save changes"}
              </Button>{" "}
              <Button color="secondary" onClick={this.openFileUpload}>
                Cancel
              </Button>
            </ModalFooter>
          </FormWithConstraints>
        </Modal>
        <ModalDelete
          ref="modalDelete"
          // userName={userName}
          // fetchListBook={this.fetchListBook}
        />
        <div className="main-container">
          <PageHeader menu={"Product"} subMenu={param} />
          <div className="content-wrapper">
            <Link
              to={`${prefix2}/book/show/`}
              style={{
                margin: "0px 0 10px 10px",
              }}
              className="btn btn-outline-secondary btn-rounded"
            >
              Go back
            </Link>
            <h3 className="text-center">{param}</h3>
            <div className="row gutters">
              <div className="col-xl-3 col-lg-3 col-md-12 col-sm-12">
                <div className="card">
                  <div className="card-header">
                    <div className="card-title">Book cover</div>
                  </div>
                  <div className="card-body">
                    <div className="student-profile">
                      <div className="student-thumb">
                        <img src={`/download/${param}`} alt="Staff" />
                      </div>
                      <div className="input-group mb-3">
                        <Button
                          color="link"
                          onClick={(e) => this.openFileUpload(e)}
                          style={{ margin: "0 auto" }}
                        >
                          Change Picture
                        </Button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="col-xl-9 col-lg-9 col-md-8 col-sm-12">
                <div className="card">
                  <div className="card-header">
                    <div className="card-title">Book Innformation</div>
                  </div>
                  <div className="card-body">
                    <div className="row gutters">
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                        <div className="form-group">
                          <label htmlFor="firstName">Title</label>
                          <input
                            type="text"
                            className="form-control"
                            value={newBook.bookTitle}
                            disabled
                          />
                        </div>
                      </div>
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                        <div className="form-group">
                          <label htmlFor="inputWeb">Author</label>
                          <select
                            className="form-control"
                            name="authorID"
                            id="authorID"
                            value={newBook.authorID}
                            onChange={(e) => this.onHandleChange(e)}
                            required
                            disabled={true}
                          >
                            {listAuthor.map((data) => {
                              return (
                                <option
                                  key={data.authorID}
                                  value={data.authorID}
                                >
                                  {data.authorName}
                                </option>
                              );
                            })}
                          </select>
                        </div>
                      </div>
                      <div className="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-12">
                        <div className="form-group">
                          <label htmlFor="inputEmail">Catergory</label>
                          <select
                            className="form-control"
                            name="authorID"
                            id="authorID"
                            value={newBook.categoryID}
                            onChange={(e) => this.onHandleChange(e)}
                            required
                            disabled
                          >
                            {listCategory.map((data) => {
                              return (
                                <option
                                  key={data.categoryID}
                                  value={data.categoryID}
                                >
                                  {data.categoryName}
                                </option>
                              );
                            })}
                          </select>
                        </div>
                      </div>
                      <div className="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-12">
                        <div className="form-group">
                          <label htmlFor="lastName">Amount</label>
                          <NumberFormat
                            name="amount"
                            id="amount"
                            className="form-control"
                            value={!!newBook.amount ? newBook.amount : 0}
                            thousandSeparator={true}
                            decimalScale={2}
                            disabled
                          />
                        </div>
                      </div>
                      <div className="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-12">
                        <div className="form-group">
                          <label htmlFor="lastName">Price</label>
                          <NumberFormat
                            name="price"
                            id="price"
                            className="form-control"
                            value={!!newBook.price ? newBook.price : ""}
                            thousandSeparator={true}
                            decimalScale={2}
                            disabled
                          />
                        </div>
                      </div>
                      <div className="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                        <div className="form-group">
                          <label htmlFor="education">Description</label>
                          <textarea
                            type="text"
                            className="form-control"
                            placeholder="Description"
                            value={newBook.description}
                            disabled
                          ></textarea>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="col-xl-12 col-lg-12 col-md-8 col-sm-12">
                <Permission
                  user={user}
                  hasPermission={PermissionEnum.ADMIN.value}
                >
                  <button
                    style={{
                      float: "right",
                      margin: "0px 10px 10px 0px",
                    }}
                    className="btn btn-outline-primary btn-rounded"
                    onClick={(e) => this.handleEditBook(e)}
                  >
                    Edit
                  </button>
                </Permission>
              </div>
              <div className="col-xl-12 col-lg-12 col-md-8 col-sm-12">
                <div className="card">
                  <div className="card-header">
                    <div className="card-title">History Price</div>
                  </div>
                  <div className="card-body">
                    <div className="row gutters">
                      <div className="table-responsive">
                        <ReactTable
                          data={listPrice}
                          showPageSizeOptions={true}
                          id="table"
                          showPagination={true}
                          columns={[
                            {
                              Header: "#",
                              id: "index",
                              accessor: (d) => d.historyPriceID,
                              Cell: ({ row }) => <div>{row._index + 1}</div>,
                              maxWidth: 100,
                            },
                            {
                              Header: "Price",
                              id: "price",
                              accessor: (d) => d.price,
                              Cell: ({ row }) => (
                                <div>{row._original.price}</div>
                              ),
                              maxWidth: 500,
                            },
                            {
                              Header: "Start Date",
                              id: "startdate",
                              accessor: (d) => d.startDate,
                              Cell: ({ row }) => (
                                <div>{row._original.startDate}</div>
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

export default BookDetail;
