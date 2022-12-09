import axios from "axios";
import React, { Component } from "react";
import {
  NotificationContainer,
  NotificationManager
} from "react-notifications";
import "react-notifications/lib/notifications.css";
import { Link } from "react-router-dom";
import ReactTable from "react-table";
import "react-table/react-table";
import "react-table/react-table.css";
import "../../App.css";
import { path_variable, prefix2 } from "../../path_variable";
import { PermissionEnum } from "../../PermissionEnum";
import PageHeader from "../PageHeader";
import TabHeader from "../tab-component/TabHeader";
import Permission from "./../../Permission";
import ModalDelete from "./ModalDelete";
import ModalForm from "./ModalForm";

class Book extends Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      listAuthor: [],
      listCategory: [],
      listBook: [],
      newBook: {
        bookID: "",
        bookTitle: "",
        amount: "",
        price: "",
      },
      typeModal: "New",
      userName: "",
    };
  }

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

  fetchListBook = () => {
    axios({
      method: "GET",
      url: path_variable.Book.GET_LIST_BOOK,
      data: null,
    })
      .then((res) => {
        this.setState({
          listBook: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Show list Book failed");
      });
  };

  handleAddBook = (e) => {
    this.refs.modalForm.toggle(e);
  };

  handleDelete = (e,row)=>{
    this.refs.modalDelete.toggle(e,row.bookID);
  }

  componentDidMount() {
    this.fetchListBook();
    this.fetchListAuthor();
    this.fetchListCategory();
  }

  render() {
    var { listBook, listAuthor, listCategory } = this.state;
    var { user, userName } = this.props;
    return (
      <div>
        <TabHeader id={2} user={user} />
        <ModalForm
          ref="modalForm"
          userName={userName}
          fetchListBook={this.fetchListBook}
          listAuthor={listAuthor}
          listCategory={listCategory}
        />
        <ModalDelete
          ref="modalDelete"
          userName={userName}
          fetchListBook={this.fetchListBook}
        />
        <div className="main-container">
          <PageHeader menu={"Product"} subMenu={"Book"} />
          <div className="content-wrapper">
            <div className="row gutters">
              <div className="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                <div className="card">
                  <div className="card-body">
                    <button
                      type="button"
                      className="btn btn-outline-success btn-rounded"
                      onClick={(e) => this.handleAddBook(e)}
                    >
                      Insert
                    </button>
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
                            Header: "Title",
                            id: "bookTitle",
                            accessor: (d) => d.bookTitle,
                            Cell: ({ row }) => (
                              <div>{row._original.bookTitle}</div>
                            ),
                            maxWidth: 450,
                          },
                          {
                            Header: "Amount",
                            id: "amount",
                            accessor: (d) => d.amount,
                            Cell: ({ row }) => (
                              <div>{row._original.amount}</div>
                            ),
                            maxWidth: 100,
                          },
                          {
                            Header: "Price",
                            id: "price",
                            accessor: (d) => d.price,
                            Cell: ({ row }) => <div>{row._original.price}</div>,
                            maxWidth: 150,
                          },
                          {
                            Header: "Action",
                            id: "bookID",
                            style: { textAlign: "center" },
                            accessor: (d) => d.bookID,
                            Cell: ({ row }) => (
                              <div>
                                <Link
                                  to={
                                    `${prefix2}/book/show/` +
                                    row._original.bookID
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
                                      this.handleDelete(e, listBook[row._index])
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
                        defaultPageSize={100}
                        style={{}}
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

export default Book;
