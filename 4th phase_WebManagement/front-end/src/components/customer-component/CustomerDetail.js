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
import ModalEdit from "./ModalEdit";

class CustomerDetail extends Component {
  constructor(props) {
    super(props);
    this.state = {
      listPoint: [],
      typeModal: "New",
      newCustomer: {
        customerName: "",
        address: "",
        customerPoint: 0,
        phone: "",
        email: "",
        password: "",
      },
      modal: false,
    };
  }

  openFileUpload = (e) => {
    this.setState({
      modal: !this.state.modal,
    });
  };

  onHandleChange = (e) => {
    var target = e.target;
    var name = target.name;
    var value = target.value;
    this.setState({
      newCustomer: {
        ...this.state.newCustomer,
        [name]: value,
      },
    });
  };

  fetchCustomerDetail = (customerID) => {
    axios({
      method: "GET",
      url: path_variable.Customer.GET_CUSTOMER_DETAILS + customerID,
      data: null,
    })
      .then((res) => {
        this.setState({
          newCustomer: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Get Customer failed");
      });
  };

  fetchListPoint = (customerID) => {
    axios({
      method: "GET",
      url: path_variable.Customer.GET_HISTORY_POINT_CUSTOMER + customerID,
      data: null,
    })
      .then((res) => {
        this.setState({
          listPoint: res.data,
        });
      })
      .catch((err) => {
        console.log(err);
        NotificationManager.error("Error message", "Get list point failed");
      });
  };

  handleEditCustomer = (e) => {
    this.refs.modalEdit.toggle(e, this.state.newCustomer);
  };

  handleSubmitImage = (e) => {
    e.preventDefault();
    var self = this;
    const param = this.props.match.match.params.customerid;
    this.refs.attachment.uploadAllFileSameTime(param, function () {
      self.fetchCustomerDetail(param);
    });
    this.props.match.history.go(0);
  };

  componentDidMount() {
    const param = this.props.match.match.params.customerid;
    this.fetchCustomerDetail(param);
    this.fetchListPoint(param);
  }

  render() {
    var { modal, typeModal, listPoint, newCustomer } = this.state;
    const param = this.props.match.match.params.customerid;
    var { user, userName } = this.props;
    return (
      <div>
        <ModalEdit
          ref="modalEdit"
          userName={userName}
          param={param}
          fetchCustomerDetail={this.fetchCustomerDetail}
          fetchListPoint={this.fetchListPoint}
        />
        <TabHeader id={4} user={user} />
        <Modal isOpen={modal}>
          <ModalHeader>Upload Image</ModalHeader>
          <FormWithConstraints
            // ref={(formWithConstraints) => (this.form = formWithConstraints)}
            onSubmit={(e) => this.handleSubmitImage(e)}
          >
            <ModalBody>
              <Image ref="attachment" refID={param} />
            </ModalBody>
            <ModalFooter>
              <Button type="submit" color="primary">
                {typeModal === "New" ? "Add" : "Save changes"}
              </Button>{" "}
              <Button color="secondary" onClick={this.openFileUpload}>
                Cancel
              </Button>
            </ModalFooter>
          </FormWithConstraints>
        </Modal>
        <div className="main-container">
          <PageHeader menu={"Sale"} subMenu={param} />
          <div className="content-wrapper">
            <Link
              to={`${prefix2}/customer/show/`}
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
                    <div className="card-title">Profile image</div>
                  </div>
                  <div className="card-body">
                    <div className="student-profile">
                      <div className="student-thumb">
                        <img src={`/download/${param}`} alt="Staff" />
                      </div>
                      {/* <ImageCustomer ref="imageCustomer" imagePath={imagePath} /> */}
                      <div className="input-group mb-3">
                        <div className="custom-file">
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
              </div>
              <div className="col-xl-5 col-lg-5 col-md-8 col-sm-12">
                <div className="card">
                  <div className="card-header">
                    <div className="card-title">Personal Details</div>
                  </div>
                  <div className="card-body">
                    <div className="row gutters">
                      <div className="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                        <div className="form-group">
                          <label htmlFor="firstName">Fullname</label>
                          <input
                            type="text"
                            className="form-control"
                            placeholder="Customer Name"
                            name="customerName"
                            value={newCustomer.customerName}
                            onChange={(e) => this.onHandleChange(e)}
                            disabled
                          />
                        </div>
                      </div>
                      <div className="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                        <div className="form-group">
                          <label htmlFor="firstName">Address</label>
                          <input
                            type="text"
                            className="form-control"
                            placeholder="Current Address"
                            name="address"
                            value={newCustomer.address}
                            onChange={(e) => this.onHandleChange(e)}
                            disabled
                          />
                        </div>
                      </div>
                      <div className="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                        <div className="mb-1"></div>
                      </div>
                      <div className="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                        <div className="form-group">
                          <label htmlFor="firstName">Point</label>
                          <input
                            type="number"
                            className="form-control"
                            placeholder="Current Point"
                            name="customerPoint"
                            value={newCustomer.customerPoint}
                            onChange={(e) => this.onHandleChange(e)}
                            disabled
                          />
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="col-xl-4 col-lg-4 col-md-4 col-sm-12">
                <div className="card">
                  <div className="card-header">
                    <div className="card-title">Account</div>
                  </div>
                  <div className="card-body">
                    <div className="row gutters">
                      <div className="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                        <div className="form-group">
                          <label htmlFor="phone">Phone</label>
                          <input
                            type="text"
                            className="form-control"
                            id="phone"
                            placeholder="Phone number"
                            name="phone"
                            value={newCustomer.phone}
                            onChange={(e) => this.onHandleChange(e)}
                            disabled
                          />
                        </div>
                      </div>
                      <div className="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                        <div className="form-group">
                          <label htmlFor="userName">Email</label>
                          <input
                            type="text"
                            className="form-control"
                            id="userName"
                            placeholder="@gmail.com"
                            name="email"
                            value={newCustomer.email}
                            onChange={(e) => this.onHandleChange(e)}
                            disabled
                          />
                        </div>
                      </div>
                      <div className="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                        <div className="form-group">
                          <label htmlFor="password">Action</label>
                          <br />
                          {/* <input
                            type="password"
                            className="form-control"
                            id="password"
                            placeholder="Enter password"
                            name="password"
                            value={newCustomer.password}
                            onChange={(e) => this.onHandleChange(e)}
                            
                          /> */}
                          <Permission
                            user={user}
                            hasPermission={PermissionEnum.ADMIN.value}
                          >
                            <button
                              style={{
                                marginRight: "10px",
                              }}
                              className="btn btn-outline-primary btn-rounded"
                              onClick={(e) => this.handleEditCustomer(e)}
                            >
                              Edit
                            </button>
                          </Permission>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="col-xl-12 col-lg-12 col-md-8 col-sm-12">
                <div className="card">
                  <div className="card-header">
                    <div className="card-title">History Points</div>
                  </div>
                  <div className="card-body">
                    <div className="row gutters">
                      <div className="table-responsive">
                        <ReactTable
                          data={listPoint}
                          showPageSizeOptions={true}
                          id="table"
                          showPagination={true}
                          columns={[
                            {
                              Header: "#",
                              id: "index",
                              accessor: (d) => d.customerID,
                              Cell: ({ row }) => <div>{row._index + 1}</div>,
                              maxWidth: 50,
                            },
                            {
                              Header: "Date",
                              id: "date",
                              accessor: (d) => d.date,
                              Cell: ({ row }) => (
                                <div>{row._original.date}</div>
                              ),
                              maxWidth: 480,
                            },
                            {
                              Header: "Point",
                              id: "point",
                              accessor: (d) => d.point,
                              Cell: ({ row }) => (
                                <div>{row._original.point}</div>
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

export default CustomerDetail;
