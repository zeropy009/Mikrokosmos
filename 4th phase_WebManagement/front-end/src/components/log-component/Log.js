import axios from "axios";
import React, { Component } from "react";
import "react-datepicker/dist/react-datepicker.css";
import {
  NotificationContainer,
  NotificationManager
} from "react-notifications";
import "react-notifications/lib/notifications.css";
import ReactTable from "react-table";
import "react-table/react-table";
import "react-table/react-table.css";
import {
  Button
} from "reactstrap";
import "../../App.css";
import { path_variable } from "../../path_variable";
import Permission from "../../Permission";
import { PermissionEnum } from "../../PermissionEnum";
import PageHeader from "../PageHeader";
import TabHeader from "../tab-component/TabHeader";
import ModalDelete from "./ModalDelete";

export default class Log extends Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      listLog: [],
      newLog: {
        logID: "",
        date: "",
        logName: "",
        note: "",
        referenceID: "",
      },
      typeModal: "New",
      userName: "",
    };
  }

  toggle = (e, row) => {
    if (row === undefined) {
      this.setState({
        newLog: {},
        typeModal: "New",
      });
    } else {
      this.setState({
        newLog: {
          ...row,
        },
        typeModal: "Edit",
      });
    }
    this.setState({
      modal: !this.state.modal,
    });
  };

  onTodoChangeDate = (value, name) => {
    if (value) {
      this.setState({
        newLog: {
          ...this.state.newLog,
          [name]: !value ? "" : value.customFormatYMD("MM/dd/yyyy"),
        },
      });
    }
  };

  fetchListLog = () => {
    axios({
      method: "GET",
      url: path_variable.Log.GET_LIST_LOG,
      data: null,
    })
      .then((res) => {
        this.setState({
          listLog: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Show list Log failed");
      });
  };

  onHandleChange = (e) => {
    var target = e.target;
    var name = target.name;
    var value = target.value;
    this.setState({
      newLog: {
        ...this.state.newLog,
        [name]: value,
      },
    });
  };

  handleSubmit = (e) => {
    e.preventDefault();
    console.log(this.state.newLog);
    this.insertOrUpdateLog(this.state.newLog);
  };

  handleDelete = (e, row) => {
    this.refs.modalDelete.toggle(e, row.logID);
  };

  componentWillMount() {
    this.fetchListLog();
    this.setState({
      userName: this.props.userName,
    });
  }

  render() {
    let validateissueDate = Date.now();
    var { modal, listLog, newLog, typeModal, userName } = this.state;
    var { user } = this.props;
    return (
      <div>
        <TabHeader id={7} user={user} />
        <ModalDelete
          ref="modalDelete"
          userName={userName}
          fetchListLog={this.fetchListLog}
        />
        <div className="main-container">
          <PageHeader menu={"Convention"} subMenu={"Log"} />
          <div className="content-wrapper">
            <div className="row gutters">
              <div className="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                <div className="card">
                  <div className="card-body">
                    <div className="table-responsive">
                      <ReactTable
                        data={listLog}
                        showPageSizeOptions={true}
                        id="table"
                        showPagination={true}
                        columns={[
                          {
                            Header: "ID",
                            id: "LogID",
                            accessor: (d) => d.logID,
                            Cell: ({ row }) => <div>{row._original.logID}</div>,
                            maxWidth: 50,
                          },
                          {
                            Header: "Log Name",
                            id: "LogName",
                            accessor: (d) => d.logName,
                            Cell: ({ row }) => (
                              <div>{row._original.logName}</div>
                            ),
                          },
                          {
                            Header: "Note",
                            id: "Note",
                            accessor: (d) => d.note,
                            Cell: ({ row }) => <div>{row._original.note}</div>,
                          },
                          {
                            Header: "Date",
                            id: "Date",
                            accessor: (d) => d.date,
                            Cell: ({ row }) => <div>{row._original.date}</div>,
                          },
                          {
                            Header: "User",
                            id: "User",
                            accessor: (d) => d.referenceID,
                            Cell: ({ row }) => (
                              <div>{row._original.referenceID}</div>
                            ),
                          },
                          {
                            Header: "Action",
                            id: "LogID",
                            style: { textAlign: "center" },
                            accessor: (d) => d.LogID,
                            Cell: ({ row }) => (
                              <Permission
                                user={user}
                                hasPermission={PermissionEnum.ADMIN.value}
                              >
                                <Button //disabled={isCheckEditing}//disabled={!isPDClerk || isPoClosed || editingByAnother}
                                  color="btn btn-outline-secondary btn-rounded"
                                  onClick={(e) =>
                                    this.handleDelete(e, listLog[row._index])
                                  }
                                >
                                  Delete
                                </Button>
                              </Permission>
                            ),
                          },
                        ]}
                        defaultPageSize={50}
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
