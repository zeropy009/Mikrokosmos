import React, { Component } from "react";
import TabHeader from "../tab-component/TabHeader";
import {
  Button,
  Row,
  Modal,
  ModalHeader,
  ModalFooter,
  ModalBody,
  Input,
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
import Image from "../image-component/Image";
import moment from "moment";
import ModalChangePassword  from './ModalChangePassword'

class InvoiceDetail extends Component {
  constructor(props) {
    super(props);
    this.state = {
      typeModal: "New",
      modal: false,
      newStaff: {
        userName: "",
      },
      listDepart: [],
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
      newStaff: {
        ...this.state.newStaff,
        [name]: value,
      },
    });
  };

  fetchStaffDetail = (staffID) => {
    axios({
      method: "GET",
      url: path_variable.Staff.GET_STAFF_DETAILS + staffID,
      data: null,
    })
      .then((res) => {
        this.setState({
          newStaff: res.data,
        });
      })
      .catch((err) => {
        NotificationManager.error("Error message", "Show staff detail failed");
      });
  };

  handleSubmitImage = (e) => {
    e.preventDefault();
    var self = this;
    var { username } = this.state.newStaff;
    this.refs.attachment.uploadAllFileSameTime(username, function () {
      self.fetchBookDetail(username);
    });
    window.location.reload();
  };

  fetchListDepart = () => {
    axios({
      method: "GET",
      url: path_variable.Depart.GET_LIST_DEPART,
      data: null,
    })
      .then((res) => {
        this.setState({
          listDepart: res.data,
        });
      })
      .catch((err) => {
        NotificationManager.error("Error message", "Show list Depart failed");
      });
  };

  openChangePasswordForm = e=>{
    const param = this.props.match.match.params.staffid;
    this.refs.modalChangePassword.toggle(e,param);
  }

  handleSubmit = (e) => {
    axios({
      method: "PUT",
      url: path_variable.Staff.UPDATE_STAFF+this.props.userName,
      data: this.state.newStaff,
    })
      .then((res) => {
        if(res.data){
          NotificationManager.success("Success message", "Update Staff Detail successfully");
        }else{
          NotificationManager.error("Error message", "Update Staff Detail failed");
        }
      })
      .catch((err) => {
        NotificationManager.error("Error message", "Update Staff Detail failed");
      });
  }

  componentWillMount() {
    const param = this.props.match.match.params.staffid;
    this.fetchStaffDetail(param);
    this.fetchListDepart();
  }

  render() {
    var { modal, typeModal, newStaff, listDepart } = this.state;
    var { user } = this.props;
    const param = this.props.match.match.params.staffid;
    return (
      <div> 
        <ModalChangePassword 
          ref="modalChangePassword"
        />
        <Modal isOpen={modal}>
          <ModalHeader>Upload Image</ModalHeader>
          <FormWithConstraints
            ref={(formWithConstraints) => (this.form = formWithConstraints)}
            onSubmit={(e) => this.handleSubmitImage(e)}
          >
            <ModalBody>
              <Image ref="attachment" refID={newStaff.username} />
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
        <TabHeader user={user} id={5} />
        <div className="main-container">
          <PageHeader menu={"Sale"} subMenu={param} />
          <div className="content-wrapper">
            <Link
              to={`${prefix2}/staff/show/`}
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
                        <img
                          src={`/download/${newStaff.username}`}
                          alt="Staff"
                        />
                      </div>
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
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                        <div className="form-group">
                          <label htmlFor="lastName">Fullname</label>
                          <input
                            type="text"
                            value={newStaff.staffName}
                            name="staffName"
                            className="form-control"
                            placeholder="Enter fullname"
                            onChange={(e) => this.onHandleChange(e)}
                          />
                        </div>
                      </div>
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                        <div className="form-group">
                          <label htmlFor="education">Birthday</label>
                          <Input
                            type="date"
                            value={moment(newStaff.birthDay).format(
                              "YYYY-MM-DD"
                            )}
                            name="birthDay"
                            className="form-group"
                            id="datepicker"
                            onChange={(e) => {
                              this.onHandleChange(e);
                            }}
                          />
                        </div>
                      </div>
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                        <div className="form-group">
                          <label htmlFor="education">Phone</label>
                          <input
                            type="text"
                            value={newStaff.phone}
                            name="phone"
                            className="form-control"
                            placeholder="Phone number"
                            onChange={(e) => this.onHandleChange(e)}
                          />
                        </div>
                      </div>
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                        <div className="form-group">
                          <label htmlFor="addreSs">Email</label>
                          <input
                            type="email"
                            value={newStaff.email}
                            name="email"
                            className="form-control"
                            placeholder="Email"
                            onChange={(e) => this.onHandleChange(e)}
                          />
                        </div>
                      </div>

                      <div className="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                        <div className="form-group">
                          <label htmlFor="addreSs">Address</label>
                          <input
                            type="text"
                            value={newStaff.address}
                            name="address"
                            className="form-control"
                            id="addreSs"
                            placeholder="Current Address"
                            onChange={(e) => this.onHandleChange(e)}
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
                          <label htmlFor="userName">Username</label>
                          <input
                            type="text"
                            value={newStaff.username}
                            name="username"
                            className="form-control"
                            id="userName"
                            placeholder="Enter username"
                            onChange={(e) => this.onHandleChange(e)}
                            disabled
                          />
                        </div>
                      </div>
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                        <div className="form-group">
                          <label htmlFor="inputEmail">Depart</label>
                          <select className="form-control" value={newStaff.departID} name="departID">
                            {
                              !!listDepart ?
                                listDepart.map((depart, index) => {
                                  return (
                                    <option key={index} value={depart.departID}>
                                      {depart.departName}
                                    </option>
                                  )
                                }) : ''
                            }
                          </select>
                        </div>
                      </div>
                      <div className="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                        <div className="form-group">
                          <label htmlFor="inputWeb">Position</label>
                          <select
                            className="form-control"
                            value={newStaff.role}
                            name="role"
                          >
                            <option value={false}>Manager</option>
                            <option value={true}>Employee</option>
                          </select>
                        </div>
                      </div>
                      <div className="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                        <div className="form-group">
                          <label htmlFor="password">Action</label><br />
                          <button 
                          className="btn btn-outline-success btn-rounded"
                            onClick={e=>this.openChangePasswordForm(e)}
                          >
                            Change Password
                          </button>
                          <button className="btn btn-outline-primary btn-rounded"
                            style={{ marginLeft: 10 }}
                            onClick={e => this.handleSubmit(e)}
                          >
                            Save Details
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div className="w-100"></div>
              <NotificationContainer   />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default InvoiceDetail;
