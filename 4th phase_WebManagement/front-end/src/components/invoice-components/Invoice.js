import React, { Component } from "react";
import TabHeader from "../tab-component/TabHeader";
import { Button, Row } from "reactstrap";
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
import Permission from "../../Permission";
import { PermissionEnum } from "../../PermissionEnum";

export default class Invoice extends Component {
  constructor(props) {
    super(props);
    this.state = {
      listInvoice: [],
      userName: "",
    };
  }

  fetchListInvoice = () => {
    axios({
      method: "GET",
      url: path_variable.Invoice.GET_LIST_INVOICE,
      data: null,
    })
      .then((res) => {
        this.setState({
          listInvoice: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Show list Invoice failed");
      });
  };

  handleDelete = (e, row) => {
    this.refs.modalDelete.toggleInvoice(e, row.invoiceID);
  };

  componentWillMount() {
    this.fetchListInvoice();
  }

  render() {
    var { listInvoice } = this.state;
    var { user, userName } = this.props;
    return (
      <div>
        <TabHeader id={4} user={user} />
        <ModalDelete
          ref="modalDelete"
          userName={userName}
          type={`Invoice`}
          fetchListInvoice={this.fetchListInvoice}
        />
        <div className="main-container">
          <PageHeader menu={"Sale"} subMenu={"Customer"} />
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
                        data={listInvoice}
                        showPageSizeOptions={true}
                        id="table"
                        showPagination={true}
                        columns={[
                          {
                            Header: "#",
                            id: "index",
                            accessor: (d) => d.invoiceID,
                            Cell: ({ row }) => <div>{row._index + 1}</div>,
                            maxWidth: 50,
                          },
                          {
                            Header: "ID",
                            id: "invoiceID",
                            accessor: (d) => d.invoiceID,
                            Cell: ({ row }) => (
                              <div>{row._original.invoiceID}</div>
                            ),
                            maxWidth: 100,
                          },
                          {
                            Header: "Customer",
                            id: "customerName",
                            accessor: (d) => d.customerName,
                            Cell: ({ row }) => (
                              <div>{row._original.customerName}</div>
                            ),
                            maxWidth: 460,
                          },
                          {
                            Header: "Staff",
                            id: "staffName",
                            accessor: (d) => d.staffName,
                            Cell: ({ row }) => (
                              <div>{row._original.staffName}</div>
                            ),
                          },
                          {
                            Header: "SOLD DATE",
                            id: "soldDate",
                            accessor: (d) => d.soldDate,
                            Cell: ({ row }) => (
                              <div>{row._original.soldDate}</div>
                            ),
                            maxWidth: 100,
                          },
                          {
                            Header: "Action",
                            id: "bookID",
                            style: { textAlign: "center" },
                            accessor: (d) => d.invoiceID,
                            Cell: ({ row }) => (
                              <div>
                                <Link
                                  to={
                                    `${prefix2}/invoice/show/` +
                                    row._original.invoiceID
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
                                        listInvoice[row._index]
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
